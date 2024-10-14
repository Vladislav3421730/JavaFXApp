module com.example.frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires com.google.gson;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.frontend to javafx.fxml;
    exports com.example.frontend;
    exports com.example.frontend.controllers;
    opens com.example.frontend.controllers to javafx.fxml;
    opens com.example.frontend.models.entities to com.google.gson,javafx.base;
    opens com.example.frontend.models.enums to com.google.gson;
    opens com.example.frontend.TCP to com.google.gson;
    exports com.example.frontend.TCP.enums to com.google.gson;
    opens com.example.frontend.dto to com.google.gson, javafx.base;

}
