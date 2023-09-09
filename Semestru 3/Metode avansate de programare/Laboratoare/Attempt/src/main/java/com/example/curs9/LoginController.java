package com.example.curs9;

import com.example.curs9.ubbcluj.map.domain.MyModels.User;
import com.example.curs9.ubbcluj.map.domain.MyValidators.FriendshipRepoValidator;
import com.example.curs9.ubbcluj.map.domain.MyValidators.UserRepoValidator;
import com.example.curs9.ubbcluj.map.service.NetworkService;
import com.example.curs9.ubbcluj.map.utils.MessageAlert;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;


public class LoginController {
    @FXML
    private TextField textFieldEmail;

    @FXML
    private TextField textFieldPassword;

    @FXML
    private TextField textFieldDesc;
    @FXML
    private TextField textFieldFrom;
    @FXML
    private TextField textFieldTo;
    @FXML
    private TextArea textAreaMessage;
    @FXML
    private DatePicker datePickerDate;

    private NetworkService service;
    Stage currentStage;
    User userCurent;

    @FXML
    private void initialize() {
    }


    public void setService(NetworkService service1, Stage currentStage) {
        this.service = service1;
        this.currentStage = currentStage;
        //this.dialogStage=stage;
    }

    @FXML
    public void handleLogIn() throws UserRepoValidator, IOException, FriendshipRepoValidator {
        String email=textFieldEmail.getText();

        User user;

        if (service.hasUserByEmail(email) == true) {

            user = service.getUserByEmail(email);
            service.setCurrentUser(user);

            currentStage.close();

            startApp();
        } else if (email.equals("admin")) {
            user = service.getUserByEmail("Andrei.Cusiac@map.com");
            service.setCurrentUser(user);

            currentStage.close();

            startApp();
        }
        else {
            MessageAlert.showErrorMessage(null, "Atentie!\nUser-ul cu email-ul " + email + " nu exista!");
        }
    }

    private void startApp() throws UserRepoValidator, FriendshipRepoValidator {
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("app-view.fxml"));

        //FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("app-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 900, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Spicee");
        stage.setScene(scene);
        stage.show();

        AppController appController = fxmlLoader.getController();
        appController.setService(service);
    }

    @FXML
    public void handleCancel(){
        currentStage.close();
    }
}
