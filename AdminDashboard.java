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
class AdminDashboard {

    AdminDashboard() {

        JFrame frame = new JFrame("Main Admin Dashboard");
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Background Panel
        JPanel background = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon img = new ImageIcon("src/resources/university_main.jpg");
                g.drawImage(img.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        background.setLayout(new BorderLayout());

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();

        JMenu manage = new JMenu("Manage");
        manage.add(new JMenuItem("Colleges"));
        manage.add(new JMenuItem("Admins"));

        JMenu reports = new JMenu("Reports");
        reports.add(new JMenuItem("System Report"));

        JMenu logout = new JMenu("Logout");

        menuBar.add(manage);
        menuBar.add(reports);
        menuBar.add(logout);

        frame.setJMenuBar(menuBar);
        frame.add(background);

        frame.setVisible(true);
    }
}
