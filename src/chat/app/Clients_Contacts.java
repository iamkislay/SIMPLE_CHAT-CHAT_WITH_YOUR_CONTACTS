package chat.app;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.mysql.cj.xdevapi.Statement;

import net.miginfocom.swing.MigLayout;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;



public class Clients_Contacts {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/chatapp";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "kislay565";
	private static newJpanel head;
	static newJpanel lists;
	static JFrame Jclist;
	static Box vertical = Box.createVerticalBox();
	static String User_name;
	static String tablename;
	static String myPic;
	private static Color theme;
	
	
	Clients_Contacts(String s){
		User_name=s;
		
		
		profileSet();
		
		
		Jclist=new JFrame();
		
		Jclist.setSize(465,700);
		 String titles="si$pleChat";
	    titles=titles.replace("$", "(\\/)");
	    Jclist.setTitle(titles+" //" + User_name);
		Jclist.setIconImage(new ImageIcon(ClassLoader.getSystemResource("icons/logo_myapp.png")).getImage());
		
		head=new newJpanel();
		head.setBackground(theme);
		Jclist.getContentPane().add(head, BorderLayout.NORTH);
		head.setPreferredSize(new Dimension(300,90));
		head.setLayout(new MigLayout("", "[35.00px][-12.00px][91.00][19.00][40.00][16.00][40.00][][][][18.00][43.00]", "[62.00px]"));
		
		
		JLabel title=new JLabel("Contacts");
		title.setVerticalAlignment(SwingConstants.BOTTOM);
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Yu Gothic Medium", Font.BOLD, 21));
		head.add(title, "cell 2 0,aligny bottom");
		title.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent ee) {
				vertical.removeAll();
				listofContact();
				profileSet();
				head.setBackground(theme);
				head.repaint();
				head.validate();
				
			}
		});
		
		
		ImageIcon addC=new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("icons/add_contacts.png")).getImage().getScaledInstance(47, 47, Image.SCALE_SMOOTH));
		JLabel addContact = new JLabel(addC);
		addContact.setVerticalAlignment(SwingConstants.BOTTOM);
		head.add(addContact, "cell 9 0");
		addContact.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent ee) {
				new AddContact(tablename);
				Jclist.validate();
				Jclist.repaint();
				
			}
		});
		
		
		
		ImageIcon Menuimg=new ImageIcon(new ImageIcon(ClassLoader.getSystemResource(myPic)).getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));
		JLabel Menu = new JLabel(Menuimg);
		Menu.setVerticalAlignment(SwingConstants.CENTER);
		
		head.add(Menu, "cell 11 0,alignx left");
		JPopupMenu popupMenu = createPopupMenu();
		popupMenu.setSize(new Dimension(400,100));
		Menu.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent ee) {
				if (ee.getButton() == MouseEvent.BUTTON1) {
                    popupMenu.show(Menu, 0, Menu.getHeight());
				
			}
		}});
	//	head.add(addContact, "cell 9 0");
		
		lists = new newJpanel();
		lists.setBackground(Color.white);
		lists.setBorder(new EmptyBorder(31, 1, 5, 5));
		
		
		JScrollPane scrollPane = new JScrollPane(lists);
		scrollPane.setViewportBorder(null);
	    scrollPane.setPreferredSize(new Dimension(300, 400));
	    scrollPane.setBorder(new EmptyBorder(0,0,0,-12));
	    scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());  
	    scrollPane.getViewport().setBackground(Color.WHITE);
	    Jclist.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
	    
	    Jclist.setSize(465,700);
		Jclist.getContentPane().setBackground(Color.WHITE);
		listofContact();
		Jclist.setVisible(true);
		Jclist.validate();
		Jclist.repaint();
	}	
	
	private static void profileSet() {
		try {
			 Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			 String query = "SELECT profilePicURL,ColorString FROM users where username='$username'";
			 query=query.replace("$username", User_name);
			 System.out.println(User_name);
			 PreparedStatement statement = connection.prepareStatement(query);
			 //statement.setString(1, User_name);
	         System.out.println("Connected");
	         ResultSet resultSet = statement.executeQuery(query);
	         System.out.println("Connected");
	         
	         
	         while(resultSet.next()) {
	        	myPic=resultSet.getString("profilePicURL");
	        	String[] clr=resultSet.getString("ColorString").split(",",3);
	        	theme=new Color(Integer.parseInt(clr[0]),Integer.parseInt(clr[1]),Integer.parseInt(clr[2]));
	         }
	         statement.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
		
		private static void listofContact() {
		
		 try {
			 
			 head.repaint();
			 
			 Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			 String query = "SELECT table_name FROM users where username='$username'";
			 query=query.replace("$username", User_name);
			 System.out.println(User_name);
			 PreparedStatement statement = connection.prepareStatement(query);
			 //statement.setString(1, User_name);
	         System.out.println("Connected");
	         ResultSet resultSet = statement.executeQuery(query);
	         System.out.println("Connected");
	         
	         
	         while(resultSet.next()) {
	        	 tablename=resultSet.getString("table_name");
	         }
	         statement.close();
	         String query_11 = "SELECT $tablename.nickname,$tablename.username,users.profilePicURL FROM $tablename INNER JOIN users ON $tablename.contact_id=users.userid";
			 System.out.println("Connected");
			 String query_1=query_11.replace("$tablename", tablename);
			 PreparedStatement statement_1 = connection.prepareStatement(query_1);
			
	         System.out.println("Connected");
	         ResultSet resultSet_1 = statement_1.executeQuery(query_1);
	         System.out.println("Connected");
	         
	         while (resultSet_1.next()) {
	             // Retrieve data from the current row
	        	 
	             
	             String name = resultSet_1.getString("nickname");
	             String username = resultSet_1.getString("username");
	             String frndpicurl=resultSet_1.getString("profilePicURL");
	            // String ContactPic=resultSet_1.getString("username");
	             // ...
	             
	             // Process the data
	             System.out.println("ID: " + username + ", Name: " + name);
	             
	             JPanel user=new JPanel();
	             user.setOpaque(false);
	          
	             user.setLayout(new BoxLayout(user,BoxLayout.Y_AXIS));
	             JLabel nameLabel =new JLabel(name);
	             JLabel usernameLabel =new JLabel(username);
	             //nameLabel.setBackground(Color.gray);
	             
	             nameLabel.setForeground(Color.BLACK);
	             nameLabel.setFont(new Font("San_Serif",Font.BOLD,18));
	             nameLabel.setBorder(new EmptyBorder(10,5,5,50));
	             //nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);;
	            // usernameLabel.setBackground(Color.CYAN);
	             usernameLabel.setForeground(Color.BLACK);
	             usernameLabel.setFont(new Font("San_Serif",Font.PLAIN,14));
	             usernameLabel.setBorder(new EmptyBorder(1,10,15,50));
	             
	            
	             JPanel userPanel=new JPanel();
	             userPanel.setLayout(new BoxLayout(userPanel,BoxLayout.X_AXIS));
	             
	             
	             JPanel forPic=new JPanel();
	             ImageIcon friendPic=new ImageIcon(new ImageIcon(ClassLoader.getSystemResource(frndpicurl)).getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));
	             JLabel friendPicLabel = new JLabel(friendPic);
	            friendPicLabel.setBorder(new EmptyBorder(7,5,5,5));
	     		forPic.add(friendPicLabel);
	     		forPic.setAlignmentY((float) 0.5);
	             forPic.setOpaque(false);
	             user.add(nameLabel);
	             user.add(usernameLabel);
	             
	             userPanel.add(forPic);
	             userPanel.add(user);
	             userPanel.setOpaque(false);
	             //lists.add(user,BorderLayout.PAGE_START);
	             lists.setLayout(new BorderLayout());
	             newJpanel leftAlign=new newJpanel(new BorderLayout());
	             leftAlign.setBackground(Color.gray);
	             leftAlign.add(userPanel,BorderLayout.LINE_START);
	             vertical.add(leftAlign);
	             vertical.add(Box.createVerticalStrut(5));
					
	             lists.add(vertical,BorderLayout.PAGE_START);
	             
	             System.out.println("3"+Thread.currentThread().getName());
	             leftAlign.addMouseListener(new MouseAdapter() {
	     			public void mouseClicked(MouseEvent ee) {
	     				System.out.println("1"+Thread.currentThread().getName());
	     				//new Server();
	     				
	     				client_3 client=new client_3(username,User_name);
	     				Thread th =new Thread(client);
	     				
	     				th.start();
	     				Jclist.dispose();
	     				
	     				
	     			}
	     		});
	             leftAlign.addMouseListener(new MouseAdapter () {
	            	 public void mouseEntered(MouseEvent i) {
	            		
	            		 leftAlign.setBackground(Color.LIGHT_GRAY);
	            		 leftAlign.repaint();
	            	 }
	            	 
	            	 public void mouseExited(MouseEvent me) {
	            		 leftAlign.setBackground(Color.gray);
	            		 leftAlign.repaint();
	                 }
	             });
	             Jclist.validate();
	         }
	         statement_1.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
		 Jclist.repaint();
         // Create a prepared statement to insert the registration data
        
         
	}
		
	    private static JPopupMenu createPopupMenu() {
	        JPopupMenu popupMenu = new JPopupMenu();
	        JMenuItem menuItem1 = new JMenuItem("Delete Contacts");
	        JMenuItem menuItem2 = new JMenuItem("Profile");
	        JMenuItem menuItem3 = new JMenuItem("Setting");
	        JMenuItem menuItem4 = new JMenuItem("Display Color");
	        
	       
	        menuItem1.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					new Del_Contacts(tablename);
					
					
				}
	        	
	        });
	        menuItem2.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					new ProfilePic(User_name);
					
					
				}
	        	
	        });
	        
	        menuItem4.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					new ThemePicker(User_name);
										
				}
	        	
	        });
	        popupMenu.add(menuItem1);
	        popupMenu.add(menuItem2);
	        popupMenu.add(menuItem3);
	        popupMenu.add(menuItem4);

	        return popupMenu;
	    }
	
//	public static void main(String[] args) {
//		new test("iamkislay");
//	}
	
	
}

