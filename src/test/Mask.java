package test;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class Mask {
	final static String Path= "C:/prj/";
	public static String face_cascade = Path+"/OpenCV/haarcascades/haarcascade_frontalface_alt2.xml";
    public static String eye_cascade = Path+"/OpenCV//haarcascades/haarcascade_eye.xml";
    public static String nose_cascade = Path+"/OpenCV/haarcascades/haarcascade_mcs_nose.xml";
    public static String mouth_cascade = Path+"/OpenCV/haarcascades/haarcascade_mcs_mouth.xml";

    public static String Photo_Path ="man.png";
    public static String path_out = "C:/prj/man";
    public static String Project_Path=Path+Photo_Path;

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		Mat im= Highgui.imread(Project_Path,-1);//読み込み

        //マスクの用意
		Mat mask2 = Highgui.imread("C:/prj/manmask.png",-1);//パーツ部分が白、他が黒のマスク
		tohka(mask2);//黒部分を透過させる
		Mat mask = Highgui.imread("C:/prj/mansk.png",-1);//パーツ部分の白だけ残したマスク

		Mat dst = new Mat();
		Mat dst2 = new Mat();

		//パーツ部分だけ切り出し
		Core.bitwise_and(im, mask, dst);
		Highgui.imwrite(path_out+"_parts.png", dst);

		//顔を埋める
		//Mat hada = new Mat(im.rows(), im.cols(), im.type(), new Scalar(181,213,255,255));
		Mat hada = blur(im);

		Highgui.imwrite(path_out+"_blur.png",hada);
		Core.bitwise_and(hada, mask2, hada);
		Highgui.imwrite(path_out+"_blur2.png",hada);

		Core.bitwise_not(mask2, mask2);
		Core.bitwise_and(im, mask2, im);
		Core.bitwise_or(im, hada, dst2);

		//ノイズ除去
		Highgui.imwrite(path_out+"_new.png", dst2);
		Mat img= Highgui.imread(path_out+"_new.png");//読み込み
		Mat res = new Mat();
		Imgproc.bilateralFilter(img, res, 70,85,150);
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

}

//Imgproc.cvtColor(image, hsv, Imgproc.COLOR_BGR2HSV); BGR -> HSV