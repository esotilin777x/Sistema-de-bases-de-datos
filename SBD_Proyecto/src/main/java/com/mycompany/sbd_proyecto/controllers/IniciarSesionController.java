/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.sbd_proyecto.controllers;

import com.mycompany.sbd_proyecto.modelo.Rol;
import com.mycompany.sbd_proyecto.modelo.Usuario;
import com.mycompany.sbd_proyecto.modelo.UsuarioService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class IniciarSesionController implements Initializable {

    @FXML
    private TextField tfEmail;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private Button iniciarSesionButton;

    @FXML
    private Label errorLabel;
    
    @FXML
    private Label REGISTRARSE;
    
    @FXML
    private ComboBox<Rol> rolCB;

    @FXML
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Comprobación de ejecución
        System.out.println("initialize ejecutado");
        // Asociar el evento de clic al Label REGISTRARSE
        REGISTRARSE.setOnMouseClicked(event -> {
            try {
                // Cargar el nuevo archivo FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/registro.fxml"));
                Parent root = loader.load();

                // Obtener el Stage actual a través del Label
                Stage stage = (Stage) REGISTRARSE.getScene().getWindow();

                // Configurar la nueva escena y mostrarla
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Registro");
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    // Métodos para obtener email y contraseña
    @FXML
    private String getEmail() {
        return tfEmail.getText();
    }
    
    @FXML
    private Rol showRoles() {
        return rolCB.getValue();
    }
    
    @FXML
    private String getPassword() {
        return tfPassword.getText();
    }

    // Método para manejar el inicio de sesión
// Método para manejar el inicio de sesión
    @FXML
    private void iniciarSesion() {
        String email = getEmail();
        String password = getPassword();

        UsuarioService usuarioService = new UsuarioService();
        Usuario usuario = usuarioService.autenticarUsuario(email, password);

        if (usuario != null) {
            // Usuario autenticado correctamente, redirigir según su rol
            redirigirSegunRol(usuario);
        } else {
            // Mostrar mensaje de error
            errorLabel.setText("Credenciales incorrectas");
        }
    }



    // Método para redirigir al usuario según su rol
    @SuppressWarnings("CallToPrintStackTrace")
    private void redirigirSegunRol(Usuario usuario) {
        try {
            Stage stage = (Stage) iniciarSesionButton.getScene().getWindow();
            Parent root;

            switch (usuario.getRol()) {
                case "Dueño del Negocio":
                    root = FXMLLoader.load(getClass().getResource("/fxml/dueno.fxml"));
                    break;
                case "Gerente":
                    root = FXMLLoader.load(getClass().getResource("/fxml/gerente.fxml"));
                    break;
                case "Cajero":
                    root = FXMLLoader.load(getClass().getResource("/fxml/cajero.fxml"));
                    break;
                case "Proveedor":
                    root = FXMLLoader.load(getClass().getResource("/fxml/proveedor.fxml"));
                    break;
                default:
                    root = FXMLLoader.load(getClass().getResource("/fxml/inicio.fxml"));
                    break;
            }

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Panel de " + usuario.getRol());
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}