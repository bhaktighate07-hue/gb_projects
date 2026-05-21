
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DELL
 */

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JFrame {

    public static final String DB_URL
            = "jdbc:postgresql://localhost:5432/postgres";
    public static final String DB_USER = "postgres";
    public static final String DB_PASS = "bhakti";

    JTextField usernameField;
    JPasswordField passwordField;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login().createUI());
    }

    public void createUI() {

        JFrame frame = new JFrame("University Management System");
        frame.setSize(450, 550);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 🌈 Gradient Background
        JPanel background = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(220, 230, 255),
                        0, getHeight(), new Color(180, 200, 255));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // 💳 Card Panel
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(350, 420));
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1, true),
                new EmptyBorder(25, 25, 25, 25)
        ));

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        styleTextField(usernameField);
        styleTextField(passwordField);

        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");

        styleButton(loginBtn, new Color(30, 144, 255));
        styleButton(registerBtn, new Color(46, 204, 113));

        // Labels Left Align
        JLabel userLabel = new JLabel("Username");
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel passLabel = new JLabel("Password");
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // card.add(titlePanel);
        card.add(Box.createRigidArea(new Dimension(0, 25)));

        card.add(userLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(usernameField);

        card.add(Box.createRigidArea(new Dimension(0, 15)));

        card.add(passLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(passwordField);

        card.add(Box.createRigidArea(new Dimension(0, 25)));
        card.add(loginBtn);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(registerBtn);

        background.add(card);
        add(background);
        setVisible(true);

        // 🔒 LOGIN LOGIC (UNCHANGED)
        loginBtn.addActionListener(e -> {

            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (username.isEmpty() && password.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please enter Username and Password");
                return;
            }

            if (!username.isEmpty() && password.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please enter the Password");
                return;
            }
 
            if (username.isEmpty() && !password.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please enter the Username");
                return;
            }

            try (Connection con = getConnection()) {

                String sql
                        = "SELECT u.user_id, u.role, u.phone, "
                        + "f.faculty_name, f.designation, f.qualification, "
                        + "s.student_name, "
                        + "COALESCE(f.department_id, s.department_id) AS department_id, "
                        + // ✅ ADDED
                        "COALESCE(ca.college_id, d.college_id) AS college_id, "
                        + "c.college_name, c.background_image "
                        + "FROM users u "
                        + "LEFT JOIN faculty f ON u.user_id = f.user_id "
                        + "LEFT JOIN students s ON u.user_id = s.user_id "
                        + "LEFT JOIN departments d ON COALESCE(f.department_id, s.department_id) = d.department_id "
                        + "LEFT JOIN college_admin ca ON u.user_id = ca.user_id "
                        + "LEFT JOIN colleges c ON c.college_id = COALESCE(ca.college_id, d.college_id) "
                        + "WHERE u.username = ? AND u.password = ? AND u.is_active = true";

                
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, username);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    String role = rs.getString("role");
                    int userId = rs.getInt("user_id");
                    String facultyName = rs.getString("faculty_name");
                    String mobile = rs.getString("phone");
                    String qualification = rs.getString("qualification");
                    int departmentId = rs.getInt("department_id");
                    String designation = rs.getString("designation");
                    String collegeName = rs.getString("college_name");
                    String bgPath = rs.getString("background_image");
                    int collegeId = rs.getInt("college_id");
                    dispose();

                    switch (role) {
                        case "UNIVERSITY_ADMIN":
                            new UniversityAdminDashboard();
                            break;
                        case "COLLEGE_ADMIN":
                            new CollegeAdminDashboard(collegeName, bgPath, collegeId).setVisible(true);
                            break;
                        case "FACULTY":
                            new FacultyDashboard(userId, facultyName, collegeName, mobile,
                                    qualification, departmentId, designation).setVisible(true);
                            break;
                        case "STUDENT":
                            new StudentDashboard(username);
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Invalid Role");
                    }

                } else {
                    JOptionPane.showMessageDialog(this,
                            "Invalid Username or Password");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Database Error: " + ex.getMessage());
            }
        });

        registerBtn.addActionListener(e -> {
            new StudentRegistrationForm().setVisible(true); 
        });
    }

    private void styleTextField(JTextField field) {
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        field.setBorder(new CompoundBorder(
                new LineBorder(new Color(180, 180, 180), 1, true),
                new EmptyBorder(5, 10, 5, 10)
        ));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setMaximumSize(new Dimension(200, 40));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorder(new EmptyBorder(8, 20, 8, 20));
    }

    public Connection getConnection() throws Exception {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
}
