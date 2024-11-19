/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeloDAO;
import java.sql.*;
import config.*;
import modelo.UsuarioMunicipalidad;

/**
 *
 * @author carlo
 */
public class UsuarioMunicipalidadDAO {
    private final clsconexion conexion = new clsconexion();

    public UsuarioMunicipalidad validarUsuario(String usuario, String contraseña) {
        UsuarioMunicipalidad usuarioMunicipalidad = null;
        String sql = "SELECT * FROM LoginMunicipalidad WHERE Usuario = ? AND Contraseña = ?";
        
        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, usuario);
            ps.setString(2, contraseña);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuarioMunicipalidad = new UsuarioMunicipalidad();
                    usuarioMunicipalidad.setID_LoginMuni(rs.getInt("ID_LoginMuni"));
                    usuarioMunicipalidad.setUsuario(rs.getString("Usuario"));
                    usuarioMunicipalidad.setContraseña(rs.getString("Contraseña"));
                    usuarioMunicipalidad.setEstado(rs.getInt("Estado"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarioMunicipalidad;
    }

    public UsuarioMunicipalidad obtenerUsuarioPorNombre(String usuario) {
        UsuarioMunicipalidad usuarioMunicipalidad = null;
        String sql = "SELECT * FROM LoginMunicipalidad WHERE Usuario = ?";
        
        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, usuario);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuarioMunicipalidad = new UsuarioMunicipalidad();
                    usuarioMunicipalidad.setID_LoginMuni(rs.getInt("ID_LoginMuni"));
                    usuarioMunicipalidad.setUsuario(rs.getString("Usuario"));
                    usuarioMunicipalidad.setContraseña(rs.getString("Contraseña"));
                    usuarioMunicipalidad.setEstado(rs.getInt("Estado"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarioMunicipalidad;
    }
}