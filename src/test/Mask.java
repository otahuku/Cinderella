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

    public static String Photo_Path ="tkkrkn.png";//高倉健さん
    public static String path_out = "C:/prj/tkkrkn";

    public static String Project_Path=Path+Photo_Path;


	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		Mat im= Highgui.imread(Project_Path,-1);//読み込み

		face face = new face();
		face.GetFace(im);


        //マスクの用意
		//Mat mask2 = Highgui.imread("C:/prj/manmask.png",-1);//パーツ部分が白、他が黒のマスク
		Mat mask2 = Highgui.imread("C:/prj/maskn.png",-1);//高倉健さん用
		tohka(mask2);//黒部分を透過させる
		Mat mask = Highgui.imread("C:/prj/mansk.png",-1);//パーツ部分の白だけ残したマスク

		Mat dst = new Mat();
		Mat dst2 = new Mat();

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
		Highgui.imwrite(path_out+"_new.png", dst2);
		Mat img= Highgui.imread(path_out+"_new.png");//読み込み
		Mat res = new Mat();
		Imgproc.bilateralFilter(img, res, 75,80,150);

		//顔部分にだけ適用(顔が認識されない場合エラー)
		/*
		if(){
			Mat trmask = new Mat(img.rows(), img.cols(), img.type(), new Scalar(0,0,0));
			Core.rectangle(trmask, new Point(face.face.x, face.face.y),
				new Point(face.face.x + face.face_width, face.face.y + face.face_height), new Scalar(255,255,255), -1);
			Core.bitwise_and(res, trmask, res);
			Core.bitwise_not(trmask, trmask);
			Core.bitwise_and(img, trmask, img);
			Core.bitwise_or(img, res, res);
		}
		*/

		Highgui.imwrite(path_out+"_new.png", res);


		System.out.println("Done!!");
	}

	//白以外透過させるメソッド
	static Mat tohka(Mat mat){
		Mat matt = mat;
	    for (int y = 0; y < matt.cols(); y++) {
	        for (int x = 0; x < matt.rows(); x++) {
	            double px[] = matt.get(x, y);
	            if (px[0] + px[1] + px[2] < 765) {
	            	double [] data = {px[0],px[1],px[2],-1};
	            	matt.put(x,y,data);
	            }
	        }
	    }
	    Highgui.imwrite("C:/prj/mansk.png",matt);
		return matt;
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