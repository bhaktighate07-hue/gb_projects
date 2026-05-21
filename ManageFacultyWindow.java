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

public class ManageFacultyWindow extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private int collegeId;

    public ManageFacultyWindow(int collegeId) {

        this.collegeId = collegeId;

        setTitle("Manage Faculty");
        setSize(900,550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel header = new JPanel();
        header.setBackground(new Color(15,30,80));

        JLabel title = new JLabel("Manage Faculty");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI",Font.BOLD,22));

        header.add(title);
        add(header,BorderLayout.NORTH);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "Faculty ID","Faculty Name","Designation","Qualification"
        });

        table = new JTable(model);
        table.setRowHeight(28);

        JScrollPane sp = new JScrollPane(table);
        add(sp,BorderLayout.CENTER);

        JPanel bottom = new JPanel();

        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton refreshBtn = new JButton("Refresh");

        bottom.add(addBtn);
        bottom.add(updateBtn);
        bottom.add(deleteBtn);
        bottom.add(refreshBtn);

        add(bottom,BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addFaculty());
        updateBtn.addActionListener(e -> updateFaculty());
        deleteBtn.addActionListener(e -> deleteFaculty());
        refreshBtn.addActionListener(e -> loadFaculty());

        loadFaculty();

        setVisible(true);
    }

    private void loadFaculty(){

        try(Connection con = DBConnection.getConnection()){

            String sql =
            "SELECT f.faculty_id, f.faculty_name, f.designation, f.qualification " +
            "FROM faculty f " +
            "JOIN departments d ON f.department_id = d.department_id " +
            "WHERE d.college_id = ? " +
            "ORDER BY f.faculty_id";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1,collegeId);

            ResultSet rs = ps.executeQuery();

            model.setRowCount(0);

            while(rs.next()){

                model.addRow(new Object[]{
                        rs.getInt("faculty_id"),
                        rs.getString("faculty_name"),
                        rs.getString("designation"),
                        rs.getString("qualification")
                });
            }

        }catch(Exception e){
            JOptionPane.showMessageDialog(this,e.getMessage());
        }
    }

    private void addFaculty(){

        JTextField name = new JTextField();
        JTextField desig = new JTextField();
        JTextField qual = new JTextField();

        Object[] fields = {
                "Faculty Name:",name,
                "Designation:",desig,
                "Qualification:",qual
        };

        int option = JOptionPane.showConfirmDialog(this,fields,"Add Faculty",JOptionPane.OK_CANCEL_OPTION);

        if(option == JOptionPane.OK_OPTION){

            try(Connection con = DBConnection.getConnection()){

                String sql =
                "INSERT INTO faculty (user_id, faculty_name, department_id, designation, qualification) " +
                "SELECT COALESCE(MAX(user_id),0)+1, ?, ?, ?, ? FROM faculty";

                PreparedStatement ps = con.prepareStatement(sql);

                //ps.setInt(1,1); // temporary user_id

                ps.setString(1,name.getText());

                int deptId = getDepartmentId(con);

                ps.setInt(2,deptId);

                ps.setString(3,desig.getText());
                ps.setString(4,qual.getText());

                ps.executeUpdate();

                loadFaculty();

            }catch(Exception e){
                JOptionPane.showMessageDialog(this,e.getMessage());
            }
        }
    }

    private int getDepartmentId(Connection con) throws SQLException {

        String sql =
        "SELECT department_id FROM departments WHERE college_id=? LIMIT 1";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1,collegeId);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            return rs.getInt("department_id");
        }

        return 1;
    }

    private void updateFaculty(){

        int row = table.getSelectedRow();

        if(row==-1){
            JOptionPane.showMessageDialog(this,"Select faculty first");
            return;
        }

        int id = (int) model.getValueAt(row,0);

        JTextField name = new JTextField((String)model.getValueAt(row,1));
        JTextField desig = new JTextField((String)model.getValueAt(row,2));
        JTextField qual = new JTextField((String)model.getValueAt(row,3));

        Object[] fields = {
                "Faculty Name:",name,
                "Designation:",desig,
                "Qualification:",qual
        };

        int option = JOptionPane.showConfirmDialog(this,fields,"Update Faculty",JOptionPane.OK_CANCEL_OPTION);

        if(option == JOptionPane.OK_OPTION){

            try(Connection con = DBConnection.getConnection()){

                String sql =
                "UPDATE faculty SET faculty_name=?, designation=?, qualification=? WHERE faculty_id=?";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1,name.getText());
                ps.setString(2,desig.getText());
                ps.setString(3,qual.getText());
                ps.setInt(4,id);

                ps.executeUpdate();

                loadFaculty();

            }catch(Exception e){
                JOptionPane.showMessageDialog(this,e.getMessage());
            }
        }
    }

    private void deleteFaculty(){

        int row = table.getSelectedRow();

        if(row==-1){
            JOptionPane.showMessageDialog(this,"Select faculty first");
            return;
        }

        int id = (int) model.getValueAt(row,0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete this faculty?","Confirm",JOptionPane.YES_NO_OPTION);

        if(confirm == JOptionPane.YES_OPTION){

            try(Connection con = DBConnection.getConnection()){

                String sql = "DELETE FROM faculty WHERE faculty_id=?";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setInt(1,id);

                ps.executeUpdate();

                loadFaculty();

            }catch(Exception e){
                JOptionPane.showMessageDialog(this,e.getMessage());
            }
        }
    }
    
}