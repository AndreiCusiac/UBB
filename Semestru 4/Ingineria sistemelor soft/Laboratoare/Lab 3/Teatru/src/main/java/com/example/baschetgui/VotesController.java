package com.example.baschetgui;

import com.example.baschetgui.cs.MessageAlert;
import com.example.baschetgui.cs.Observer;
import com.example.baschetgui.cs.models.Spectacol;
import com.example.baschetgui.cs.models.Spectator;
import com.example.baschetgui.cs.services.AppService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class VotesController implements Observer {
    AppService appService;
    ObservableList<Spectacol> modelSpectacolTable = FXCollections.observableArrayList();
    /*ObservableList<User> modelFriendsTable = FXCollections.observableArrayList();
    ObservableList<Friendship> modelFrom = FXCollections.observableArrayList();
    ObservableList<Friendship> modelTo = FXCollections.observableArrayList();*/

    Stage currentStage;

    Spectator currentSpectator;

    @FXML
    Text fullUser;

    @FXML
    TableView<Spectacol> tableSpectacols;

    @FXML
    private TextField spectacolNameField;

    @FXML
    private TextField spectacolAuthorField;

    @FXML
    private TextField spectacolSuggestionsField;

    @FXML
    TableColumn<Spectacol, String> tableColumnIdSpectacol;

    @FXML
    TableColumn<Spectacol, String> tableColumnName;

    @FXML
    TableColumn<Spectacol, String> tableColumnAuthor;

    @FXML
    TableColumn<Spectacol, String> tableColumnSuggestions;

    @FXML
    TableColumn<Spectacol, Integer> tableColumnVotes;


    public void setService(AppService networkService1, Stage currentStage1) {
        appService = networkService1;
        //appService.addObserver(this);
        currentSpectator = appService.getCurrentSpectator();
        currentStage = currentStage1;

        appService.addObserver(this);

        initSpectacols();
    }

    private Comparator<Spectacol> sortVotes() {
        Comparator<Spectacol> compareVotes = Comparator.comparing(Spectacol::getVotes).reversed();
//        Comparator<Spectacol> compareFirstName = Comparator.comparing(User::getFirstName);

//        return compareLastName.thenComparing(compareFirstName);
        return compareVotes;
    }

    private void initSpectacols() {
        modelSpectacolTable.setAll(getSuggestedSpectacolsList());
    }

    private List<Spectacol> getSuggestedSpectacolsList() {
        Iterable<Spectacol> matches = appService.getAllSpectacolsSuggested();
        return StreamSupport.stream(matches.spliterator(), false)
                .sorted(sortVotes())
                .collect(Collectors.toList());
    }

    @FXML
    public void initialize() {
        tableColumnIdSpectacol.setCellValueFactory(new PropertyValueFactory<Spectacol, String>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<Spectacol, String>("name"));
        tableColumnAuthor.setCellValueFactory(new PropertyValueFactory<Spectacol, String>("author"));
        tableColumnSuggestions.setCellValueFactory(new PropertyValueFactory<Spectacol, String>("suggestions"));
        tableColumnVotes.setCellValueFactory(new PropertyValueFactory<Spectacol, Integer>("votes"));

        tableSpectacols.setItems(modelSpectacolTable);
    }

    @FXML
    public void handleVote(ActionEvent ev) {
        // TODO
        Spectacol selectedSpectacol = tableSpectacols.getSelectionModel().getSelectedItem();

        if (selectedSpectacol != null) {
            try {
                appService.voteSpectacol(selectedSpectacol);
                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Status vot", "Spectacolul " + selectedSpectacol.getName() + " de " + selectedSpectacol.getAuthor() + " a primit un vot din partea ta!");
            } catch (Exception e) {
                MessageAlert.showErrorMessage(null, e.getMessage());
            }
        }
        else {
            MessageAlert.showErrorMessage(null, "Nu există spectacol selectat!");
        }
    }

    @FXML
    public void handleSuggest(ActionEvent ev) {
        // TODO

        if (spectacolNameField == null || spectacolAuthorField == null) {
            MessageAlert.showErrorMessage(null, "Numele spectacolului și autorul nu pot fi nule!");
            return;
        }

        String name = spectacolNameField.getText();
        String author = spectacolAuthorField.getText();
        String suggestions = spectacolSuggestionsField.getText();

        if (name.equals("") || author.equals("")) {
            MessageAlert.showErrorMessage(null, "Numele spectacolului și autorul nu pot fi nule!");
            return;
        }

        try {
            Spectacol suggestedSpectacol = new Spectacol(name, author, suggestions, 0);
            suggestedSpectacol.setAccepted(false);

            if (appService.saveSuggestedSpectacol(suggestedSpectacol)) {
                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Status spectacol", "Spectacolul " + name + " de " + author + " a fost adăugat pe lista de sugestii!");
            } else {
                MessageAlert.showErrorMessage(null, "A apărut o eroare la adăugarea spectacolului sugerat!");
            }
        } catch (Exception e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
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

    @Override
    public void update() {
        initSpectacols();
    }
}