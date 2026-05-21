/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DELL
 */


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DELL
 */


import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class StudentDashboard extends JFrame {

    private String username;

    public StudentDashboard(String username) {
        this.username = username;

        setTitle("Student Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        /* ================= BACKGROUND IMAGE ================= */
        ImageIcon bg = new ImageIcon(
                getClass().getResource("/icons/collegeA.jpg")
        );
        JLabel background = new JLabel(bg);
        background.setLayout(new GridBagLayout());
        add(background, BorderLayout.CENTER);

        /* ================= MAIN CARD ================= */
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(420, 360));
        card.setBackground(new Color(255, 255, 255, 220));
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        background.add(card);

        /* ================= TITLE BAR ================= */
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        titlePanel.setBackground(new Color(20, 40, 80));

        ImageIcon icon = new ImageIcon(
                getClass().getResource("/icons/student.png")
        );
        JLabel iconLabel = new JLabel(icon);

        JLabel title = new JLabel("Student Dashboard");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));

        titlePanel.add(iconLabel);
        titlePanel.add(title);

        card.add(titlePanel, BorderLayout.NORTH);

        /* ================= BUTTONS PANEL ================= */
        JPanel btnPanel = new JPanel();
        btnPanel.setOpaque(false);
        btnPanel.setLayout(new GridLayout(4, 1, 15, 15));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(60, 40, 30, 40));

        JButton profileBtn = createButton("Profile");
        JButton resultBtn = createButton("Results");
        JButton logoutBtn = createButton("Logout");

        btnPanel.add(profileBtn);        
        btnPanel.add(resultBtn);
        btnPanel.add(logoutBtn);

        card.add(btnPanel, BorderLayout.CENTER);

        /* ================= BUTTON ACTIONS ================= */

        profileBtn.addActionListener(e -> showProfile()); 
         resultBtn.addActionListener(e -> showResult());
        logoutBtn.addActionListener(e -> {
            dispose();
            JOptionPane.showMessageDialog(this, "Logged out successfully");
        });

        setVisible(true);
    }

    /* ================= BUTTON DESIGN ================= */
    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(new Color(30, 60, 120));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }

    /* ================= PROFILE ================= */
 private void showProfile() {

    try {

        Connection con = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres",
        "postgres",
        "bhakti"
        );

        String sql =
        "SELECT s.student_name, s.roll_number, s.year, u.email, u.phone, u.course " +
        "FROM students s JOIN users u ON s.user_id = u.user_id " +
        "WHERE u.username = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, username);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){

            String name = rs.getString("student_name");
            String roll = rs.getString("roll_number");
            String year = rs.getString("year");
            String email = rs.getString("email");
            String phone = rs.getString("phone");
            String course = rs.getString("course");

            new StudentProfile(name,roll,course,phone,email,year);

        }

    } catch(Exception e){
        JOptionPane.showMessageDialog(this,"Error loading profile");
    }
}
 
 
 private void showResult() {

    try {

        Connection con = DBConnection.getConnection();

        String sql =
        "SELECT s.student_name, m.course, m.marks " +
        "FROM marks m " +
        "JOIN students s ON m.student_id = s.student_id " +
        "JOIN users u ON s.user_id = u.user_id " +
        "WHERE u.username = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, username);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){

            String name = rs.getString("student_name");
            String course = rs.getString("course");
            int marks = rs.getInt("marks");

            new StudentResult(name, course, marks);

        } else {

            JOptionPane.showMessageDialog(this,"Result not uploaded yet");

        }

        rs.close();
        ps.close();
        con.close();

    } catch(Exception e){
        JOptionPane.showMessageDialog(this,"Error loading result");
    }
}
    
}