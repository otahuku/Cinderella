package test;
import java.io.File;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class MaskComplete {
	final static String Path= "C:/prj/";
	public static String face_cascade = Path+"/OpenCV/haarcascades/haarcascade_frontalface_alt2.xml";

	public static void main(String args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		final String Photo_Path=args+"_re.jpg";
		final String Mask_Path=args+"_re_mask.jpg";
		final String path_out = args+"_re";

		Mat im= Highgui.imread(Photo_Path);//読み込み

        //マスクの用意
		Mat mask2 = Highgui.imread(Mask_Path);

		//あとで重ねあわせる用
		Mat im_cl= Highgui.imread(Photo_Path);
		Mat mask_cl = Highgui.imread(Mask_Path);
		Core.bitwise_not(mask_cl, mask_cl);

		Mat dst2 = new Mat();

		face face = new face();
		face.GetFace(im);
		int[] size = {(int)face.face.x,(int)face.face.y,face.face_width,face.face_height};
		//int size[] = {120,300,380,380};//謎の西洋人re

		//顔を埋める
		Mat hada = blur(im);
		Core.bitwise_and(hada, mask2, hada);
		Core.bitwise_not(mask2, mask2);
		Core.bitwise_and(im, mask2, im);
		Core.bitwise_or(im, hada, dst2);

		//ノイズ除去
		Mat noise = new Mat();
		Imgproc.bilateralFilter(dst2, noise, 37,45,105);



		//顔部分にだけ適用(顔が認識されない場合エラー)
		Mat res = adapt(dst2,noise,size);

		Highgui.imwrite(path_out+"_null.jpg", res);

		res.copyTo(im_cl,mask_cl);
		Mat fres = new Mat();
		Imgproc.bilateralFilter(im_cl, fres, 7,65,20);
		fres = adapt(im_cl,fres,size);
		Highgui.imwrite(path_out+"_new.jpg", fres);

		File nul = new File(path_out+"_null.jpg");
		nul.delete();
		File re = new File(path_out+".jpg");
		re.delete();
		File tmp = new File(path_out+"_tmp.jpg");
		tmp.delete();
		File mask = new File(path_out+"_mask.jpg");
		mask.delete();


		System.out.println("NewFace has Done!!");

	}

	//何かしら処理したものを顔部分にだけ適用させるメソッド
	public static Mat adapt(Mat img, Mat noise, int size[]){
		Mat trmask = new Mat(img.rows(), img.cols(), img.type(), new Scalar(0,0,0));
		Mat res = noise.clone();

		Core.rectangle(trmask, new Point(size[0], size[1]),
			new Point(size[0]+size[2],size[1]+size[3]), new Scalar(255,255,255), -1);

		Core.bitwise_and(res, trmask, res);
		Core.bitwise_not(trmask, trmask);
		Core.bitwise_and(img, trmask, img);
		Core.bitwise_or(img, res, res);

		return res;
	}

	//ぼかし
	static Mat blur(Mat image){
		Mat im = new Mat();
		Mat im2 = new Mat();
		Imgproc.GaussianBlur(image, im, new Size(51,1),50,3);
		Imgproc.medianBlur(im, im2, 51);
		return im2;
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