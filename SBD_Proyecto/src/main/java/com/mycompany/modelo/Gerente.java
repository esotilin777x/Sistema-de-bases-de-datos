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
public class Gerente extends Usuario {

    public Gerente(int id, String nombreUsuario, String contrasena, String nombre, String apellido, String email) {
        super(id, nombreUsuario, contrasena, nombre, apellido, email);
    }

    // Método para generar un reporte de ventas
    public ResultSet generarReporteVentas() {
        String query = "SELECT * FROM ventasPorUsuario WHERE UsuarioID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, this.getId());
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean agregarUsuario(Usuario nuevoUsuario) {
        return nuevoUsuario.registrarUsuario();
    }
    
    //eliminar, modificar faltantes
}

