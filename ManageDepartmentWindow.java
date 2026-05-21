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

public class ManageDepartmentWindow extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private int collegeId;

    public ManageDepartmentWindow(int collegeId){

        this.collegeId = collegeId;

        setTitle("Manage Departments");
        setSize(800,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel header = new JPanel();
        header.setBackground(new Color(20,40,90));

        JLabel title = new JLabel("Manage Departments");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI",Font.BOLD,22));

        header.add(title);

        add(header,BorderLayout.NORTH);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "Department ID","Department Name"
        });

        table = new JTable(model);
        table.setRowHeight(28);

        add(new JScrollPane(table),BorderLayout.CENTER);

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

        addBtn.addActionListener(e -> addDepartment());
        updateBtn.addActionListener(e -> updateDepartment());
        deleteBtn.addActionListener(e -> deleteDepartment());
        refreshBtn.addActionListener(e -> loadDepartments());

        loadDepartments();

        setVisible(true);
    }

    private void loadDepartments(){

        try(Connection con = DBConnection.getConnection()){

            String sql =
            "SELECT department_id, department_name " +
            "FROM departments WHERE college_id=? ORDER BY department_id";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1,collegeId);

            ResultSet rs = ps.executeQuery();

            model.setRowCount(0);

            while(rs.next()){

                model.addRow(new Object[]{
                        rs.getInt("department_id"),
                        rs.getString("department_name")
                });
            }

        }catch(Exception e){
            JOptionPane.showMessageDialog(this,e.getMessage());
        }
    }

    private void addDepartment(){

        String name = JOptionPane.showInputDialog(this,"Enter Department Name");

        if(name==null || name.isEmpty()) return;

        try(Connection con = DBConnection.getConnection()){

            String sql =
            "INSERT INTO departments (department_name, college_id) VALUES (?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1,name);
            ps.setInt(2,collegeId);

            ps.executeUpdate();

            loadDepartments();

        }catch(Exception e){
            JOptionPane.showMessageDialog(this,e.getMessage());
        }
    }

    private void updateDepartment(){

        int row = table.getSelectedRow();

        if(row==-1){
            JOptionPane.showMessageDialog(this,"Select department first");
            return;
        }

        int id = (int) model.getValueAt(row,0);

        String newName = JOptionPane.showInputDialog(
                this,
                "Enter New Department Name",
                model.getValueAt(row,1)
        );

        if(newName==null || newName.isEmpty()) return;

        try(Connection con = DBConnection.getConnection()){

            String sql =
            "UPDATE departments SET department_name=? WHERE department_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1,newName);
            ps.setInt(2,id);

            ps.executeUpdate();

            loadDepartments();

        }catch(Exception e){
            JOptionPane.showMessageDialog(this,e.getMessage());
        }
    }

    private void deleteDepartment(){

        int row = table.getSelectedRow();

        if(row==-1){
            JOptionPane.showMessageDialog(this,"Select department first");
            return;
        }

        int id = (int) model.getValueAt(row,0);

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete this department?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if(confirm==JOptionPane.YES_OPTION){

            try(Connection con = DBConnection.getConnection()){

                String sql =
                "DELETE FROM departments WHERE department_id=?";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setInt(1,id);

                ps.executeUpdate();

                loadDepartments();

            }catch(Exception e){
                JOptionPane.showMessageDialog(this,e.getMessage());
            }
        }
    }
}
