/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

/**
 *
 * @author carlo
 */
import java.sql.*;
public class clsconexion {
    Connection con=null;
    public clsconexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
                con=DriverManager.getConnection("jdbc:mysql://mysql8003.site4now.net:3306/db_aadada_promuni","aadada_promuni","carlos60005");
            System.out.println("Si se conecto");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("No se conecto");
        }
    }
    public Connection getConnection(){
        return con;
    }
}
