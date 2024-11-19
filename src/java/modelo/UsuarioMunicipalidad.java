/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author carlo
 */
// UsuarioMunicipalidad.java
public class UsuarioMunicipalidad {
    private int ID_LoginMuni;
    private String usuario;
    private String contraseña;
    private int estado;

    // Constructor
    public UsuarioMunicipalidad() {}

    // Getters y Setters
    public int getID_LoginMuni() {
        return ID_LoginMuni;
    }

    public void setID_LoginMuni(int ID_LoginMuni) {
        this.ID_LoginMuni = ID_LoginMuni;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}

