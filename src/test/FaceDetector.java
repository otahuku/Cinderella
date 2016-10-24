package test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;

public class FaceDetector {
    public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		// 入力画像の取得
        Mat im = Highgui.imread("C:/project/sample.jpg");
		// カスケード分類器で顔探索
		CascadeClassifier faceDetector = new CascadeClassifier("C:/project/openCV/haarcascades/haarcascade_frontalface_alt.xml");
		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(im, faceDetections);
		// 見つかった顔を矩形で囲む
        for (Rect rect : faceDetections.toArray()) {
            Core.rectangle(im, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 0, 255), 5);
            System.out.print(rect.x+","+rect.y+","+rect.width+","+rect.height);
        }
		// 結果を保存
        Highgui.imwrite("C:/project/ouput.jpg", im);
        System.out.println("Done!");
    }
}