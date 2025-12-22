package Library_Management;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class member_page {
    private JFrame frame;
    private JTextField nameField, addressField, emailField;
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library_db";
    private static final String USER = "root";
    private static final String PASS = "Pnagi2005&";

    public member_page() {
        frame = new JFrame("Member Registration");
        frame.setSize(400, 450);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(230, 230, 250)); // Lavender background

        JLabel heading = new JLabel("New Member Registration", SwingConstants.CENTER);
        heading.setBounds(0, 20, 400, 30);
        heading.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(heading);

        // Input Fields
        createLabelAndField("Name:", 80, nameField = new JTextField());
        createLabelAndField("Address:", 140, addressField = new JTextField());
        createLabelAndField("Email:", 200, emailField = new JTextField());

        // Register Button
        JButton regBtn = new JButton("REGISTER");
        regBtn.setBounds(125, 280, 150, 40);
        regBtn.setBackground(new Color(60, 179, 113)); // Sea Green
        regBtn.setForeground(Color.WHITE);
        frame.add(regBtn);

        regBtn.addActionListener(e -> registerMember());
        
        JButton backBtn = new JButton("BACK");
        backBtn.setBounds(125, 340, 150, 30); 
        backBtn.setBackground(Color.DARK_GRAY);
        backBtn.setForeground(Color.WHITE);
        frame.add(backBtn);

        // 2. Add the action listener here
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Closes the Member Registration window
                new homePage();  // Opens a fresh Home Page
            }
        });

        frame.setVisible(true);
    }

    private void createLabelAndField(String text, int y, JTextField field) {
        JLabel label = new JLabel(text);
        label.setBounds(50, y, 100, 30);
        frame.add(label);
        field.setBounds(150, y, 180, 30);
        frame.add(field);
    }

    private void registerMember() {
        String name = nameField.getText();
        String address = addressField.getText();
        String email = emailField.getText();

        if (name.isEmpty() || address.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill all details!");
            return;
        }

        String sql = "INSERT INTO members (name, address, email) VALUES (?, ?, ?)";

        // We use RETURN_GENERATED_KEYS to get the ID created by MySQL
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, name);
            pstmt.setString(2, address);
            pstmt.setString(3, email);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        String message = "Registration Successful!\n" +
                                         "Name: " + name + "\n" +
                                         "Member ID: " + id;
                        JOptionPane.showMessageDialog(frame, message, "Success", JOptionPane.INFORMATION_MESSAGE);
                        clearFields();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Database Error: " + e.getMessage());
        }
    }

    private void clearFields() {
        nameField.setText("");
        addressField.setText("");
        emailField.setText("");
    }
}