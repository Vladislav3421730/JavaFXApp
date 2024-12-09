package com.example.frontend.controllers;

import com.example.frontend.TCP.ClientSocket;
import com.example.frontend.TCP.Request;
import com.example.frontend.TCP.Response;
import com.example.frontend.TCP.enums.RequestType;
import com.example.frontend.dto.PatentDTO;
import com.example.frontend.models.entities.Patent;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddPatent  {

    private  Patent patent;


    public Patent getPatent() {
        return patent;
    }

    public void setPatent(Patent patent) {
        this.patent = patent;
        SaveButton.setText("Редактировать");
        name.setText(patent.getName());
        companyName.setText(patent.getCompany());
        term.setText(String.valueOf(patent.getValidity_period()));
        LocalDate localDate = Instant.ofEpochMilli(patent.getPurchase_date().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        dateStart.setValue(localDate);
        coast.setText( String.valueOf(patent.getCost()));
    }


    @FXML
    private Label message;
    @FXML
    private TextField coast;
    @FXML
    private DatePicker dateStart;
    @FXML
    private TextField term;
    @FXML
    private TextField companyName;
    @FXML
    private TextField name;
    @FXML
    private Button BackButton;

    @FXML
    private Button SaveButton;



    public void BackButtonPressed(ActionEvent actionEvent) throws IOException {
        Stage stage= (Stage) BackButton.getScene().getWindow();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/frontend/Patents.fxml")));
        stage.setTitle("Патенты");
        stage.setScene(new Scene(root));
    }

    public void SaveButtonPressed(ActionEvent actionEvent) throws IOException {
        try {
            Float.parseFloat(term.getText());
            Double.parseDouble(coast.getText());
        } catch (Exception e){
            message.setStyle("-fx-text-fill: red;");
            message.setText("Стоимость и срок должны быть числом с плавающей точкей");
            message.setVisible(true);
            return;
        }
        Date startDate=Date.from(Instant.from(dateStart.getValue().atStartOfDay().atZone(ZoneId.systemDefault())));

        Patent patentAdd=new Patent
                .PatentBuilder()
                .name(name.getText())
                .company(companyName.getText())
                .cost(BigDecimal.valueOf(Double.parseDouble(coast.getText())))
                .validity_period(Float.parseFloat(term.getText()))
                .purchase_date(startDate)
                .build();
        if (patent!=null){
            patentAdd.setId(patent.getId());
            ClientSocket.getInstance().getOut().println(new Gson().toJson(new Request(new Gson().toJson(patentAdd), RequestType.UPDATE_PATENT)));
        }
        else {
            ClientSocket.getInstance().getOut().println(new Gson().toJson(new Request(new Gson().toJson(patentAdd), RequestType.ADD_PATENT)));
        }
        ClientSocket.getInstance().getOut().flush();
        Response response=new Gson().fromJson(ClientSocket.getInstance().getIn().readLine(), Response.class);
        BackButtonPressed(actionEvent);

    }

}
