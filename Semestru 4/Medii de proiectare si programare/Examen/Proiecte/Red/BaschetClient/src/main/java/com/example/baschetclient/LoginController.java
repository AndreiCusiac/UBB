package com.example.baschetclient;

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
import models.TicketBooth;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


public class LoginController {
    @FXML
    private TextField textFieldName;

    @FXML
    private TextField textFieldSeePassword;

    @FXML
    private PasswordField textFieldHidePassword;

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

    @FXML
    private ImageView eyePass;



    private IService service;
    Stage currentStage;
    TicketBooth ticketBoothCurrent;

    @FXML
    private void initialize() {
    }


    public void setService(IService service1, Stage currentStage) throws InterruptedException {
        this.service = service1;
        this.currentStage = currentStage;
        //this.dialogStage=stage;
        //handleAnimationRotateOn();
    }

    @FXML
    public void handleLogIn() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String name = textFieldName.getText();
        String password = textFieldHidePassword.getText();

        TicketBooth ticketBooth;

        if (service.isAuthValid(name, password) == true) {

            ticketBooth = service.getTicketBoothByName(name);
            service.setCurrentTicketBooth(ticketBooth);

            currentStage.close();

            startApp();
        } /*else if (email.equals("admin")) {
            ticketBooth = service.getUserByEmail("Andrei.Cusiac@map.com");
            service.setCurrentUser(ticketBooth);

            startApp();
        } else if(email.equals("admin1")){
            ticketBooth=service.getUserByEmail("Aaa.Bbbb@map.com");
            service.setCurrentUser(ticketBooth);

            startApp();
        }*/ else {
            MessageAlert.showErrorMessage(null, "Atentie!\nNumele casei de bilete și/ sau parola nu sunt valide!");
        }
    }

    private void startApp() {
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("home-view.fxml"));

        //FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("app-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 900, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String title = "Baschet - " + service.getCurrentTicketBooth().getName();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        currentStage.close();

        HomeController homeController = fxmlLoader.getController();
        homeController.setService(service, stage);
    }

    @FXML
    public void handleCancel() {
        currentStage.close();
    }

//    public void handleSeePass() {
//        eyePass.setImage(seePass);
//        //currentStage.getScene().setCursor(Cursor.CROSSHAIR);
//
//        textFieldSeePassword.setText(textFieldHidePassword.getText());
//        textFieldHidePassword.setVisible(false);
//        textFieldSeePassword.setVisible(true);
//    }
//
//    public void handleHidePass() {
//        eyePass.setImage(hidePass);
//        //currentStage.getScene().setCursor(Cursor.DEFAULT);
//
//        textFieldHidePassword.setText(textFieldSeePassword.getText());
//        textFieldSeePassword.setVisible(false);
//        textFieldHidePassword.setVisible(true);
//    }


}