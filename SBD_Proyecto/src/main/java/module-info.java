module com.mycompany.sbd_proyecto {
    requires javafx.controls;
    requires java.base;
    requires javafx.fxml; 
    opens com.mycompany.sbd_proyecto to javafx.fxml;
    exports com.mycompany.sbd_proyecto;
}
