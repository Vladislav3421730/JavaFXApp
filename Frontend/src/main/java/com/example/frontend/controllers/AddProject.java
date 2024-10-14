package com.example.frontend.controllers;

import com.example.frontend.TCP.ClientSocket;
import com.example.frontend.TCP.Request;
import com.example.frontend.TCP.Response;
import com.example.frontend.TCP.enums.RequestType;
import com.example.frontend.models.entities.Project;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

public class AddProject {

    @FXML
    private Label message;
    @FXML
    private TextField budget;
    @FXML
    private DatePicker end_date;
    @FXML
    private DatePicker start_date;
    @FXML
    private TextField status;
    @FXML
    private TextArea description;
    @FXML
    private TextField name;
    @FXML
    private Button BackButton;

    public void CommitChanges(ActionEvent actionEvent) throws IOException {

        try {
            Double.parseDouble(budget.getText());
        } catch (Exception e){
            message.setStyle("-fx-text-fill: red;");
            message.setText("Бюджет должен быть числом с плавающей точкей");
            message.setVisible(true);
            return;
        }
        Date startDate=Date.from(Instant.from(start_date.getValue().atStartOfDay().atZone(ZoneId.systemDefault())));
        Date endDate=Date.from(Instant.from(end_date.getValue().atStartOfDay().atZone(ZoneId.systemDefault())));

        if(startDate.compareTo(endDate)>0){
            message.setStyle("-fx-text-fill: red;");
            message.setText("Введите корректные сроки работы");
            message.setVisible(true);
            return;
        }

        Project project=new Project.ProjectBuilder()
                .setName(name.getText())
                .setStatus(status.getText())
                .setDescription(description.getText())
                .setStartDate(startDate)
                .setEndDate(endDate)
                .setBudget(BigDecimal.valueOf(Double.parseDouble(budget.getText())))
                .build();

        ClientSocket.getInstance().getOut().println(new Gson().toJson(new Request(new Gson().toJson(project), RequestType.ADD_PROJECT)));
        ClientSocket.getInstance().getOut().flush();

        Response response=new Gson().fromJson(ClientSocket.getInstance().getIn().readLine(), Response.class);

        message.setVisible(true);
        message.setStyle("-fx-text-fill: blue;");
        message.setText(response.getMessage());


    }

    public void BackButton_pressed(ActionEvent actionEvent) throws IOException {
        Stage stage= (Stage) BackButton.getScene().getWindow();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/frontend/ManagerPanel.fxml")));
        stage.setTitle("Панель менеджера");
        stage.setScene(new Scene(root));
    }
}
