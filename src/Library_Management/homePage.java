package Library_Management;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class homePage {
    private JFrame frame;

    public homePage() {
        // Create the Frame
        frame = new JFrame("Home");
        frame.setSize(600, 500);
        frame.getContentPane().setBackground(Color.cyan);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Heading: Library Management
        JLabel heading = new JLabel("Library Management", SwingConstants.CENTER);
        heading.setBounds(0, 50, 600, 50);
        heading.setFont(new Font("Arial", Font.BOLD, 30));
        heading.setForeground(Color.DARK_GRAY);
        frame.add(heading);

        // Member Register Button
        JButton memberBtn = new JButton("Member Register");
        memberBtn.setBounds(200, 150, 200, 40);
        memberBtn.setFocusable(false);
        frame.add(memberBtn);
        memberBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new member_page();
                frame.dispose();// Opens the registration form
            }
        });

        // Book Button
        JButton bookBtn = new JButton("Book");
        bookBtn.setBounds(200, 220, 200, 40);
        bookBtn.setFocusable(false);
        frame.add(bookBtn);
        bookBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new bookPage(); 
                frame.dispose();
            }
        });
        frame.setVisible(true);
    }
}