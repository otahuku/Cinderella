package test;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class getMouth {
	public static void main(String args[]){
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat im = Highgui.imread("C:/project/sample.jpg");											// 入力画像の取得
		Mat hsv = new Mat();
		Mat mask = new Mat();
		Mat im2 = new Mat();
		Imgproc.cvtColor(im, hsv, Imgproc.COLOR_BGR2HSV);						// HSV色空間に変換

		Core.inRange(hsv, new Scalar(172, 66, 0), new Scalar(178, 256, 256), mask);	// 赤色領域のマスク作成
		im.copyTo(im2, mask);																	// マスクを 用いて入力画像から緑色領域を抽出
		Highgui.imwrite("C:/project/test3.jpg", im2);												// 画像の出力




	}
}

/*
		//C++における画素値の取得
		Mat src; //load image here
		Mat HSV;
		Mat RGB=image(Rect(x,y,1,1)); // use your x and y value
		cvtColor(RGB, HSV,CV_BGR2HSV);
		Vec3b hsv=HSV.at<Vec3b>(0,0);
		int H=hsv.val[0]; //hue
		int S=hsv.val[1]; //saturation
		int V=hsv.val[2]; //value

		//画素値の取得
		double[] data = hsv.get(315,236);
		double data1=data[0];
		double data2= data[1];
		System.out.println(data1+","+data2);

		//顔判定（雑）
		Core.inRange(hsv,new Scalar(0,0,0),new Scalar(10,256,256),mask);
		im.copyTo(im3,mask);
		Highgui.imwrite("C:/project/test4.jpg", im3);



 */
