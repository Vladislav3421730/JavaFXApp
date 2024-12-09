package com.example.frontend.controllers;

import com.example.frontend.TCP.ClientSocket;
import com.example.frontend.TCP.Request;
import com.example.frontend.TCP.Response;
import com.example.frontend.TCP.enums.RequestType;
import com.example.frontend.dto.ProjectDTO;
import com.example.frontend.mappers.MapToProjectDTO;
import com.example.frontend.models.entities.Equipment;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
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

public class ManagerPanel implements Initializable {

    @FXML
    private TextField search;
    @FXML
    private TableView<ProjectDTO> projectsTable;
    @FXML
    private TableColumn<ProjectDTO,Integer> id;
    @FXML
    private TableColumn<ProjectDTO,String> name;
    @FXML
    private TableColumn<ProjectDTO,String> description;
    @FXML
    private TableColumn<ProjectDTO,String> start;
    @FXML
    private TableColumn<ProjectDTO,String> end;
    @FXML
    private TableColumn<ProjectDTO, BigDecimal> budget;
    @FXML
    private TableColumn<ProjectDTO,String> status;
    @FXML
    private TableColumn<ProjectDTO,Button> info;
    @FXML
    private Button BackButton;

    @FXML
    private Button AddProjectButton;

    private ObservableList<ProjectDTO> observableProjectListDTO;
    private List<ProjectDTO> projectListDTO;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        start.setCellValueFactory(data->
                new SimpleStringProperty(new SimpleDateFormat("yyyy.MM.dd").format(data.getValue().getStart_date())));
        end.setCellValueFactory(data->
                new SimpleStringProperty(new SimpleDateFormat("yyyy.MM.dd").format(data.getValue().getEnd_date())));
        budget.setCellValueFactory(new PropertyValueFactory<>("budget"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        info.setCellValueFactory(date->new SimpleObjectProperty<>(date.getValue().getInfo()));

        ClientSocket.getInstance().getOut().println(new Gson().toJson(new Request(null, RequestType.GET_ALL_PROJECTS)));
        ClientSocket.getInstance().getOut().flush();
        try {
            Response response = new Gson().fromJson(ClientSocket.getInstance().getIn().readLine(), Response.class);
            Project[] projects = new Gson().fromJson(response.getMessage(), Project[].class);
            projectListDTO = Arrays.stream(projects).map(MapToProjectDTO::mapToProjectDTO).collect(Collectors.toList());
            observableProjectListDTO = FXCollections.observableArrayList(projectListDTO);
            projectsTable.setItems(observableProjectListDTO);
            observableProjectListDTO.forEach(data->{
                data.getInfo().setOnAction(event->{
                    Stage stage= (Stage) BackButton.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/frontend/Project.fxml"));
                    try {

                        Parent root = loader.load();
                        ProjectCon controller = loader.getController();
                        ClientSocket.getInstance().getOut().println(new Gson()
                                .toJson(new Request(new Gson().toJson(data.getId()), RequestType.GET_PROJECT_BY_ID)));
                        ClientSocket.getInstance().getOut().flush();
                        Response responseProject = new Gson().fromJson(ClientSocket.getInstance().getIn().readLine(), Response.class);
                        Project project = new Gson().fromJson(responseProject.getMessage(), Project.class);

                        controller.setProject(project);
                        stage.setTitle("Исследование");
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

    public void SearchChanged(KeyEvent keyEvent) {
        String searchText = search.getText().toLowerCase();
        List<ProjectDTO> filteredProjects = projectListDTO.stream().filter(projectDTO ->
                projectDTO.getName().toLowerCase().contains(searchText) ||
                        projectDTO.getDescription().toLowerCase().contains(searchText)
        ).collect(Collectors.toList());
        observableProjectListDTO.setAll(filteredProjects);
        projectsTable.setItems(observableProjectListDTO);
    }
}
