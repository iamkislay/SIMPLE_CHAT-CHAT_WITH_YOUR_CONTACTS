package chat.app;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class test_intro extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new Server();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test_intro frame = new test_intro();
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
	public test_intro() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 428, 536);
		String title="si$pleChat";
        title=title.replace("$", "(\\/)");
        setTitle(title);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		ImageIcon logo=new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("icons/logo_myapp.png")).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
		contentPane.setLayout(null);
		JLabel lblNewLabel = new JLabel(logo);
		lblNewLabel.setBounds(5, 5, 404, 244);
		contentPane.add(lblNewLabel);
		
		filterJButton btnNewButton = new filterJButton("Enter into "+title);
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setLocation(123, 329);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LoginAndregistration();
			}
		});
		
		btnNewButton.setSize(new Dimension(178, 55));
		contentPane.add(btnNewButton);
	}
}
