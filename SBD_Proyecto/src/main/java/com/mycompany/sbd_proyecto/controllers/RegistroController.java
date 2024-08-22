/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.sbd_proyecto.controllers;

import com.mycompany.sbd_proyecto.modelo.Rol;
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
public class RegistroController implements Initializable {

    @FXML
    private Label ACCEDER;
    
    @FXML
    private Button crearCuentaButton;
    
    @FXML
    private TextField tfEmail;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private ComboBox<Rol> rolCB;
            
    @FXML
    private String getEmail() {
        return tfEmail.getText();
    }

    // Método para obtener la contraseña ingresada
    @FXML
    private String getPassword() {
        return tfPassword.getText();
    }
    
    @FXML
    private Label errorLabel;
    
    @FXML
    private Rol getRol() {
        return rolCB.getValue();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Aquí se inicializan los componentes después de cargar el FXML
        rolCB.getItems().setAll(Rol.values());
        // Asociar el evento de clic al Label ACCEDER
        ACCEDER.setOnMouseClicked(event -> {
            try {
                // Cargar el nuevo archivo FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/iniciarSesion.fxml"));
                Parent root = loader.load();

                // Obtener el Stage actual a través del Label
                Stage stage = (Stage) ACCEDER.getScene().getWindow();

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

    @FXML
    private void crearCuenta() {
        String email = getEmail();  // Obtener email del formulario
        String password = getPassword();  // Obtener contraseña del formulario
        Rol rolSeleccionado = getRol();  // Obtener rol seleccionado del ComboBox

        if (email.isEmpty() || password.isEmpty() || rolSeleccionado == null) {
            errorLabel.setText("Por favor, complete todos los campos.");
            return;
        }

        UsuarioService usuarioService = new UsuarioService();
        boolean registroExitoso = usuarioService.registrarUsuario(email, password, rolSeleccionado.toString());

        if (registroExitoso) {
            // Redirigir a la pantalla de inicio de sesión o mostrar un mensaje de éxito
            errorLabel.setText("Registro exitoso. Ahora puede iniciar sesión.");
        } else {
            errorLabel.setText("Error en el registro. Intente nuevamente.");
        }
    }
} 
    
