package Library_Management;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class GUI{
	
	private static final String DB_URL = "jdbc:mysql://localhost:3306/library_db";
    private static final String USER = "root"; 
    private static final String PASS = "Pnagi2005&";
    
    private JFrame f;
    private JTextField usernameField;
    private JPasswordField passwordField;
    
    public GUI()
    {

		f=new JFrame("Admin Login");
		f.getContentPane().setBackground(Color.cyan);
		f.setSize(500,500);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(null);
		
		JLabel name=new JLabel("Username:");
		name.setBounds(0, 40, 200, 30);
		name.setOpaque(true);
		name.setBackground(Color.white);
		name.setForeground(Color.black);
		f.add(name);
		
		usernameField=new JTextField();
		usernameField.setBounds(0, 70, 200, 30);
		usernameField.setOpaque(true);
		usernameField.setBackground(Color.red);
		usernameField.setForeground(Color.yellow);
		f.add(usernameField);
		
		JLabel name1=new JLabel("Password:");
		name1.setBounds(0, 120, 200, 30);
		name1.setOpaque(true);
		name1.setBackground(Color.white);
		name1.setForeground(Color.black);
		f.add(name1);
		
		passwordField=new JPasswordField();
		passwordField.setBounds(0, 150, 200, 30);
		passwordField.setOpaque(true);
		passwordField.setBackground(Color.red);
		passwordField.setForeground(Color.yellow);
		f.add(passwordField);
		
		
		JButton name2=new JButton("SIGN IN");
		f.add(name2);
		name2.setBounds(0, 200, 100, 30);
		name2.setOpaque(true);
		name2.setBackground(Color.white);
		name2.setForeground(Color.black);
		
		name2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticateUser();
            }
        });
		
		f.setVisible(true);
    }
    
    private void authenticateUser() {
    	String username = usernameField.getText();
        String password = new String(passwordField.getPassword()); 
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(f, "Please enter both fields", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "SELECT * FROM admins WHERE username = ? AND password = ?";
        
        try (
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(f, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                
                new homePage(); 
                f.dispose();
            } else {
                JOptionPane.showMessageDialog(f, "Invalid Username or Password.", "Error", JOptionPane.ERROR_MESSAGE);
            }            
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(f, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
    }
		
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			new GUI();
		
	}	

}
