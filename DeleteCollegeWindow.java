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

public class DeleteCollegeWindow extends JFrame {

    private JTextField idField;
    private JButton deleteBtn;

    public DeleteCollegeWindow() {

        setTitle("Delete College");
        setSize(350,150);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2,2,10,10));

        add(new JLabel("College ID:"));
        idField = new JTextField();
        add(idField);

        deleteBtn = new JButton("Delete");
        add(deleteBtn);

        deleteBtn.addActionListener(e -> deleteCollege());

        setVisible(true);
    }

    private void deleteCollege() {

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        try (Connection con = DBConnection.getConnection()) {

            String sql = "DELETE FROM colleges WHERE college_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, idField.getText());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "College Deleted Successfully");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "College Not Found");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}