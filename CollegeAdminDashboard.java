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

public class CollegeAdminDashboard extends JFrame {

    private String collegeName;
    private Image backgroundImage;
    private int collegeId;

    public CollegeAdminDashboard(String collegeName, String backgroundImageName, int collegeId) {

        this.collegeName = collegeName;
        this.collegeId = collegeId;
        
        setTitle("College Admin Dashboard - " + collegeName);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ===== LOAD IMAGE DYNAMICALLY =====
        if (backgroundImageName != null && !backgroundImageName.isEmpty()) {

            String imagePath = "/icons/" + backgroundImageName;

            java.net.URL imgURL = getClass().getResource(imagePath);

            if (imgURL != null) {
                backgroundImage = new ImageIcon(imgURL).getImage();
            } else {
                System.out.println("Image not found: " + imagePath);
            }
        }

        // ===== CUSTOM BACKGROUND PANEL =====
        JPanel backgroundPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                if (backgroundImage != null) {

                    int panelWidth = getWidth();
                    int panelHeight = getHeight();

                    int imgWidth = backgroundImage.getWidth(this);
                    int imgHeight = backgroundImage.getHeight(this);

                    double scale = Math.max(
                            (double) panelWidth / imgWidth,
                            (double) panelHeight / imgHeight);

                    int newW = (int) (imgWidth * scale);
                    int newH = (int) (imgHeight * scale);

                    int x = (panelWidth - newW) / 2;
                    int y = (panelHeight - newH) / 2;

                    g.drawImage(backgroundImage, x, y, newW, newH, this);
                }
            }
        };

        backgroundPanel.setLayout(new BorderLayout());

        // ===== TOP WHITE BAR =====
        JPanel topBar = new JPanel();
        topBar.setBackground(Color.WHITE);
        topBar.setPreferredSize(new Dimension(100, 38));
        topBar.setLayout(new BorderLayout());

        JLabel collegeLabel = new JLabel(collegeName, SwingConstants.CENTER);
        collegeLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        collegeLabel.setForeground(Color.BLACK);

        topBar.add(collegeLabel, BorderLayout.CENTER);

        // ===== MENU BAR =====
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(20, 40, 90));
        menuBar.setPreferredSize(new Dimension(100, 35));
        menuBar.setBorderPainted(false);

        JMenu manageStudents = new JMenu("Manage Students");
        JMenu manageFaculty = new JMenu("Manage Faculty");
        JMenu departments = new JMenu("Departments");
        //JMenu courses = new JMenu("View Courses");
        JMenu aboutMenu = new JMenu("About College");
        JMenu logout = new JMenu("Logout");

        Font menuFont = new Font("Segoe UI", Font.BOLD, 15);

        JMenu[] menus = {manageStudents, manageFaculty, departments, aboutMenu, logout};

        for (JMenu menu : menus) {
            menu.setForeground(Color.WHITE);
            menu.setFont(menuFont);
        }

        // ===== MENU ITEMS =====
        JMenuItem openStudents = new JMenuItem("Open Manage Students");
        JMenuItem openFaculty = new JMenuItem("Open Manage Faculty");
        JMenuItem manageDepartments = new JMenuItem("Manage Departments");
        JMenuItem aboutCollege  = new JMenuItem("About College");
        //JMenuItem logout = new JMenuItem("Logout");
        
        manageStudents.add(openStudents);
        manageFaculty.add(openFaculty);
        departments.add(manageDepartments);
        aboutMenu.add(aboutCollege);
        
        // ===== ACTION LISTENERS =====
        openStudents.addActionListener(e -> {
            new ManageStudentsWindow(collegeId);
        });

        openFaculty.addActionListener(e -> {
             new ManageFacultyWindow(collegeId);
        });
        
        manageDepartments.addActionListener(e -> {
             new ManageDepartmentWindow(collegeId);
        });
        
        aboutCollege.addActionListener(e -> {
             new AboutCollegeWindow(collegeId);
        });
        
        logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {

                int confirm = JOptionPane.showConfirmDialog(
                        CollegeAdminDashboard.this,
                        "Are you sure you want to logout?",
                        "Logout",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    dispose(); // dashboard close
                }
            }
        });
        
        

        menuBar.add(manageStudents);
        menuBar.add(manageFaculty);
        menuBar.add(departments);
        //menuBar.add(courses);
        menuBar.add(aboutMenu);
        menuBar.add(logout);

        // ===== HEADER CONTAINER =====
        JPanel headerContainer = new JPanel(new BorderLayout());
        headerContainer.setOpaque(false);

        headerContainer.add(topBar, BorderLayout.NORTH);
        headerContainer.add(menuBar, BorderLayout.SOUTH);

        backgroundPanel.add(headerContainer, BorderLayout.NORTH);

        setContentPane(backgroundPanel);
        setVisible(true);
    }
    
    
}