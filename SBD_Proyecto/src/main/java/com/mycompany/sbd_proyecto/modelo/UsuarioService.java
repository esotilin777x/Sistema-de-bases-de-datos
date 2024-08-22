/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sbd_proyecto.modelo;
import com.mycompany.sbd_proyecto.modelo.Usuario;
import com.mycompany.sbd_proyecto.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author LENOVO
 */
public class UsuarioService {

    // Método para autenticar usuarios usando la base de datos
    public Usuario autenticarUsuario(String email, String password) {
        Usuario usuario = null;
        String query = "SELECT * FROM usuarios WHERE NombreUsuario = ? AND Contrasena = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Si encuentra un usuario con esas credenciales
                usuario = new Usuario(
                    rs.getInt("UsuarioID"),
                    rs.getString("NombreUsuario"),
                    rs.getString("Contrasena"),
                    rs.getString("Rol"),
                    rs.getString("FechaCreacion")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario; // Retorna el usuario o null si no se encontró
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
                // Crear un nuevo objeto Usuario con los datos de la base de datos
                Usuario usuario = new Usuario(
                    rs.getInt("UsuarioID"),
                    rs.getString("NombreUsuario"),
                    rs.getString("Contrasena"),
                    rs.getString("Rol"),
                    rs.getString("FechaCreacion")
                );
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para registrar un nuevo usuario en la base de datos
    public boolean registrarUsuario(String email, String password, String rol) {
        String query = "INSERT INTO usuarios (NombreUsuario, Contrasena, Rol) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, rol);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}