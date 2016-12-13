package test;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class getMouth {
	public static void main(String args[]){
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat im = Highgui.imread("C:/project/test.jpg");											// 入力画像の取得
		Mat hsv = new Mat();
		Mat mask = new Mat();
		Mat im2 = new Mat();
		Imgproc.cvtColor(im, hsv, Imgproc.COLOR_BGR2HSV);						// HSV色空間に変換

		Core.inRange(hsv, new Scalar(150, 0, 0), new Scalar(256, 256, 256), mask);	// 赤色領域のマスク作成
		im.copyTo(im2, mask);																	// マスクを 用いて入力画像から緑色領域を抽出
		Highgui.imwrite("C:/project/test2.jpg", im2);												// 画像の出力



	}
}

