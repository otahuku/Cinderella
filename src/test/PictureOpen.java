package test;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.AreaAveragingScaleFilter;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.simple.parser.ParseException;

public class PictureOpen extends JFrame {
	JLabel label,donelabel;
    JPanel panel;
	private JTextField textField;
	Image img,area;
	FilteredImageSource fis;
	static int labelX,labelY;
	File f;
	String FilePath;
	public static String agerange_b ="";
	public static String agerange_a ="";


	public static void main(String[] args) throws ParseException{
		labelX = 240;
		labelY = 320;
		new PictureOpen();
	}

	public PictureOpen() throws ParseException{
		init();
	}

	private void init() throws ParseException{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Cinderella");
		setBounds(100, 100, 660, 475);

		JLabel lblNewLabel = new JLabel("Image");
		lblNewLabel.setBounds(21, 22, 220, 29);
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(null);
		panel.add(lblNewLabel);

		JButton button = new JButton("OPEN");
		button.setBounds(70, 19, 104, 33);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// ファイル選択ダイアログを表示し、選択したファイルをラベルに設定するメソッドを呼び出す
				open();
			}
		});
		panel.add(button);

		JButton btnImport = new JButton("import");
		btnImport.setBounds(373, 19, 147, 33);
		btnImport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//加齢・減齢した画像を保存するメソッドを呼び出す
				importimage();

			}
		});
		panel.add(btnImport);

		textField = new JTextField();
		textField.setBounds(174, 20, 177, 31);
		panel.add(textField);
		textField.setColumns(10);

		JLabel balabel = new JLabel("⇒");
		balabel.setBounds(275, 175, 100, 50);
		balabel.setFont(new Font("serif", Font.PLAIN, 90));
		panel.add(balabel);



		JButton btnDo = new JButton("DO");
		btnDo.setBounds(290, 230, 56,25 );
		btnDo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Resize.main(FilePath);
				FacePartsJPG.main(FilePath);
				MaskComplete.main(FilePath);
				DoOpen();
			}
		});
		panel.add(btnDo);
		
		
		JLabel BfAgeRange = new JLabel();
		BfAgeRange.setBounds(180, 389, 62, 23);

		JLabel AfAgeRange = new JLabel();
		AfAgeRange.setBounds(527, 389, 62, 23);

		JButton btnBf = new JButton("Measure");
		btnBf.setBounds(28, 388, 84,25 );
		btnBf.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//BlueMixで年齢を測定するFaceTestを実行
					try {
						agerange_b = FaceTestBefore.main(FilePath);
						BfAgeRange.setText(agerange_b);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
			}
		});
		panel.add(btnBf);

		JLabel BfAge = new JLabel("AgeRange:");
		BfAge.setBounds(115, 389, 62, 23);
		panel.add(BfAge);
		panel.add(BfAgeRange);

		JButton btnAf = new JButton("Measure");
		btnAf.setBounds(375, 388, 84,25 );
		btnAf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//BlueMixで年齢を測定するFaceTestを実行
					try {
						agerange_a = FaceTestAfter.main(FilePath);
						AfAgeRange.setText(agerange_a);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
			}
		});
		panel.add(btnAf);

		JLabel AfAge = new JLabel("AgeRange:");
		AfAge.setBounds(462, 389, 62, 23);
		panel.add(AfAge);
		panel.add(AfAgeRange);

		// ラベル作成
		label = new JLabel();
		label.setBounds(35, 60, labelX, labelY);
		panel.add(label);

		donelabel = new JLabel();
		donelabel.setBounds(380, 60, labelX, labelY);
		panel.add(donelabel);


		this.add(panel);

		// フレームを表示
		setVisible(true);
	}


	// ファイル選択ダイアログを表示し、選択したファイルを食べるに設定
	private void open() {
		JFileChooser fc = new JFileChooser();
		// 画像ファイルの拡張子を設定
		fc.setFileFilter(new FileNameExtensionFilter("画像ファイル", "png", "jpg",
				"Jpeg", "GIF", "bmp"));
		// ファイル選択ダイアログを表示、戻り値がAPPROVE_OPTIONの場合画像ファイルを開く
		if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

			f = fc.getSelectedFile();
			FilePath = RemoveFileExtension(f);
			System.out.println(FilePath);
			// アイコンをラベルに設定
			ImageIcon icon = new ImageIcon(f.getPath());

			img = icon.getImage();

			//labelのサイズに画像をリサイズ
			imageresize(icon,labelX,labelY,img);

			icon.setImage(area);
			label.setIcon(icon);
			textField.setText(f.getAbsolutePath());

			System.out.print("ttttt");
		}
		System.out.print("xxxxxxx");
	}

	private void DoOpen() {

			// アイコンをラベルに設定
			ImageIcon doneicon = new ImageIcon(FilePath+"_re_new.jpg");

			Image doneimg = doneicon.getImage();

			//labelのサイズに画像をリサイズ
			imageresize(doneicon,labelX,labelY,doneimg);

			doneicon.setImage(area);
			donelabel.setIcon(doneicon);

			System.out.println("DoneOpen");

	}


	public void imageresize(ImageIcon i,int MW,int MH,Image im){
		int resizeX = 0;
		int resizeY = 0;

		if((MW > i.getIconWidth() ) && (MH > i.getIconHeight() )){
			resizeX = i.getIconWidth();
			resizeY = i.getIconHeight();
		}else if((i.getIconWidth() > (i.getIconHeight())) && (i.getIconWidth() > MW)){
			resizeX = MW;
			resizeY = i.getIconHeight() * MW / i.getIconWidth();

		}else if((i.getIconHeight() > (i.getIconWidth())) && (i.getIconHeight() > MW)){
			resizeX = i.getIconWidth() * MH / i.getIconHeight();
			resizeY = MH;
		}

		ImageFilter fl = new AreaAveragingScaleFilter(resizeX,resizeY);
		fis = new FilteredImageSource(im.getSource(),fl);
		area = createImage(fis);
	}

	public void importimage(){
		BufferedImage bi = null;

		try{
		    bi = ImageIO.read(new File(FilePath+"_re_new.jpg"));
			System.out.println("aaaaaaaaa");

		} catch(IOException e){
			System.out.println("Miss");
		}
		try {
			ImageIO.write(bi, "jpeg", new File(FilePath+"Cinderellaed.jpg"));
			File newimage = new File(FilePath+"_re_new.jpg");
			newimage.delete();
		} catch(IOException e){
			System.out.println("Miss");
		}
		System.out.println("importimage");

	}

	public static String RemoveFileExtension(File file){
		String filename = file.getAbsolutePath();
		int lastDotPos = filename.lastIndexOf('.');

		if (lastDotPos == -1){
			return filename;
		}else if (lastDotPos == 0){
			return 	filename;
		}else {
			return filename.substring(0, lastDotPos);
		}
	}

}
