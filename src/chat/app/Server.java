package chat.app;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.text.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

import net.miginfocom.swing.MigLayout;

import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Server  implements ActionListener{
	
	newJTextField text;
	public static newJpanel mPanel;
	static Box vertical = Box.createVerticalBox();
	static JFrame f=new JFrame();
	//static DataOutputStream out;
	
	static ServerSocket serv;
	private static ArrayList<ClientsHandler> clients =new ArrayList<>();
	private static ExecutorService pool = Executors.newFixedThreadPool(10);

	private static final String DB_URL = "jdbc:mysql://localhost:3306/chatapp";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "kislay565";
	
	
	

	Server(){
		
		
		newJpanel header=new newJpanel();
		header.setBackground(new Color(7,94,84));
		f.getContentPane().add(header, BorderLayout.NORTH);
		header.setLayout(new MigLayout("", "[35.00px][-12.00px][59.00][19.00][40.00][16.00][40.00][][][][][]", "[24px]"));
		
		f.setTitle("Server");
		f.setIconImage(new ImageIcon(ClassLoader.getSystemResource("icons/logo_myapp.png")).getImage());
		JLabel name=new JLabel("Server");
		name.setForeground(Color.WHITE);
		name.setFont(new Font("San_Serif",Font.BOLD,18));
		
		header.add(name, "cell 2 0");
		
		
		
				
		ImageIcon back=new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("icons/233-2338305_back-white-icon-png-png-download-white-back.png")).getImage().getScaledInstance(37, 37, Image.SCALE_SMOOTH));
		JLabel bAck = new JLabel(back);
		header.add(bAck, "cell 4 0");
		

		bAck.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ee) {
				try {
					serv.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.exit(0);
				
			}
		});
		
		ImageIcon profile=new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("icons/leader_demon_slayer.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));

		JLabel profilePhoto = new JLabel(profile);
		header.add(profilePhoto, "cell 6 0");
		
		
		
		mPanel = new newJpanel();
		
		mPanel.setBackground(Color.WHITE);
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
	    bottom.setVisible(false);
	    f.setSize(465,700);
		f.getContentPane().setBackground(Color.gray);
		
		f.setVisible(true);
		
		
	}

	    
	    
	
	public  static void main(String[] args) {
		 
		 new Server();
		 
		 String username="";
		 String myself="";
		 
		try {
			serv=new ServerSocket(5566);
			while(true) {
				System.out.println("Server waiting for client");
				Socket s=serv.accept();
				Thread.sleep(500);
				Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	            System.out.println("Connected");

	            // Create a prepared statement to insert the registration data
	            String query = "SELECT * FROM connections WHERE socketAddress=?";
	           
	            PreparedStatement statement = connection.prepareStatement(query);
	            statement.setString(1, String.valueOf(s.getRemoteSocketAddress()));
	            ResultSet rs=statement.executeQuery();
	            while(rs.next()) {
	            	System.out.print("inside rs");
	            	myself=rs.getString("username");
	            	
	            	username=rs.getString("participant");
	            	System.out.println(username);
					System.out.println(myself);
	            }
				statement.close();
				System.out.println("Server connected for client");
				System.out.println(s.getRemoteSocketAddress());
				System.out.println(Thread.currentThread().getName());
				System.out.println(Thread.currentThread().getPriority());
				System.out.println(username);
				System.out.println(myself);
				ClientsHandler clientThread=new ClientsHandler(s,clients,username,myself);
				clients.add(clientThread);
				pool.execute(clientThread);
				
			}
		}catch(IOException i) {
			i.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	public void actionPerformed(ActionEvent e) {
		newJLabel message=new newJLabel(text.getText());
		try {
		
		
		message.setBackground(new Color(7,94,84));
    	message.setForeground(Color.WHITE);
    	message.setBorder(new EmptyBorder(15,15,15,50));
    	
		JPanel newMessPanel = new JPanel();
		newMessPanel.setBackground(Color.WHITE);
	    newMessPanel.setLayout(new BoxLayout(newMessPanel,BoxLayout.Y_AXIS));
		Calendar calender=Calendar.getInstance();
		SimpleDateFormat format=new SimpleDateFormat("HH:mm");
		
		JLabel timeLabel=new JLabel(format.format(calender.getTime()));;
		
		
		newMessPanel.add(message);
		newMessPanel.add(timeLabel);
		
		mPanel.setLayout(new BorderLayout());
		
		JPanel rightAlign=new JPanel(new BorderLayout());
		rightAlign.add(newMessPanel,BorderLayout.LINE_END);
		rightAlign.setBackground(Color.WHITE);
		vertical.add(rightAlign);
		vertical.add(Box.createVerticalStrut(5));
		
		mPanel.add(vertical,BorderLayout.PAGE_START);
		
		
		text.setText("");
		f.repaint();
		f.invalidate();
		f.validate();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	 
	
}

 class newJpanel extends javax.swing.JPanel {

    public newJpanel() {
        setOpaque(false);

    }

    public newJpanel(BorderLayout borderLayout) {
		// TODO Auto-generated constructor stub
    	super(borderLayout);
    	setOpaque(false);
	}

	@Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 60, 60));
        g2.dispose();
        super.paintComponent(grphcs);
    }
 }
 class newJTextField extends javax.swing.JTextField {

	    public newJTextField() {
	        setOpaque(false);
	        setBorder(null);
	    }

	    @Override
	    protected void paintComponent(Graphics grphcs) {
	        Graphics2D g2 = (Graphics2D) grphcs.create();
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g2.setColor(getBackground());
	        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 50, 50));
	        g2.dispose();
	        super.paintComponent(grphcs);
	    }
	 }
 
 class newJButton extends javax.swing.JButton {

	    public newJButton (ImageIcon icon) {
	    	super(icon);
	    	Dimension size = getPreferredSize();
	    	size.width = size.height = Math.max(size.width, size.height);
	    	setPreferredSize(size);
	    	setContentAreaFilled(false);
	    }
	    public newJButton (String text) {
	    	super(text);
	    	Dimension size = getPreferredSize();
	    	size.width = size.height = Math.max(size.width, size.height);
	    	setPreferredSize(size);
	    	setContentAreaFilled(false);
	    }
	    
	    protected void paintComponent(Graphics g) {
	        if (getModel().isArmed()) {
	              g.setColor(Color.lightGray);
	        } else {
	             g.setColor(getBackground());
	        }
	        g.fillOval(0, 0, getSize().width-1, getSize().height-1);
	        super.paintComponent(g);
	   }
	   protected void paintBorder(Graphics g) {
	        g.setColor(new Color(7,94,84));
	        g.drawOval(0, 0, getSize().width-1, getSize().height-1);
	   }
	   Shape shape;
	   public boolean contains(int x, int y) {
	        if (shape == null || !shape.getBounds().equals(getBounds())) {
	             shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
	        }
	        return shape.contains(x, y);
	   }
	 }
 
 
 class newJLabel extends javax.swing.JLabel {

	    public newJLabel (String text) {
	    	super(text);
	    	
	    	setOpaque(false);	    	
	    	
	    }
	    
	    
	    
	    protected void paintComponent(Graphics grphcs) {
	        Graphics2D g2 = (Graphics2D) grphcs.create();
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g2.setColor(getBackground());
	        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 50, 50));
	        g2.dispose();
	        super.paintComponent(grphcs);
	    }
 }
 
 class CustomScrollBarUI extends BasicScrollBarUI {
	    private final Dimension BUTTON_SIZE = new Dimension(0, 0);
	    private final Color SCROLL_BAR_COLOR = Color.WHITE;
	    private final Color THUMB_COLOR = new Color(100, 100, 100);
	    
	    
	    
	    @Override
	    protected JButton createDecreaseButton(int orientation) {
	        JButton button = new JButton();
	        button.setPreferredSize(BUTTON_SIZE);
	       
	        button.setVisible(false);
	        return button;
	    }

	    @Override
	    protected JButton createIncreaseButton(int orientation) {
	        JButton button = new JButton();
	        button.setPreferredSize(BUTTON_SIZE);
	        button.setVisible(false);
	        return button;
	    }

	    @Override
	    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
	        g.setColor(SCROLL_BAR_COLOR);
	        g.fillRect(trackBounds.x, trackBounds.y,trackBounds.width, trackBounds.height);
	    }

	    @Override
	    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
	        if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
	            return;
	        }

	        Graphics2D g2 = (Graphics2D) g.create();
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g2.setColor(THUMB_COLOR);
	        g2.fillRoundRect(thumbBounds.x, thumbBounds.y, 4, thumbBounds.height, 10,10);
	        g2.dispose();
	    }
	}
 
