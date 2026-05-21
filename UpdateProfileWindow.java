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

public class UpdateProfileWindow extends JFrame {

    private int adminId;
    private JTextField nameField, emailField;

    public UpdateProfileWindow(int adminId) {

        this.adminId = adminId;

        setTitle("Update Profile");
        setSize(400,250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3,2,10,10));

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        JButton updateBtn = new JButton("Update");
        add(updateBtn);

        loadData();

        updateBtn.addActionListener(e -> updateProfile());

        setVisible(true);
    }

    private void loadData() {
        try(Connection con = DBConnection.getConnection()) {

            String sql = "SELECT name,email FROM admin WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,adminId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                nameField.setText(rs.getString("name"));
                emailField.setText(rs.getString("email"));
            }

        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void updateProfile() {

        try(Connection con = DBConnection.getConnection()) {

            String sql = "UPDATE admin SET name=?,email=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,nameField.getText());
            ps.setString(2,emailField.getText());
            ps.setInt(3,adminId);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,"Profile Updated Successfully");
            dispose();

        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
}