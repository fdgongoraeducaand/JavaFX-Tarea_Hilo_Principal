module com.example.tarea_larga_hilo_principal {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tarea_larga_hilo_principal to javafx.fxml;
    exports com.example.tarea_larga_hilo_principal;
}