package test;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class Resize {
    public static void main(String args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		final String Photo_Path=args+".jpg";
		final String path_out = args;

		Mat im= Highgui.imread(Photo_Path,-1);//読み込み

		while(im.size().width > 640){
		Imgproc.resize(im, im,
				  new Size(im.size().width*0.95, im.size().height*0.95));
		}

		Highgui.imwrite(path_out+"_re.jpg", im);
    }

}
