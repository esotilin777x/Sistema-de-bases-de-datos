module com.mycompany.sbd_proyecto {
    // modulo para jbdc
    requires java.sql;

    
    requires javafx.controls;
    requires javafx.fxml;

    
    exports com.mycompany.modelo;

    opens com.mycompany.sbd_proyecto to javafx.fxml;
}

