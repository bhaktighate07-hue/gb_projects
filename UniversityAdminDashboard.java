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

public class UniversityAdminDashboard extends JFrame {

    public UniversityAdminDashboard() {

        setTitle("Global Tech University - Admin");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== TOP TITLE BAR =====
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE); // Dark Blue
        titlePanel.setPreferredSize(new Dimension(100, 70));
        titlePanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("GLOBAL TECH UNIVERSITY");
        titleLabel.setForeground(new Color(0, 0, 139));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // ===== MENU BAR =====
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(0, 0, 102)); // Dark Blue
        menuBar.setPreferredSize(new Dimension(100, 40));
        
        // ===== MENU BAR START =====



       /* ===== DASHBOARD =====
        JMenu dashboardMenu = new JMenu("Dashboard");
        dashboardMenu.setForeground(Color.WHITE);
        dashboardMenu.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuSelected(javax.swing.event.MenuEvent e) {
                JOptionPane.showMessageDialog(null, "Dashboard Opened");
            }

            public void menuDeselected(javax.swing.event.MenuEvent e) {
            }

            public void menuCanceled(javax.swing.event.MenuEvent e) {
            }
        });
        */
        // ===== COLLEGES =====
        JMenu collegesMenu = new JMenu("Colleges");
        collegesMenu.setForeground(Color.WHITE);

        JMenuItem addCollege = new JMenuItem("Add College");
        JMenuItem viewColleges = new JMenuItem("View Colleges");
        
        
        collegesMenu.add(addCollege);
        collegesMenu.add(viewColleges);
        
        //menuBar.add(collegesMenu);
        

        // 👉 OPEN ADD COLLEGE FORM WINDOW
        addCollege.addActionListener(e -> {
            new AddCollegeWindow();
           
        });

        // 👉 OPEN COLLEGES TABLE WINDOW
        viewColleges.addActionListener(e -> {
            new CollegesWindow();
        });

        //collegesMenu.add(addCollege);
        //collegesMenu.add(viewCollege);

        /* ===== STUDENTS =====
        JMenu studentsMenu = new JMenu("Students");
        studentsMenu.setForeground(Color.WHITE);

        JMenuItem viewStudents = new JMenuItem("View Students");

        viewStudents.addActionListener(e
                -> JOptionPane.showMessageDialog(null, "View Students Clicked"));

        studentsMenu.add(viewStudents);*/

        /*===== REPORTS =====
        JMenu reportsMenu = new JMenu("Reports");
        reportsMenu.setForeground(Color.WHITE);
        JMenuItem viewReports = new JMenuItem("View Reports");

        reportsMenu.add(viewReports);
        menuBar.add(reportsMenu);

        viewReports.addActionListener(e -> new ReportsWindow());
        
        // ===== SETTINGS =====
        JMenu settingsMenu = new JMenu("Settings");
        settingsMenu.setForeground(Color.WHITE);

        JMenuItem changePassword = new JMenuItem("Change Password");

        changePassword.addActionListener(e
                -> JOptionPane.showMessageDialog(null, "Change Password Clicked"));

        settingsMenu.add(changePassword);
        */

       // ===== LOGOUT =====
        JMenu logoutMenu = new JMenu("Logout");
        logoutMenu.setForeground(Color.WHITE);

        logoutMenu.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuSelected(javax.swing.event.MenuEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to logout?");
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }

            public void menuDeselected(javax.swing.event.MenuEvent e) {
            }

            public void menuCanceled(javax.swing.event.MenuEvent e) {
            }
        });
        
        

        // ===== ADD ALL MENUS =====
        //menuBar.add(dashboardMenu);
        menuBar.add(collegesMenu);
        //menuBar.add(reportsMenu);
        //menuBar.add(settingsMenu);
        menuBar.add(logoutMenu);

        // ===== MENU BAR END =====

        
        // ===== NORTH PANEL (Title + Menu Together) =====
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        northPanel.add(titlePanel, BorderLayout.NORTH);
        northPanel.add(menuBar, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);

        // ===== BACKGROUND IMAGE PANEL =====
        BackgroundPanel bgPanel = new BackgroundPanel("src/icons/university_main.jpg");
        add(bgPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // ===== Custom Background Panel =====
    class BackgroundPanel extends JPanel {

        private Image image;

        public BackgroundPanel(String path) {
            image = new ImageIcon(path).getImage();
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }
}