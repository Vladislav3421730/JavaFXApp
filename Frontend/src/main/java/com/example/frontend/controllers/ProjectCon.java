package com.example.frontend.controllers;


import com.example.frontend.dto.EquipmentDTO;
import com.example.frontend.dto.ProjectDTO;
import com.example.frontend.models.entities.*;
import com.example.frontend.models.enums.Gender;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class ProjectCon {


    private Project project;

    @FXML
    private Button back;
    @FXML
    private Label title;
    @FXML
    private Label description;
    @FXML
    private Label start;
    @FXML
    private Label end;
    @FXML
    private Label budget;
    @FXML
    private Label status;
    @FXML
    private TableView<Equipment> equipments;
    @FXML
    private TableColumn<Equipment, Integer> eqId;
    @FXML
    private TableColumn<Equipment, String> eqTitle;
    @FXML
    private TableColumn<Equipment, String> eqDescription;
    @FXML
    private TableColumn<Equipment, String> eqStart;

    @FXML
    private TableColumn<Equipment, BigDecimal> eqCoast;
    @FXML
    private Button edit;
    @FXML
    private Button delete;
    @FXML
    private TextField addEquipmentId;
    @FXML
    private TableView<Patent> patents;
    @FXML
    private TableColumn<Patent, Integer> patId;
    @FXML
    private TableColumn<Patent, String> patTitle;
    @FXML
    private TableColumn<Patent, String> patCompany;
    @FXML
    private TableColumn<Patent, BigDecimal> patCoast;
    @FXML
    private TableColumn<Patent, String> patStart;
    @FXML
    private TableColumn<Patent, String> patDeadline;
    @FXML
    private Button addPatentButton;
    @FXML
    private TextField addPatentId;
    @FXML
    private Button addEquipmentButton;
    @FXML
    private TableView<PersonData> workers;
    @FXML
    private TableColumn<PersonData, Integer> wId;
    @FXML
    private TableColumn<PersonData, String> wName;
    @FXML
    private TableColumn<PersonData, String> wSurname;
    @FXML
    private TableColumn<PersonData, String> wEmail;
    @FXML
    private TableColumn<PersonData, String> wPosition;
    @FXML
    private TextField addWorkerId;
    @FXML
    private Button addWorkerButton;
    @FXML
    private TextField deleteEquipmentId;
    @FXML
    private Button deleteEquipmentButton;
    @FXML
    private TextField deletePatentId;
    @FXML
    private Button deletePatentButton;
    @FXML
    private TextField DeleteWorkerId;
    @FXML
    private Button deleteWorkerButton;

    private ObservableList<Patent> observablePatentList;
    private ObservableList<Equipment> observableEquipmentList;
    private ObservableList<PersonData> observablePersonDataList;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
        title.setText(project.getName());
        description.setText(project.getDescription());
        budget.setText(project.getBudget().toString());

        start.setText(new SimpleDateFormat("yyyy.MM.dd").format(project.getStart_date()));
        end.setText(new SimpleDateFormat("yyyy.MM.dd").format(project.getStart_date()));
        status.setText(project.getStatus());

        eqId.setCellValueFactory(new PropertyValueFactory<>("id"));
        eqTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        eqCoast.setCellValueFactory(new PropertyValueFactory<>("cost"));
        eqDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        eqStart.setCellValueFactory(data ->
                new SimpleStringProperty(new SimpleDateFormat("yyyy.MM.dd").format(data.getValue().getPurchase_date())));

        observableEquipmentList = FXCollections.observableArrayList(project.getEquipmentList());
        equipments.setItems(observableEquipmentList);

        patId.setCellValueFactory(new PropertyValueFactory<>("id"));
        patTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        patCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
        patCoast.setCellValueFactory(new PropertyValueFactory<>("cost"));
        patStart.setCellValueFactory(data ->
                new SimpleStringProperty(new SimpleDateFormat("yyyy.MM.dd").format(data.getValue().getPurchase_date())));
        patDeadline.setCellValueFactory(new PropertyValueFactory<>("validity_period"));

        observablePatentList = FXCollections.observableArrayList(project.getPatents());
        patents.setItems(observablePatentList);

        wId.setCellValueFactory(new PropertyValueFactory<>("id"));
        wName.setCellValueFactory(new PropertyValueFactory<>("name"));
        wSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        wEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        wPosition.setCellValueFactory(new PropertyValueFactory<>("position"));

        observablePersonDataList = FXCollections.observableArrayList(project.getEmployees());
        workers.setItems(observablePersonDataList);
    }
}
