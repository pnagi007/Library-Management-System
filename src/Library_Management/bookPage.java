package Library_Management;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class bookPage {
    private JFrame frame;
    private JComboBox<Integer> idBox;
    private JTextField titleField, authorField, copiesField;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/library_db";
    private static final String USER = "root";
    private static final String PASS = "Pnagi2005&";

    public bookPage() {
        frame = new JFrame("Book Details");
        frame.setSize(400, 400);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.cyan);

        JLabel l1 = new JLabel("Select Book ID:");
        l1.setBounds(50, 50, 100, 30);
        frame.add(l1);

        idBox = new JComboBox<>();
        idBox.setBounds(160, 50, 150, 30);
        frame.add(idBox);

        // Labels and Fields
        createField("Title:", 100, titleField = new JTextField());
        createField("Author:", 150, authorField = new JTextField());
        createField("Copies:", 200, copiesField = new JTextField());

        loadBookIDs(); // Fill the ComboBox from Database

        // Action when an ID is selected
        idBox.addActionListener(e -> fetchBookDetails());
        
        JButton backBtn = new JButton("BACK");
        backBtn.setBounds(50, 300, 100, 30); 
        backBtn.setBackground(Color.DARK_GRAY);
        backBtn.setForeground(Color.WHITE);
        frame.add(backBtn);

        // 2. Add the action listener here
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Closes the Book Details window
                new homePage();  // Opens a fresh Home Page
            }
        });

        frame.setVisible(true);
    }

    private void createField(String text, int y, JTextField field) {
        JLabel label = new JLabel(text);
        label.setBounds(50, y, 100, 30);
        frame.add(label);
        field.setBounds(160, y, 150, 30);
        field.setEditable(false); // Make them read-only
        frame.add(field);
    }

    private void loadBookIDs() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT book_id FROM books")) {
            while (rs.next()) {
                idBox.addItem(rs.getInt("book_id"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void fetchBookDetails() {
        int selectedId = (int) idBox.getSelectedItem();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM books WHERE book_id = ?")) {
            pstmt.setInt(1, selectedId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                titleField.setText(rs.getString("title"));
                authorField.setText(rs.getString("author"));
                copiesField.setText(String.valueOf(rs.getInt("available_copies")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
   
}