package chat.app;

import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;


public class ClientsHandler implements Runnable {
	
	private Socket client;
	private DataInputStream in;
	private DataOutputStream out;
	
	private ArrayList<ClientsHandler> clients;
	private String username;
	private String myself;
	
	public ClientsHandler(Socket clientSocket,ArrayList<ClientsHandler> clients,String username,String myself) throws IOException {
		this.client=clientSocket;
		this.username=username;
		this.myself=myself;
		this.clients=clients;
		in=new DataInputStream(client.getInputStream());
		out = new DataOutputStream(client.getOutputStream());
		
		
	}
	
	
	@Override
	public void run() {
		
		System.out.println(client.getRemoteSocketAddress());
		
		
		System.out.println("Client in Thread");
		System.out.println("4"+Thread.currentThread().getName());
		// TODO Auto-generated method stub
		try {
			
			
			boolean loop =true;
			while(loop) {
				

				
				System.out.println("wrong");
				String Mess=in.readUTF();
				System.out.println("Message arrived"+Mess);
				Boolean check=in.readBoolean();
				System.out.println("check arrived"+check);
				
					String []messArr=Mess.split("@@@",2);
					String inMess=messArr[0];
					System.out.println("Client promted"+inMess);
					
				
					newJLabel received=new newJLabel(inMess);
					JPanel ReecrivedPanel = new JPanel();
					ReecrivedPanel.setLayout(new BoxLayout(ReecrivedPanel,BoxLayout.Y_AXIS));
					received.setBackground(Color.BLACK);
					received.setForeground(Color.WHITE);
					received.setBorder(new EmptyBorder(15,15,15,50));
					
					
					if(check) {
						System.out.println("Message panel added");
						JLabel name=new JLabel(messArr[1]);
						ReecrivedPanel.add(received);
						ReecrivedPanel.add(name);
						
					
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
					
					int fileId=0;
					
					
					String filename=new String(fileNameBytes);
					
					
					
					if(fileContentLength>0) {
						
						
						System.out.println("Inside Content loop");
						newJLabel receivedFile=new newJLabel(filename);
						
						receivedFile.setBackground(Color.BLUE);
						receivedFile.setForeground(Color.WHITE);
						receivedFile.setBorder(new EmptyBorder(15,15,15,50));
						
						if(AttachFile.getFileExtension(filename).equalsIgnoreCase("txt")) {
							ReecrivedPanel.setName(String.valueOf(fileId));
							
								ReecrivedPanel.add(receivedFile);
						}
						else {
							ReecrivedPanel.setName(String.valueOf(fileId));
							ReecrivedPanel.add(receivedFile);
						}
						
					}
					
				}
				
				for(ClientsHandler h: clients) {
					System.out.println(myself+" "+username);
					System.out.println(h.myself+" "+h.username);
					if(h.myself.contains(username) && h.username.contains(myself)) {
						h.out.writeUTF(inMess);
						System.out.println("out from server");
						h.out.writeBoolean(check);
						h.out.writeInt(fileNameLength);
						h.out.write(fileNameBytes);
						h.out.writeInt(fileContentLength);
						h.out.write(fileContentBytes);
					}
				}
				
				
				
				
				
		    	
				Calendar cal=Calendar.getInstance();
				SimpleDateFormat format=new SimpleDateFormat("HH:mm");
				
				JLabel recivedtimeLabel=new JLabel(format.format(cal.getTime()));;
				
				
				
				ReecrivedPanel.add(recivedtimeLabel);
				ReecrivedPanel.setBackground(Color.WHITE);
			
				
				
				Server.mPanel.setLayout(new BorderLayout());
				
				JPanel leftAlign=new JPanel(new BorderLayout());
				leftAlign.add(ReecrivedPanel,BorderLayout.LINE_START);
				leftAlign.setBackground(Color.WHITE);
				Server.vertical.add(leftAlign);
				Server.vertical.add(Box.createVerticalStrut(5));
				
				Server.mPanel.add(Server.vertical,BorderLayout.PAGE_START);
				Server.f.validate();
				if (in.available() != 0) {
	                
					in.readUTF();
	            }
				
				System.out.println("bad");
				
			}
		}catch(IOException e) {
				e.printStackTrace();
			}
		   finally {
			   
			   try {
				in.close();
				out.close();
				System.out.println("out closed");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   }
		}
	
	
		
}


