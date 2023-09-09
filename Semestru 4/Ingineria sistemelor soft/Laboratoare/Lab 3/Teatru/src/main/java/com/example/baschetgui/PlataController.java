package com.example.baschetgui;

import com.example.baschetgui.cs.MessageAlert;
import com.example.baschetgui.cs.models.Regizor;
import com.example.baschetgui.cs.models.Spectacol;
import com.example.baschetgui.cs.services.AppService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class PlataController {
    AppService appService;

    ArrayList<Button> currentSeats;
   /*ObservableList<User> modelFriendsTable = FXCollections.observableArrayList();
    ObservableList<Friendship> modelFrom = FXCollections.observableArrayList();
    ObservableList<Friendship> modelTo = FXCollections.observableArrayList();*/

    Stage currentStage;
    String currentNameReservation;
    String currentEmailReservation;

    @FXML
    private TextField cardNumber;

    @FXML
    private TextField cardNameHolder;

    @FXML
    private DatePicker cardDateExpiration;

    @FXML
    private TextField cardSecurityNumber;


    public void setService(AppService networkService1, Stage currentStage1, ArrayList<Button> seats, String currentNameReservation, String currentEmailReservation) {
        appService = networkService1;
        currentStage = currentStage1;
        this.currentSeats = seats;
        this.currentNameReservation = currentNameReservation;
        this.currentEmailReservation = currentEmailReservation;

        initialize();

        //initModel1();

        //greetings();
    }

    @FXML
    public void initialize() {
    }

    @FXML
    public void handleConfirmPayment(ActionEvent ev) throws IOException, InterruptedException {
        // TODO

        if (cardNumber == null || cardNameHolder == null || cardDateExpiration == null || cardSecurityNumber == null) {
            MessageAlert.showErrorMessage(null, "Toate câmpurile trebuie completate!");
            return;
        }

        String numberText = cardNumber.getText();
        String nameHolderText = cardNameHolder.getText();
        LocalDate expirationValue = cardDateExpiration.getValue();
        String securityNumberText = cardSecurityNumber.getText();

        if (numberText == "" || nameHolderText == "" || expirationValue == null || securityNumberText == "") {
            MessageAlert.showErrorMessage(null, "Toate câmpurile trebuie completate!");
            return;
        }

        if (expirationValue.isBefore(LocalDate.now())) {
            MessageAlert.showErrorMessage(null, "Data nu poate fi înainte de ziua curentă!");
            return;
        }

        try {
            ArrayList<String> seatsToModify = new ArrayList<>();

            for (var x : currentSeats) {
                seatsToModify.add(x.getId());
            }

            appService.paySeats(currentNameReservation, currentEmailReservation, seatsToModify);
            MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Confirmare plată", "Plată efectuată cu succes!");
        } catch (Exception e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
            MessageAlert.showErrorMessage(null, "Comanda a fost anulată!");
        }

        goBackToSala();
    }

    @FXML
    public void goBackToSala() throws InterruptedException, IOException {
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
        String title = "Spectator - " + appService.getCurrentSpectator().getName();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        currentStage.close();

        SpectatorController spectatorController = fxmlLoader.getController();
        spectatorController.setService(appService, stage);

    }
}