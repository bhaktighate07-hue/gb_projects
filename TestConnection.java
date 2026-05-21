/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DELL
 */

    import java.sql.Connection;

public class TestConnection {

    public static void main(String[] args) {

        System.out.println("🔄 Checking database connection...");

        Connection con = DBConnection.getConnection();

        if (con != null) {
            System.out.println("✅ Database connected successfully");
        } else {
            System.out.println("❌ Database connection FAILED");
        }
    }
}
    

