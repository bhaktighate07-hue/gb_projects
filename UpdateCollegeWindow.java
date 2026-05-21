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
import java.awt.event.*;
import java.sql.*;

public class UpdateCollegeWindow extends JFrame {

    private JTextField idField, nameField, locationField;
    private JButton searchBtn, updateBtn;

    public UpdateCollegeWindow() {

        setTitle("Update College");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5,2,10,10));

        add(new JLabel("College ID:"));
        idField = new JTextField();
        add(idField);

        searchBtn = new JButton("Search");
        add(searchBtn);

        add(new JLabel("College Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Location:"));
        locationField = new JTextField();
        add(locationField);

        updateBtn = new JButton("Update");
        add(updateBtn);

        // 🔍 Search Logic
        searchBtn.addActionListener(e -> searchCollege());

        // 🔄 Update Logic
        updateBtn.addActionListener(e -> updateCollege());

        setVisible(true);
    }

    private void searchCollege() {
        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT * FROM colleges WHERE college_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, idField.getText());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                nameField.setText(rs.getString("college_name"));
                locationField.setText(rs.getString("location"));
            } else {
                JOptionPane.showMessageDialog(this, "College Not Found");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void updateCollege() {
        try (Connection con = DBConnection.getConnection()) {

            String sql = "UPDATE colleges SET college_name=?, location=? WHERE college_id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, nameField.getText());
            ps.setString(2, locationField.getText());
            ps.setString(3, idField.getText());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "College Updated Successfully");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Update Failed");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
