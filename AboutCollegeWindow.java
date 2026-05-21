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

public class AboutCollegeWindow extends JFrame {

    private int collegeId;

    JLabel nameLabel;
    JLabel cityLabel;
    JLabel yearLabel;
    JLabel emailLabel;
    JLabel phoneLabel;
    JTextArea descriptionArea;
    JLabel imageLabel;

    public AboutCollegeWindow(int collegeId) {

        this.collegeId = collegeId;

        setTitle("About College");
        setSize(800,550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== HEADER =====
        JPanel header = new JPanel();
        header.setBackground(new Color(10,35,85));
        header.setPreferredSize(new Dimension(100,60));

        nameLabel = new JLabel("College Name");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));

        header.add(nameLabel);

        add(header,BorderLayout.NORTH);

        // ===== MAIN PANEL =====
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20,30,20,30));

        // ===== LEFT INFO PANEL =====
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(4,1,10,10));

        Font infoFont = new Font("Segoe UI",Font.PLAIN,16);

        cityLabel = new JLabel();
        yearLabel = new JLabel();
        emailLabel = new JLabel();
        phoneLabel = new JLabel();

        cityLabel.setFont(infoFont);
        yearLabel.setFont(infoFont);
        emailLabel.setFont(infoFont);
        phoneLabel.setFont(infoFont);

        infoPanel.add(cityLabel);
        infoPanel.add(yearLabel);
        infoPanel.add(emailLabel);
        infoPanel.add(phoneLabel);

        mainPanel.add(infoPanel,BorderLayout.WEST);

        // ===== RIGHT IMAGE =====
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        mainPanel.add(imageLabel,BorderLayout.EAST);

        add(mainPanel,BorderLayout.CENTER);

        // ===== DESCRIPTION PANEL =====
        descriptionArea = new JTextArea();
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        descriptionArea.setFont(new Font("Segoe UI",Font.PLAIN,15));
        descriptionArea.setBorder(BorderFactory.createTitledBorder("About College"));

        add(new JScrollPane(descriptionArea),BorderLayout.SOUTH);

        loadCollegeData();

        setVisible(true);
    }

    private void loadCollegeData() {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM colleges WHERE college_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, collegeId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                String collegeName = rs.getString("college_name");
                String city = rs.getString("city");
                int year = rs.getInt("established_year");
                String email = rs.getString("contact_email");
                String phone = rs.getString("contact_phone");
                String imageName = rs.getString("background_image");

                nameLabel.setText(collegeName);

                cityLabel.setText("City : " + city);
                yearLabel.setText("Established Year : " + year);
                emailLabel.setText("Email : " + email);
                phoneLabel.setText("Phone : " + phone);

                // ===== DESCRIPTION PARAGRAPH =====
                descriptionArea.setText(
                        collegeName + " is a reputed educational institution located in " + city +
                        ". Established in " + year + ", the college is committed to providing " +
                        "quality education, innovative learning opportunities, and excellent " +
                        "academic resources. With experienced faculty members and modern " +
                        "infrastructure, the institution focuses on developing skilled " +
                        "professionals who can contribute effectively to society and industry. " +
                        "The college encourages research, creativity, and holistic student " +
                        "development to build successful careers."
                );

                // ===== LOAD IMAGE =====
                if(imageName != null){

                    ImageIcon icon = new ImageIcon(getClass().getResource("/icons/" + imageName));
                    Image img = icon.getImage().getScaledInstance(250,150,Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(img));
                }

            }

        } catch(Exception e) {

            JOptionPane.showMessageDialog(this, e.getMessage());

        }

    }
}