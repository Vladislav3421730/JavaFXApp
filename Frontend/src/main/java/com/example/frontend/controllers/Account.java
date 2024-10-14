package com.example.frontend.controllers;

import com.example.frontend.TCP.ClientSocket;
import com.example.frontend.TCP.Request;
import com.example.frontend.TCP.Response;
import com.example.frontend.TCP.enums.RequestType;
import com.example.frontend.TCP.enums.ResponseType;
import com.example.frontend.models.entities.PersonData;
import com.example.frontend.models.entities.User;
import com.example.frontend.models.enums.Gender;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Account implements Initializable {

    private final User user= ClientSocket.getInstance().getUser();

    @FXML
    private TextField position;

    @FXML
    private Label message;

    @FXML
    private RadioButton Female;

    @FXML
    private Button BackButton;
    @FXML
    private Button CommitChangesButton;
    @FXML
    private RadioButton Male;
    @FXML
    private TextField salary;
    @FXML
    private TextField age;
    @FXML
    private TextField email;
    @FXML
    private TextField login;
    @FXML
    private TextField surname;
    @FXML
    private TextField name;

    private ToggleGroup genderGroup;

    public void CommitChanges(ActionEvent actionEvent) throws IOException {

        try {
            Double.parseDouble(salary.getText());
            Integer.parseInt(age.getText());
        } catch (Exception e){
            message.setStyle("-fx-text-fill: red;");
            message.setText("Поле возраст должно содержать целое число. Поле зарплата должно содержать десятичную дробь");
            message.setVisible(true);
            return;
        }

        PersonData personData = new PersonData.Builder()
                .setId(user.getPersonData().getId())
                .setName(name.getText())
                .setSurname(surname.getText())
                .setSalary(BigDecimal.valueOf(Double.parseDouble(salary.getText())))
                .setEmail(email.getText())
                .setAge(Integer.parseInt(age.getText()))
                .setPosition(position.getText())
                .setGender(Male.isSelected() ? Gender.MALE : Gender.FEMALE)
                .build();

      user.setLogin(login.getText());
      user.setPersonData(personData);


      ClientSocket.getInstance().getOut().println(new Gson().toJson(new Request(new Gson().toJson(user), RequestType.UPDATE_ACCOUNT)));
      ClientSocket.getInstance().getOut().flush();

      Response response=new Gson().fromJson(ClientSocket.getInstance().getIn().readLine(), Response.class);

      if(response.getResponseType()== ResponseType.OK){
          message.setStyle("-fx-text-fill: blue;");
          message.setText(response.getMessage());
          message.setVisible(true);
          SetInformationAboutUser();
      }
      else{
          message.setStyle("-fx-text-fill: red;");
          message.setText(response.getMessage());
          message.setVisible(true);
      }

    }

    public void BackButtonPressed(ActionEvent actionEvent) throws IOException {
        Stage stage= (Stage) BackButton.getScene().getWindow();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/frontend/DefaultPage.fxml")));
        stage.setTitle("Начальная страница");
        stage.setScene(new Scene(root));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        genderGroup = new ToggleGroup();
        Male.setToggleGroup(genderGroup);
        Female.setToggleGroup(genderGroup);
      SetInformationAboutUser();
    }

    public void SetInformationAboutUser(){
        name.setText(user.getPersonData().getName());
        surname.setText(user.getPersonData().getSurname());
        login.setText(user.getLogin());
        email.setText(user.getPersonData().getEmail());
        age.setText(String.valueOf(user.getPersonData().getAge()));
        salary.setText(String.valueOf(user.getPersonData().getSalary()));
        position.setText(user.getPersonData().getPosition());
        if(user.getPersonData().getGender().equals(Gender.MALE))  Male.setSelected(true);
        else Female.setSelected(true);
    }
}
