/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DELL
 */

    import java.sql.*;

public class DBConnection {

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(
              "jdbc:postgresql://localhost:5432/postgres",
              "postgres",
              "bhakti"   // तुझा pg password
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

