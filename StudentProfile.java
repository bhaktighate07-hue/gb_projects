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

import javax.swing.*;
import java.awt.*;

public class StudentProfile extends JFrame {

    public StudentProfile(String name,String roll,String course,String phone,String email,String year){

        setTitle("Student Profile");
        setSize(450,400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6,2,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));

        panel.add(new JLabel("Name:"));
        panel.add(new JLabel(name));

        panel.add(new JLabel("Roll Number:"));
        panel.add(new JLabel(roll));

        panel.add(new JLabel("Course:"));
        panel.add(new JLabel(course));

        panel.add(new JLabel("Phone:"));
        panel.add(new JLabel(phone));

        panel.add(new JLabel("Email:"));
        panel.add(new JLabel(email));

        panel.add(new JLabel("Year:"));
        panel.add(new JLabel(year));

        add(panel);

        setVisible(true);
    }
}
