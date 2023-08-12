package chat.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;

public class Del_Contacts  {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/chatapp";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "kislay565";
	static String tablename;
	private static ArrayList<String> contacts=new ArrayList<String>();
	static Connection connection;
	private static filterJButton delete;
	private static JCheckBox[] check;
	static JFrame f;
	

	private static JPanel contentPane;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Del_Contacts frame = new Del_Contacts("kislayu9zcl");
//					
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public Del_Contacts(String tablename) {
		
		f=new JFrame();
		this.tablename=tablename;
		//f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBounds(100, 100, 500, 538);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		//setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[445.00]", "[][][][]"));
		f.add(contentPane);
		JScrollPane scrollPane = new JScrollPane(contentPane);
		scrollPane.setViewportBorder(null);
	    scrollPane.setPreferredSize(new Dimension(300, 400));
	    scrollPane.setBorder(new EmptyBorder(0,0,0,-12));
	    scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());  
	    scrollPane.getViewport().setBackground(Color.WHITE);
	    f.add(BorderLayout.CENTER,scrollPane);
	    delete=new filterJButton("Delete");
	    getContact();
	    f.validate();
	    f.setVisible(true);
	    
	}
	
	private static void getContact() {
		
		try {
			
			 connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			 
	         String query_11 = "SELECT * FROM $tablename";
			 System.out.println("Connected");
			 String query_1=query_11.replace("$tablename", tablename);
			 PreparedStatement statement_1 = connection.prepareStatement(query_1);
			
	         System.out.println("Connected");
	         ResultSet resultSet_1 = statement_1.executeQuery(query_1);
	         System.out.println("Connected");
	         int i=0;
	        // int count=0;
	         while (resultSet_1.next()) {
	             // Retrieve data from the current row
	        	 
	             
	             
	             String username = resultSet_1.getString("username");
	             contacts.add(username);
	             // ...
	         }
	         check=new JCheckBox[contacts.size()];
	         for(String c:contacts) {
	        	 
	             // Process the data
	             check[i] = new JCheckBox(c);
	     		contentPane.add(check[i], "cell 0 "+i++);
	     		//count++;
	         }
	         statement_1.close();
	         
		
		
		
		contentPane.add(delete,"cell 0 "+i);
		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				delContact();
				
			}
			
		});
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		f.validate();
		
	}
	
	private static void delContact() {
		for(JCheckBox k: check) {
			
	                if (k.isSelected()) {
	                 String query = "DELETE  FROM $tablename WHERE username='$contact'";
	       			 System.out.println("Connected");
	       			 query=query.replace("$tablename", tablename);
	       			 query=query.replace("$contact", k.getText());
	       			 try {
	       			 PreparedStatement statement_1 = connection.prepareStatement(query);
	       			
	       	         System.out.println("Connected2");
	       	         statement_1.executeUpdate(query);
	       	         statement_1.close();
	       	         contentPane.remove(k);
	       	         
	       	         f.validate();
	       	         f.repaint();
	       			 }catch(Exception ee) {
	       				 ee.printStackTrace();
	       			 }
	                } 
	                
	            
	       
		}
		//getContact();
		
	}

}
