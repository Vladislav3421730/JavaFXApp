package com.example.frontend.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class EquipmentController {
    @FXML
    private Button BackButton;

    public void BackButtonPressed(ActionEvent actionEvent) throws IOException {
        Stage stage= (Stage) BackButton.getScene().getWindow();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/frontend/ManagerPanel.fxml")));
        stage.setTitle("Панель менеджера");
        stage.setScene(new Scene(root));
    }

    public void AddButtonPressed(ActionEvent actionEvent) throws IOException {
        Stage stage= (Stage) BackButton.getScene().getWindow();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/frontend/AddEquipment.fxml")));
        stage.setTitle("Добавление патента");
        stage.setScene(new Scene(root));
    }
}
