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

public class ReportsWindow extends JFrame {

    JLabel totalCollegesLabel;
    JTextArea cityReport;

    public ReportsWindow(){

        setTitle("University Reports");
        setSize(700,500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // HEADER
        JLabel title = new JLabel("University Reports", JLabel.CENTER);
        title.setFont(new Font("Segoe UI",Font.BOLD,26));
        title.setBorder(BorderFactory.createEmptyBorder(15,0,15,0));
        add(title,BorderLayout.NORTH);

        // STATS PANEL
        JPanel statsPanel = new JPanel(new GridLayout(1,1,20,20));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20,40,20,40));

        JPanel card1 = new JPanel();
        card1.setBackground(new Color(0,102,204));
        card1.setLayout(new BorderLayout());

        totalCollegesLabel = new JLabel("Loading...", JLabel.CENTER);
        totalCollegesLabel.setFont(new Font("Segoe UI",Font.BOLD,22));
        totalCollegesLabel.setForeground(Color.WHITE);

        card1.add(totalCollegesLabel,BorderLayout.CENTER);
        statsPanel.add(card1);

        add(statsPanel,BorderLayout.CENTER);

        // CITY REPORT
        cityReport = new JTextArea();
        cityReport.setFont(new Font("Segoe UI",Font.PLAIN,16));
        cityReport.setEditable(false);

        JScrollPane scroll = new JScrollPane(cityReport);
        scroll.setBorder(BorderFactory.createTitledBorder("Colleges By City"));

        add(scroll,BorderLayout.SOUTH);

        loadReports();

        setVisible(true);
    }

    private void loadReports(){

        try(Connection con = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "bhakti"
        )){

            // TOTAL COLLEGES
            PreparedStatement ps1 = con.prepareStatement(
                    "SELECT COUNT(*) FROM colleges"
            );

            ResultSet rs1 = ps1.executeQuery();

            if(rs1.next()){
                int total = rs1.getInt(1);
                totalCollegesLabel.setText("Total Colleges : "+total);
            }

            // CITY REPORT
            PreparedStatement ps2 = con.prepareStatement(
                    "SELECT city, COUNT(*) FROM colleges GROUP BY city"
            );

            ResultSet rs2 = ps2.executeQuery();

            cityReport.setText("");

            while(rs2.next()){

                String city = rs2.getString(1);
                int count = rs2.getInt(2);

                cityReport.append(city + " : " + count + "\n");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}