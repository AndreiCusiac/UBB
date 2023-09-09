package baschetclient;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.TicketBooth;
import services.IService;

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
    private HomeController homeController;

    Parent mainParent;


    public void setServer(IService s){
        System.out.println("SetServer");
        service=s;
    }

    public void setParent(Parent p){
        mainParent = p;
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    @FXML
    private void initialize() {
    }


    public void setService(IService service1, Stage currentStage) throws InterruptedException {
        this.service = service1;
        this.currentStage = currentStage;
        //this.dialogStage=stage;
        //handleAnimationRotateOn();
    }

    public void setTicketBoothCurrent(TicketBooth ticketBooth) {
        this.ticketBoothCurrent = ticketBooth;
    }

//    @FXML
//    public void handleLogIn() throws Exception {
//        String name = textFieldName.getText();
//        String password = textFieldHidePassword.getText();
//
//        TicketBooth ticketBooth;
//
//        if (service.isAuthValid(name, password, homeController) == true) {
//
//            ticketBooth = service.getTicketBoothByName(name);
//
//            currentStage.close();
//
//            startApp();
//        } /*else if (email.equals("admin")) {
//            ticketBooth = service.getUserByEmail("Andrei.Cusiac@map.com");
//            service.setCurrentUser(ticketBooth);
//
//            startApp();
//        } else if(email.equals("admin1")){
//            ticketBooth=service.getUserByEmail("Aaa.Bbbb@map.com");
//            service.setCurrentUser(ticketBooth);
//
//            startApp();
//        }*/ else {
//            MessageAlert.showErrorMessage(null, "Atentie!\nNumele casei de bilete și/ sau parola nu sunt valide!");
//        }
//    }


    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    @FXML
    public void handleLogIn() throws Exception {
        String name = textFieldName.getText();
        String password = textFieldHidePassword.getText();

        TicketBooth ticketBooth;

        System.out.println("Voi apela isAuthValid");

        try{
            if (service.isAuthValid(name, password, homeController) == true) {

                ticketBooth = service.getTicketBoothByName(name);

                ticketBoothCurrent = new TicketBooth(name, password);
                currentStage.close();

                startApp(new TicketBooth(name, password));
            /*
            Stage stage=new Stage();
            stage.setTitle("Baschet - " + name);
            stage.setScene(new Scene(mainParent));


            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    try {
                        homeController.handleLogOut();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);
                }
            });

            homeController.setCurrentTicketBooth(new TicketBooth(name, password));
            //homeController.initDisplay();
//            homeController.setLoggedFriends();
//            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

            stage.show();*/
            }
            else {
                MessageAlert.showErrorMessage(null, "Atentie!\nNumele casei de bilete și/ sau parola nu sunt valide!");
            }
        } catch (Exception e) {
            MessageAlert.showErrorMessage(null, "Eroare: " + e.getMessage());
        }

    }

    private void startApp(TicketBooth ticketBoothToLogIn) throws Exception {
        /*Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("home-view.fxml"));

        //FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("app-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 900, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String title = "Baschet - " + ticketBoothCurrent.getName();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        currentStage.close();

        HomeController homeController = fxmlLoader.getController();
//        HomeController.setCurrentTicketBooth(ticketBoothCurrent);
        homeController.setService(service, stage);*/

        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("home-view.fxml"));

        //FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("app-view.fxml"));
        Scene scene = null;
        scene = new Scene(mainParent);
        String title = "Baschet - " + ticketBoothCurrent.getName();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
//                    homeController.handleLogOut();
                    service.logout(ticketBoothToLogIn, homeController);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }
        });

        homeController.setCurrentTicketBooth(ticketBoothToLogIn);
        homeController.initDisplay();
        homeController.setCurrentStage(stage);

        stage.show();

        currentStage.close();

//        HomeController homeController = fxmlLoader.getController();
//        HomeController.setCurrentTicketBooth(ticketBoothCurrent);
//        homeController.setService(service, stage);
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