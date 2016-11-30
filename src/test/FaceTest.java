package test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FaceTest {

	public static byte[] getImageByteArray(String imageUrl, String fileNameExtension) {
		File imgFile   = null;
		imgFile = new File(imageUrl);
		  BufferedImage readImage = null;
		try {
			readImage = ImageIO.read(imgFile);
		} catch (IOException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
		  ByteArrayOutputStream outPutStream = new ByteArrayOutputStream();
		  try {
			ImageIO.write(readImage, fileNameExtension, outPutStream);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		  return outPutStream.toByteArray();
	}

	
	
	public static void main(String[] args){
		byte[] binary = getImageByteArray("./tkkrkn.png", "png");
	}
		
}

