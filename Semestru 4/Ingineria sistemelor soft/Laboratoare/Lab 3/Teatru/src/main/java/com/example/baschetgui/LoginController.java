package com.example.baschetgui;

import com.example.baschetgui.cs.MessageAlert;
import com.example.baschetgui.cs.models.Regizor;
import com.example.baschetgui.cs.models.Spectator;
import com.example.baschetgui.cs.services.AppService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


public class LoginController {
    @FXML
    private TextField textFieldName;

    @FXML
    private TextField textFieldNameRegizor;

    @FXML
    private PasswordField textFieldHidePassword;

    @FXML
    private PasswordField textFieldHidePasswordRegizor;

    private AppService service;
    Stage currentStage;
//    TicketBooth ticketBoothCurrent;

    @FXML
    private void initialize() {
    }


    public void setService(AppService service1, Stage currentStage) throws InterruptedException {
        this.service = service1;
        this.currentStage = currentStage;
        //this.dialogStage=stage;
        //handleAnimationRotateOn();
    }

    @FXML
    public void handleLogInSpectator() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

        if (textFieldName == null || textFieldHidePassword == null) {
            MessageAlert.showErrorMessage(null, "Numele și parola nu pot fi nule!");
            return;
        }

        String name = textFieldName.getText();
        String password = textFieldHidePassword.getText();

        Spectator spectator;

        if (service.isAuthSpectatorValid(name, password) == true) {

            spectator = service.getSpectatorByName(name);
            service.setCurrentSpectator(spectator);

            //currentStage.close();

            startAppSpectator();
            currentStage.close();

        } /*else if (email.equals("admin")) {
            ticketBooth = service.getUserByEmail("Andrei.Cusiac@map.com");
            service.setCurrentUser(ticketBooth);

            startApp();
        } else if(email.equals("admin1")){
            ticketBooth=service.getUserByEmail("Aaa.Bbbb@map.com");
            service.setCurrentUser(ticketBooth);

            startApp();
        }*/ else {
            MessageAlert.showErrorMessage(null, "Atentie!\nNumele spectatorului și/ sau parola nu sunt valide!");
        }
    }

    @FXML
    public void handleLogInRegizor() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

        if (textFieldNameRegizor == null || textFieldHidePasswordRegizor == null) {
            MessageAlert.showErrorMessage(null, "Numele și parola nu pot fi nule!");
            return;
        }

        String name = textFieldNameRegizor.getText();
        String password = textFieldHidePasswordRegizor.getText();

        Regizor regizor;

        if (service.isAuthRegizorValid(name, password) == true) {

            regizor = service.getRegizorByName(name);
            service.setCurrentRegizor(regizor);

            //currentStage.close();

            startAppRegizor();
            currentStage.close();

        } /*else if (email.equals("admin")) {
            ticketBooth = service.getUserByEmail("Andrei.Cusiac@map.com");
            service.setCurrentUser(ticketBooth);

            startApp();
        } else if(email.equals("admin1")){
            ticketBooth=service.getUserByEmail("Aaa.Bbbb@map.com");
            service.setCurrentUser(ticketBooth);

            startApp();
        }*/ else {
            MessageAlert.showErrorMessage(null, "Atentie!\nNumele regizorului și/ sau parola nu sunt valide!");
        }

    }

    private void startAppSpectator() {
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("SalaWindow.fxml"));

        //FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("app-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String title = "Spectator - " + service.getCurrentSpectator().getName();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        //currentStage.close();

        SpectatorController spectatorController = fxmlLoader.getController();
        spectatorController.setService(service, stage);
    }

    private void startAppRegizor() {
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("GestiuneWindow.fxml"));

        //FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("app-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String title = "Regizor - " + service.getCurrentRegizor().getName();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        //currentStage.close();

        RegizorController regizorController = fxmlLoader.getController();
        regizorController.setService(service, stage);
    }
}