package test;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class FaceOutline {
	public static void main(String args[]){
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat im = Highgui.imread("C:/project/tkkrkn.png");			// ���͉摜�̎擾
		Mat gray = new Mat();           
		Imgproc.cvtColor(im, gray, Imgproc.COLOR_RGB2GRAY); 	// �O���[�X�P�[���ϊ�
		Imgproc.Canny(gray, gray, 400, 500, 5, true);       	// Canny�A���S���Y���ŗ֊s���o
		Highgui.imwrite("C:/project/test.png", gray);			// �G�b�W�摜�̏o��
	}
}