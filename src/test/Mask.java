package test;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class Mask {
	final static String Path= "C:/prj/";
	public static String face_cascade = Path+"/OpenCV/haarcascades/haarcascade_frontalface_alt2.xml";

    //public static String Photo_Path ="man.png";//今でしょもどき
    //public static String path_out = "C:/prj/man";

    //public static String Photo_Path ="tkkrkn.png";//高倉健さん
    //public static String path_out = "C:/prj/tkkrkn";

    public static String Photo_Path ="tmpp.png";//ともぴっぴ
    public static String path_out = "C:/prj/tmpp";

    public static String Project_Path=Path+Photo_Path;


	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		Mat im= Highgui.imread(Project_Path,-1);//読み込み

		//あとで重ねあわせる用
		Mat im_cl= Highgui.imread(Project_Path);

		//Mat mask_cl = Highgui.imread("C:/prj/manmask.png");//今でしょもどき
		//Mat mask_cl = Highgui.imread("C:/prj/tkkrmask.png");//高倉健さん
		Mat mask_cl = Highgui.imread("C:/prj/tmppmask.png");//ともぴっぴ
		Core.bitwise_not(mask_cl, mask_cl);

        //マスクの用意
		//Mat mask2 = Highgui.imread("C:/prj/manmask.png",-1);//今でしょもどき
		//Mat mask2 = Highgui.imread("C:/prj/tkkrmask.png",-1);//高倉健さん
		Mat mask2 = Highgui.imread("C:/prj/tmppmask.png",-1);//ともぴっぴ
		tohka(mask2);//黒部分を透過
		Mat mask = Highgui.imread("C:/prj/maska.png",-1);//パーツ部分の白マスク

		Mat dst = new Mat();
		Mat dst2 = new Mat();

		face face = new face();
		face.GetFace(im);
		//int[] size = {(int)face.face.x,(int)face.face.y,face.face_width,face.face_height};
		//int size[] = {370,100,283,369};//高倉健さん
		int size[] = {460,430,490,470};//ともぴっぴ

		//パーツ部分だけ切り出し
		Core.bitwise_and(im, mask, dst);
		Highgui.imwrite(path_out+"_parts.png", dst);

		//顔を埋める
		Mat hada = blur(im);
		Core.bitwise_and(hada, mask2, hada);
		Core.bitwise_not(mask2, mask2);
		Core.bitwise_and(im, mask2, im);
		Core.bitwise_or(im, hada, dst2);

		//ノイズ除去
		Highgui.imwrite(path_out+"_null.png", dst2);
		Mat img= Highgui.imread(path_out+"_null.png");//読み込み
		Mat noise = new Mat();
		Imgproc.bilateralFilter(img, noise, 75,90,210);
		//Imgproc.pyrMeanShiftFiltering(noise, noise, 0.1,0.1);


		//顔部分にだけ適用(顔が認識されない場合エラー)
		Mat res = adapt(img,noise,size);

		//res = gammaFilter(res,0.9);
		Highgui.imwrite(path_out+"_null.png", res);


		res.copyTo(im_cl,mask_cl);
		Mat fres = new Mat();
		Imgproc.bilateralFilter(im_cl, fres, 7,65,20);
		fres = adapt(im_cl,fres,size);
		Highgui.imwrite(path_out+"_new.png", fres);



		System.out.println("Done!!");
	}

	//白以外透過させるメソッド
	static Mat tohka(Mat mat){
		Mat reslut = mat;
	    for (int y = 0; y < reslut.cols(); y++) {
	        for (int x = 0; x < reslut.rows(); x++) {
	            double px[] = reslut.get(x, y);
	            if (px[0] + px[1] + px[2] < 765) {
	            	double [] data = {px[0],px[1],px[2],-1};
	            	reslut.put(x,y,data);
	            }
	        }
	    }
	    Highgui.imwrite("C:/prj/maska.png",reslut);
		return reslut;
	}


	//何かしら処理したものを顔部分にだけ適用させるメソッド
	public static Mat adapt(Mat img, Mat noise, int size[]){
		Mat trmask = new Mat(img.rows(), img.cols(), img.type(), new Scalar(0,0,0));
		Mat res = noise.clone();

		Core.rectangle(trmask, new Point(size[0], size[1]),
			new Point(size[0]+size[2],size[1]+size[3]), new Scalar(255,255,255), -1);//高倉健さんの顔位置

		Core.bitwise_and(res, trmask, res);
		Core.bitwise_not(trmask, trmask);
		Core.bitwise_and(img, trmask, img);
		Core.bitwise_or(img, res, res);

		return res;
	}
	//ぼかし
	static Mat blur(Mat image){
		Mat im = new Mat();
		Mat im2 = new Mat();
		Imgproc.GaussianBlur(image, im, new Size(51,1),50,3);
		Imgproc.medianBlur(im, im2, 51);
		return im2;
	}

	//ガンマ補正(使うかも)
    public static Mat gammaFilter(Mat inputPicture, double gamma) {
    	Mat result = new Mat();
        inputPicture.copyTo(result);
        Mat lut = new Mat(1, 256, CvType.CV_8UC1);
        lut.setTo(new Scalar(0));
        for (int i = 0; i < 256; i++)
        {
        	lut.put(0, i, Math.pow((double)(1.0 * i/255), 1/gamma) * 255);
        }
        Core.LUT(inputPicture, lut, result);
        return result;
    }


    static class face{
		Point face;
		int face_width;
		int face_height;

		void GetFace(Mat im){
			CascadeClassifier faceDetector = new CascadeClassifier(Path+"/openCV/haarcascades/haarcascade_frontalface_alt.xml");
			MatOfRect faceDetections = new MatOfRect();
			faceDetector.detectMultiScale(im, faceDetections);

	        for (Rect rect : faceDetections.toArray()) {
	            face=new Point(rect.x,rect.y);
	            face_width=rect.width;
	            face_height=rect.height;
	            break;
	        }

		}

    }
}



//Imgproc.cvtColor(image, hsv, Imgproc.COLOR_BGR2HSV); BGR -> HSV