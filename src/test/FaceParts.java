package test;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;



public class FaceParts {
	final static String Path= "C:/project/";
	public static String face_cascade = Path+"/openCV/haarcascades/haarcascade_frontalface_alt2.xml";
    public static String eye_cascade = Path+"/openCV//haarcascades/haarcascade_eye.xml";
    public static String nose_cascade = Path+"/openCV/haarcascades/haarcascade_mcs_nose.xml";
    public static String mouth_cascade = Path+"/openCV/haarcascades/haarcascade_mcs_mouth.xml";



	public static void main(String[] args) {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    String Photo_Path ="sample.jpg";
	    String Project_Path=Path+Photo_Path;

	    face project=new face();

		Mat im= Highgui.imread(Project_Path);

		project.GetFace(im);
		System.out.println("face "+project.face.x+","+project.face.y);

		project.GetLEye(im);
		System.out.println("L_eye "+project.l_eye.x+","+project.l_eye.y);

		project.GetREye(im);
		System.out.println("R_eye "+project.r_eye.x+","+project.r_eye.y);

		project.GetNose(im);
		System.out.println("Nose "+project.nose.x+","+project.nose.y);

		project.GetMouth(im);
		System.out.println("mouth "+project.mouth.x+","+project.mouth.y);

		double RGB[]=project.GetMouth_RGB(im);
		int[] HSV =project.RGBtoHSV((int)RGB[0], (int)RGB[1], (int)RGB[2]);
		System.out.println("H="+HSV[0]);
		System.out.println("S="+HSV[1]);
		System.out.println("V="+HSV[2]);
		// 結果を保存
		Highgui.imwrite(Path+"/output_parts2.jpg", im);


		System.out.println("Done!!");
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

		Point Mouth_c;

		void GetFace(Mat im){
			CascadeClassifier faceDetector = new CascadeClassifier(Path+"/openCV/haarcascades/haarcascade_frontalface_alt.xml");
			MatOfRect faceDetections = new MatOfRect();
			faceDetector.detectMultiScale(im, faceDetections);

	        for (Rect rect : faceDetections.toArray()) {
	        	Core.rectangle(im, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 0, 255), 5);
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
		    		Core.rectangle(im, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(255, 255, 255), 5);
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
		    		Core.rectangle(im, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(255, 180, 255), 5);
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
	    		Core.rectangle(im, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0,255,255), 5);
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
	    			Core.rectangle(im, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 0, 0), 5);
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
	    	System.out.println(mouth_center[0]+","+mouth_center[1]);
	    	System.out.println("Blue：" + data[0]);
	    	System.out.println("Green：" + data[1]);
	    	System.out.println("Red：" + data[2]);
	    	Core.rectangle(im, Mouth_c, Mouth_c, new Scalar(0, 0, 255), 1);

	    	return data;
	    }

	    int[] RGBtoHSV(int red, int green, int blue){

        int[] hsv = new int[3];
        float max = Math.max(red, Math.max(green, blue));
        float  min = Math.min(red, Math.min(green, blue));

        // h
        if(max == min)	hsv[0] = 0;
        else if(max == red)	hsv[0] = (int) ((60 * (green - blue) / (max - min) + 360) % 360);
        else if(max == green)	hsv[0] = (int) ((60 * (blue - red) / (max - min)) + 120);
	    else if(max == blue)	hsv[0] = (int) ((60 * (red - green) / (max - min)) + 240);
        // s
        if(max == 0)	hsv[1] = 0;
        else	hsv[1] = (int) (255 * ((max - min) / max));
        // v
        hsv[2] = (int)max;

        return hsv;
    }


	    void GetRedSpace(Mat im){

	    }
	}


}
