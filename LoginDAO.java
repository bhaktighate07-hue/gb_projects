/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DELL
 */

    import java.sql.*;

public class LoginDAO {

    public static ResultSet login(String u,String p) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps =
              con.prepareStatement(
              "SELECT role,college FROM users WHERE username=? AND password=?");

            ps.setString(1,u);
            ps.setString(2,p);
            return ps.executeQuery();

        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
    

