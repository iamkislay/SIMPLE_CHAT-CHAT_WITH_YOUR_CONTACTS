package chat.app;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProfilePic extends JFrame {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/chatapp";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "kislay565";
	private static String myPic;
   private static ImageIcon[] dp=new ImageIcon[16];
   private static JLabel[] dplabel=new JLabel[16];
   private static String username;

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProfilePic frame = new ProfilePic("shuhoney");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ProfilePic(String username) {
		this.username=username;
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 481, 470);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		
		for(int i=0;i<16;i++) {
			String ImageURL="icons/profile_$num.png";
			ImageURL=ImageURL.replace("$num", String.valueOf(i+1));
			dp[i]=new ImageIcon(new ImageIcon(ClassLoader.getSystemResource(ImageURL)).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
			dp[i].setDescription(ImageURL);
			dplabel[i]=new JLabel(dp[i]);
			
			
			
		}
		
		for(JLabel l:dplabel) {
			l.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent ee) {
					Color borderColor = Color.GREEN;
			        int borderThickness = 5;
			        Border coloredBorder = BorderFactory.createLineBorder(borderColor, borderThickness);

			        // Set the custom border to the JLabel
			        l.setBorder(coloredBorder);
			        if (l.getIcon() instanceof ImageIcon) {
                        ImageIcon clickedImageIcon = (ImageIcon) l.getIcon();
                        myPic = clickedImageIcon.getDescription();
                        if (myPic != null) {
                            System.out.println("Clicked image URL: " + myPic);
                        } else {
                            System.out.println("Image URL not available.");
                        }
                    }
				}
			});
			
			contentPane.add(l);
		}
		
		filterJButton set=new filterJButton("Confirm");
		contentPane.add(set);
		set.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
				// TODO Auto-generated method stub
				 Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				 String query = "UPDATE users SET profilePicURL='$URL' WHERE username='$username'";
				 query=query.replace("$username", username);
				 query=query.replace("$URL", myPic);
				 System.out.println(username);
				 PreparedStatement statement = connection.prepareStatement(query);
				 //statement.setString(1, User_name);
		         System.out.println("Connected");
		         statement.executeUpdate(query);
		         System.out.println("Connected");
		         statement.close();
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
				dispose();
			}
			
		});
		setVisible(true);
	}

}


