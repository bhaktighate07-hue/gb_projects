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

public class StudentRegistrationForm extends JFrame {

    JTextField txtName, txtCourse, txtEmail, txtPhone;
    JComboBox<String> comboCollege, comboDepartment, comboYear;

    JButton btnRegister, btnClear, btnLogin;

    public StudentRegistrationForm() {

        setTitle("Student Registration");
        setSize(500,450);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(8,2,15,15));

        // Student Name
        add(new JLabel("Student Name"));
        txtName = new JTextField();
        add(txtName);

        // College
        add(new JLabel("College Name"));
        comboCollege = new JComboBox<>();
        add(comboCollege);

        // Department
        add(new JLabel("Department"));
        comboDepartment = new JComboBox<>();
        add(comboDepartment);

        // Course
        add(new JLabel("Course"));
        txtCourse = new JTextField();
        txtCourse.setEditable(false);
        add(txtCourse);

        // Admission Year
        add(new JLabel("Admission Year"));
        comboYear = new JComboBox<>(new String[]{"First Year","Second Year","Third Year"});
        add(comboYear);

        // Email
        add(new JLabel("Email"));
        txtEmail = new JTextField();
        add(txtEmail);

        // Phone
        add(new JLabel("Phone"));
        txtPhone = new JTextField();
        add(txtPhone);

        // ✅ ONLY ADDITION: Phone field restriction (digits + max 10)
        txtPhone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {

                char c = evt.getKeyChar();

                if (!Character.isDigit(c) || txtPhone.getText().length() >= 10) {
                    evt.consume();
                }
            }
        });

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));

        btnRegister = new JButton("Register");
        btnClear = new JButton("Clear");
        btnLogin = new JButton("Login");

        buttonPanel.add(btnRegister);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnLogin);

        add(new JLabel(""));
        add(buttonPanel);

        loadColleges();
        comboCollege.addActionListener(e -> loadDepartmentsByCollege());
        comboDepartment.addActionListener(e -> loadCourse());

        btnRegister.addActionListener(e -> registerStudent());
        btnClear.addActionListener(e -> clearForm());

        setVisible(true);
    }

    // Load Colleges
    private void loadColleges() {

        try {

            Connection con = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres",
                    "postgres",
                    "bhakti"
            );

            String sql = "SELECT college_name FROM colleges";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                comboCollege.addItem(rs.getString("college_name"));
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    // Load Departments
    private void loadDepartmentsByCollege() {

        comboDepartment.removeAllItems();

        try {

            Connection con = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres",
                    "postgres",
                    "bhakti"
            );

            String college = comboCollege.getSelectedItem().toString();

            String sql =
            "SELECT d.department_name " +
            "FROM departments d " +
            "JOIN colleges c ON d.college_id = c.college_id " +
            "WHERE c.college_name = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, college);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                comboDepartment.addItem(rs.getString("department_name"));
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    // Auto Fill Course
    private void loadCourse() {

        try {

            Connection con = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres",
                    "postgres",
                    "bhakti"
            );

            String dept = comboDepartment.getSelectedItem().toString();

            String sql = "SELECT course FROM departments WHERE department_name=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, dept);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                txtCourse.setText(rs.getString("course"));
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    // Register Student
    private void registerStudent() {

        try {

            Connection con = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres",
                    "postgres",
                    "bhakti"
            );

            String name = txtName.getText();
            String email = txtEmail.getText();
            String phone = txtPhone.getText();
            String course = txtCourse.getText();
            String year = comboYear.getSelectedItem().toString();

            // ✅ ONLY ADDITION: exact 10 digit validation
            if (!phone.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(this, "Phone number must be exactly 10 digits!");
                return;
            }

            String username = name.toLowerCase().replace(" ", ".");
            String password = "Stu@123";

            // insert users
            String sqlUser = "INSERT INTO users(username,password,role,email,phone,course,year) VALUES(?,?,?,?,?,?,?)";

            PreparedStatement psUser = con.prepareStatement(sqlUser,Statement.RETURN_GENERATED_KEYS);

            psUser.setString(1,username);
            psUser.setString(2,password);
            psUser.setString(3,"STUDENT");
            psUser.setString(4,email);
            psUser.setString(5,phone);
            psUser.setString(6,course);
            psUser.setString(7,year);

            psUser.executeUpdate();

            ResultSet rs = psUser.getGeneratedKeys();

            int userId = 0;

            if(rs.next()) {
                userId = rs.getInt(1);
            }

            String prefix;

            if (course != null && course.length() >= 3) {
                prefix = course.substring(0, 3).toUpperCase();
            } else {
                prefix = course.toUpperCase(); // fallback
            }

            String rollNo = prefix + userId;

            
            // insert students table
            String sqlStudent = "INSERT INTO students(user_id,student_name,roll_number,year) VALUES(?,?,?,?)";

            PreparedStatement psStudent = con.prepareStatement(sqlStudent);

            psStudent.setInt(1,userId);
            psStudent.setString(2,name);
            psStudent.setString(3,rollNo);
            psStudent.setString(4,year);

            psStudent.executeUpdate();

            JOptionPane.showMessageDialog(this,
                    "Registration Successful\nUsername: "+username+
                            "\nPassword: "+password+
                            "\nRoll No: "+rollNo);

        }

        catch(Exception e){

            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"Error");

        }
    }

    private void clearForm() {

        txtName.setText("");
        txtCourse.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
    }
}