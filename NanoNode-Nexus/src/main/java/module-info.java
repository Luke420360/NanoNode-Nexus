module com.example.nanonodenexus {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens com.example.nanonodenexus to javafx.fxml;
    exports com.example.nanonodenexus;
}