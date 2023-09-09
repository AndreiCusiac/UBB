package com.example.baschetgui;

import com.example.baschetgui.cs.MessageAlert;
import com.example.baschetgui.cs.models.Regizor;
import com.example.baschetgui.cs.models.Spectacol;
import com.example.baschetgui.cs.services.AppService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.format.datetime.joda.LocalDateParser;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;

public class ManageController {
    AppService appService;
    ObservableList<Spectacol> modelSpectacolTable = FXCollections.observableArrayList();
    ObservableList<Spectacol> modelSpectacolTableApproved = FXCollections.observableArrayList();
    /*ObservableList<User> modelFriendsTable = FXCollections.observableArrayList();
    ObservableList<Friendship> modelFrom = FXCollections.observableArrayList();
    ObservableList<Friendship> modelTo = FXCollections.observableArrayList();*/

    Stage currentStage;

    Regizor currentRegizor;
    Spectacol selectedSpectacol;

    @FXML
    private TextField newNameSpectacol;

    @FXML
    private TextField newAuthorSpectacol;

    @FXML
    private DatePicker newDateSpectacol;

    @FXML
    private TextField newActorsSpectacol;


    public void setService(AppService networkService1, Stage currentStage1, Spectacol selectedSpectcol) {
        appService = networkService1;
        //appService.addObserver(this);
        currentRegizor = appService.getCurrentRegizor();
        currentStage = currentStage1;
        this.selectedSpectacol = selectedSpectcol;

        //labelLookWho.setVisible(false);
        //labelUhOh.setVisible(false);

        initialize();

        //initModel1();

        //greetings();
    }

    @FXML
    public void initialize() {
        if (selectedSpectacol != null) {
            newNameSpectacol.setText(selectedSpectacol.getName());
            newAuthorSpectacol.setText(selectedSpectacol.getAuthor());
            newDateSpectacol.setValue(selectedSpectacol.getDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate());
            newActorsSpectacol.setText(selectedSpectacol.getActors());
        }
    }

    @FXML
    public void handleConfirmSpectacol(ActionEvent ev) {
        // TODO

        if (newNameSpectacol == null || newAuthorSpectacol == null || newDateSpectacol == null || newActorsSpectacol == null) {
            MessageAlert.showErrorMessage(null, "Toate câmpurile trebuie completate!");
            return;
        }

        String name = newNameSpectacol.getText();
        String author = newAuthorSpectacol.getText();
        LocalDate date = newDateSpectacol.getValue();
        String actors = newActorsSpectacol.getText();

        if (date.isBefore(LocalDate.now())) {
            MessageAlert.showErrorMessage(null, "Data nu poate fi înainte de ziua curentă!");
            return;
        }

        try {
            if (selectedSpectacol == null) {

                Spectacol spectacol = new Spectacol(name, author, java.sql.Date.valueOf(date), actors, true);
                appService.saveSuggestedSpectacol(spectacol);

                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Status spectacol", "Spectacolul " + name + " de " + author + " a fost adăugat pe lista de spectacole înregistrate!");
            } else {
                Spectacol spectacol = new Spectacol(selectedSpectacol);
                spectacol.setId(selectedSpectacol.getId());
                spectacol.setName(name);
                spectacol.setAuthor(author);
                spectacol.setDate(java.sql.Date.valueOf(date));
                spectacol.setActors(actors);
                spectacol.setAccepted(true);

                appService.updateSpectacol(spectacol);

                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Status spectacol", "Spectacolul " + name + " de " + author + " a fost modificat pe lista de spectacole înregistrate!");
            }
        } catch (Exception e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
    }

    @FXML
    public void handleGoBackToGestiune() throws InterruptedException, IOException {
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
        String title = "Regizor - " + appService.getCurrentRegizor().getName();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        currentStage.close();

        RegizorController regizorController = fxmlLoader.getController();
        regizorController.setService(appService, stage);
    }
}