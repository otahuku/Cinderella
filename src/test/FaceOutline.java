package test;

import java.util.ArrayList;  
import java.util.List;  
import org.opencv.core.Core;
import org.opencv.core.Scalar;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class FaceOutline {
	public static void main(String args[]){
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		Mat im = Highgui.imread("C:/project/man.png");			// 入力画像の取得
		
		Mat gray = new Mat();           
		Imgproc.cvtColor(im, gray, Imgproc.COLOR_RGB2GRAY); 	// グレースケール変換
		Highgui.imwrite("C:/project/test0.png", gray);
		
		Mat bin = new Mat();  
		Imgproc.adaptiveThreshold(gray, bin, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, 7, 8);	// 2値化
		Highgui.imwrite("C:/project/test1.png", bin);
		 
	    //輪郭の座標リスト
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>(); 
	 
	    //輪郭取得
	    ////cv::findContours(binImage, contours, CV_RETR_EXTERNAL, CV_CHAIN_APPROX_NONE);
		//Mat hierarchy=Mat.zeros(new Size(5,5), CvType.CV_8UC1);
		Mat hierarchy = new Mat();
	    Imgproc.findContours(bin, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_NONE);
	    

	    for(int i=0; i<10; i++){
	    	System.out.println(contours.get(i));
	    }
	    Imgproc.drawContours(bin,  contours,  2,  new Scalar(255,0,0), -1);
	    //Core.polylines(bin, contours, true, new Scalar(0,255,0), 5);
		Highgui.imwrite("C:/project/test2.png", bin);
	}
}