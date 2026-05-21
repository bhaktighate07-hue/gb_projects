/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DELL
 */
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class CollegesWindow extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JTextField searchField;

    public CollegesWindow() {

        setTitle("Colleges Management");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ===== TOP PANEL =====
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel heading = new JLabel("All Colleges");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 24));

        //searchField = new JTextField(20);
       // JButton searchBtn = new JButton("Search");
        JButton addBtn = new JButton("Add College");
        JButton deleteBtn = new JButton("Delete");
        JButton refreshBtn = new JButton("Refresh");

        JPanel rightPanel = new JPanel();
        //rightPanel.add(searchField);
        //rightPanel.add(searchBtn);
        rightPanel.add(addBtn);
        rightPanel.add(deleteBtn);
        rightPanel.add(refreshBtn);

        topPanel.add(heading, BorderLayout.WEST);
        topPanel.add(rightPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // ===== TABLE =====
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "ID", "College Name", "City", "Email", "Phone", "Established_year"
        });

        table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(0, 0, 102));
        header.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        loadColleges();

        // ===== BUTTON ACTIONS =====
        addBtn.addActionListener(e -> {new AddCollegeWindow();
        loadColleges();
        });

        refreshBtn.addActionListener(e -> loadColleges());

        deleteBtn.addActionListener(e -> deleteCollege());

        //searchBtn.addActionListener(e ->
                //loadColleges(searchField.getText())
        

        setVisible(true);
    }

    public void loadColleges() {

    model.setRowCount(0);

    try (Connection con = DBConnection.getConnection()) {

        String sql = "SELECT * FROM colleges";
        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
                    rs.getInt("college_id"),
                    rs.getString("college_name"),
                    rs.getString("city"),
                    rs.getString("contact_email"),
                    rs.getString("contact_phone"),
                    rs.getInt("established_year")
            });
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    private void deleteCollege() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a college first!");
            return;
        }

        int id = (int) model.getValueAt(row, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure to delete this college?");

        if (confirm == JOptionPane.YES_OPTION) {

            try (Connection con = DBConnection.getConnection()) {

                PreparedStatement ps =
                        con.prepareStatement("DELETE FROM colleges WHERE college_id=?");

                ps.setInt(1, id);
                ps.executeUpdate();

                loadColleges();
                JOptionPane.showMessageDialog(this, "Deleted Successfully");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
