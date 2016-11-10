package test;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;

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
		Mat mask = Highgui.imread("C:/prj/mansk2.png",-1);//パーツ部分の白だけ残したマスク
		Mat dst = new Mat();
		Mat dst2 = new Mat();

		//パーツ部分だけ切り出し
		Core.bitwise_and(im, mask, dst);
		Highgui.imwrite(path_out+"_parts.png", dst);

		//適当な肌色を用意して顔に埋める
		Mat hada = new Mat(im.rows(), im.cols(), im.type(), new Scalar(181,228,255,255));
		Core.bitwise_and(hada, mask2, hada);
		Core.bitwise_not(mask2, mask2);
		Core.bitwise_and(im, mask2, im);
		Core.bitwise_or(im, hada, dst2);

		Highgui.imwrite(path_out+"_new.png", dst2);

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
	    Highgui.imwrite("C:/prj/mansk2.png",matt);
		return matt;
	}


}