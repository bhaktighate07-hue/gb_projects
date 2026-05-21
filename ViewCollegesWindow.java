/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DELL
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewCollegesWindow extends JFrame {

    JTable table;
    DefaultTableModel model;

    public ViewCollegesWindow() {

        setTitle("View Colleges");
        setSize(900,500);
        setLocationRelativeTo(null);

        model = new DefaultTableModel();
        table = new JTable(model);

        model.addColumn("ID");
        model.addColumn("College Name");
        model.addColumn("City");
        model.addColumn("Email");
        model.addColumn("Phone");
        model.addColumn("Established Year");
       

        JScrollPane scroll = new JScrollPane(table);

        add(scroll);

        loadColleges();

        setVisible(true);
    }

    private void loadColleges() {
        
        model.setRowCount(0);

        try(Connection con = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "bhakti"
        )){

            String sql = "SELECT college_id,college_name,city,contact_email,contact_phone,established_year FROM colleges";

            PreparedStatement ps = con.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();

            
            int count = 0;
            while(rs.next()){
                count++;
                
            System.out.println(rs.getString("college_name"));
                model.addRow(new Object[]{
                  
                        rs.getInt("college_id"),
                        rs.getString("college_name"),
                        rs.getString("city"),
                        rs.getString("contact_email"),
                        rs.getString("contact_phone"),
                        rs.getInt("established_year"),
                });
              
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
        new ViewCollegesWindow();
        });
  }
}