package com.example.frontend;

import com.example.frontend.TCP.ClientSocket;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class HelloApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        ClientSocket.getInstance().getSocket();
        Parent parent =FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("login.fxml")));
        stage.setTitle("Вход в систему");
        stage.setScene(new Scene(parent));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
