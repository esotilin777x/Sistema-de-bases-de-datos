package com.mycompany.sbd_proyecto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */


public class App extends Application {

    @Override
    public void start(Stage stage) {
        try {
            // Cargar el archivo FXML de la pantalla de inicio
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/registro.fxml"));

            Parent root = loader.load();

            // Crear la escena con el archivo FXML cargado
            Scene scene = new Scene(root);

            // Configurar el escenario (Stage) y mostrarlo
            stage.setScene(scene);
            stage.setWidth(700);  // Ancho deseado
            stage.setHeight(500); // Altura deseada
            stage.setTitle("Pantalla de Inicio");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}