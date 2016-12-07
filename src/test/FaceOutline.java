package test;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class FaceOutline {
	public static void main(String args[]){
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat im = Highgui.imread("C:/project/tkkrkn.png");			// 入力画像の取得
		Mat gray = new Mat();           
		Imgproc.cvtColor(im, gray, Imgproc.COLOR_RGB2GRAY); 	// グレースケール変換
		Imgproc.Canny(gray, gray, 400, 500, 5, true);       	// Cannyアルゴリズムで輪郭検出
		Highgui.imwrite("C:/project/test.png", gray);			// エッジ画像の出力
	}
}