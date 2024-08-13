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
public class Proveedor extends Usuario {
    private String informacionContacto;

    public Proveedor(int id, String nombreUsuario, String contrasena, String nombre, String apellido, String email, String informacionContacto) {
        super(id, nombreUsuario, contrasena, nombre, apellido, email);
        this.informacionContacto = informacionContacto;
    }

    // Getters y Setters
    public String getInformacionContacto() {
        return informacionContacto;
    }

    public void setInformacionContacto(String informacionContacto) {
        this.informacionContacto = informacionContacto;
    }

    // Método para actualizar la información de contacto
    public boolean actualizarInformacionContacto(String nuevaInformacion) {
        String query = "UPDATE Proveedores SET InformacionContacto = ? WHERE UsuarioID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nuevaInformacion);
            stmt.setInt(2, this.getId());
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                this.informacionContacto = nuevaInformacion;
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para ver los productos suministrados
    public ResultSet verProductosSuministrados() {
        String query = "SELECT * FROM productosProveedor WHERE ProveedorID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, this.getId());
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
