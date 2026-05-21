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

public class AddCollegeWindow extends JFrame {

    JTextField nameField,adminNameField, cityField, emailField, phoneField, yearField;
    JTextField departmentField, courseField;

    public AddCollegeWindow(){

        setTitle("Add College");
        setSize(450,500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(8,2,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,30,20,30));

        panel.add(new JLabel("College Name"));
        nameField = new JTextField();
        panel.add(nameField);
        
        panel.add(new JLabel("Admin Name"));
        adminNameField = new JTextField();
        panel.add(adminNameField);

        panel.add(new JLabel("City"));
        cityField = new JTextField();
        panel.add(cityField);

        panel.add(new JLabel("Contact Email"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Contact Phone"));
        phoneField = new JTextField();
        panel.add(phoneField);

        panel.add(new JLabel("Established Year"));
        yearField = new JTextField();
        panel.add(yearField);

        panel.add(new JLabel("Department"));
        departmentField = new JTextField();
        panel.add(departmentField);

        panel.add(new JLabel("Course"));
        courseField = new JTextField();
        panel.add(courseField);

        JButton saveBtn = new JButton("Save College");

        add(panel, BorderLayout.CENTER);
        add(saveBtn, BorderLayout.SOUTH);

        saveBtn.addActionListener(e -> saveCollege());

        setVisible(true);
    }

    private void saveCollege(){

        String name = nameField.getText();
        String adminName = adminNameField.getText();
        String[] parts = adminName.trim().toLowerCase().split("\\s+");

        String username = "";

        if(!adminName.isEmpty()){
          
            username = "mr." + parts[0];

            if(parts.length > 1){
                username += "." + parts[1];
            }
        }
      
        String password = "Admin@123";
        String city = cityField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String year = yearField.getText();
        String department = departmentField.getText();
        String course = courseField.getText();

        if(name.isEmpty() || city.isEmpty() || email.isEmpty() || phone.isEmpty() || year.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please fill all fields");
            return;
        }

        try{

            Connection con = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres",
                    "postgres",
                    "bhakti"
            );
            
            // Insert College
            String collegeSql = "INSERT INTO colleges(college_name,background_image,city,contact_email,contact_phone,established_year) VALUES(?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(collegeSql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1,name);
            ps.setString(2,"default.jpg");
            ps.setString(3,city);
            ps.setString(4,email);
            ps.setString(5,phone);
            ps.setInt(6,Integer.parseInt(year));

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            int collegeId = 0;

            if(rs.next()){
                collegeId = rs.getInt(1);
            }

            // Insert Department
            if(!department.isEmpty()){
                String deptSql = "INSERT INTO departments(department_name,college_id,course) VALUES(?,?,?)";

                PreparedStatement ps2 = con.prepareStatement(deptSql);
                ps2.setString(1,department);
                ps2.setInt(2,collegeId);
                ps2.setString(3,course);

                ps2.executeUpdate();
             }

            // Save course in users table
            if(!adminName.isEmpty()){
                String userSql = "INSERT INTO users(username,password,role,is_active,created_at,email) VALUES(?,?,?,?,NOW(),?)";

                PreparedStatement psUser = con.prepareStatement(userSql);

                psUser.setString(1,username);
                psUser.setString(2,password);
                psUser.setString(3,"COLLEGE_ADMIN");
                psUser.setBoolean(4,true);
                psUser.setString(5,email);

                psUser.executeUpdate();
            }

            JOptionPane.showMessageDialog(this,"College Added Successfully");

            con.close();
            dispose();

        }catch(Exception e){
            JOptionPane.showMessageDialog(this,e.getMessage());
            
        }
    }

    public static void main(String[] args){
        new AddCollegeWindow();
    }
}