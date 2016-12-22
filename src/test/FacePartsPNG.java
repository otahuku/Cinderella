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
import org.opencv.photo.Photo;



public class FacePartsPNG {
	final static String Path= "C:/prj/";
	public static String face_cascade = Path+"/openCV/haarcascades/haarcascade_frontalface_alt2.xml";
    public static String eye_cascade = Path+"/openCV//haarcascades/haarcascade_eye.xml";
    public static String nose_cascade = Path+"/openCV/haarcascades/haarcascade_mcs_nose.xml";
    public static String mouth_cascade = Path+"/openCV/haarcascades/haarcascade_mcs_mouth.xml";



	public static void main(String[] args) {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    String Photo_Path ="frenchman"; //args[0]に書き換えることでパス名に対応します
	    String Project_Path=Path+Photo_Path;
	    String Project_Path_PNG=Path+Photo_Path+".png";

	    face project=new face();

		Mat im= Highgui.imread(Project_Path_PNG);
		Mat IM=new Mat();
		im.copyTo(IM, im);


		project.GetFace(im);
		System.out.println("face "+project.face.x+","+project.face.y);

		project.GetLEye(im);
		System.out.println("L_eye "+project.l_eye.x+","+project.l_eye.y);

		project.GetREye(im);
		System.out.println("R_eye "+project.r_eye.x+","+project.r_eye.y);


		project.GetNose(im);
		System.out.println("Nose "+project.nose.x+","+project.nose.y);

/*		project.GetMouth(im);
		System.out.println("mouth "+project.mouth.x+","+project.mouth.y);

		double RGB[]=project.GetMouth_RGB(IM);
		/*int[] HSV =project.RGBtoHSV((int)RGB[0], (int)RGB[1], (int)RGB[2]);
		System.out.println("H="+HSV[0]);
		System.out.println("S="+HSV[1]);
		System.out.println("V="+HSV[2]);
		*/

		IM=project.GetRedSpace(IM,Project_Path);

		project.Draw(IM);

		// 結果を保存
		Highgui.imwrite(Project_Path+"_mask.png", IM);


		System.out.println("Mask has Done!!");
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

/*		Point mouth;
		int mouth_width;
		int mouth_height;

		Point Mouth_c;

        int[] hsv = new int[3];
*/
		void GetFace(Mat im){
			CascadeClassifier faceDetector = new CascadeClassifier(Path+"/openCV/haarcascades/haarcascade_frontalface_alt.xml");
			MatOfRect faceDetections = new MatOfRect();
			faceDetector.detectMultiScale(im, faceDetections);

	        for (Rect rect : faceDetections.toArray()) {
	        	//Core.rectangle(im, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 0, 255), 5);
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
				if((rect.x>face.x) && (rect.y>face.y) && (rect.x > (face.x + face_width/2) ) && (rect.y < (face.y + face_height/2) ) && (rect.x+rect.width > (face.x + face_width/2)) ){
		    		//Core.rectangle(im, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(255, 255, 255), 5);
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
				if((rect.x>face.x) && (rect.y>face.y) && (rect.x < (face.x + face_width/2) ) && (rect.y < (face.y + face_height/2) ) && (rect.x+rect.width < (face.x + face_width/2)) ){
		    		//Core.rectangle(im, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(255, 180, 255), 5);
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
	    		if(rect.x>face.x+face_width/4 && rect.x<face.x+face_width-face_width/4 && rect.y>face.y+face_height/4 && rect.y<face.y+face_height-face_height/4){
	    		nose=new Point(rect.x,rect.y);
    			nose_width=rect.width;
    			nose_height=rect.height;
    			break;
	    		}
	    	}
	    }


	    void Draw(Mat im){
	    	Core.ellipse(im, new Point(l_eye.x+l_eye_width/2,l_eye.y+l_eye_height/2), new Size(l_eye_width/2,l_eye_height/4), 0, 0, 360, new Scalar(255, 255, 255, 55), -1);
	    	Core.ellipse(im, new Point(r_eye.x+r_eye_width/2,r_eye.y+r_eye_height/2), new Size(r_eye_width/2,r_eye_height/4), 0, 0, 360, new Scalar(255, 255, 255, 55), -1);
	    	Core.rectangle(im, new Point(nose.x, nose.y), new Point(nose.x + nose_width,nose.y + nose_height), new Scalar(255,255,255), -1);
	    }

/*	    void GetMouth(Mat im){

		    CascadeClassifier mouthDetector = new CascadeClassifier(Path+"/openCV/haarcascades/haarcascade_mcs_mouth.xml");
	    	MatOfRect mouthDetections = new MatOfRect();
	    	mouthDetector.detectMultiScale(im, mouthDetections);
	    	for (Rect rect : mouthDetections.toArray()) {
	    		if( (rect.y>=face.y+face_height/2) && (rect.x>=face.x) && (rect.x<=face.x+face_width) && (rect.y>=face.y) && (rect.y<=face.y+face_height)){
	    			//Core.rectangle(im, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 0, 0), 5);
	    			mouth=new Point(rect.x,rect.y);
	    			mouth_width=rect.width;
	    			mouth_height=rect.height;
	    			break;
	    		}
			}
	    }

	    double[] GetMouth_RGB(Mat im){
	    	double[] data = new double[3];
	    	int[] mouth_center = new int[2];
	    	mouth_center[0] = (int)(mouth.x) + (mouth_width/2);
	    	mouth_center[1] = (int)(mouth.y) + (mouth_height/2);
	    	Mouth_c=new Point(mouth_center[0],mouth_center[1]);

	    	data = im.get(mouth_center[0], mouth_center[1]);
	    	System.out.println("Blue：" + data[0]);
	    	System.out.println("Green：" + data[1]);
	    	System.out.println("Red：" + data[2]);

	    	return data;
	    }

	    int[] RGBtoHSV(int blue, int green, int red){

        int max = Math.max(blue, Math.max(green, red));
        int min = Math.min(blue, Math.min(green, red));

        hsv[0]=0;
		if(red>green && red>blue){
			hsv[0]=60*((green-blue)%(max-min))%360;
		}
		if(green>red && green>blue){
			hsv[0]=60*((blue-green)%(max-min))%360;
		}
		if(blue>red && blue>green){
			hsv[0]=60*((red-green)%(max-min))%360;
		}
		hsv[1] = (max-min)%max;
		hsv[2] = max;

        // h
        if(max==0&& min==0)	hsv[0] = 0;
        if(max == blue)	hsv[0] = 60 * (4+((green - red) / (max - min))) ;
        else if(max == green)	hsv[0] = 60 * (2+((red - blue) / (max - min)));
	    else if(max == red)	hsv[0] =  (60 * (blue - green) / (max - min));
        if(hsv[0]<0) hsv[0]=hsv[0]+360;
        // s
        if(max == 0)	hsv[1] = 0;
        else	hsv[1] = (int) (255 * ((max - min) / max));
        // v
        hsv[2] = (int)max;

        return hsv;
    }
*/

	    Mat GetRedSpace(Mat im,String P_Path){
	    	Mat hsv = new Mat();
	    	Mat mask = new Mat();
	    	Mat im2 = new Mat();
	    	Mat im3 = new Mat();

	    	Photo.fastNlMeansDenoising(im, im);

	    	Imgproc.cvtColor(im, hsv, Imgproc.COLOR_BGR2HSV);						// HSV色空間に変換

	    	Core.inRange(hsv, new Scalar(1.5, 0, 0), new Scalar(4, 256, 256), mask);	// 赤色領域のマスク作成
	    	im.copyTo(im2, mask);																	// マスクを 用いて入力画像から緑色領域を抽出					// HSV色空間に変換
	    	Core.inRange(hsv, new Scalar(172, 0, 0), new Scalar(178, 256, 256), mask);	// 赤色領域のマスク作成
	    	im.copyTo(im2, mask);																	// マスクを 用いて入力画像から緑色領域を抽出
	    	Imgproc.medianBlur(im2, im2, 3);
	    	Highgui.imwrite(P_Path+"_tmp.png", im2);												// 画像の出力

	    	im3=Highgui.imread(P_Path+"_tmp.png");

	    	int width = im3.cols();
	    	int height = im3.rows();
	    	double[] data = new double[3];
	    	double[] White_data=new double[3];
	    	double[] Black_data=new double[3];
	    	for(int i = 0 ; i < 3 ; i++ ){
	    		White_data[i]=255;
	    		Black_data[i]=0;
	    	}
	    	for(int x=0; x<width-1; ++x) {
	    		for(int y=0; y<height-1; ++y) {
	    			if(x>=face.x && x<=face.x+face_width && y>=face.y &&y<=face.y+face_height){
	    				data=im3.get(y, x);
	    				if(data[0] > 10 || data[1] > 10 || data[2] > 10){
	    					im3.put(y, x, White_data);
	    				}
	    			}else{
	    				im3.put(y, x, Black_data);
	    			}
	    		}
	    	}
	    	return im3;
	    }
	}



}
