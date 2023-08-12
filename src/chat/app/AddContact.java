package chat.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;

import java.io.IOException;
import java.sql.*;


public class AddContact extends JFrame {

	private newJpanel contentPane;
	private newJLabel lblNewLabel;
	private newJTextField txtUsername;
	private filterJButton Add;
	
	private static final String DB_URL = "jdbc:mysql://localhost:3306/chatapp";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "kislay565";
	private newJLabel NickName;
	private newJTextField textField;
	private  String tablename;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					AddContact frame = new AddContact();
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
	public AddContact(String tablename) {
		this.tablename=tablename;
		setType(Type.POPUP);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 476, 232);
		contentPane = new newJpanel();
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(198,201,198));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[150px][90.00px][185.00px,grow]", "[35.00px][29.00][35.00][]"));
		
		lblNewLabel = new newJLabel("Enter username*");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPane.add(lblNewLabel, "cell 0 0,grow");
		
		txtUsername = new newJTextField();
		txtUsername.setPreferredSize(new Dimension(500,50));
		txtUsername.setBorder(new EmptyBorder(5,20,5,5));
		contentPane.add(txtUsername, "cell 2 0,grow");
		txtUsername.setColumns(10);
		
		Add = new filterJButton("Add contact");
		Add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				addContacts();
				
			}
			
		});
		
		NickName = new newJLabel("Nick Name*");
		NickName.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPane.add(NickName, "cell 0 2,alignx right");
		
		textField = new newJTextField();
		textField.setPreferredSize(new Dimension(500,50));
		textField.setBorder(new EmptyBorder(5,20,5,5));
		contentPane.add(textField, "cell 2 2,growx");
		textField.setColumns(10);
		
		contentPane.add(Add, "cell 1 3,grow");
		setVisible(true);
		
	}
	
	private void addContacts() {
		String username=txtUsername.getText();
		String nick=textField.getText();
		int userid=0;
		try {
			
			if(username.isEmpty() || nick.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Please Enter username/Nick name.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(!checkforcontacts(username)) {
				JOptionPane.showMessageDialog(this, "Contact Already present.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			 Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			 String querycheck="SELECT username,userid FROM users where username='$username'";
			 querycheck=querycheck.replace("$username", username);
			 PreparedStatement statement1 = connection.prepareStatement(querycheck);
			// statement1.setString(1, username);
			 ResultSet rs=statement1.executeQuery();
			 
			 
			 while(rs.next()) {
				 userid=rs.getInt("userid");
			 }
			 if(userid==0) {
				 JOptionPane.showMessageDialog(this, "No such user found with provided username.", "Error", JOptionPane.ERROR_MESSAGE);
				 return;
			 }
			 
			 String query = "INSERT INTO $tablename VALUES ('$id','$username','$nick')";
			 System.out.println("Connected");
			 query=query.replace("$tablename", tablename);
			 query=query.replace("$username", username);
			 query=query.replace("$nick", nick);
			 query=query.replace("$id", String.valueOf(userid));
			 PreparedStatement statement = connection.prepareStatement(query);
//			 statement.setString(2,nick);
//			 statement.setString(1, username);
	         System.out.println("Connected");
	         
	         System.out.println(userid);
	         System.out.println(username);
	         System.out.println(nick);
	         
	         
	         
	         
	         statement.executeUpdate(query);
	         statement.close();
	         statement1.close();
	         System.out.println("Connected");
	         
	         
		}catch(SQLException e) {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(this, "Contact Successfully added", "Success", JOptionPane.INFORMATION_MESSAGE);
		
		this.dispose();
		
	}
	private  boolean checkforcontacts(String username) {
			try {
				Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				 String querycheck="SELECT username FROM $tablename where username='$username'";
				 querycheck=querycheck.replace("$tablename", tablename);
				 querycheck=querycheck.replace("$username", username);
				 PreparedStatement statement1 = connection.prepareStatement(querycheck);
				 
				
				 ResultSet rs=statement1.executeQuery();
				 
				 while(rs.next()) {
					 return false;
				 }
			}catch(SQLException s) {
				s.printStackTrace();
			}
			return true;
	}
}
