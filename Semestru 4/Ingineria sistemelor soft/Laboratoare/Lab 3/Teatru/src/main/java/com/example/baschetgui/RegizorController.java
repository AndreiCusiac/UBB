package com.example.baschetgui;

import com.example.baschetgui.cs.MessageAlert;
import com.example.baschetgui.cs.Observer;
import com.example.baschetgui.cs.models.Regizor;
import com.example.baschetgui.cs.models.Spectacol;
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
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RegizorController implements Observer {
    AppService appService;
    ObservableList<Spectacol> modelSpectacolTable = FXCollections.observableArrayList();
    ObservableList<Spectacol> modelSpectacolTableApproved = FXCollections.observableArrayList();
    /*ObservableList<User> modelFriendsTable = FXCollections.observableArrayList();
    ObservableList<Friendship> modelFrom = FXCollections.observableArrayList();
    ObservableList<Friendship> modelTo = FXCollections.observableArrayList();*/

    Stage currentStage;

    Regizor currentRegizor;

    @FXML
    Text fullUser;

    @FXML
    TableView<Spectacol> tableSpectacols;

    @FXML
    TableView<Spectacol> tableSpectacolsApproved;

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

    @FXML
    TableColumn<Spectacol, String> tableColumnIdSpectacolApproved;

    @FXML
    TableColumn<Spectacol, String> tableColumnNameApproved;

    @FXML
    TableColumn<Spectacol, String> tableColumnAuthorApproved;

    @FXML
    TableColumn<Spectacol, Date> tableColumnDateApproved;

    @FXML
    TableColumn<Spectacol, String> tableColumnActorsApproved;

    @FXML
    TableColumn<Spectacol, Integer> tableColumnVotesApproved;


    public void setService(AppService networkService1, Stage currentStage1) {
        appService = networkService1;
        //appService.addObserver(this);
        currentRegizor = appService.getCurrentRegizor();
        currentStage = currentStage1;

        appService.addObserver(this);

        //labelLookWho.setVisible(false);
        //labelUhOh.setVisible(false);

        initSpectacols();
        initSpectacolsApproved();
        //initModel1();

        //greetings();
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

    private void initSpectacolsApproved() {
        modelSpectacolTableApproved.setAll(getApprovedSpectacolsList());
    }

    private List<Spectacol> getSuggestedSpectacolsList() {
        Iterable<Spectacol> matches = appService.getAllSpectacolsSuggested();
        return StreamSupport.stream(matches.spliterator(), false)
                .sorted(sortVotes())
                .collect(Collectors.toList());
    }

    private List<Spectacol> getApprovedSpectacolsList() {
        Iterable<Spectacol> matches = appService.getAllSpectacolsAccepted();
        return StreamSupport.stream(matches.spliterator(), false)
                //.sorted(sortVotes())
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

        tableColumnIdSpectacolApproved.setCellValueFactory(new PropertyValueFactory<Spectacol, String>("id"));
        tableColumnNameApproved.setCellValueFactory(new PropertyValueFactory<Spectacol, String>("name"));
        tableColumnAuthorApproved.setCellValueFactory(new PropertyValueFactory<Spectacol, String>("author"));
        tableColumnDateApproved.setCellValueFactory(new PropertyValueFactory<Spectacol, Date>("date"));
        tableColumnActorsApproved.setCellValueFactory(new PropertyValueFactory<Spectacol, String>("actors"));
        tableColumnVotesApproved.setCellValueFactory(new PropertyValueFactory<Spectacol, Integer>("votes"));

        tableSpectacolsApproved.setItems(modelSpectacolTableApproved);
    }

    @FXML
    public void handleAddNewSpectacol(ActionEvent ev) {
        // TODO

        System.out.println("Ok0");

        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("IntroducereWindow.fxml"));

        System.out.println("Ok1");

        //FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("app-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Ok2");

        String title = "Regizor - " + appService.getCurrentRegizor().getName();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        System.out.println("Ok3");

        currentStage.close();

        ManageController manageController = fxmlLoader.getController();
        manageController.setService(appService, stage, null);

        System.out.println("Ok4");
    }

    @FXML
    public void handleModifySpectacol(ActionEvent ev) {
        // TODO
        Spectacol selectedSpectacol = tableSpectacolsApproved.getSelectionModel().getSelectedItem();
        System.out.println(selectedSpectacol);

        if (selectedSpectacol != null) {

            System.out.println("Ok0");

            Stage stage = new Stage();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ModificareWindow.fxml"));

            System.out.println("Ok1");

            //FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("app-view.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Ok2");

            String title = "Regizor - " + appService.getCurrentRegizor().getName();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            System.out.println("Ok3");

            currentStage.close();

            ManageController manageController = fxmlLoader.getController();
            manageController.setService(appService, stage, selectedSpectacol);

            System.out.println("Ok4");
        }
        else {
            MessageAlert.showErrorMessage(null, "Nu există spectacol selectat!");
        }
    }

    @FXML
    public void handleDeleteSpectacol(ActionEvent ev) {
        // TODO
        Spectacol selectedSpectacol = tableSpectacolsApproved.getSelectionModel().getSelectedItem();

        if (selectedSpectacol != null) {
            try {
                appService.deleteSpectacol(selectedSpectacol);
                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Status spectacol", "Spectacolul " + selectedSpectacol.getName() + " de " + selectedSpectacol.getAuthor() + " a fost șters cu succes!");
            } catch (Exception e) {
                MessageAlert.showErrorMessage(null, e.getMessage());
            }
        }
        else {
            MessageAlert.showErrorMessage(null, "Nu există spectacol selectat!");
        }
    }

    @FXML
    public void handleAddSuggested(ActionEvent ev) {
        // TODO
        Spectacol selectedSpectacol = tableSpectacols.getSelectionModel().getSelectedItem();

        if (selectedSpectacol != null) {

            System.out.println("Ok0");

            Stage stage = new Stage();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("IntroducereWindow.fxml"));

            System.out.println("Ok1");

            //FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("app-view.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Ok2");

            String title = "Regizor - " + appService.getCurrentRegizor().getName();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            System.out.println("Ok3");

            currentStage.close();

            ManageController manageController = fxmlLoader.getController();
            manageController.setService(appService, stage, selectedSpectacol);

            System.out.println("Ok4");
        }
        else {
            MessageAlert.showErrorMessage(null, "Nu există spectacol selectat!");
        }
    }

    @FXML
    public void handleLogOut() throws InterruptedException, IOException {
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("LoginWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Log in");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        currentStage.close();

        LoginController loginController = fxmlLoader.getController();
        loginController.setService(appService, stage);
    }
    @Override
    public void update() {
        initSpectacols();
        initSpectacolsApproved();
    }
}