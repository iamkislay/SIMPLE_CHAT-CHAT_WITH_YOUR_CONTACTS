package chat.app;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.text.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;




public class client_3 extends Thread implements ActionListener{
	newJTextField text;
	static newJpanel mPanel;
	static Box vertical = Box.createVerticalBox();
	static DataOutputStream out;
	static JFrame f =new JFrame();
	static Socket sock;
	static String user;
	static String myself;
	static String ProfilePicURL;
	static ArrayList<MyFIles> myFiles =new ArrayList<>();
	private static HashMap<SocketAddress,String> username=new HashMap<SocketAddress,String>();
	private static HashMap<SocketAddress,String> ownnameMap=new HashMap<SocketAddress,String>();
	private static final String DB_URL = "jdbc:mysql://localhost:3306/chatapp";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "kislay565";
	private static Color theme;
	

	
	client_3(String t,String myself){
		
		
		this.myself=myself;
		user=t;
		System.out.println("5"+Thread.currentThread().getName());
		
		 String titles="si$pleChat";
		    titles=titles.replace("$", "(\\/)");
		    f.setTitle(titles+" //" + myself);
			f.setIconImage(new ImageIcon(ClassLoader.getSystemResource("icons/logo_myapp.png")).getImage());
		
			
			try {
				 Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				 String query = "SELECT profilePicURL FROM users where username='$username'";
				 query=query.replace("$username", user);
				 System.out.println(user);
				 PreparedStatement statement = connection.prepareStatement(query);
				 //statement.setString(1, User_name);
		         System.out.println("Connected");
		         ResultSet resultSet = statement.executeQuery(query);
		         System.out.println("Connected");
		         
		         
		         while(resultSet.next()) {
		        	ProfilePicURL=resultSet.getString("profilePicURL");
		         }
		         statement.close();
		         
		         String query2 = "SELECT ColorString FROM users where username='$username'";
				 query2=query2.replace("$username", myself);
				 System.out.println(myself);
				 PreparedStatement statement2 = connection.prepareStatement(query2);
				 //statement.setString(1, User_name);
		         System.out.println("Connected");
		         ResultSet result = statement2.executeQuery(query2);
		         System.out.println("Connected");
		         
		         
		         while(result.next()) {
		        	String[] clr=result.getString("ColorString").split(",",3);
		        	theme=new Color(Integer.parseInt(clr[0]),Integer.parseInt(clr[1]),Integer.parseInt(clr[2]));
		         }
		         statement2.close();
		         
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		newJpanel header=new newJpanel();
		header.setBackground(theme);
		f.getContentPane().add(header, BorderLayout.NORTH);
		header.setLayout(new MigLayout("", "[35.00px][-12.00px][59.00][19.00][27.00][6.00][30.00][49.00][39.00][36.00][25.00][]", "[24px]"));
		
		
		JLabel name=new JLabel(t);
		name.setForeground(Color.WHITE);
		name.setFont(new Font("San_Serif",Font.BOLD,18));
		
		header.add(name, "cell 2 0");
		
				
		ImageIcon back=new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("icons/233-2338305_back-white-icon-png-png-download-white-back.png")).getImage().getScaledInstance(37, 37, Image.SCALE_SMOOTH));
		JLabel bAck = new JLabel(back);
		header.add(bAck, "cell 4 0");
		

		bAck.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ee) {
				try {
					Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
					 String query_new= "DELETE  FROM connections where username='$username' AND participant='$participant'";
					 query_new=query_new.replace("$username", myself);
					 query_new=query_new.replace("$participant", user);
					 System.out.println(user);
					 PreparedStatement statement = connection.prepareStatement(query_new);
					 //statement.setString(1, User_name);
			         System.out.println("Connected");
			         statement.executeUpdate(query_new);
			         System.out.println("Connected");
				}catch(SQLException sq) {
					sq.printStackTrace();
				}
				f.dispose();
				
			}
		});
		
		
		
		ImageIcon profile=new ImageIcon(new ImageIcon(ClassLoader.getSystemResource(ProfilePicURL)).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
		JLabel profilePhoto=new JLabel(profile);
		header.add(profilePhoto, "cell 6 0");
		
		
		
		mPanel = new newJpanel();
		
		mPanel.setBackground(Color.white);
		mPanel.setBorder(new EmptyBorder(31, 1, 5, 5));
		
		
		JScrollPane scrollPane = new JScrollPane(mPanel);
		scrollPane.setViewportBorder(null);
	    scrollPane.setPreferredSize(new Dimension(300, 400));
	    scrollPane.setBorder(new EmptyBorder(0,0,0,-12));
	    scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());

	        
	    scrollPane.getViewport().setBackground(Color.gray);
	    f.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		
	    newJpanel bottom =new newJpanel();
		bottom.setLayout(new FlowLayout());
		f.getContentPane().add(bottom, BorderLayout.SOUTH);
	        
	        
		text = new newJTextField();
		text.setHorizontalAlignment(SwingConstants.LEFT);
	   
	    text.setFont(new Font("SAN SERIF",Font.PLAIN,16));
	    text.setPreferredSize(new Dimension(300,40));
	    text.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 5));
	    
	    bottom.add(text);
	    
	    ImageIcon Send=new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("icons/602.9-send-message-icon-iconbunny.png")).getImage().getScaledInstance(46, 46, Image.SCALE_SMOOTH));
	    newJButton send=new newJButton(Send);
	    send.setHorizontalAlignment(SwingConstants.RIGHT);
	  
	    
	    send.setPreferredSize(new Dimension(46,46));
	    send.addActionListener(this);
	    bottom.add(send);
	    send.setBorder(BorderFactory.createEmptyBorder(5, 25, 5, 1));
	    
	    f.setSize(465,700);
		f.getContentPane().setBackground(Color.gray);
		
		f.setVisible(true);
	    
	    
	    ImageIcon AttachFileimg=new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("icons/attach_filee.png")).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
	    newJButton AttachLabel=new newJButton(AttachFileimg);
	    AttachLabel.setBackground(theme);
	  
	    AttachLabel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean yes;
				// TODO Auto-generated method stub
				System.out.println("File add request sent");
				AttachFile.newFrameCreate(out);
				
				
				if(AttachFile.getValue()) {
					newJLabel message=new newJLabel("File Sended");
					message.setBackground(theme);
			    	message.setForeground(Color.BLACK);
			    	message.setBorder(new EmptyBorder(15,15,15,50));
			    	
					JPanel newMessPanel = new JPanel();
				    newMessPanel.setLayout(new BoxLayout(newMessPanel,BoxLayout.Y_AXIS));
				    
				    ImageIcon fileicon=new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("icons/file_image.png")).getImage().getScaledInstance(45,45, Image.SCALE_SMOOTH));
				    JLabel fileiconlabel=new JLabel(fileicon);
				    fileiconlabel.setBackground(Color.LIGHT_GRAY);
				    
					Calendar calender=Calendar.getInstance();
					SimpleDateFormat format=new SimpleDateFormat("HH:mm");
					JLabel timeLabel=new JLabel(format.format(calender.getTime()));;
					
					JPanel notify=new JPanel();
					notify.setLayout(new BoxLayout(notify,BoxLayout.X_AXIS));
					notify.add(fileiconlabel);
					notify.add(message);
					
					newMessPanel.setBackground(Color.WHITE);
					newMessPanel.add(notify);
					newMessPanel.add(timeLabel);
					
					mPanel.setLayout(new BorderLayout());
					
					JPanel rightAlign=new JPanel(new BorderLayout());
					rightAlign.add(newMessPanel,BorderLayout.LINE_END);
					rightAlign.setBackground(Color.WHITE);
					vertical.add(rightAlign);
					vertical.add(Box.createVerticalStrut(5));
					
					mPanel.add(vertical,BorderLayout.PAGE_START);
					
					
					f.setSize(465,700);
					f.repaint();
					f.invalidate();
					f.validate();
					
				}
			}
	    	
	    });
	    bottom.add(AttachLabel);
	    
	    
	    ImageIcon forward=new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("icons/233-2338305_back-white-icon-png-png-download-white-forward.png")).getImage().getScaledInstance(37, 37, Image.SCALE_SMOOTH));
		JLabel forwardOption=new JLabel(forward);
		
		header.add(forwardOption, "cell 7 0");
		
	    ImageIcon call=new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("icons/98-981549_resultado-de-imagen-para-icono-telefono-blanco-phone.png")).getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH));
		JLabel callLabel=new JLabel(call);
		callLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		header.add(callLabel, "cell 7 0");
		callLabel.setVisible(false);
		
		
		
		ImageIcon Vcall=new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("icons/download_vc.png")).getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH));
		JLabel VcallLabel=new JLabel(Vcall);
		VcallLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		header.add(VcallLabel, "cell 8 0");
		VcallLabel.setVisible(false);
		
		ImageIcon returnOption=new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("icons/download_line.png")).getImage().getScaledInstance(30, 50, Image.SCALE_SMOOTH));
		JLabel returnOLabel=new JLabel(returnOption);
		
		header.add(returnOLabel, "cell 9 0");
		returnOLabel.setVisible(false);
		
		
		
		
		
		forwardOption.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ee) {
			    
				forwardOption.setVisible(false);
				
				callLabel.setVisible(true);
				VcallLabel.setVisible(true);
				returnOLabel.setVisible(true);
				
				
				returnOLabel.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent ee1) {
						callLabel.setVisible(false);
						VcallLabel.setVisible(false);
						returnOLabel.setVisible(false);
						forwardOption.setVisible(true);
						
					}
				});
					    
				
			}
		});
		ImageIcon menu=new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("icons/three_dot.png")).getImage().getScaledInstance(30, 40, Image.SCALE_SMOOTH));
		JLabel menuLabel=new JLabel(menu);
		
		menuLabel.setBounds(390,13,20,40);
		header.add(menuLabel, "cell 10 0");
	    
		JPopupMenu popupMenu = createPopupMenu();
		popupMenu.setSize(new Dimension(400,100));
		menuLabel.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent ee) {
				if (ee.getButton() == MouseEvent.BUTTON1) {
                    popupMenu.show(menuLabel, 0, menuLabel.getHeight());
				
			}
		}});
	    f.setSize(465,700);
		f.getContentPane().setBackground(Color.gray);
		f.setVisible(true);
		//(new String[2]);
		
	}
	
	
	 public void run() {
		 System.out.println("6"+Thread.currentThread().getName());
		 work();
	 }
	    
	
	public  void work() {
		//new client_3();
		int fileId=0;
		
		try {
			System.out.println("4"+Thread.currentThread().getName());
			System.out.println(Thread.currentThread().getPriority());
			sock=new Socket("192.168.29.9",5566);
			
			
			
			
			Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected");

            // Create a prepared statement to insert the registration data
            String query = "INSERT INTO connections (username, participant,socketAddress) VALUES (?, ?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, myself);
            statement.setString(2, user);
            statement.setString(3, String.valueOf(sock.getLocalSocketAddress()));
            statement.executeUpdate();
            statement.close();
            
            
			System.out.println(sock.getLocalSocketAddress());
			//Server.setUsernameandOwnname(user, sock.getLocalSocketAddress(),myself);
			
			//notify();
			out=new DataOutputStream(sock.getOutputStream());
			
				
			DataInputStream in=new DataInputStream(sock.getInputStream());
				
				
				while(true) {
//					while (in.available() == 0) {
//		                // Sleep for a certain interval before polling again
//		                Thread.sleep(500);
//		            }
				
					//String b=in.readUTF();
					System.out.println("wrong");
					String inMess=in.readUTF();
					System.out.println("Message arrived"+inMess);
					Boolean check=in.readBoolean();
					System.out.println("check arrived"+check);
					
						//String []messArr=Mess.split("@@@",2);
						//String inMess=messArr[0];
						System.out.println("Client promted"+inMess);
						
						//out.writeUTF(inMess);
						newJLabel received=new newJLabel(inMess);
						JPanel ReecrivedPanel = new JPanel();
						ReecrivedPanel.setLayout(new BoxLayout(ReecrivedPanel,BoxLayout.Y_AXIS));
						received.setBackground(Color.BLACK);
						received.setForeground(Color.WHITE);
						received.setBorder(new EmptyBorder(15,15,15,50));
						//JLabel name=new JLabel(messArr[1]);
						
						if(check) {
							System.out.println("Message panel added");
							ReecrivedPanel.add(received);
						//	ReecrivedPanel.add(name);
							
						
					}
					int fileNameLength=in.readInt();
					System.out.println("file name length arrived"+fileNameLength);
					byte[] fileNameBytes=new byte[fileNameLength];
					in.readFully(fileNameBytes,0,fileNameBytes.length);
					System.out.println("file name Bytes arrived"+fileNameBytes);
					int fileContentLength=in.readInt();
					System.out.println("file content length arrived"+fileContentLength);
					byte[] fileContentBytes=new byte[fileContentLength];
					in.readFully(fileContentBytes,0,fileContentLength);
					
					System.out.println("file ontent Bytes arrived"+fileContentBytes);
					if(fileNameLength>0){
						System.out.println("Inside Name loop");
					
						
						String filename=new String(fileNameBytes);
						
					
						
						if(fileContentLength>0) {
							
							
							System.out.println("Inside Content loop");
							newJLabel receivedFile=new newJLabel(filename);
							
							receivedFile.setBackground(Color.BLUE);
							receivedFile.setForeground(Color.WHITE);
							receivedFile.setBorder(new EmptyBorder(15,15,15,50));
							
							if(AttachFile.getFileExtension(filename).equalsIgnoreCase("txt")) {
								ReecrivedPanel.setName(String.valueOf(fileId));
								ReecrivedPanel.addMouseListener(MyMouseListener());
								
									ReecrivedPanel.add(receivedFile);
							}
							else {
								ReecrivedPanel.setName(String.valueOf(fileId));
								ReecrivedPanel.addMouseListener(MyMouseListener());
								ReecrivedPanel.add(receivedFile);
								f.validate();
							}
							myFiles.add(new MyFIles(fileId,filename,fileContentBytes,AttachFile.getFileExtension(filename)));
							fileId++;
						}
						
					
						
					}
					
				
					
					
					
					
					
			    	
					Calendar cal=Calendar.getInstance();
					SimpleDateFormat format=new SimpleDateFormat("HH:mm");
					
					JLabel recivedtimeLabel=new JLabel(format.format(cal.getTime()));;
					
					
					ReecrivedPanel.setBackground(Color.WHITE);
					ReecrivedPanel.add(recivedtimeLabel);
					
				
					
					
					mPanel.setLayout(new BorderLayout());
					
					JPanel leftAlign=new JPanel(new BorderLayout());
					leftAlign.add(ReecrivedPanel,BorderLayout.LINE_START);
					vertical.add(leftAlign);
					vertical.add(Box.createVerticalStrut(5));
					leftAlign.setBackground(Color.WHITE);
					mPanel.add(vertical,BorderLayout.PAGE_START);
					f.validate();
					f.repaint();
					if (in.available() != 0) {
		                
						in.readUTF();
		            }
					
					System.out.print("bad");
		            
				}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}


	public void actionPerformed(ActionEvent e) {
		newJLabel message=new newJLabel(text.getText());
		 
		try {
			
		
		message.setBackground(theme);
    	message.setForeground(Color.WHITE);
    	message.setBorder(new EmptyBorder(15,15,15,50));
    	
		JPanel newMessPanel = new JPanel();
	    newMessPanel.setLayout(new BoxLayout(newMessPanel,BoxLayout.Y_AXIS));
		Calendar calender=Calendar.getInstance();
		SimpleDateFormat format=new SimpleDateFormat("HH:mm");
		
		JLabel timeLabel=new JLabel(format.format(calender.getTime()));;

		
		newMessPanel.add(message);
		newMessPanel.add(timeLabel);
		newMessPanel.setBackground(Color.WHITE);
		mPanel.setLayout(new BorderLayout());
		
		JPanel rightAlign=new JPanel(new BorderLayout());
		rightAlign.add(newMessPanel,BorderLayout.LINE_END);
		rightAlign.setBackground(Color.WHITE);
		vertical.add(rightAlign);
		vertical.add(Box.createVerticalStrut(5));
		
		mPanel.add(vertical,BorderLayout.PAGE_START);
		
		
		System.out.println("output arrived here");
		
		out.writeUTF(text.getText()+"@@@"+user);
		System.out.println("1");
		out.writeBoolean(true);
		System.out.println("2");
		out.writeInt(0);
		System.out.println("3");
		out.write(new byte[1]);
		System.out.println("4");
		out.writeInt(0);
		System.out.println("5");
		out.write(new byte[1]);
		System.out.println("6");
		
		text.setText("");
		f.repaint();
		f.invalidate();
		f.validate();
		
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static MouseListener MyMouseListener() {
		return new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				JPanel jpanel = (JPanel)e.getSource();
				int fileId=Integer.parseInt(jpanel.getName());
				for(MyFIles myFile:myFiles) {
					if(myFile.getId()==fileId) {
						JFrame jfPreview =createDownFrame(myFile.getName(),myFile.getData(),myFile.getFileExtension());
						jfPreview.setVisible(true);
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		};
	}
	
	public static JFrame createDownFrame(String filename,byte[] filedata,String fileExtension) {
		JFrame frame=new JFrame("Downloader");
		frame.setSize(400,400);
		
		JPanel panel=new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		
		JLabel notice=new JLabel("Are you sure to download "+filename);
		notice.setFont(new Font("Arial",Font.BOLD,20));
		notice.setBorder(new EmptyBorder(20,0,10,0));
		notice.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		filterJButton jYes=new filterJButton("Yes");
		jYes.setPreferredSize(new Dimension(100,50));
		jYes.setFont(new Font("Arial",Font.BOLD,15));
		
		filterJButton jNo=new filterJButton("No");
		jNo.setPreferredSize(new Dimension(100,50));
		jNo.setFont(new Font("Arial",Font.BOLD,15));
		
		JPanel buttonspanel=new JPanel();
		buttonspanel.setBorder(new EmptyBorder(20,0,10,0));
		buttonspanel.add(jYes);
		buttonspanel.add(jNo);
		
		jYes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				File downloadFile=new File(filename);
				try {
					FileOutputStream outstream=new FileOutputStream(downloadFile);
					outstream.write(filedata);
					outstream.close();
					frame.dispose();
					
				}catch(IOException i) {
					i.printStackTrace();
				}
			}
			
		});
		jNo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
			}
			
		});
	panel.add(notice);
	panel.add(buttonspanel);
	frame.add(panel);
	
	return frame;
	}

	
	private static JPopupMenu createPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItem1 = new JMenuItem("Clear Chat");
       
        
       
        menuItem1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				vertical.removeAll();
				f.validate();
			}
        	
        });
        
        
        
        popupMenu.add(menuItem1);
       

        return popupMenu;
    }
}


