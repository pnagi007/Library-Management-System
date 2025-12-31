package Library_Management;

import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import javax.swing.*;

public class IssueBookPage {
    private JFrame frame;
    private JComboBox<Integer> bookIdBox, memberIdBox;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library_db";
    private static final String USER = "root";
    private static final String PASS = "Pnagi2005&";

    public IssueBookPage() {
        frame = new JFrame("Issue Book");
        frame.setSize(400, 400);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(255, 228, 196)); // Bisque color

        JLabel l1 = new JLabel("Select Book ID:");
        l1.setBounds(50, 50, 120, 30);
        frame.add(l1);

        bookIdBox = new JComboBox<>();
        bookIdBox.setBounds(180, 50, 150, 30);
        frame.add(bookIdBox);

        JLabel l2 = new JLabel("Select Member ID:");
        l2.setBounds(50, 100, 120, 30);
        frame.add(l2);

        memberIdBox = new JComboBox<>();
        memberIdBox.setBounds(180, 100, 150, 30);
        frame.add(memberIdBox);

        JButton issueBtn = new JButton("ISSUE BOOK");
        issueBtn.setBounds(50, 180, 280, 40);
        issueBtn.setBackground(new Color(30, 144, 255)); // Dodger Blue
        issueBtn.setForeground(Color.WHITE);
        frame.add(issueBtn);

        JButton backBtn = new JButton("BACK");
        backBtn.setBounds(140, 240, 100, 30);
        frame.add(backBtn);

        // Load data into boxes
        loadData();

        issueBtn.addActionListener(e -> issueBook());
        backBtn.addActionListener(e -> { frame.dispose(); new homePage(); });

        frame.setVisible(true);
    }

    private void loadData() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            // Load Book IDs
            ResultSet rsBooks = conn.createStatement().executeQuery("SELECT book_id FROM books");
            while (rsBooks.next()) bookIdBox.addItem(rsBooks.getInt("book_id"));

            // Load Member IDs
            ResultSet rsMembers = conn.createStatement().executeQuery("SELECT member_id FROM members");
            while (rsMembers.next()) memberIdBox.addItem(rsMembers.getInt("member_id"));
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void issueBook() {
        int bId = (int) bookIdBox.getSelectedItem();
        int mId = (int) memberIdBox.getSelectedItem();
        String today = LocalDate.now().toString(); // Gets current date

        String sql = "INSERT INTO issued_books (book_id, member_id, issue_date) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bId);
            pstmt.setInt(2, mId);
            pstmt.setString(3, today);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(frame, "Book Issued Successfully to Member " + mId);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage());
        }
    }
}