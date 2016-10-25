package test;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;


public class Trimming {
	static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        String path_in = "C:/prj/Lenna.bmp";
        String path_out = "C:/prj/Lenna";

        //各パーツの開始座標と大きさ
        int[] eyer = {123,128,25,10};
        int[] eyel = {158,130,20,10};
        int[] nose = {144,127,15,30};
        int[] mouse = {133,172,28,11};

        //画像の取得
        Mat im = Highgui.imread(path_in);

        //各パーツのトリミング
        Rect roi_eyer = new Rect(eyer[0], eyer[1], eyer[2], eyer[3]);
        Mat im_eyer = new Mat(im, roi_eyer);
        Highgui.imwrite(path_out + "_eyer.bmp", im_eyer);

        Rect roi_eyel = new Rect(eyel[0], eyel[1], eyel[2], eyel[3]);
        Mat im_eyel = new Mat(im, roi_eyel);
        Highgui.imwrite(path_out + "_eyel.bmp", im_eyel);

        Rect roi_nose = new Rect(nose[0], nose[1], nose[2], nose[3]);
        Mat im_nose = new Mat(im, roi_nose);
        Highgui.imwrite(path_out + "_nose.bmp", im_nose);

        Rect roi_mouse = new Rect(mouse[0], mouse[1], mouse[2], mouse[3]);
        Mat im_mouse = new Mat(im, roi_mouse);
        Highgui.imwrite(path_out + "_mouse.bmp", im_mouse);

        //各パーツの近くの色を取得、各roiの四角形を描画
		double[] data_eyer = new double[3];
		double[] data_eyel = new double[3];
		double[] data_nose = new double[3];
		double[] data_mouse = new double[3];

		data_eyer = im.get(eyer[0], eyer[1]-10);
		Core.rectangle(im, new Point(eyer[0], eyer[1]), new Point(eyer[0]+eyer[2],eyer[1]+eyer[3]), new Scalar(data_eyer[0],data_eyer[1],data_eyer[2]), -1);

		data_eyel = im.get(eyel[0], eyel[1]-10);
		Core.rectangle(im, new Point(eyel[0], eyel[1]), new Point(eyel[0]+eyel[2],eyel[1]+eyel[3]), new Scalar(data_eyel[0],data_eyel[1],data_eyel[2]), -1);

		data_nose = im.get(nose[0], nose[1]);
		Core.rectangle(im, new Point(nose[0], nose[1]), new Point(nose[0]+nose[2],nose[1]+nose[3]), new Scalar(data_nose[0],data_nose[1],data_nose[2]), -1);

		data_mouse = im.get(mouse[0], mouse[1]-10);
		Core.rectangle(im, new Point(mouse[0], mouse[1]), new Point(mouse[0]+mouse[2],mouse[1]+mouse[3]), new Scalar(data_mouse[0],data_mouse[1],data_mouse[2]), -1);


		//ぼかし
        Mat im3 = new Mat();
		Imgproc.blur(im, im3, new Size(5, 5));
		Highgui.imwrite(path_out+"_new.bmp", im3);


    }
}