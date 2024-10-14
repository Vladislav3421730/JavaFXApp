package com.example.frontend.controllers;

import com.example.frontend.TCP.ClientSocket;
import com.example.frontend.TCP.Request;
import com.example.frontend.TCP.Response;
import com.example.frontend.TCP.enums.RequestType;
import com.example.frontend.TCP.enums.ResponseType;
import com.example.frontend.dto.UserDTO;
import com.example.frontend.mappers.MapToUserDTO;
import com.example.frontend.models.entities.Project;
import com.example.frontend.models.entities.User;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AdminPanel implements Initializable {

    @FXML
    private TextField search;
    @FXML
    private Label message;
    @FXML
    private TableColumn<UserDTO,Button> delete;
    @FXML
    private TableColumn<UserDTO,Button> role;
    @FXML
    private TableColumn<UserDTO,Button> bun;
    @FXML
    private TableColumn<UserDTO,BigDecimal> salary;
    @FXML
    private TableColumn<UserDTO,String> surname;
    @FXML
    private TableColumn<UserDTO,String> name;
    @FXML
    private TableColumn<UserDTO,String> roles;
    @FXML
    private TableColumn<UserDTO,String> login;
    @FXML
    private TableColumn<UserDTO,Integer> id;
    @FXML
    private TableView<UserDTO> table;
    @FXML
    private Button BackToPreviousPage;

    private ObservableList<UserDTO> observableUserDTOList;
    private List<UserDTO> userDTOList;

    public void BackButtonPressed(ActionEvent actionEvent) throws IOException {
        Stage stage= (Stage) BackToPreviousPage.getScene().getWindow();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/frontend/DefaultPage.fxml")));
        stage.setTitle("Начальная страница");
        stage.setScene(new Scene(root));
    }

    private void refreshTableData() {
        ClientSocket.getInstance().getOut().println(new Gson().toJson(new Request(null, RequestType.GET_ALL_USERS)));
        ClientSocket.getInstance().getOut().flush();
        try {
            Response response = new Gson().fromJson(ClientSocket.getInstance().getIn().readLine(), Response.class);
            User[] users = new Gson().fromJson(response.getMessage(), User[].class);
            userDTOList = Arrays.stream(users)
                    .map(MapToUserDTO::mapToUserDto)
                    .collect(Collectors.toList());
            observableUserDTOList = FXCollections.observableArrayList(userDTOList);
            table.setItems(observableUserDTOList);

            observableUserDTOList.forEach(data -> {
                data.getIsBun().setOnAction(event -> {
                    ClientSocket.getInstance().getOut().println(new Gson().toJson(
                            new Request(new Gson().toJson(data.getIsBun().getId()), RequestType.BUN_USER)));
                    ClientSocket.getInstance().getOut().flush();
                    try {
                        Response responseBun = new Gson().fromJson(ClientSocket.getInstance().getIn().readLine(), Response.class);
                        if(responseBun.getResponseType()== ResponseType.OK) {
                            if (responseBun.getMessage().equals("Пользователь был разбанен")) {
                                data.getIsBun().setText("Разбанить");
                            } else {
                                data.getIsBun().setText("Забанить");
                            }
                            message.setText(responseBun.getMessage());
                        }
                        else{
                            message.setStyle("-fx-text-fill: red;");
                            message.setText(responseBun.getMessage());
                        }

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    refreshTableData();
                });
                data.getAddRole().setOnAction(event -> {
                    ClientSocket.getInstance().getOut().println(new Gson().toJson(
                            new Request(new Gson().toJson(data.getAddRole().getId()), RequestType.ADD_ROLE_MANAGER)));
                    ClientSocket.getInstance().getOut().flush();
                    try {
                        Response responseAddRole = new Gson().fromJson(ClientSocket.getInstance().getIn().readLine(), Response.class);

                        if(responseAddRole.getResponseType()== ResponseType.OK) {
                            if (responseAddRole.getMessage().equals("Добавлена новая роль")) {
                                data.getAddRole().setText("Лишить");
                            } else {
                                data.getAddRole().setText("Сделать");
                            }
                            message.setText(responseAddRole.getMessage());
                        }
                        else{
                            message.setStyle("-fx-text-fill: red;");
                            message.setText(responseAddRole.getMessage());
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    refreshTableData();
                });
                data.getDelete().setOnAction(event -> {
                    ClientSocket.getInstance().getOut().println(new Gson().toJson(
                            new Request(new Gson().toJson(data.getDelete().getId()), RequestType.DELETE_USER)));
                    ClientSocket.getInstance().getOut().flush();
                    try {
                        Response responseDelete = new Gson().fromJson(ClientSocket.getInstance().getIn().readLine(), Response.class);
                        if(responseDelete.getResponseType()==ResponseType.OK){
                            message.setText(responseDelete.getMessage());
                        }
                        else{
                            message.setStyle("-fx-text-fill: red;");
                            message.setText(responseDelete.getMessage());

                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    refreshTableData();
                });
            });


        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        login.setCellValueFactory(new PropertyValueFactory<>("login"));
        name.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        surname.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSurname()));
        salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        roles.setCellValueFactory(data -> new SimpleStringProperty(String.join(", ", data.getValue().getRoles())));
        bun.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getIsBun()));
        role.setCellValueFactory(data->new SimpleObjectProperty<>(data.getValue().getAddRole()));
        delete.setCellValueFactory(data->new SimpleObjectProperty<>(data.getValue().getDelete()));

        refreshTableData();

    }

    public void SearchChanged(KeyEvent keyEvent) {
        String searchText = search.getText().toLowerCase();

        List<UserDTO> filteredUserDto = userDTOList.stream().filter(userDTO -> {
                  return   userDTO.getName().toLowerCase().contains(searchText) ||
                            userDTO.getLogin().toLowerCase().contains(searchText) ||
                          userDTO.getSurname().toLowerCase().contains(searchText);
                }
        ).collect(Collectors.toList());
        observableUserDTOList.setAll(filteredUserDto);
        table.setItems(observableUserDTOList);

    }
}
