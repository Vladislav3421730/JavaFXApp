package com.example.frontend.controllers;

import com.example.frontend.HelloApplication;
import com.example.frontend.TCP.ClientSocket;
import com.example.frontend.TCP.Request;
import com.example.frontend.TCP.Response;
import com.example.frontend.TCP.enums.RequestType;
import com.example.frontend.TCP.enums.ResponseType;
import com.example.frontend.models.entities.User;
import com.example.frontend.utils.PasswordEncoder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;


public class Login {


    @FXML
    private Label message;
    @FXML
    private PasswordField password;

    @FXML
    private TextField login;

    @FXML
    private Button ButtonLogin;

    @FXML
    private Button ButtonRegister;

    public void Register(ActionEvent actionEvent) throws IOException {
        Stage stage= (Stage) ButtonRegister.getScene().getWindow();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/frontend/registration.fxml")));
        stage.setTitle("Регистрация");
        stage.setScene(new Scene(root));
    }

    public void Login(ActionEvent actionEvent) throws IOException {
        if(login.getText().isEmpty() || password.getText().isEmpty()){
            message.setStyle("-fx-text-fill: red;");
            message.setText("Обязательно заполните поля логин и пароль");
            message.setVisible(true);
            return;
        }

        User user=new User.Builder()
                .setLogin(login.getText())
                .setPassword(password.getText())
                .build();

        ClientSocket.getInstance().getOut().println(new Gson().toJson(new Request(new Gson().toJson(user), RequestType.LOGIN)));
        ClientSocket.getInstance().getOut().flush();

        Response response= new Gson().fromJson(ClientSocket.getInstance().getIn().readLine(), Response.class);

        if(response.getResponseType()== ResponseType.OK){
            message.setStyle("-fx-text-fill: blue;");
            message.setText("Вход выполнен успешно");
            message.setVisible(true);
            ClientSocket.getInstance().setUser(new Gson().fromJson(response.getMessage(), User.class));
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> {
                try {
                    Stage stage= (Stage) ButtonLogin.getScene().getWindow();
                    Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/frontend/DefaultPage.fxml")));
                    stage.setTitle("Начальная страница");
                    stage.setScene(new Scene(root));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            pause.play();
        }
        else{
            message.setStyle("-fx-text-fill: red;");
            message.setText(response.getMessage());
            message.setVisible(true);
        }

    }
}
