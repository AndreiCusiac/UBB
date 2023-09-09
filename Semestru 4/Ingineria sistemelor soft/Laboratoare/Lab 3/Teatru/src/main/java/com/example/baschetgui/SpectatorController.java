package com.example.baschetgui;

import com.example.baschetgui.cs.MessageAlert;
import com.example.baschetgui.cs.Observer;
import com.example.baschetgui.cs.models.Loc;
import com.example.baschetgui.cs.models.Spectacol;
import com.example.baschetgui.cs.models.Spectator;
import com.example.baschetgui.cs.services.AppService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class SpectatorController implements Observer {
    AppService appService;
//    ObservableList<Spectacol> modelSpectacolTable = FXCollections.observableArrayList();
    /*ObservableList<User> modelFriendsTable = FXCollections.observableArrayList();
    ObservableList<Friendship> modelFrom = FXCollections.observableArrayList();
    ObservableList<Friendship> modelTo = FXCollections.observableArrayList();*/

    ArrayList<Button> seats;

    Stage currentStage;

    Spectator currentSpectator;

    String currentNameReservation;
    String currentEmailReservation;

    @FXML
    Text fullUser;

    @FXML
    Button seat11;
    @FXML
    Button seat12;
    @FXML
    Button seat13;
    @FXML
    Button seat14;
    @FXML
    Button seat15;
    @FXML
    Button seat16;
    @FXML
    Button seat17;
    @FXML
    Button seat18;
    @FXML
    Button seat21;
    @FXML
    Button seat22;
    @FXML
    Button seat23;
    @FXML
    Button seat24;
    @FXML
    Button seat25;
    @FXML
    Button seat26;
    @FXML
    Button seat27;
    @FXML
    Button seat28;
    @FXML
    Button seat29;
    @FXML
    Button seat210;
    @FXML
    Button seat211;
    @FXML
    Button seat212;
    @FXML
    Button seat213;
    @FXML
    Button seat214;
    @FXML
    Button seat215;
    @FXML
    Button seat216;
    @FXML
    Button seat31;
    @FXML
    Button seat32;
    @FXML
    Button seat33;
    @FXML
    Button seat34;
    @FXML
    Button seat35;
    @FXML
    Button seat36;
    @FXML
    Button seat37;
    @FXML
    Button seat38;
    @FXML
    Button seat39;
    @FXML
    Button seat310;
    @FXML
    Button seat311;
    @FXML
    Button seat312;
    @FXML
    Button seat313;
    @FXML
    Button seat314;
    @FXML
    Button seat315;
    @FXML
    Button seat316;
    @FXML
    Button seat41;
    @FXML
    Button seat42;
    @FXML
    Button seat43;
    @FXML
    Button seat44;
    @FXML
    Button seat45;
    @FXML
    Button seat46;
    @FXML
    Button seat47;
    @FXML
    Button seat48;
    @FXML
    Button seat49;
    @FXML
    Button seat410;
    @FXML
    Button seat411;
    @FXML
    Button seat412;
    @FXML
    Button seat413;
    @FXML
    Button seat414;

    @FXML
    private TextField spectatorNameField;

    @FXML
    private TextField spectatorEmailField;

    private boolean canReserve;

    public void setService(AppService networkService1, Stage currentStage1) {
        appService = networkService1;
        //appService.addObserver(this);
        currentSpectator = appService.getCurrentSpectator();
        currentStage = currentStage1;
        appService.addObserver(this);

        seats = new ArrayList<>();
        canReserve = false;

        initSeats();
    }

    private void initSeats() {
        System.out.println("M-am apelat");
        var reservedSeats = appService.getAllLocsReserved();

        for (var seat : reservedSeats) {
            String selector = "#" + seat.getSeat();
            Button btn1 = (Button) currentStage.getScene().lookup(selector);
            btn1.setStyle("-fx-background-color: grey; ");
            btn1.setCursor(Cursor.DEFAULT);
            btn1.setOnAction(handleAlreadyReserved());
            //btn1.setOnAction(null);
        }
    }

    @FXML
    public void initialize() {
    }

    @FXML
    public void handleReservationColor(ActionEvent ev) {
        Button btn = (Button) ev.getSource();

        if (!seats.contains(btn)){
            seats.add(btn);
            btn.setStyle("-fx-background-color: blue; ");
        }
        else {
            seats.remove(btn);
            btn.setStyle("-fx-background-color: orange; ");
        }

        /*if (btn.getId().equals("seat11")) {
            Button btn1 = (Button) currentStage.getScene().lookup("#seat12");
            btn1.setStyle("-fx-background-color: grey; ");
        }*/
    }

    @FXML
    public EventHandler<ActionEvent> handleAlreadyReserved() {
        //MessageAlert.showErrorMessage(null, "Locul selectat este deja rezervat");
        return null;
    }

    @FXML
    public void handleMakeReservation(ActionEvent ev) {

        if (spectatorNameField == null || spectatorEmailField == null) {
            MessageAlert.showErrorMessage(null, "Numele și email-ul nu pot fi nule!");
            return;
        }

        String name = spectatorNameField.getText();
        String email = spectatorEmailField.getText();

        if (name.equals("") || email.equals("")) {
            MessageAlert.showErrorMessage(null, "Numele și email-ul nu pot fi nule!");
            return;
        }

        if (seats.isEmpty()) {
            MessageAlert.showErrorMessage(null, "Nu există locuri selectate!");
            return;
        }

        currentNameReservation = name;
        currentEmailReservation = email;

        canReserve = true;

        StringBuilder locuri = new StringBuilder();

        for (var x : seats) {
            locuri.append(x.getId());
            locuri.append(" ");
        }

        MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Confirmare date", "Date introduse cu succes:\nNume: " + currentNameReservation + "\nEmail: " + currentEmailReservation + "\nLocuri: " + locuri);
    }

    @FXML
    public void handleAbortReservation(ActionEvent ev) {

        for (var x : seats) {
            x.setStyle("-fx-background-color: orange; ");
        }

        seats.clear();

        currentNameReservation = "";
        currentEmailReservation = "";

        spectatorNameField.setText("");
        spectatorEmailField.setText("");

        canReserve = false;

        MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Anulare date", "Ați anulat comanda!");
    }

    @FXML
    public void handleVotesAndSuggestions(ActionEvent ev) {

        System.out.println("Ok0");

        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("VotesWindow.fxml"));

        System.out.println("Ok1");

        //FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("app-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Ok2");


        String title = "Spectator - " + appService.getCurrentSpectator().getName();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        System.out.println("Ok3");

        currentStage.close();

        VotesController votesController = fxmlLoader.getController();
        votesController.setService(appService, stage);

        System.out.println("Ok4");
    }

    @FXML
    public void handleReservation(ActionEvent ev) {

        if (canReserve) {
            ArrayList<String> seatsToModify = new ArrayList<>();

            for (var x : seats) {
                seatsToModify.add(x.getId());
            }

            seats.clear();

            try {
                appService.reserveSeats(currentNameReservation, currentEmailReservation, seatsToModify);
                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Confirmare rezervare", "Rezervare efectuată!\nPuteți achita suma la casieria teatrului nu mai târziu de 30 de minute înainte de începerea spectacolului!");
            } catch (Exception e) {
                MessageAlert.showErrorMessage(null, e.getMessage());
            }
        }
        else {
            MessageAlert.showErrorMessage(null, "Nu ați confirmat datele!");
        }
    }

    @FXML
    public void handlePayCash(ActionEvent ev) {

        if (canReserve) {
            ArrayList<String> seatsToModify = new ArrayList<>();

            for (var x : seats) {
                seatsToModify.add(x.getId());
            }
            seats.clear();
            try {
                appService.reserveSeats(currentNameReservation, currentEmailReservation, seatsToModify);
                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Confirmare plată cash", "Rezervare efectuată!\nPuteți achita suma la casieria teatrului nu mai târziu de 30 de minute înainte de începerea spectacolului!");
            } catch (Exception e) {
                MessageAlert.showErrorMessage(null, e.getMessage());
            }
        }
        else {
            MessageAlert.showErrorMessage(null, "Nu ați confirmat datele!");
        }
    }

    @FXML
    public void handlePayCard(ActionEvent ev) {

        if (canReserve) {
            System.out.println("Ok0");

            Stage stage = new Stage();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("PlataCard.fxml"));

            System.out.println("Ok1");

            //FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("app-view.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Ok2");


            String title = "Spectator - " + appService.getCurrentSpectator().getName();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            System.out.println("Ok3");

            currentStage.close();

            PlataController plataController = fxmlLoader.getController();
            plataController.setService(appService, stage, seats, currentNameReservation, currentEmailReservation);

            System.out.println("Ok4");

        } else {
            MessageAlert.showErrorMessage(null, "Nu ați confirmat datele!");
        }
    }

    @FXML
    public void handlePayBitcoin(ActionEvent ev) {
        if (canReserve) {
            System.out.println("Ok0");

            Stage stage = new Stage();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("PlataBitcoin.fxml"));

            System.out.println("Ok1");

            //FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("app-view.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Ok2");


            String title = "Spectator - " + appService.getCurrentSpectator().getName();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            System.out.println("Ok3");

            currentStage.close();

            PlataController plataController = fxmlLoader.getController();
            plataController.setService(appService, stage, seats, currentNameReservation, currentEmailReservation);

            System.out.println("Ok4");

        } else {
            MessageAlert.showErrorMessage(null, "Nu ați confirmat datele!");
        }
    }

    @FXML
    public void handlePayPaypal(ActionEvent ev) {
        if (canReserve) {
            System.out.println("Ok0");

            Stage stage = new Stage();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("PlataPaypal.fxml"));

            System.out.println("Ok1");

            //FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("app-view.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Ok2");


            String title = "Spectator - " + appService.getCurrentSpectator().getName();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            System.out.println("Ok3");

            currentStage.close();

            PlataController plataController = fxmlLoader.getController();
            plataController.setService(appService, stage, seats, currentNameReservation, currentEmailReservation);

            System.out.println("Ok4");

        } else {
            MessageAlert.showErrorMessage(null, "Nu ați confirmat datele!");
        }
    }

    @FXML
    public void goBackToLogin() throws InterruptedException, IOException {
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("LoginWindow.fxml"));

        //FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("app-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String title = "Login";
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        currentStage.close();

        LoginController loginController = fxmlLoader.getController();
        loginController.setService(appService, stage);
    }

    @Override
    public void update() {
        initSeats();
    }
}