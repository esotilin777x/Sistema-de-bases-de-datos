/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.modelo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author LENOVO
 */
public class Usuario {
    private int id;
    private String nombreUsuario;
    private String contrasena;
    private String nombre;
    private String apellido;
    private String email;

    public Usuario(int id, String nombreUsuario, String contrasena, String nombre, String apellido, String email) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

 // Método para iniciar sesión
    public boolean iniciarSesion(String nombreUsuario, String contrasena) {
        String query = "SELECT * FROM Usuarios WHERE nombreUsuario = ? AND contrasena = ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombreUsuario);
            stmt.setString(2, contrasena);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Inicializa los datos del usuario en la instancia actual
                this.id = rs.getInt("UsuarioID");
                this.nombre = rs.getString("Nombre");
                this.apellido = rs.getString("Apellido");
                this.email = rs.getString("Email");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean registrarUsuario() {
        String query = "INSERT INTO Usuarios (nombreUsuario, contrasena, nombre, apellido, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, this.nombreUsuario);
            stmt.setString(2, this.contrasena);
            stmt.setString(3, this.nombre);
            stmt.setString(4, this.apellido);
            stmt.setString(5, this.email);
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}

