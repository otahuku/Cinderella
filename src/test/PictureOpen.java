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

public class PictureOpen extends JFrame {
	JLabel label;
    JPanel panel;
	private JTextField textField;
	Image img,area;
	FilteredImageSource fis;
	static int labelX,labelY;
	File f;

	public static void main(String[] args) {
		labelX = 240;
		labelY = 320;
		new PictureOpen();
	}

	public PictureOpen() {
		init();
	}

	private void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Cinderella");
		setBounds(100, 100, 650, 450);

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
				// 繝輔ぃ繧､繝ｫ驕ｸ謚槭ム繧､繧｢繝ｭ繧ｰ繧定｡ｨ遉ｺ縺励��驕ｸ謚槭＠縺溘ヵ繧｡繧､繝ｫ繧偵Λ繝吶Ν縺ｫ險ｭ螳壹☆繧九Γ繧ｽ繝�繝峨ｒ蜻ｼ縺ｳ蜃ｺ縺�
				open();
			}
		});
		panel.add(button);

		JButton btnImport = new JButton("import");
		btnImport.setBounds(373, 19, 147, 33);
		btnImport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//蜉�鮨｢繝ｻ貂幃ｽ｢縺励◆逕ｻ蜒上ｒ菫晏ｭ倥☆繧九Γ繧ｽ繝�繝峨ｒ蜻ｼ縺ｳ蜃ｺ縺�
				importimage();

			}
		});
		panel.add(btnImport);

		textField = new JTextField();
		textField.setBounds(165, 20, 186, 31);
		panel.add(textField);
		textField.setColumns(10);

		JLabel balabel = new JLabel("竍�");
		balabel.setBounds(275, 175, 100, 50);
		balabel.setFont(new Font("serif", Font.PLAIN, 90));
		panel.add(balabel);



		JButton btnDo = new JButton("DO");
		btnDo.setBounds(290, 230, 56,25 );
		btnDo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//蜉�鮨｢繝ｻ貂幃ｽ｢繝｡繧ｽ繝�繝韻inderella縺ｮ蜻ｼ縺ｳ蜃ｺ縺�


			}
		});
		panel.add(btnDo);

		// 繝ｩ繝吶Ν菴懈��
		label = new JLabel();
		label.setBounds(35, 50, labelX, labelY);
		panel.add(label);

		this.add(panel);

		// 繝輔Ξ繝ｼ繝�繧定｡ｨ遉ｺ
		setVisible(true);
	}

	// 繝輔ぃ繧､繝ｫ驕ｸ謚槭ム繧､繧｢繝ｭ繧ｰ繧定｡ｨ遉ｺ縺励��驕ｸ謚槭＠縺溘ヵ繧｡繧､繝ｫ繧帝｣溘∋繧九↓險ｭ螳�
	private void open() {
		JFileChooser fc = new JFileChooser();
		// 逕ｻ蜒上ヵ繧｡繧､繝ｫ縺ｮ諡｡蠑ｵ蟄舌ｒ險ｭ螳�
		fc.setFileFilter(new FileNameExtensionFilter("逕ｻ蜒上ヵ繧｡繧､繝ｫ", "png", "jpg",
				"Jpeg", "GIF", "bmp"));
		// 繝輔ぃ繧､繝ｫ驕ｸ謚槭ム繧､繧｢繝ｭ繧ｰ繧定｡ｨ遉ｺ縲∵綾繧雁�､縺窟PPROVE_OPTION縺ｮ蝣ｴ蜷育判蜒上ヵ繧｡繧､繝ｫ繧帝幕縺�
		if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

			f = fc.getSelectedFile();
			// 繧｢繧､繧ｳ繝ｳ繧偵Λ繝吶Ν縺ｫ險ｭ螳�
			ImageIcon icon = new ImageIcon(f.getPath());

			img = icon.getImage();

			//label縺ｮ繧ｵ繧､繧ｺ縺ｫ逕ｻ蜒上ｒ繝ｪ繧ｵ繧､繧ｺ
			imageresize(icon,labelX,labelY);

			icon.setImage(area);
			label.setIcon(icon);

			System.out.print("ttttt");
		}
		System.out.print("xxxxxxx");
	}

	public void imageresize(ImageIcon i,int MW,int MH){
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
		fis = new FilteredImageSource(img.getSource(),fl);
		area = createImage(fis);
	}

	public void importimage(){
		BufferedImage bi = null;

		try{
		    bi = ImageIO.read(f);
			System.out.println("aaaaaaaaa");

		} catch(IOException e){
			System.out.println("Miss");
		}
		try {
			ImageIO.write(bi, "jpeg", new File("Cinderellaed.jpg"));
		} catch(IOException e){
			System.out.println("Miss");
		}
		System.out.println("importimage");


	}
}
