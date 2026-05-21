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

public class SettingsWindow extends JFrame {

    private int adminId;

    public SettingsWindow(int adminId) {

        this.adminId = adminId;

        setTitle("Settings");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3,1,15,15));

        JButton accountBtn = new JButton("Update Profile");
        JButton passwordBtn = new JButton("Change Password");
        JButton logoutBtn = new JButton("Logout");

        accountBtn.addActionListener(e -> new UpdateProfileWindow(adminId));
        passwordBtn.addActionListener(e -> new ChangePasswordWindow(adminId));

        logoutBtn.addActionListener(e -> {
            dispose();
            JOptionPane.showMessageDialog(null,"Logged Out Successfully");
        });

        add(accountBtn);
        add(passwordBtn);
        add(logoutBtn);

        setVisible(true);
    }
}
