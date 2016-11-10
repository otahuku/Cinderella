package test;

import org.opencv.core.Core;
import org.opencv.core.Mat;
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

		Mat im= Highgui.imread(Project_Path,-1);


        //bit演算を用いてます
		Mat mask = Highgui.imread("C:/prj/mansk.png",-1);
		Mat dst = new Mat();

		Highgui.imwrite(path_out+"_p.png", mask);

		Core.bitwise_and(im, mask, dst);

		Highgui.imwrite(path_out+"_parts.png", dst);

		System.out.println("Done!!");
	}

}