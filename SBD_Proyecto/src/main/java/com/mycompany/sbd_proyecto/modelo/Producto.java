/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sbd_proyecto.modelo;
import com.mycompany.sbd_proyecto.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author LENOVO
 */
public class Producto {
    private int id;
    private String nombreProducto;
    private double precio;
    private int stock;

    public Producto(int id, String nombreProducto, double precio, int stock) {
        this.id = id;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.stock = stock;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

 // Método para actualizar el stock del producto
    public boolean actualizarStock(int cantidad) {
        String query = "UPDATE Productos SET Stock = ? WHERE ProductoID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, this.stock + cantidad); // O resta dependiendo de la operación
            stmt.setInt(2, this.id);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                this.stock += cantidad;
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para insertar un nuevo producto en la base de datos
    public boolean agregarProducto() {
        String query = "INSERT INTO Productos (NombreProducto, Precio, Stock) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, this.nombreProducto);
            stmt.setDouble(2, this.precio);
            stmt.setInt(3, this.stock);
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

