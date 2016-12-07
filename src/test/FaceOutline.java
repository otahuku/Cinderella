package test;

import java.util.ArrayList;  
import java.util.List;  
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class FaceOutline {
	public static void main(String args[]){
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		Mat im = Highgui.imread("C:/project/man.png");			// ���͉摜�̎擾
		
		Mat gray = new Mat();           
		Imgproc.cvtColor(im, gray, Imgproc.COLOR_RGB2GRAY); 	// �O���[�X�P�[���ϊ�
		Highgui.imwrite("C:/project/test0.png", gray);
		
		Mat bin = new Mat();  
		Imgproc.threshold(gray, bin, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);	// 2�l��
		Highgui.imwrite("C:/project/test1.png", bin);
		
        Mat hierarchy=Mat.zeros(new Size(5,5), CvType.CV_8UC1);  
       
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();  
        //��ԊO���݂̂�OK  
        Imgproc.findContours(bin, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_TC89_L1);  
        Mat dst=Mat.zeros(new Size(bin.width(),bin.height()),CvType.CV_8UC3);  
        Scalar color=new Scalar(255,255,255);  
          
        Imgproc.drawContours(dst, contours, -1, color,1);  
          
        int i=0;  
        for(i=0;i<contours.size();i++)  
        {  
            MatOfPoint ptmat= contours.get(i);  
  
            //���_�`��  
            /* 
             int k=0; 
             for(k=0;k<ptmat.height();k++) 
            { 
                double[] m=ptmat.get(k, 0); 
                vertex.x=m[0]; 
                vertex.y=m[1]; 
                Core.circle(dst, vertex, 2, color,-1); 
            }*/  
              
            color=new Scalar(255,0,0);  
            MatOfPoint2f ptmat2 = new MatOfPoint2f( ptmat.toArray() );  
            RotatedRect bbox=Imgproc.minAreaRect(ptmat2);  
            Rect box=bbox.boundingRect();  
            Core.circle(dst, bbox.center, 5, color,-1);  
            color=new Scalar(0,255,0);  
            Core.rectangle(dst,box.tl(),box.br(),color,2);  
              
        }  
        
		Highgui.imwrite("C:/project/test2.png", dst);
	}
}