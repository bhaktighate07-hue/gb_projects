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

public class ChangePasswordWindow extends JFrame {

    private int adminId;
    private JPasswordField currentPass, newPass, confirmPass;

    public ChangePasswordWindow(int adminId) {

        this.adminId = adminId;

        setTitle("Change Password");
        setSize(400,250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4,2,10,10));

        add(new JLabel("Current Password:"));
        currentPass = new JPasswordField();
        add(currentPass);

        add(new JLabel("New Password:"));
        newPass = new JPasswordField();
        add(newPass);

        add(new JLabel("Confirm Password:"));
        confirmPass = new JPasswordField();
        add(confirmPass);

        JButton changeBtn = new JButton("Update Password");
        add(changeBtn);

        changeBtn.addActionListener(e -> changePassword());

        setVisible(true);
    }

    private void changePassword() {

        try(Connection con = DBConnection.getConnection()) {

            String checkSql = "SELECT password FROM admin WHERE id=?";
            PreparedStatement ps = con.prepareStatement(checkSql);
            ps.setInt(1, adminId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                String dbPass = rs.getString("password");

                if(!dbPass.equals(new String(currentPass.getPassword()))) {
                    JOptionPane.showMessageDialog(this,"Incorrect Current Password");
                    return;
                }

                if(!new String(newPass.getPassword())
                        .equals(new String(confirmPass.getPassword()))) {
                    JOptionPane.showMessageDialog(this,"Passwords Do Not Match");
                    return;
                }

                String updateSql = "UPDATE admin SET password=? WHERE id=?";
                PreparedStatement ps2 = con.prepareStatement(updateSql);
                ps2.setString(1,new String(newPass.getPassword()));
                ps2.setInt(2,adminId);
                ps2.executeUpdate();

                JOptionPane.showMessageDialog(this,"Password Updated Successfully");
                dispose();
            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
