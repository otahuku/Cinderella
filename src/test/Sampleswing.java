package test;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Sampleswing extends JFrame {

	private JPanel contentPane;
	private final Action action = new SwingAction();
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sampleswing frame = new Sampleswing();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Sampleswing() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 546, 405);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("画�?");
		lblNewLabel.setBounds(11, 21, 220, 29);
		contentPane.add(lblNewLabel);

		JButton button = new JButton("New button");
		button.setBounds(60, 19, 104, 33);
		button.setAction(action);
		contentPane.add(button);

		JButton btnImport = new JButton("import");
		btnImport.setBounds(373, 19, 147, 33);
		contentPane.add(btnImport);

		textField = new JTextField();
		textField.setBounds(165, 20, 186, 31);
		contentPane.add(textField);
		textField.setColumns(10);
	}



	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "開く");
			putValue(SHORT_DESCRIPTION, "Some short description");

		}

		public void actionPerformed(ActionEvent e) {
			 JFileChooser filechooser = new JFileChooser();
	            int selected = filechooser.showOpenDialog(contentPane);
	            if (selected == JFileChooser.APPROVE_OPTION){
	                File file = filechooser.getSelectedFile();
	                textField.setText(file.getAbsolutePath());

			}
		}

	}
}
