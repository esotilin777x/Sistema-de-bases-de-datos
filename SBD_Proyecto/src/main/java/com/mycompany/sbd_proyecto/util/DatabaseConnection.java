/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sbd_proyecto.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author LENOVO
 */

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/TiendaOnline"; // Cambia esto según tu configuración
    private static final String USER = "root"; // Cambia esto por tu usuario
    private static final String PASSWORD = "esotilin"; // Cambia esto por tu contraseña

    // Constructor privado para evitar instancias de esta clase
    private DatabaseConnection() {
    }

    // Método para obtener una conexión a la base de datos
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Método opcional para cerrar la conexión
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

