/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sbd_proyecto.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mycompany.sbd_proyecto.util.DatabaseConnection;
/**
 *
 * @author LENOVO
 */
public class Cajero extends Usuario {

    public Cajero(int id, String nombreUsuario, String contrasena, String rol, String fechaCreacion) {
        super(id, nombreUsuario, contrasena, rol, fechaCreacion);
    }

    // Métodos específicos del Cajero
  public boolean registrarVenta(Pedido pedido) {
        String query = "INSERT INTO Pedidos (ClienteID, UsuarioID, FechaPedido, Total) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, pedido.getClienteID());
            stmt.setInt(2, this.getId());
            stmt.setDate(3, pedido.getFechaPedido());
            stmt.setDouble(4, pedido.getTotal());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para consultar el stock de un producto
    public Producto consultarStock(int productoID) {
        String query = "SELECT * FROM Productos WHERE ProductoID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, productoID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Producto(
                        rs.getInt("ProductoID"),
                        rs.getString("NombreProducto"),
                        rs.getDouble("Precio"),
                        rs.getInt("Stock")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

