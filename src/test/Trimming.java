package test;

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

public class Trimming {
	final static String Path= "C:/prj/";
	public static String face_cascade = Path+"/OpenCV/haarcascades/haarcascade_frontalface_alt2.xml";
    public static String eye_cascade = Path+"/OpenCV//haarcascades/haarcascade_eye.xml";
    public static String nose_cascade = Path+"/OpenCV/haarcascades/haarcascade_mcs_nose.xml";
    public static String mouth_cascade = Path+"/OpenCV/haarcascades/haarcascade_mcs_mouth.xml";


	public static void main(String[] args) {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    String Photo_Path ="man.jpg";
        String path_out = "C:/prj/man";
        String Project_Path=Path+Photo_Path;

	    face project=new face();

		Mat im= Highgui.imread(Project_Path);

		project.GetFace(im);
		project.GetLEye(im);
		project.GetREye(im);
		project.GetNose(im);
		project.GetMouth(im);


        //各パーツの開始座標と大きさ
		int[] eyel = {(int)project.l_eye.x,(int)project.l_eye.y,project.l_eye_width,project.l_eye_height};
        int[] eyer = {(int)project.r_eye.x,(int)project.r_eye.y,project.r_eye_width,project.r_eye_height};
        int[] nose = {(int)project.nose.x,(int)project.nose.y,project.nose_width,project.nose_height};
        int[] mouth = {(int)project.mouth.x,(int)project.mouth.y,project.mouth_width,project.mouth_height};

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

        Rect roi_mouth = new Rect(mouth[0], mouth[1], mouth[2], mouth[3]);
        Mat im_mouth = new Mat(im, roi_mouth);
        Highgui.imwrite(path_out + "_mouth.bmp", im_mouth);


        //各パーツの近くの色を取得、各roiの四角形を描画
        //パーツの範囲が広すぎて的確な色の抽出が困難なためコメント化中
/*
		double[] data_eyer = new double[3];
		double[] data_eyel = new double[3];
		double[] data_nose = new double[3];
		double[] data_mouth = new double[3];

		data_eyer = im.get(eyer[0], eyer[1]);
		data_eyel = im.get(eyel[0], eyel[1]);
		data_nose = im.get(nose[0], nose[1]);
		data_mouth = im.get(mouth[0], mouth[1]);

		Core.rectangle(im, new Point(eyer[0], eyer[1]), new Point(eyer[0]+eyer[2],eyer[1]+eyer[3]), new Scalar(data_eyer[0],data_eyer[1],data_eyer[2]), -1);
		Core.rectangle(im, new Point(eyel[0], eyel[1]), new Point(eyel[0]+eyel[2],eyel[1]+eyel[3]), new Scalar(data_eyel[0],data_eyel[1],data_eyel[2]), -1);
		Core.rectangle(im, new Point(nose[0], nose[1]), new Point(nose[0]+nose[2],nose[1]+nose[3]), new Scalar(data_nose[0],data_nose[1],data_nose[2]), -1);
		Core.rectangle(im, new Point(mouth[0], mouth[1]), new Point(mouth[0]+mouth[2],mouth[1]+mouth[3]), new Scalar(data_mouth[0],data_mouth[1],data_mouth[2]), -1);
*/

		//一部ぼかし
        //bit演算を用いてます
		Mat bl = blur(im);
		Mat mask = new Mat(im.rows(), im.cols(), im.type(), new Scalar(0,0,0));

		Core.rectangle(mask, new Point(eyer[0], eyer[1]), new Point(eyer[0]+eyer[2],eyer[1]+eyer[3]), new Scalar(255,255,255), -1);
		Core.rectangle(mask, new Point(eyel[0], eyel[1]), new Point(eyel[0]+eyel[2],eyel[1]+eyel[3]), new Scalar(255,255,255), -1);
		Core.rectangle(mask, new Point(nose[0], nose[1]), new Point(nose[0]+nose[2],nose[1]+nose[3]), new Scalar(255,255,255), -1);
		Core.rectangle(mask, new Point(mouth[0], mouth[1]), new Point(mouth[0]+mouth[2],mouth[1]+mouth[3]), new Scalar(255,255,255), -1);

		Core.bitwise_and(bl, mask, bl);
		Core.bitwise_not(mask, mask);
		Core.bitwise_and(im, mask, im);
		Mat dst = new Mat();
		Core.bitwise_or(im, bl, dst);

		Highgui.imwrite(path_out+"_new.bmp", dst);
		System.out.println("Done!!");
	}

	//ぼかしメソッド(短い)
	static Mat blur(Mat image){
		Mat im = new Mat();
		Imgproc.blur(image, im, new Size(30, 30));
		return im;
	}

	static class face{
		Point face;
		int face_width;
		int face_height;

		Point l_eye;
		int l_eye_width;
		int l_eye_height;

		Point r_eye;
		int r_eye_width;
		int r_eye_height;

		Point nose;
		int nose_width;
		int nose_height;

		Point mouth;
		int mouth_width;
		int mouth_height;

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

	    void GetLEye(Mat im){

		    CascadeClassifier eyeDetector = new CascadeClassifier(Path+"/openCV//haarcascades/haarcascade_eye.xml");
			MatOfRect eyeDetections = new MatOfRect();
			eyeDetector.detectMultiScale(im, eyeDetections);
			for (Rect rect : eyeDetections.toArray()) {
				if((rect.x > (face.x + face_width/2) ) && (rect.y < (face.y + face_height/2) ) && (rect.x+rect.width > (face.x + face_width/2)) ){
					l_eye=new Point(rect.x,rect.y);
		            l_eye_width=rect.width;
		            l_eye_height=rect.height;
		            break;
				}
			}
		}

	    void GetREye(Mat im){
		    CascadeClassifier eyeDetector = new CascadeClassifier(Path+"/openCV//haarcascades/haarcascade_eye.xml");
			MatOfRect eyeDetections = new MatOfRect();
			eyeDetector.detectMultiScale(im, eyeDetections);
			for (Rect rect : eyeDetections.toArray()) {
				if((rect.x < (face.x + face_width/2) ) && (rect.y < (face.y + face_height/2) ) && (rect.x+rect.width < (face.x + face_width/2)) ){
					r_eye=new Point(rect.x,rect.y);
		            r_eye_width=rect.width;
		            r_eye_height=rect.height;
		            break;
				}
			}
		}

	    void GetNose(Mat im){

		    CascadeClassifier noseDetector = new CascadeClassifier(Path+"/openCV/haarcascades/haarcascade_mcs_nose.xml");
	    	MatOfRect noseDetections = new MatOfRect();
	    	noseDetector.detectMultiScale(im, noseDetections);
	    	for (Rect rect : noseDetections.toArray()) {
	    		nose=new Point(rect.x,rect.y);
    			nose_width=rect.width;
    			nose_height=rect.height;
    			break;
	    	}
	    }

	    void GetMouth(Mat im){

		    CascadeClassifier mouthDetector = new CascadeClassifier(Path+"/openCV/haarcascades/haarcascade_mcs_mouth.xml");
	    	MatOfRect mouthDetections = new MatOfRect();
	    	mouthDetector.detectMultiScale(im, mouthDetections);
	    	for (Rect rect : mouthDetections.toArray()) {
	    		if(rect.y > (face.y + face_height*3/4) ){
	    			mouth=new Point(rect.x,rect.y);
	    			mouth_width=rect.width;
	    			mouth_height=rect.height;
	    			break;
	    		}
			}
	    }
	}


}