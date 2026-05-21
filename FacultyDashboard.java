/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DELL
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DELL
 */
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class FacultyDashboard extends JFrame {

     private int userId;
private String facultyName;
private String collegeName;
private String mobile;
private String qualification;
private int departmentId;
private String designation;
        private JLabel backgroundLabel;

    
    public FacultyDashboard(int userId, String facultyName, String collegeName,
                            String mobile, String qualification, int departmentId,
                            String designation) {
        this.userId = userId;
        this.facultyName = facultyName;
        this.collegeName = collegeName;
        this.mobile = mobile;
        this.qualification = qualification;
        this.departmentId = departmentId;
        this.designation = designation;

        initUI();
        setVisible(true);
    }

    private void initUI() {
        setTitle("Faculty Dashboard - " + facultyName);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== MENU BAR =====
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(0, 70, 140)); // Blue Color
        menuBar.setForeground(Color.WHITE);

        // Text Color Fix
        UIManager.put("Menu.foreground", Color.WHITE);
        UIManager.put("MenuItem.foreground", Color.BLACK);

        // ===== MENUS =====
        
        JMenu studentsMenu = new JMenu("Students");
       
        JMenu marksMenu = new JMenu("Marks");
        JMenu profileMenu = new JMenu("Profile");
        JMenu logoutMenu = new JMenu("Logout");

        // ===== MENU ITEMS =====
        JMenuItem viewStudents = new JMenuItem("View Students");
        viewStudents.addActionListener(e -> showStudentsPanel());
        JMenuItem enterMarks = new JMenuItem("Enter Marks");
        enterMarks.addActionListener(e -> showMarksPanel());
        JMenuItem viewProfile = new JMenuItem("My Profile");
        viewProfile.addActionListener(e -> showProfilePanel());
        JMenuItem logoutItem = new JMenuItem("Logout");
        
        profileMenu.add(viewProfile);
        studentsMenu.add(viewStudents);
        marksMenu.add(enterMarks);       
        logoutMenu.add(logoutItem);

        menuBar.add(profileMenu);
        menuBar.add(studentsMenu);
        menuBar.add(marksMenu);        
        menuBar.add(logoutMenu);

        setJMenuBar(menuBar);

        // ===== CENTER PANEL =====
        backgroundLabel = new JLabel();
        backgroundLabel.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel(
                "Welcome " + facultyName + " | " + collegeName,
                SwingConstants.CENTER
        );
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 26));
        welcomeLabel.setForeground(Color.WHITE);

        backgroundLabel.add(welcomeLabel, BorderLayout.NORTH);

        add(backgroundLabel, BorderLayout.CENTER);

        // ===== ACTIONS =====
        logoutItem.addActionListener(e -> {
            dispose();
            new Login().setVisible(true);
        });
    }

    // ===== Dynamic Background Method =====
    private void setDynamicBackground(String imagePath) {

    if (imagePath != null && !imagePath.isEmpty()) {

        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));

        if (icon.getIconWidth() == -1) {
            System.out.println("Image Not Found!");
            backgroundLabel.setOpaque(true);
            backgroundLabel.setBackground(Color.GRAY);
            return;
        }

        Image img = icon.getImage().getScaledInstance(
                backgroundLabel.getWidth(),
                backgroundLabel.getHeight(),
                Image.SCALE_SMOOTH
        );

        backgroundLabel.setIcon(new ImageIcon(img));

    } else {
        backgroundLabel.setOpaque(true);
        backgroundLabel.setBackground(new Color(30, 30, 30));
    }
}
    
   private void showProfilePanel() {

    JPanel mainPanel = new JPanel(new BorderLayout());

    JPanel panel = new JPanel(new GridLayout(6,2,15,15));
    panel.setBorder(BorderFactory.createEmptyBorder(50,150,30,150));

    Font labelFont = new Font("Arial", Font.BOLD, 16);

    JTextField nameField = new JTextField();
    JTextField mobileField = new JTextField();
    JTextField collegeField = new JTextField();
    JTextField deptField = new JTextField();
    JTextField qualificationField = new JTextField();
    JTextField courseField = new JTextField(); // course display

    nameField.setEditable(false);
    collegeField.setEditable(false);
    deptField.setEditable(false);
    qualificationField.setEditable(false);
    courseField.setEditable(false);

    JLabel name = new JLabel("Name:");
    name.setFont(labelFont);

    JLabel mobile = new JLabel("Mobile No:");
    mobile.setFont(labelFont);

    JLabel college = new JLabel("College:");
    college.setFont(labelFont);

    JLabel dept = new JLabel("Department:");
    dept.setFont(labelFont);

    JLabel course = new JLabel("Course:");
    course.setFont(labelFont);

    JLabel qualification = new JLabel("Qualification:");
    qualification.setFont(labelFont);

    panel.add(name);
    panel.add(nameField);

    panel.add(mobile);
    panel.add(mobileField);

    panel.add(college);
    panel.add(collegeField);

    panel.add(dept);
    panel.add(deptField);

    panel.add(course);
    panel.add(courseField);

    panel.add(qualification);
    panel.add(qualificationField);

    mainPanel.add(panel, BorderLayout.CENTER);

    // ===== Buttons Panel =====
   // ===== Buttons Panel =====
JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,30,10));

JButton saveButton = new JButton("Save");
JButton backButton = new JButton("Back");

buttonPanel.add(saveButton);
buttonPanel.add(backButton);

mainPanel.add(buttonPanel, BorderLayout.SOUTH);
saveButton.setPreferredSize(new Dimension(120,35));
backButton.setPreferredSize(new Dimension(120,35));


    fetchProfileData(nameField, mobileField, collegeField, deptField, courseField, qualificationField);

    backgroundLabel.removeAll();
    backgroundLabel.add(mainPanel, BorderLayout.CENTER);
    backgroundLabel.revalidate();
    backgroundLabel.repaint();

    // ===== SAVE ACTION =====
   saveButton.addActionListener(e -> {

    String mobileNumber = mobileField.getText().trim();

    if(mobileNumber.length()!=10 || !mobileNumber.matches("\\d+")){
        JOptionPane.showMessageDialog(this,"Enter valid mobile number");
        return;
    }

    try(Connection con = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/postgres",
            "postgres","bhakti")){

        String sql="UPDATE users SET phone=? WHERE user_id=?";
        PreparedStatement ps=con.prepareStatement(sql);

        ps.setString(1,mobileNumber);
        ps.setInt(2,userId);

        ps.executeUpdate();

        JOptionPane.showMessageDialog(this,"Mobile number updated successfully");

    }catch(Exception ex){
        ex.printStackTrace();
    }

});

    // ===== BACK BUTTON =====
    backButton.addActionListener(e -> {

        backgroundLabel.removeAll();

        JLabel welcomeLabel = new JLabel(
                "Welcome " + facultyName + " | " + collegeName,
                SwingConstants.CENTER
        );

        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 26));
        welcomeLabel.setForeground(Color.WHITE);

        backgroundLabel.add(welcomeLabel, BorderLayout.NORTH);

        backgroundLabel.revalidate();
        backgroundLabel.repaint();
    });
}
    private void fetchProfileData(JTextField nameField,
JTextField mobileField,
JTextField collegeField,
JTextField deptField,
JTextField courseField,
JTextField qualificationField) {

    try(Connection con = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/postgres",
            "postgres",
            "bhakti")) {

        String sql = """
        SELECT f.faculty_name,
               f.qualification,
               u.phone AS mobile_no,
               u.course,
               d.department_name,
               c.college_name
        FROM faculty f
        JOIN users u ON f.user_id = u.user_id
        JOIN departments d ON f.department_id = d.department_id
        JOIN colleges c ON d.college_id = c.college_id
        WHERE f.user_id = ?
        """;

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            nameField.setText(rs.getString("faculty_name"));
            mobileField.setText(rs.getString("mobile_no"));
            collegeField.setText(rs.getString("college_name"));
            deptField.setText(rs.getString("department_name"));
            qualificationField.setText(rs.getString("qualification"));
            courseField.setText(rs.getString("course"));
        }

    } catch(Exception e){
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error fetching profile: " + e.getMessage());
    }
}
    
    private void showStudentsPanel() {
    JPanel panel = new JPanel(new BorderLayout());
    
    // Table Columns
    String[] columns = {"Student ID", "Name", "Department", "Course", "College"};
    DefaultTableModel model = new DefaultTableModel(columns, 0);
    JTable table = new JTable(model);
    
    JScrollPane scrollPane = new JScrollPane(table);
    panel.add(scrollPane, BorderLayout.CENTER);

    backgroundLabel.removeAll(); // आधीचं content clear करा
    backgroundLabel.add(panel, BorderLayout.CENTER);
    backgroundLabel.revalidate();
    backgroundLabel.repaint();

    // Fetch students for this faculty
    fetchStudentsForFaculty(model);
}
    
    private void fetchStudentsForFaculty(DefaultTableModel model) {
    try (Connection con = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/postgres",
            "postgres", "bhakti")) {

        String sql = """
            SELECT s.student_id, s.student_name, d.department_name, u.course, c.college_name
            FROM students s
            JOIN users u ON s.user_id = u.user_id
            JOIN departments d ON s.department_id = d.department_id
            JOIN colleges c ON d.college_id = c.college_id
            WHERE s.department_id = ? AND u.course = ? AND c.college_name = ?
        """;

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, this.departmentId);           // faculty departmentId
        ps.setString(2, getFacultyCourse());       // faculty course
        ps.setString(3, this.collegeName);         // faculty college

        ResultSet rs = ps.executeQuery();

        model.setRowCount(0); // clear table

        while(rs.next()){
            Object[] row = {
                rs.getInt("student_id"),
                rs.getString("student_name"),
                rs.getString("department_name"),
                rs.getString("course"),
                rs.getString("college_name")
            };
            model.addRow(row);
        }

    } catch(Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error fetching students: " + e.getMessage());
    }
}
    
    private String getFacultyCourse() {
    try (Connection con = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/postgres",
            "postgres","bhakti")) {

        String sql = "SELECT course FROM users WHERE user_id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, this.userId);

        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            return rs.getString("course");
        }

    } catch(Exception e) {
        e.printStackTrace();
    }
    return null;
}
    
    private void showMarksPanel() {

    JPanel panel = new JPanel(new BorderLayout());

    String[] columns = {"Student ID","Student Name", "College", "Course", "Marks"};

   DefaultTableModel model = new DefaultTableModel(columns,0){
    @Override
    public boolean isCellEditable(int row,int column){
        return column==4; // only marks column editable
    }
};

JTable table = new JTable(model);

    JScrollPane scrollPane = new JScrollPane(table);
    panel.add(scrollPane, BorderLayout.CENTER);

    JButton saveButton = new JButton("Save Marks");

    panel.add(saveButton, BorderLayout.SOUTH);

    backgroundLabel.removeAll();
    backgroundLabel.add(panel, BorderLayout.CENTER);
    backgroundLabel.revalidate();
    backgroundLabel.repaint();

    fetchStudentsForMarks(model);

    saveButton.addActionListener(e -> saveMarks(table));
}
   
    
    private void fetchStudentsForMarks(DefaultTableModel model){

    try(Connection con = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/postgres",
            "postgres","bhakti")){

        String sql = """
        SELECT s.student_id,
               s.student_name,
               c.college_name,
               u.course
        FROM students s
        JOIN users u ON s.user_id = u.user_id
        JOIN departments d ON s.department_id = d.department_id
        JOIN colleges c ON d.college_id = c.college_id
        WHERE s.department_id=? AND c.college_name=? AND u.course=?
        """;

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1,this.departmentId);
        ps.setString(2,this.collegeName);
        ps.setString(3,getFacultyCourse());

        ResultSet rs = ps.executeQuery();
        model.setRowCount(0);

        while(rs.next()){

            Object[] row = {
                rs.getInt("student_id"),
                rs.getString("student_name"),
                rs.getString("college_name"),
                rs.getString("course"),
                ""
            };

            model.addRow(row);
        }

    }catch(Exception e){
        e.printStackTrace();
    }
}
    
    
    private String getSubjectFromCourse(String course){

    if(course.equalsIgnoreCase("M.E"))
        return "Mechanics";

    if(course.equalsIgnoreCase("C.E"))
        return "Engineering Drawing";

    if(course.equalsIgnoreCase("C.S"))
        return "Programming";

    return "General Subject";
}
    
    
    private void saveMarks(JTable table){
        
        if(table.isEditing())
        {
            table.getCellEditor().stopCellEditing();
        }

    try(Connection con = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/postges",
            "postgres","bhakti")){

        for(int i=0;i<table.getRowCount();i++){
            
            int studentId = Integer.parseInt(table.getValueAt(i,0).toString());
            String course = table.getValueAt(i,3).toString();
            String marks = table.getValueAt(i,4)==null ? "" : table.getValueAt(i,4).toString();

            if(marks.isEmpty()) continue;
            
            // check number format
            if(!marks.matches("\\d+")){
                JOptionPane.showMessageDialog(this,"Marks must be numeric value");
                return;
            }

            // convert to integer
            int marksValue = Integer.parseInt(marks);

            // check range
            if(marksValue < 0 || marksValue > 100){
                JOptionPane.showMessageDialog(this,"Marks must be between 0 and 100");
                return;
            }

                    String insertSql="""
                    INSERT INTO marks(student_id,course,marks)
                    VALUES(?,?,?)
                    """;

                    PreparedStatement ps=con.prepareStatement(insertSql);

                    ps.setInt(1,studentId);
                    ps.setString(2,course);
                    ps.setInt(3,marksValue);

                    ps.executeUpdate();
            }
        

        JOptionPane.showMessageDialog(this,"Marks Saved Successfully");

    }catch(Exception e){
        e.printStackTrace();
    }
}

}