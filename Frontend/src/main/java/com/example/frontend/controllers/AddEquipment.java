package com.example.frontend.controllers;

import com.example.frontend.TCP.ClientSocket;
import com.example.frontend.TCP.Request;
import com.example.frontend.TCP.Response;
import com.example.frontend.TCP.enums.RequestType;
import com.example.frontend.models.entities.Equipment;
import com.example.frontend.models.entities.Patent;
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

public class AddEquipment {

    @FXML
    private TextArea description;
    @FXML
    private Label message;
    @FXML
    private Button BackButton;
    @FXML
    private TextField name;
    @FXML
    private TextField cost;
    @FXML
    private DatePicker dateStart;

    public void BackButtonPressed(ActionEvent actionEvent) throws IOException {
        Stage stage= (Stage) BackButton.getScene().getWindow();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/frontend/Equipment.fxml")));
        stage.setTitle("Оборудование");
        stage.setScene(new Scene(root));
    }

    public void SaveButtonPressed(ActionEvent actionEvent) throws IOException {
        try {
            Double.parseDouble(cost.getText());
        } catch (Exception e){
            message.setStyle("-fx-text-fill: red;");
            message.setText("Стоимость должна быть числом с плавающей точкей");
            message.setVisible(true);
            return;
        }
        Date startDate=Date.from(Instant.from(dateStart.getValue().atStartOfDay().atZone(ZoneId.systemDefault())));

        Equipment equipment=new Equipment.EquipmentBuilder()
                .name(name.getText())
                .cost( BigDecimal.valueOf(Double.parseDouble(cost.getText())))
                .description(description.getText())
                .purchase_date(startDate)
                .build();

        ClientSocket.getInstance().getOut().println(new Gson().toJson(new Request(new Gson().toJson(equipment), RequestType.ADD_EQUIPMENT)));
        ClientSocket.getInstance().getOut().flush();
        Response response=new Gson().fromJson(ClientSocket.getInstance().getIn().readLine(), Response.class);
        BackButtonPressed(actionEvent);
    }
}
