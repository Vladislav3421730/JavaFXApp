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

public class ManagerPanel {

    @FXML
    private Button BackButton;

    @FXML
    private Button AddProjectButton;

    public void BackButtonPressed(ActionEvent actionEvent) throws IOException {
        Stage stage= (Stage) BackButton.getScene().getWindow();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/frontend/DefaultPage.fxml")));
        stage.setTitle("Начальная страница");
        stage.setScene(new Scene(root));

    }

    public void AddProjectButton_Pressed(ActionEvent actionEvent) throws IOException {
        Stage stage= (Stage) AddProjectButton.getScene().getWindow();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/frontend/AddProject.fxml")));
        stage.setTitle("Добавление исследования");
        stage.setScene(new Scene(root));
    }

    public void AddPatentPressed(ActionEvent actionEvent) throws IOException {
        Stage stage= (Stage) BackButton.getScene().getWindow();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/frontend/Patents.fxml")));
        stage.setTitle("Патенты");
        stage.setScene(new Scene(root));

    }

    public void AddEquipmentPressed(ActionEvent actionEvent) throws IOException {
        Stage stage= (Stage) BackButton.getScene().getWindow();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/frontend/Equipment.fxml")));
        stage.setTitle("Оборудование компании");
        stage.setScene(new Scene(root));
    }
}
