package com.example.frontend.controllers;

import com.example.frontend.TCP.ClientSocket;
import com.example.frontend.TCP.Request;
import com.example.frontend.TCP.Response;
import com.example.frontend.TCP.enums.RequestType;
import com.example.frontend.TCP.enums.ResponseType;
import com.example.frontend.models.entities.PersonData;
import com.example.frontend.models.entities.User;
import com.example.frontend.models.enums.Gender;
import com.example.frontend.models.enums.Role;
import com.example.frontend.utils.PasswordEncoder;
import com.google.gson.Gson;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;

public class Registration implements Initializable {

    @FXML
    private RadioButton female;
    @FXML
    private Label message;
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private RadioButton male;
    @FXML
    private TextField email;
    @FXML
    private TextField login;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;

    @FXML
    private Button BackButton;
    private ToggleGroup genderGroup;
    public void Register(ActionEvent actionEvent) throws IOException {
        if(login.getText().isEmpty() || password.getText().isEmpty()){
            message.setStyle("-fx-text-fill: red;");
            message.setText("Обязательно заполните поля логин и пароль");
            message.setVisible(true);
            return;
        }
        if(login.getText().length()<4 || password.getText().length()<4){
            message.setStyle("-fx-text-fill: red;");
            message.setText("Длина логина и пароля минимум 4 символа");
            message.setVisible(true);
            return;
        }
        if(!password.getText().equals(confirmPassword.getText())){
            message.setStyle("-fx-text-fill: red;");
            message.setText("Пароли не совпадают");
            message.setVisible(true);
            return;
        }

        PersonData personData=new PersonData.Builder()
                .setName(name.getText())
                .setSurname(surname.getText())
                .setEmail(email.getText())
                .setGender( male.isSelected() ? Gender.MALE : Gender.FEMALE)
                .build();
        User user=new User.Builder()
                .setLogin(login.getText())
                .setPassword(PasswordEncoder.hashPassword(password.getText()))
                .setRoles(Set.of(Role.USER))
                .setPersonData(personData)
                .build();

        Request request= new Request(new Gson().toJson(user),RequestType.REGISTER);
        String requestJson=new Gson().toJson(request);
        System.out.println(requestJson);
        ClientSocket.getInstance().getOut().println(requestJson);
        ClientSocket.getInstance().getOut().flush();

        Response response = new Gson().fromJson(ClientSocket.getInstance().getIn().readLine(), Response.class);

        if(response.getResponseType() == ResponseType.OK) {
            message.setStyle("-fx-text-fill: blue;");
            message.setText(response.getMessage());
            message.setVisible(true);

            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> {
                try {
                    BackToPreviousPage(actionEvent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            pause.play();
        } else {
            message.setStyle("-fx-text-fill: red;");
            message.setText(response.getMessage());
            message.setVisible(true);
        }

    }

    public void BackToPreviousPage(ActionEvent actionEvent) throws IOException {
        Stage stage= (Stage) BackButton.getScene().getWindow();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/frontend/login.fxml")));
        stage.setTitle("Вход в систему");
        stage.setScene(new Scene(root));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        genderGroup = new ToggleGroup();
        male.setToggleGroup(genderGroup);
        female.setToggleGroup(genderGroup);
    }
}
