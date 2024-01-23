module com.example.laberinto {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires javafx.swing;

    opens com.example.laberinto to javafx.fxml;
    opens com.example.laberinto.ControllerItem to javafx.fxml;
    exports com.example.laberinto;
}