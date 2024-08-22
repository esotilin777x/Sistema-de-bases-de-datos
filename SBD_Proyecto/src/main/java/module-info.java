module com.mycompany.sbd_proyecto {
    requires javafx.controls;
<<<<<<< Updated upstream
    exports com.mycompany.sbd_proyecto;
=======
    requires javafx.fxml;
    requires java.base;

    exports com.mycompany.sbd_proyecto.util;
    exports com.mycompany.sbd_proyecto;
    opens com.mycompany.sbd_proyecto.controllers to javafx.fxml;

    opens com.mycompany.sbd_proyecto to javafx.fxml;
>>>>>>> Stashed changes
}
