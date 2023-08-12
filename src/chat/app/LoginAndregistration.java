package chat.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import net.miginfocom.swing.MigLayout;


public class LoginAndregistration {

    private JFrame frmLoginregister;
    private newJTextField usernameField;
    private newJPasswordField passwordField;
    private newJTextField nameField;
    private filterJButton registerButton;
    private filterJButton loginButton;
    private Map<String, String> userMap;

    // JDBC connection parameters
    private static final String DB_URL = "jdbc:mysql://localhost:3306/chatapp";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "kislay565";

    public LoginAndregistration() {
        frmLoginregister = new JFrame("Chat App");
        String title="si$pleChat";
        title=title.replace("$", "(\\/)");
        frmLoginregister.setTitle(title);
        frmLoginregister.setBackground(new Color(163,166,153));
      
        frmLoginregister.setSize(500, 500);
               
        newJpanel pane=new newJpanel();
        pane.setBackground(new Color(198,201,198));
        frmLoginregister.setContentPane(pane);
        
        ImageIcon logo=new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("icons/logo_myapp.png")).getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH));
		JLabel Logo = new JLabel(logo);
		frmLoginregister.setIconImage(new ImageIcon(ClassLoader.getSystemResource("icons/logo_myapp.png")).getImage());

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Yu Gothic UI", Font.PLAIN, 14));
        usernameField = new newJTextField();
        usernameField.setBorder(new EmptyBorder(6,20,1,5));
        usernameField.setForeground(Color.BLACK);
        usernameField.setFont(new Font("Yu Gothic", Font.PLAIN, 14));

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Yu Gothic UI", Font.PLAIN, 14));
        passwordField = new newJPasswordField();
        passwordField.setBorder(new EmptyBorder(6,20,1,5));
        passwordField.setFont(new Font("Yu Gothic", Font.PLAIN, 14));
        
        JLabel NameLabel = new JLabel("Name");
        NameLabel.setFont(new Font("Yu Gothic UI", Font.PLAIN, 14));
        nameField = new newJTextField();
        nameField.setBorder(new EmptyBorder(6,20,1,5));
        
        nameField.setFont(new Font("Yu Gothic", Font.PLAIN, 14));

        registerButton = new filterJButton("Register");
        registerButton.setForeground(new Color(255, 255, 255));
        registerButton.setFont(new Font("Yu Gothic UI", Font.PLAIN, 14));
       
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });

        loginButton = new filterJButton("Login");
        loginButton.setForeground(new Color(255, 255, 255));
        loginButton.setFont(new Font("Yu Gothic UI", Font.PLAIN, 14));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        userMap = new HashMap<>(); // Map to store registered users
        frmLoginregister.getContentPane().setLayout(new MigLayout("", "[87.00px][128.00px][][145.00]", "[69.00px][38.00px][29.00px][39.00px][36.00][37.00][27.00][50.00]"));
        
        frmLoginregister.getContentPane().add(Logo, "cell 2 0");
        frmLoginregister.getContentPane().add(NameLabel, "flowy,cell 1 1,grow");
        frmLoginregister.getContentPane().add(nameField, "cell 3 1,grow");
        frmLoginregister.getContentPane().add(usernameLabel, "cell 1 3,grow");
        frmLoginregister.getContentPane().add(usernameField, "cell 3 3,grow");
        frmLoginregister.getContentPane().add(passwordLabel, "cell 1 5,grow");
        frmLoginregister.getContentPane().add(passwordField, "cell 3 5,grow");
        frmLoginregister.getContentPane().add(registerButton, "cell 1 7,alignx center");
        frmLoginregister.getContentPane().add(loginButton, "cell 3 7,alignx center");

        frmLoginregister.setVisible(true);
    }

    private void register() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String Name=nameField.getText();
        String uname="";
        
        try {
       	 Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected");

            // Create a prepared statement to insert the registration data
            String query = "SELECT username FROM users WHERE username=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet result=statement.executeQuery();
            
            while(result.next()) {
           	 uname=result.getString("username");
            }
            }catch(SQLException ex) {
            	ex.printStackTrace();
            }

        if (username.isEmpty() || password.isEmpty() || Name.isEmpty()) {
            JOptionPane.showMessageDialog(frmLoginregister, "Please enter a Name,username and password.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (uname==username) {
            JOptionPane.showMessageDialog(frmLoginregister, "Username already exists. Please choose a different username.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Store the registration data in the database
        if (storeRegistrationData(username, password,Name)) {
            userMap.put(username, password);
            JOptionPane.showMessageDialog(frmLoginregister, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(frmLoginregister, "Failed to register. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean storeRegistrationData(String username, String password,String name) {
    	String table_name=name.split(" ",2)[0]+getAlphaNumericString(5);
        try {
            // Establish a database connection
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected");

            // Create a prepared statement to insert the registration data
            String query = "INSERT INTO users (username, password,name,table_name) VALUES (?, ?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, name);
            statement.setString(4, table_name);
            int rowsAffected = statement.executeUpdate();

            
            String oldquery="CREATE TABLE  $tableName (contact_id INT , FOREIGN KEY(contact_id) REFERENCES users(userid) ON UPDATE RESTRICT ON DELETE CASCADE,username VARCHAR(255) ,nickname VARCHAR(255)) ENGINE=INNODB";
            // Execute the query
            String newquery=oldquery.replace("$tableName",table_name);
            PreparedStatement newstatement = connection.prepareStatement(newquery);
            
            newstatement.executeUpdate();
            // Close the resources
            statement.close();
            newstatement.close();
            connection.close();

            return rowsAffected > 0; // Return true if the registration data was successfully inserted
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        try {
        	 Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             System.out.println("Connected");

             // Create a prepared statement to insert the registration data
             String query = "SELECT * FROM users WHERE username=?";
         //    query.replace("$username", username);
             PreparedStatement statement = connection.prepareStatement(query);
             statement.setString(1,username);
             ResultSet result=statement.executeQuery();
             String uname="";
             String upassword="";
             while(result.next()) {
            	 uname=result.getString("username");
            	 upassword=result.getString("password");
             }
             
             if (username.isEmpty() || password.isEmpty()) {
                 JOptionPane.showMessageDialog(frmLoginregister, "Please enter a username and password.", "Error", JOptionPane.ERROR_MESSAGE);
                 return;
             }

             if (!uname.contains(username) || !upassword.contains(password)) {
                 JOptionPane.showMessageDialog(frmLoginregister, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                 return;
             }
             statement.close();
             
        }catch(SQLException e) {
        	e.printStackTrace();
        }
        

        
        
        JOptionPane.showMessageDialog(frmLoginregister, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        clearFields();
        new Clients_Contacts(username);
        frmLoginregister.dispose();
       
    }

    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        nameField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginAndregistration();
            }
        });
    }
    
    static String getAlphaNumericString(int n)
    {
    
     // choose a Character random from this String
     String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "0123456789"
            + "abcdefghijklmnopqrstuvxyz";
    
     // create StringBuffer size of AlphaNumericString
     StringBuilder sb = new StringBuilder(n);
    
     for (int i = 0; i < n; i++) {
    
      // generate a random number between
      // 0 to AlphaNumericString variable length
      int index
       = (int)(AlphaNumericString.length()
         * Math.random());
    
      // add Character one by one in end of sb
      sb.append(AlphaNumericString
         .charAt(index));
     }
    
     return sb.toString();
    }
    
    
}

class filterJButton extends JButton{
	public filterJButton (String text) {
    	super(text);
    	setPreferredSize(new Dimension(110,40));
    	setBorderPainted(false);
        setBackground(new Color(7,94,81));

    	setContentAreaFilled(false);
    }
	
	protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
        g2.dispose();
        super.paintComponent(grphcs);
    }
}


class newJPasswordField extends javax.swing.JPasswordField {

    public newJPasswordField() {
    	//super()
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



