package test;

/*
Alchemy-ao
{
  "url": "https://gateway-a.watsonplatform.net/calls",
  "note": "This is your previous free key. If you want a different one, please wait 24 hours after unbinding the key and try again.",
  "apikey": "2ca87380e234b5f2dd34005b3add6332f8ec50ca"
}

Visual Recognition-do
{
  "url": "https://gateway-a.watsonplatform.net/visual-recognition/api",
  "note": "It may take up to 5 minutes for this key to become active",
  "api_key": "3df4ef090a3cb813f1a6eeed4d89a7e0bc187aeb"
}
 */

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.json.simple.parser.ParseException;

public class FaceTest {


	public static byte[] getImageByteArray(String imageUrl, String fileNameExtension) {
		File imgFile   = null;
		imgFile = new File(imageUrl);
		  BufferedImage readImage = null;
		try {
			readImage = ImageIO.read(imgFile);
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		  ByteArrayOutputStream outPutStream = new ByteArrayOutputStream();
		  try {
			ImageIO.write(readImage, fileNameExtension, outPutStream);
		} catch (IOException e) {

			e.printStackTrace();
		}
		  return outPutStream.toByteArray();
	}


	public static void doPost(byte[] binary){
		String apikey = "2ca87380e234b5f2dd34005b3add6332f8ec50ca";
		String urlString ="https://gateway-a.watsonplatform.net/calls/image/ImageGetRankedImageFaceTags?imagePostMode=raw&outputMode=json&apikey="+apikey;

		//実験用のURL
		//String urlString = "http://access.alchemyapi.com/calls/url/URLGetRankedImageFaceTags?url=http://cdn.amanaimages.com/cen3tzG4fTr7Gtw1PoeRer/01809017023.jpg&apikey=2ca87380e234b5f2dd34005b3add6332f8ec50ca&outputMode=json";
		//String urlString = "file:///C:/prj/bluemix/test.html";
		//String urlString ="https://gateway-a.watsonplatform.net/calls/image/ImageGetRankedImageFaceTags?apikey=2ca87380e234b5f2dd34005b3add6332f8ec50ca&outputMode=json&imagePostMode=raw";
		//String urlString = "http://access.alchemyapi.com/calls/url/URLGetRankedImageFaceTags?url=http://cdn.amanaimages.com/cen3tzG4fTr7Gtw1PoeRer/01809017023.jpg&apikey="+apikey+"&outputMode=json";

		try{
			URL url = new URL(urlString);
			HttpURLConnection uc = (HttpURLConnection)url.openConnection();

			uc.setDoOutput(true);//POST可能に
			uc.setRequestMethod("POST");
			DataOutputStream writer = new DataOutputStream(uc.getOutputStream());
            writer.write(binary,0,binary.length);
            writer.flush();


        	InputStream is = uc.getInputStream();//結果を取得
        	BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        	String s;
        	String age = "";
        	String text = "";
        	int j = 0;
        	while ((s = reader.readLine()) != null) {
        		System.out.println(s);//一応全文表示させてます。あとで消してネ。
        		text = text + s;
        	}

    		String texts[]=text.split("");
    		for(int i=0;i<texts.length-12;i++){
    			if((texts[i]+texts[i+1]+texts[i+2]+texts[i+3]+texts[i+4]+texts[i+5]
    					+texts[i+6]+texts[i+7]+texts[i+8]+texts[i+9]+texts[i+10]+texts[i+11]).equals("\"ageRange\": ")){
    				while(!(texts[i+13+j].equals("\""))){
    					age = age + texts[i+13+j];
    					j++;
    				}

    			}
    		}
    		System.out.println(age);

        	reader.close();
        } catch (MalformedURLException e) {
        	System.err.println("Invalid URL format: " + urlString);
        	System.exit(-1);
    	} catch (IOException e) {
        	System.err.println("Can't connect to " + urlString);
        	System.exit(-1);
    	}
	}



	public static void main(String[] args) throws ParseException{
		byte[] binary = getImageByteArray("C:/project/m.png", "png");
		doPost(binary);
		System.out.println("\n"+"Done!!");
	}

}

