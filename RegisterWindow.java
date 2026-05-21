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
import java.util.Random;

public class RegisterWindow extends JFrame {

    private JTextField fullNameField, emailField, phoneField, courseField, yearField;
    private JComboBox<String> roleBox;

    public RegisterWindow() {

        setTitle("User Registration");
        setSize(500, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel heading = new JLabel("Register New User");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setForeground(new Color(0, 0, 102));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(heading, gbc);

        gbc.gridwidth = 1;
        int y = 1;

        fullNameField = addField(panel, gbc, "Full Name *", y++);
        emailField = addField(panel, gbc, "Email *", y++);
        phoneField = addField(panel, gbc, "Phone *", y++);
        courseField = addField(panel, gbc, "Course", y++);
        yearField = addField(panel, gbc, "Year", y++);

        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(new JLabel("Role *"), gbc);

        roleBox = new JComboBox<>(new String[]{"STUDENT", "FACULTY"});
        gbc.gridx = 1;
        panel.add(roleBox, gbc);

        y++;

        JButton registerBtn = new JButton("Register");
        registerBtn.setBackground(new Color(0, 0, 102));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));

        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 2;
        panel.add(registerBtn, gbc);

        add(panel);

        registerBtn.addActionListener(e -> registerUser());

        setVisible(true);
    }

    private JTextField addField(JPanel panel, GridBagConstraints gbc, String label, int y) {
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(new JLabel(label), gbc);

        JTextField field = new JTextField();
        gbc.gridx = 1;
        panel.add(field, gbc);

        return field;
    }

    private void registerUser() {

        String fullName = fullNameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String course = courseField.getText().trim();
        String yearText = yearField.getText().trim();
        String role = roleBox.getSelectedItem().toString();

        if (fullName.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill all required fields!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String username = generateUsername(fullName);
        String password = generatePassword();

        try (Connection con = DBConnection.getConnection()) {

            String sql = "INSERT INTO users " +
                    "(username, password, role, is_active, created_at, fullname, email, phone, course, year) " +
                    "VALUES (?, ?, ?, true, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);
            ps.setString(4, fullName);
            ps.setString(5, email);
            ps.setString(6, phone);
            ps.setString(7, course);

            if (!yearText.isEmpty())
                ps.setInt(8, Integer.parseInt(yearText));
            else
                ps.setNull(8, Types.INTEGER);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,
                    "Registration Successful!\n\nUsername: " + username +
                            "\nPassword: " + password,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private String generateUsername(String fullName) {
        String base = fullName.toLowerCase().replaceAll(" ", ".");
        int random = new Random().nextInt(100);
        return base + random;
    }

    private String generatePassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#";
        StringBuilder pass = new StringBuilder();
        Random rand = new Random();

        for (int i = 0; i < 8; i++) {
            pass.append(chars.charAt(rand.nextInt(chars.length())));
        }

        return pass.toString();
    }
}
