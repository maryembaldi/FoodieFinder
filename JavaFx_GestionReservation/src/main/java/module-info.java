module com.example.javafx_gestionreservation {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.javafx_gestionreservation.Entite to javafx.base;

    opens com.example.javafx_gestionreservation to javafx.fxml;
    exports com.example.javafx_gestionreservation;
    exports com.example.javafx_gestionreservation.Controller;
    opens com.example.javafx_gestionreservation.Controller to javafx.fxml;
}