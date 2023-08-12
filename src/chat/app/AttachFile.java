package chat.app;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
public class AttachFile {
	static JFrame athFile;
	static boolean trigger;
	public static void   newFrameCreate(DataOutputStream out) {
		final File[] toSendFile=new File[1];
		trigger=true;
		athFile=new JFrame("Attach File");
		athFile.setSize(350,350);
		athFile.setLayout(new BoxLayout(athFile.getContentPane(),BoxLayout.Y_AXIS));
		//athFile.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		athFile.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        // Add a window listener to handle the close event
        athFile.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setValue(false);
                athFile.dispose();
                //System.exit(0);
            }
        });
		System.out.println("Inside Attach File class");
		newJpanel FileName=new newJpanel();
		FileName.setBackground(new Color(7,94,84));
		
		
		
		
		JLabel fname=new JLabel("Choose a file to attach");
		fname.setFont(new Font("San_Serif",Font.PLAIN,16));
		fname.setForeground(Color.WHITE);
		
		fname.setAlignmentX(Component.CENTER_ALIGNMENT);
		fname.setBorder(new EmptyBorder(50,0,0,0));
		FileName.add(fname);
		
		JPanel buttonpanel = new JPanel();
		buttonpanel.setBorder(new EmptyBorder(50,0,0,0));
		
		newJButton chooseFile=new newJButton("Choose File");
		
		chooseFile.setForeground(Color.WHITE);
		chooseFile.setBackground(new Color(7,94,84));
		chooseFile.setPreferredSize(new Dimension(120,75));
		
		newJButton sendFile=new newJButton("Send File");
		
		sendFile.setForeground(Color.WHITE);
		sendFile.setBackground(new Color(7,94,84));
		sendFile.setPreferredSize(new Dimension(120,75));
		
		buttonpanel.add(chooseFile);
		buttonpanel.add(sendFile);
		
		chooseFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser fileChooser=new JFileChooser();
				fileChooser.setDialogTitle("Choose a file to attach");
				if(fileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
					toSendFile[0]=fileChooser.getSelectedFile();
					fname.setText("File Attached : "+toSendFile[0].getName());
				}
				
			}
			
		});
		
		sendFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(toSendFile[0]==null) {
					fname.setText("Choose a File First");
					
				}
				else {
					try {
						FileInputStream input=new FileInputStream(toSendFile[0].getAbsolutePath());
						String nameOfFile=toSendFile[0].getName();
						byte[] nameOfFileBytes = nameOfFile.getBytes();
						
						byte[] fileContentBytes=new byte[(int)toSendFile[0].length()];
						input.read(fileContentBytes);
						
						out.writeUTF("nothing");
						out.writeBoolean(false);
						System.out.println("Inside out false of attach");
						out.writeInt(nameOfFileBytes.length);
						System.out.println("Inside out length"+nameOfFileBytes.length);
						out.write(nameOfFileBytes);
						System.out.println("Inside out length"+nameOfFileBytes);
						out.writeInt(fileContentBytes.length);
						out.write(fileContentBytes);
						out.flush();
					}catch(IOException er) {
						er.printStackTrace();
					}
					
					
					
				}
				setValue(false);
				setValue(true);
				athFile.dispose();
			}
			
			
		});
		
		athFile.add(FileName);
		athFile.add(buttonpanel);
		athFile.setVisible(true);
		
	}
	
	
	
	public static String getFileExtension(String filename) {
		int i= filename.lastIndexOf('.');
		if(i>0) {
			return filename.substring(i+1);
		
		}
		else {
			return "No extension found";
		}
	}
	
	public static boolean getValue() {
		return trigger;
	}
	
	public static void setValue(boolean b) {
		trigger=b;
	}

}
