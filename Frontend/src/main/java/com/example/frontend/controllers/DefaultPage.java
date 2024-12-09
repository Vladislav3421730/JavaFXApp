package com.example.frontend.controllers;

import com.example.frontend.TCP.ClientSocket;
import com.example.frontend.TCP.Request;
import com.example.frontend.TCP.Response;
import com.example.frontend.TCP.enums.RequestType;
import com.example.frontend.models.entities.Project;
import com.example.frontend.models.entities.User;
import com.example.frontend.models.enums.Role;
import com.google.gson.Gson;
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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class DefaultPage implements Initializable {

    @FXML
    private TextField search;
    @FXML
    private TableColumn<Project,Integer> id;

    @FXML
    private TableColumn<Project,String> name;

    @FXML
    private TableColumn<Project,String> description;
    @FXML
    private TableColumn<Project,String> start;

    @FXML
    private TableColumn<Project, String> end;

    @FXML
    private TableColumn<Project, BigDecimal> budget;

    @FXML
    private TableColumn<Project,String> status;
    @FXML
    private TableView<Project> Projects;
    @FXML
    private Button ManagerPanelButton;
    @FXML
    private Button AccountButton;
    @FXML
    private Label message;
    @FXML
    private Button AdminPanelButton;

    private ObservableList<Project> observableProjectList;
    private List<Project> projectList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(ClientSocket.getInstance().getUser().getRoles().contains(Role.ADMIN)){
            AdminPanelButton.setVisible(true);
        }
        if(ClientSocket.getInstance().getUser().getRoles().contains(Role.MANAGER)) {
            ManagerPanelButton.setVisible(true);
        }

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        start.setCellValueFactory(data->
                new SimpleStringProperty(new SimpleDateFormat("yyyy.MM.dd").format(data.getValue().getStart_date())));
        end.setCellValueFactory(data->
                new SimpleStringProperty(new SimpleDateFormat("yyyy.MM.dd").format(data.getValue().getEnd_date())));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        budget.setCellValueFactory(new PropertyValueFactory<>("budget"));


        ClientSocket.getInstance().getOut().println(new Gson().toJson(new Request(null, RequestType.GET_ALL_PROJECTS)));
        ClientSocket.getInstance().getOut().flush();
        try {
            Response response = new Gson().fromJson(ClientSocket.getInstance().getIn().readLine(), Response.class);
            Project[] projects = new Gson().fromJson(response.getMessage(), Project[].class);
            projectList = Arrays.stream(projects).collect(Collectors.toList());
            observableProjectList = FXCollections.observableArrayList(projectList);
            Projects.setItems(observableProjectList);


        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }


    public void AdminPanelPressed(ActionEvent actionEvent) throws IOException {
        if(!ClientSocket.getInstance().getUser().getRoles().contains(Role.ADMIN)){
            message.setText("У вас нет роли ADMIN");
            message.setVisible(true);
            return;
        }
        Stage stage= (Stage) AdminPanelButton.getScene().getWindow();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/frontend/AdminPanel.fxml")));
        stage.setTitle("Панель администратора");
        stage.setScene(new Scene(root));
    }

    public void AccountButtonPressed(ActionEvent actionEvent) throws IOException {
        Stage stage= (Stage) AccountButton.getScene().getWindow();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/frontend/account.fxml")));
        stage.setTitle("Личный кабинет");
        stage.setScene(new Scene(root));
    }

    public void BackToPreviousPage(ActionEvent actionEvent) throws IOException {
        Stage stage= (Stage) AccountButton.getScene().getWindow();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/frontend/login.fxml")));
        stage.setTitle("Вход в систему");
        stage.setScene(new Scene(root));
    }

    public void ManagerPanelButton_Pressed(ActionEvent actionEvent) throws IOException {
        if(!ClientSocket.getInstance().getUser().getRoles().contains(Role.MANAGER)){
            message.setText("У вас нет роли MANAGER");
            message.setVisible(true);
            return;
        }
        Stage stage= (Stage) AccountButton.getScene().getWindow();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/frontend/ManagerPanel.fxml")));
        stage.setTitle("Панель менеджера");
        stage.setScene(new Scene(root));
    }

    public void SearchChanged(KeyEvent event) {
        String searchText = search.getText().toLowerCase();

        List<Project> filteredProjects = projectList.stream().filter(project ->
                project.getName().toLowerCase().contains(searchText) ||
                        project.getDescription().toLowerCase().contains(searchText)
        ).collect(Collectors.toList());
        observableProjectList.setAll(filteredProjects);
        Projects.setItems(observableProjectList);

    }
}
