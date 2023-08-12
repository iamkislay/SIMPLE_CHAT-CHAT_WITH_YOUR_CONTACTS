package chat.app;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import net.miginfocom.swing.MigLayout;

public class ThemePicker extends JFrame {

	private JPanel contentPane;
	private String newTheme;
	private static String User_name;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ThemePicker frame = new ThemePicker("iamkislay");
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public ThemePicker(String username) {
		User_name=username;
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 430, 407);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("Choose the theme color");
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		colorLabel lblNewLabel_6 = new colorLabel(User_name);
		lblNewLabel_6.setOpaque(true);
		lblNewLabel_6.setPreferredSize(new Dimension (50,50));
		lblNewLabel_6.setBackground(new Color(7,94,84));
		contentPane.add(lblNewLabel_6);
		
		colorLabel lblNewLabel = new colorLabel(User_name);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setPreferredSize(new Dimension (50,50));
		lblNewLabel.setBackground(Color.BLUE);
		
		contentPane.add(lblNewLabel);
		
		colorLabel lblNewLabel5 = new colorLabel(User_name);
		lblNewLabel5.setOpaque(true);
		lblNewLabel5.setPreferredSize(new Dimension (50,50));
		lblNewLabel5.setBackground(new Color(15,209,177));
		
		
		Color l=lblNewLabel5.getBackground();
		System.out.print(l.getRed()+"" +l.getBlue()+""+l.getGreen());
		contentPane.add(lblNewLabel5);
		
		
		colorLabel lblNewLabel_1 = new colorLabel(User_name);
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setPreferredSize(new Dimension (50,50));
		lblNewLabel_1.setBackground(new Color(243,191,18));
		contentPane.add(lblNewLabel_1);
		
		colorLabel lblNewLabel_2 = new colorLabel(User_name);
		lblNewLabel_2.setOpaque(true);
		lblNewLabel_2.setPreferredSize(new Dimension (50,50));
		lblNewLabel_2.setBackground(new Color(243,130,18));
		contentPane.add(lblNewLabel_2);
		
		colorLabel lblNewLabel_3 = new colorLabel(User_name);
		lblNewLabel_3.setOpaque(true);
		lblNewLabel_3.setPreferredSize(new Dimension (50,50));
		lblNewLabel_3.setBackground(new Color(119,86,151));
		contentPane.add(lblNewLabel_3);
		
		colorLabel lblNewLabel_4 = new colorLabel(User_name);
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setPreferredSize(new Dimension (50,50));
		lblNewLabel_4.setBackground(new Color(208,112,204));
		contentPane.add(lblNewLabel_4);
		
		colorLabel lblNewLabel_5 = new colorLabel(User_name);
		lblNewLabel_5.setOpaque(true);
		lblNewLabel_5.setPreferredSize(new Dimension (50,50));
		lblNewLabel_5.setBackground(new Color(181,229,24));
		contentPane.add(lblNewLabel_5);
		
		colorLabel lblNewLabel_7 = new colorLabel(User_name);
		lblNewLabel_7.setOpaque(true);
		lblNewLabel_7.setPreferredSize(new Dimension (50,50));
		lblNewLabel_7.setBackground(new Color(192,20,49));
		contentPane.add(lblNewLabel_7);
		
		colorLabel lblNewLabel_8 = new colorLabel(User_name);
		lblNewLabel_8.setOpaque(true);
		lblNewLabel_8.setPreferredSize(new Dimension (50,50));
		lblNewLabel_8.setBackground(new Color(3,7,46));
		contentPane.add(lblNewLabel_8);
		
		
		
		
		
//		filterJButton ok =new filterJButton(" OK ");
//		ok.setForeground(Color.WHITE);
//		contentPane.add(ok);
		setIconImage(new ImageIcon(ClassLoader.getSystemResource("icons/logo_myapp.png")).getImage());
		setVisible(true);
//		

//		
//		JLabel lblNewLabel_7 = new JLabel("New label");
//		contentPane.add(lblNewLabel_7);
//		
//		JLabel lblNewLabel_8 = new JLabel("New label");
//		contentPane.add(lblNewLabel_8);
//		
//		JLabel lblNewLabel_9 = new JLabel("New label");
//		contentPane.add(lblNewLabel_9);
	}
	
//	private void setTheme()
	
	
}



class colorLabel extends JLabel{
	private static String User_name;
	private static final String DB_URL = "jdbc:mysql://localhost:3306/chatapp";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "kislay565";
	private static String themeStr;
	
	Color forTheme;
	public colorLabel(String user) {
		User_name=user;
		addMouseListener(new MouseAdapter() {

            @Override
            public void mouseExited(MouseEvent me) {
                setBorder(null);
                repaint();
            }
            public void mouseEntered(MouseEvent me) {
            	
            	Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
		        // set the border of this component
		        setBorder(border);
		        repaint();
            }
            public void mouseClicked(MouseEvent e) {
            	forTheme=getBackground();
            	themeStr=String.valueOf(forTheme.getRed())+","+String.valueOf(forTheme.getGreen())+","+String.valueOf(forTheme.getBlue());
            	System.out.println(themeStr);
            	System.out.println(User_name);
            	try {
       			 Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
       			 String query = "UPDATE  USERS SET  ColorString = '$color' where username='$username'";
       			 query=query.replace("$username", User_name);
       			query=query.replace("$color", themeStr);
       			
       			 System.out.println(query);
       			 PreparedStatement statement = connection.prepareStatement(query);
       			 //statement.setString(1, User_name);
       	         System.out.println("Connected");
       	         statement.executeUpdate(query);
       	         System.out.println("Connected");
       	        
       	         
       	         
       	      
       	         statement.close();
       		}catch(Exception error) {
       			error.printStackTrace();
       		}
            }
            

        });
	}
}
