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

public class StudentResult extends JFrame {

    public StudentResult(String name,String course,int marks){

        setTitle("Student Result");
        setSize(400,300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,2,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(40,40,40,40));

        panel.add(new JLabel("Student Name:"));
        panel.add(new JLabel(name));

        panel.add(new JLabel("Course:"));
        panel.add(new JLabel(course));

        panel.add(new JLabel("Marks:"));
        panel.add(new JLabel(String.valueOf(marks)));

        add(panel);

        setVisible(true);
    }
}
