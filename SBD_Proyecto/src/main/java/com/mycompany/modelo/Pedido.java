/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.modelo;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class Pedido {
    private int pedidoID;
    private int clienteID;
    private int usuarioID; // Usuario que registra el pedido (ej: Cajero)
    private Date fechaPedido;
    private double total;
    private List<DetallePedido> detalles;

    public Pedido(int clienteID, int usuarioID, Date fechaPedido, double total) {
        this.clienteID = clienteID;
        this.usuarioID = usuarioID;
        this.fechaPedido = fechaPedido;
        this.total = total;
        this.detalles = new ArrayList<>();
    }

    // Getters y Setters
    public int getPedidoID() {
        return pedidoID;
    }

    public void setPedidoID(int pedidoID) {
        this.pedidoID = pedidoID;
    }

    public int getClienteID() {
        return clienteID;
    }

    public void setClienteID(int clienteID) {
        this.clienteID = clienteID;
    }

    public int getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(int usuarioID) {
        this.usuarioID = usuarioID;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    // Método para agregar un detalle al pedido
    public void agregarDetalle(DetallePedido detalle) {
        this.detalles.add(detalle);
    }

    // Método para guardar el pedido en la base de datos
    public boolean guardarPedido() throws SQLException {
        String queryPedido = "INSERT INTO Pedidos (ClienteID, UsuarioID, FechaPedido, Total) VALUES (?, ?, ?, ?)";
        String queryDetalle = "INSERT INTO DetallesPedido (PedidoID, ProductoID, Cantidad, PrecioUnitario) VALUES (?, ?, ?, ?)";
        Connection conn = DatabaseConnection.getConnection();

        try (conn) {
            // Desactivar el autocommit para manejar la transacción
            conn.setAutoCommit(false);

            // Guardar el pedido principal
            try (PreparedStatement stmtPedido = conn.prepareStatement(queryPedido, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmtPedido.setInt(1, this.clienteID);
                stmtPedido.setInt(2, this.usuarioID);
                stmtPedido.setDate(3, this.fechaPedido);
                stmtPedido.setDouble(4, this.total);

                int rowsInserted = stmtPedido.executeUpdate();

                if (rowsInserted > 0) {
                    ResultSet generatedKeys = stmtPedido.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        this.pedidoID = generatedKeys.getInt(1);
                    }
                } else {
                    conn.rollback(); // Deshacer la transacción en caso de error
                    return false;
                }
            }

            // Guardar los detalles del pedido
            try (PreparedStatement stmtDetalle = conn.prepareStatement(queryDetalle)) {
                for (DetallePedido detalle : this.detalles) {
                    stmtDetalle.setInt(1, this.pedidoID);
                    stmtDetalle.setInt(2, detalle.getProductoID());
                    stmtDetalle.setInt(3, detalle.getCantidad());
                    stmtDetalle.setDouble(4, detalle.getPrecioUnitario());
                    stmtDetalle.addBatch();
                }
                stmtDetalle.executeBatch();
            }

            // Commit de la transacción
            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback(); // Deshacer la transacción en caso de error
        }
        return false;
    }

    // Método para cargar un pedido desde la base de datos
    public static Pedido cargarPedido(int pedidoID) {
        String queryPedido = "SELECT * FROM Pedidos WHERE PedidoID = ?";
        String queryDetalle = "SELECT * FROM DetallesPedido WHERE PedidoID = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            Pedido pedido = null;

            // Cargar el pedido principal
            try (PreparedStatement stmtPedido = conn.prepareStatement(queryPedido)) {
                stmtPedido.setInt(1, pedidoID);
                ResultSet rsPedido = stmtPedido.executeQuery();

                if (rsPedido.next()) {
                    pedido = new Pedido(
                            rsPedido.getInt("ClienteID"),
                            rsPedido.getInt("UsuarioID"),
                            rsPedido.getDate("FechaPedido"),
                            rsPedido.getDouble("Total")
                    );
                    pedido.setPedidoID(rsPedido.getInt("PedidoID"));
                }
            }

            if (pedido != null) {
                // Cargar los detalles del pedido
                try (PreparedStatement stmtDetalle = conn.prepareStatement(queryDetalle)) {
                    stmtDetalle.setInt(1, pedidoID);
                    ResultSet rsDetalle = stmtDetalle.executeQuery();

                    while (rsDetalle.next()) {
                        DetallePedido detalle = new DetallePedido(
                                rsDetalle.getInt("ProductoID"),
                                rsDetalle.getInt("Cantidad"),
                                rsDetalle.getDouble("PrecioUnitario")
                        );
                        pedido.agregarDetalle(detalle);
                    }
                }
            }

            return pedido;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
