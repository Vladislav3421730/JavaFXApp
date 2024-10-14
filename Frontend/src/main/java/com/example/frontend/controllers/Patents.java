package com.example.frontend.controllers;

import com.example.frontend.TCP.ClientSocket;
import com.example.frontend.TCP.Request;
import com.example.frontend.TCP.Response;
import com.example.frontend.TCP.enums.RequestType;
import com.example.frontend.TCP.enums.ResponseType;
import com.example.frontend.dto.PatentDTO;
import com.example.frontend.mappers.MapToPatentDTO;
import com.example.frontend.models.entities.Patent;
import com.example.frontend.models.entities.Project;
import com.google.gson.Gson;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Patents implements Initializable {

    @FXML
    private Label message;
    @FXML
    private TableView<PatentDTO> table;
    @FXML
    private TableColumn<PatentDTO,Integer> id;
    @FXML
    private TableColumn<PatentDTO,String> name;
    @FXML
    private TableColumn<PatentDTO,String> company;
    @FXML
    private TableColumn<PatentDTO, BigDecimal> cost;
    @FXML
    private TableColumn<PatentDTO, String> dateStart;
    @FXML
    private TableColumn<PatentDTO,Float> temp;
    @FXML
    private TableColumn<PatentDTO,Button> editing;
    @FXML
    private TableColumn<PatentDTO,Button> deleting;
    @FXML
    private Button BackButton;

    private ObservableList<PatentDTO> observablePatentList;

    public void refreshTable(){
        ClientSocket.getInstance().getOut().println(new Gson().toJson(new Request(null, RequestType.GET_ALL_PATENTS)));
        ClientSocket.getInstance().getOut().flush();
        try {
            Response response = new Gson().fromJson(ClientSocket.getInstance().getIn().readLine(), Response.class);
            Patent[] patents = new Gson().fromJson(response.getMessage(), Patent[].class);
            List<PatentDTO> patentDTOS = Arrays.stream(patents).map(MapToPatentDTO::mapToPatentDTO).collect(Collectors.toList());
            observablePatentList = FXCollections.observableArrayList(patentDTOS);
            table.setItems(observablePatentList);
            observablePatentList.forEach(data->{
                data.getDelete().setOnAction(event->{
                    ClientSocket.getInstance().getOut().println(new Gson()
                            .toJson(new Request(new Gson().toJson(data.getDelete().getId()), RequestType.DELETE_PATENT)));
                    ClientSocket.getInstance().getOut().flush();
                    try {
                        Response responseDelete = new Gson().fromJson(ClientSocket.getInstance().getIn().readLine(), Response.class);
                        if(responseDelete.getResponseType()== ResponseType.OK) {
                            message.setStyle("-fx-text-fill: blue;");
                            message.setVisible(true);
                            message.setText(responseDelete.getMessage());
                        }
                        else{
                            message.setVisible(true);
                            message.setStyle("-fx-text-fill: red;");
                            message.setText(responseDelete.getMessage());
                        }

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    refreshTable();
                });

            });
        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }

    public void BackButtonPressed(ActionEvent actionEvent) throws IOException {
        Stage stage= (Stage) BackButton.getScene().getWindow();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/frontend/ManagerPanel.fxml")));
        stage.setTitle("Панель менеджера");
        stage.setScene(new Scene(root));
    }

    public void AddPatentPressed(ActionEvent actionEvent) throws IOException {
        Stage stage= (Stage) BackButton.getScene().getWindow();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/frontend/AddPatent.fxml")));
        stage.setTitle("Добавление патента");
        stage.setScene(new Scene(root));

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        company.setCellValueFactory(new PropertyValueFactory<>("company"));
        cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        dateStart.setCellValueFactory(data->
                new SimpleStringProperty(new SimpleDateFormat("yyyy.MM.dd").format(data.getValue().getPurchase_date())));
        temp.setCellValueFactory(new PropertyValueFactory<>("validity_period"));
        deleting.setCellValueFactory(data->new SimpleObjectProperty<>(data.getValue().getDelete()));
        editing.setCellValueFactory(date->new SimpleObjectProperty<>(date.getValue().getChange()));
        refreshTable();

    }
}
