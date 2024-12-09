package com.example.frontend.controllers;

import com.example.frontend.TCP.ClientSocket;
import com.example.frontend.TCP.Request;
import com.example.frontend.TCP.Response;
import com.example.frontend.TCP.enums.RequestType;
import com.example.frontend.TCP.enums.ResponseType;
import com.example.frontend.dto.EquipmentDTO;
import com.example.frontend.mappers.MapToEquipmentDTO;
import com.example.frontend.models.entities.Equipment;
import com.example.frontend.models.entities.Patent;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class EquipmentC implements Initializable {

    @FXML
    private Label message;
    @FXML
    private TableView<EquipmentDTO> table;
    @FXML
    private TableColumn<EquipmentDTO,Integer> id;
    @FXML
    private TableColumn<EquipmentDTO,String> name;
    @FXML
    private TableColumn<EquipmentDTO, BigDecimal> cost;
    @FXML
    private TableColumn<EquipmentDTO,String> purchase_date;
    @FXML
    private TableColumn<EquipmentDTO,String> description;
    @FXML
    private TableColumn<EquipmentDTO,Button> editing;
    @FXML
    private TableColumn<EquipmentDTO,Button> deleting;

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

    private void refreshTable() {
        ClientSocket.getInstance().getOut().println(new Gson().toJson(new Request(null, RequestType.GET_ALL_EQUIPMENT)));
        ClientSocket.getInstance().getOut().flush();
        try {
            Response response = new Gson().fromJson(ClientSocket.getInstance().getIn().readLine(), Response.class);
            Equipment[] equipment = new Gson().fromJson(response.getMessage(), Equipment[].class);
            List<EquipmentDTO> equipmentDTOS = Arrays.stream(equipment).map(MapToEquipmentDTO::mapToEquipmentDTO).collect(Collectors.toList());
            ObservableList<EquipmentDTO> observableEquipmentList = FXCollections.observableArrayList(equipmentDTOS);
            table.setItems(observableEquipmentList);
            observableEquipmentList.forEach(data->{
                data.getDelete().setOnAction(event->{
                    ClientSocket.getInstance().getOut().println(new Gson()
                            .toJson(new Request(new Gson().toJson(data.getDelete().getId()), RequestType.DELETE_EQUIPMENT)));
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
                data.getChange().setOnAction(event -> {
                    Stage stage= (Stage) BackButton.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/frontend/AddEquipment.fxml"));
                    try {
                        Parent root = loader.load();
                        AddEquipment controller = loader.getController();
                        Equipment equipment1= Equipment.builder()
                                .id(data.getId())
                                .name(data.getName())
                                .description(data.getDescription())
                                .purchase_date(data.getPurchase_date())
                                .cost(data.getCost())
                                .build();
                        controller.setEquipment(equipment1);
                        stage.setTitle("Редактирование оборудования");
                        stage.setScene(new Scene(root));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                });
            });
        } catch (IOException e) {

            throw new RuntimeException(e);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        purchase_date.setCellValueFactory(data->
                new SimpleStringProperty(new SimpleDateFormat("yyyy.MM.dd").format(data.getValue().getPurchase_date())));
        deleting.setCellValueFactory(data->new SimpleObjectProperty<>(data.getValue().getDelete()));
        editing.setCellValueFactory(date->new SimpleObjectProperty<>(date.getValue().getChange()));
        refreshTable();
    }


}
