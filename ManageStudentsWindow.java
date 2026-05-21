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
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.*;

public class ManageStudentsWindow extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private int collegeId;

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASS = "bhakti";

    public ManageStudentsWindow(int collegeId) {

        this.collegeId = collegeId;

        setTitle("Manage Students");
        setSize(950,550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // -------- HEADER --------
        JPanel header = new JPanel();
        header.setBackground(new Color(10,25,70));
        header.setPreferredSize(new Dimension(100,60));

        JLabel title = new JLabel("Manage Students");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI",Font.BOLD,22));

        header.add(title);

        add(header,BorderLayout.NORTH);

        // -------- TABLE --------
        model = new DefaultTableModel();

        model.setColumnIdentifiers(new String[]{
                "Student ID","Student Name","Department","Year","Roll Number"
        });

        table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI",Font.PLAIN,14));

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Segoe UI",Font.BOLD,14));

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane,BorderLayout.CENTER);

        // -------- FOOTER --------
        JPanel footer = new JPanel();

        JButton addBtn = new JButton("Add Student");
        JButton updateBtn = new JButton("Update Student");
        JButton deleteBtn = new JButton("Delete Student");
        JButton refreshBtn = new JButton("Refresh");
        JButton closeBtn = new JButton("Close");

        styleButton(addBtn);
        styleButton(updateBtn);
        styleButton(deleteBtn);
        styleButton(refreshBtn);
        styleButton(closeBtn);

        footer.add(addBtn);
        footer.add(updateBtn);
        footer.add(deleteBtn);
        footer.add(refreshBtn);
        footer.add(closeBtn);

        add(footer,BorderLayout.SOUTH);

        // Button actions
        refreshBtn.addActionListener(e -> loadStudents());
        closeBtn.addActionListener(e -> dispose());

        addBtn.addActionListener(e -> addStudent());
        updateBtn.addActionListener(e -> updateStudent());
        deleteBtn.addActionListener(e -> deleteStudent());

        // Load students
        loadStudents();

        setVisible(true);
    }

    // -------- LOAD STUDENTS --------
    private void loadStudents(){

        model.setRowCount(0);

        try{

            Class.forName("org.postgresql.Driver");

            Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);

            String sql =
                    "SELECT s.student_id, s.student_name, d.department_name, s.year, s.roll_number "
                  + "FROM students s "
                  + "JOIN departments d ON s.department_id = d.department_id "
                  + "WHERE d.college_id = ? "
                  + "ORDER BY s.student_id";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,collegeId);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                model.addRow(new Object[]{
                        rs.getInt("student_id"),
                        rs.getString("student_name"),
                        rs.getString("department_name"),
                        rs.getString("year"),
                        rs.getString("roll_number")
                });

            }

            con.close();

        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this,"Database Error: "+e.getMessage());
        }

    }

    // -------- ADD STUDENT --------
    private void addStudent(){

        JTextField nameField = new JTextField();
        JTextField deptField = new JTextField();
        JTextField yearField = new JTextField();
        JTextField rollField = new JTextField();

        Object[] fields = {
                "Student Name:", nameField,
                "Department ID:", deptField,
                "Year:", yearField,
                "Roll Number:", rollField
        };

        int option = JOptionPane.showConfirmDialog(this,fields,"Add Student",JOptionPane.OK_CANCEL_OPTION);

        if(option == JOptionPane.OK_OPTION){

            try{

                Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);

                String sql = "INSERT INTO students(student_name,department_id,year,roll_number) VALUES(?,?,?,?)";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1,nameField.getText());
                ps.setInt(2,Integer.parseInt(deptField.getText()));
                ps.setString(3,yearField.getText());
                ps.setString(4,rollField.getText());

                ps.executeUpdate();

                con.close();

                loadStudents();

            }
            catch(Exception e){
                JOptionPane.showMessageDialog(this,"Error: "+e.getMessage());
            }

        }

    }

    // -------- UPDATE STUDENT --------
    private void updateStudent(){

        int row = table.getSelectedRow();

        if(row == -1){
            JOptionPane.showMessageDialog(this,"Select a student first");
            return;
        }

        int studentId = (int) model.getValueAt(row,0);

        String newName = JOptionPane.showInputDialog(this,"Enter new student name");

        if(newName == null || newName.isEmpty()) return;

        try{

            Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);

            String sql = "UPDATE students SET student_name=? WHERE student_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1,newName);
            ps.setInt(2,studentId);

            ps.executeUpdate();

            con.close();

            loadStudents();

        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this,"Error: "+e.getMessage());
        }

    }

    // -------- DELETE STUDENT --------
    private void deleteStudent(){

        int row = table.getSelectedRow();

        if(row == -1){
            JOptionPane.showMessageDialog(this,"Select a student first");
            return;
        }

        int studentId = (int) model.getValueAt(row,0);

        int confirm = JOptionPane.showConfirmDialog(this,"Delete this student?","Confirm",JOptionPane.YES_NO_OPTION);

        if(confirm == JOptionPane.YES_OPTION){

            try{

                Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);

                String sql = "DELETE FROM students WHERE student_id=?";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setInt(1,studentId);

                ps.executeUpdate();

                con.close();

                loadStudents();

            }
            catch(Exception e){
                JOptionPane.showMessageDialog(this,"Error: "+e.getMessage());
            }

        }

    }

    // -------- BUTTON STYLE --------
    private void styleButton(JButton btn){

        btn.setBackground(new Color(10,25,70));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI",Font.BOLD,14));
        btn.setFocusPainted(false);

    }
}