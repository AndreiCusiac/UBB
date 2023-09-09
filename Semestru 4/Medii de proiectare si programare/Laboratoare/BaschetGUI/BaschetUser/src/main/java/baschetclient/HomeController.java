package baschetclient;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Match;
import models.TicketBooth;
import services.IObserver;
import services.IService;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class HomeController implements Initializable, IObserver {
    IService appService;
    ObservableList<Match> modelMatchTable = FXCollections.observableArrayList();
    /*ObservableList<User> modelFriendsTable = FXCollections.observableArrayList();
    ObservableList<Friendship> modelFrom = FXCollections.observableArrayList();
    ObservableList<Friendship> modelTo = FXCollections.observableArrayList();*/

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    Stage currentStage;

    public Parent getMainParent() {
        return mainParent;
    }

    public void setMainParent(Parent mainParent) {
        this.mainParent = mainParent;
    }

    Parent mainParent;

    TicketBooth currentTicketBooth;
    String welcomeGreeting;

    @FXML
    Text textText;

    @FXML
    Text fullUser;

    @FXML
    Button unfriendButton;

    @FXML
    Button sortNormalButton;

    @FXML
    Button sortSeatsButton;

    @FXML
    Button sellButton;

    @FXML
    private TextField ticketNameField;

    @FXML
    private TextField ticketSeatsField;

    @FXML
    Button rejectButton;

    @FXML
    private Label labelUhOh;

    @FXML
    private Label labelLookWho;

    @FXML
    private CheckBox checkBoxActive;

    @FXML
    TableView<Match> tableMatches;

    /*@FXML
    TableView<User> tableFriends;*/

    @FXML
    TableColumn<Match, String> tableColumnIdMatch;

    @FXML
    TableColumn<Match, String> tableColumnHomeTeamMatch;

    @FXML
    TableColumn<Match, String> tableColumnAwayTeamMatch;

    @FXML
    TableColumn<Match, Float> tableColumnPriceMatch;

    @FXML
    TableColumn<Match, Integer> tableColumnTotalSeatsMatch;

    @FXML
    TableColumn<Match, Integer> tableColumnAvailableSeatsMatch;

    @FXML
    TableColumn<Match, String> tableColumnSoldOutMatch;

    @FXML
    ImageView refreshIcon;

    @FXML
    ImageView logoutIcon;

    public TicketBooth getCurrentTicketBooth() {
        return currentTicketBooth;
    }

    public IService getAppService() {
        return appService;
    }

    public void setAppService(IService appService) {
        this.appService = appService;
    }

    public void setCurrentTicketBooth(TicketBooth currentTicketBooth) {
        this.currentTicketBooth = currentTicketBooth;
    }

    public void setService(IService networkService1, Stage currentStage1) throws Exception {
        appService = networkService1;
        //appService.addObserver(this);
        currentStage = currentStage1;

        //labelLookWho.setVisible(false);
        //labelUhOh.setVisible(false);

        initMatches();
        //initModel1();

        greetings();
    }

    public void initDisplay() throws Exception {
        Platform.runLater(()-> {
            try {
                initMatches();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        greetings();
        initialize();
    }

    private void greetings() {

        welcomeGreeting = "Registered as " + currentTicketBooth.getName() + ".";
        textText.setText(welcomeGreeting);

        //fullUser.setText(currentTicketBooth.getFirstName() + " " + currentTicketBooth.getLastName());
    }



    private void initMatches() throws Exception {
        modelMatchTable.setAll(getMatchesList());
    }

    private void initMatchesGiven(ArrayList<Match> matches) throws Exception {
        modelMatchTable.setAll(StreamSupport.stream(matches.spliterator(), false)
                .collect(Collectors.toList()));
    }

    private void initMatchesSortedByAvailableSeats() throws Exception {
        modelMatchTable.setAll(getMatchesSortedByAvailableSeatsList());
    }

    private List<Match> getMatchesList() throws Exception {
        var allMatches = appService.getAllMatches();
        //System.out.println(allMatches);
        Iterable<Match> matches = allMatches;
        System.out.println();
        List<Match> matchesList = StreamSupport.stream(matches.spliterator(), false)
                //.sorted(sortNames())
                .collect(Collectors.toList());
        System.out.println(matchesList);
        return matchesList;
    }

    private List<Match> getMatchesSortedByAvailableSeatsList() throws Exception {
        var allMatchesSorted = appService.getMatchesSortedByAvailableSeatsDesc();
        System.out.println(allMatchesSorted);
        Iterable<Match> matches = allMatchesSorted;
        List<Match> matchesList = StreamSupport.stream(matches.spliterator(), false)
                //.sorted(sortNames())
                .collect(Collectors.toList());
        return matchesList;
    }

    @FXML
    public void initialize() {
        tableColumnIdMatch.setCellValueFactory(new PropertyValueFactory<Match, String>("id"));
        tableColumnHomeTeamMatch.setCellValueFactory(new PropertyValueFactory<Match, String>("homeTeam"));
        tableColumnAwayTeamMatch.setCellValueFactory(new PropertyValueFactory<Match, String>("awayTeam"));
        tableColumnPriceMatch.setCellValueFactory(new PropertyValueFactory<Match, Float>("ticketPrice"));
        tableColumnTotalSeatsMatch.setCellValueFactory(new PropertyValueFactory<Match, Integer>("totalSeats"));
        tableColumnAvailableSeatsMatch.setCellValueFactory(new PropertyValueFactory<Match, Integer>("availableSeats"));
        tableColumnSoldOutMatch.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getSoldOut()));
        tableColumnSoldOutMatch.setCellFactory(new Callback<TableColumn<Match, String>, TableCell<Match, String>>() {
            @Override
            public TableCell<Match, String> call(TableColumn<Match, String> param) {
                    return new TableCell<Match, String>() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            if (!empty) {
                                int currentIndex = indexProperty()
                                        .getValue() < 0 ? 0
                                        : indexProperty().getValue();
                                String clmStatus = param
                                        .getTableView().getItems()
                                        .get(currentIndex).getSoldOut();
                                if (clmStatus.equals("sold out")) {
                                    setTextFill(Color.RED);
                                    setStyle("-fx-font-weight: bold");
                                    setText(clmStatus);
                                }
                                else{
                                    setTextFill(Color.BLACK);
                                    setText(clmStatus);
                                }
                            }
                        }
                    };
            }
        });
        tableMatches.setItems(modelMatchTable);
    }

    @FXML
    public void handleSell(ActionEvent ev) {
        // TODO
        Match selectedMatch = tableMatches.getSelectionModel().getSelectedItem();

        String name = ticketNameField.getText();
        String seats = ticketSeatsField.getText();

        if (name.equals("") || seats.equals("")) {
            MessageAlert.showErrorMessage(null, "Numele și numărul de locuri nu pot fi nule!");
            return;
        }

        if (selectedMatch != null) {
//            currentStage.getScene().setCursor(Cursor.WAIT);

            if (selectedMatch.getAvailableSeats() <= 0) {
                MessageAlert.showErrorMessage(null, "Nu există locuri disponibile pentru meciul selectat!");
                return;
            }

            try {
                Integer.parseInt(seats);
            } catch (Exception e) {
                MessageAlert.showErrorMessage(null, e.getMessage());
            }

            if (Integer.parseInt(seats) > selectedMatch.getAvailableSeats()) {
                MessageAlert.showErrorMessage(null, "Nu există locuri suficiente pentru meciul selectat!");
                return;
            }

            try {
                appService.sellTicket(selectedMatch, name, Integer.parseInt(seats));
                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Status vânzare", name + " a achiziționat " + seats + " bilete la meciul " + selectedMatch.getId() + ".");
            } catch (Exception e) {
                MessageAlert.showErrorMessage(null, e.getMessage());
            } finally {
            }

            //currentStage.getScene().setCursor(Cursor.DEFAULT);
        }
        else {
            MessageAlert.showErrorMessage(null, "There is no selected match!");
        }
    }

    @FXML
    public void handleRefreshAllTables() throws Exception {
//        currentStage.getScene().setCursor(Cursor.WAIT);

            initMatches();

        //currentStage.getScene().setCursor(Cursor.DEFAULT);
        //MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Refresh", "Tabele actualizate cu succes!");
    }

    @FXML
    public void handleSortNormal() throws Exception {
//        currentStage.getScene().setCursor(Cursor.WAIT);


            initMatches();

        //currentStage.getScene().setCursor(Cursor.DEFAULT);
        //MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Refresh", "Tabele actualizate cu succes!");
    }

    @FXML
    public void handleSortSeats() throws Exception {
//        currentStage.getScene().setCursor(Cursor.WAIT);

        initMatchesSortedByAvailableSeats();

        //currentStage.getScene().setCursor(Cursor.DEFAULT);
        //MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Refresh", "Tabele actualizate cu succes!");
    }

    @FXML
    public void handleLogOut() throws Exception {
        /*
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 530, 350);
        stage.setTitle("Log in");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        currentStage.close();
        appService.logout(currentTicketBooth, this);

        LoginController loginController = fxmlLoader.getController();
        loginController.setService(appService, stage);*/

        try {
//            currentStage.close();
            appService.logout(currentTicketBooth, this);
        } catch (Exception e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
            return;
        }

        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 530, 350);
        stage.setTitle("Log in");
        stage.setScene(scene);
        stage.setResizable(false);


        LoginController loginController = fxmlLoader.getController();
        loginController.setService(appService, stage);

        loginController.setHomeController(this);
        loginController.setParent(mainParent);

        currentStage.close();
        stage.show();
    }

    @FXML
    public void handleExit(ActionEvent actionEvent){
        currentStage.close();
    }

    @FXML
    public void handleLogoAnimationRefresh() {
        Glow glow = new Glow();
        glow.setLevel(0.77);
        refreshIcon.setEffect(glow);
    }

    @FXML
    public void handleLogoAnimationRefreshOff() {
        Glow glow = new Glow();
        glow.setLevel(0);
        refreshIcon.setEffect(glow);
    }

    @FXML
    public void handleLogoAnimationLogout() {
        Glow glow = new Glow();
        glow.setLevel(0.77);
        logoutIcon.setEffect(glow);
    }

    @FXML
    public void handleLogoAnimationLogoutOff() {
        Glow glow = new Glow();
        glow.setLevel(0);
        logoutIcon.setEffect(glow);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void ticketSold() throws Exception {
        System.out.println("Am ajuns in ticketSold");
        Platform.runLater(()-> {
            try {
                System.out.println("Incercam sa initializam meciuri din ticketSold cu succes!");
                initMatches();
                System.out.println("Initializat meciuri din ticketSold cu succes!");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Initializat erori din ticketSold :d!");
            }
        });
//        initMatches();
        System.out.println("Am iesit din ticketSold");
    }

    @Override
    public void ticketSold(ArrayList<Match> matches) throws Exception {
        System.out.println("Am ajuns in ticketSold cu urmatoarele matches: " + matches);
        Platform.runLater(()-> {
            try {
                System.out.println("Incercam sa initializam meciuri din ticketSold cu succes!");
                initMatchesGiven(matches);
                System.out.println("Initializat meciuri din ticketSold cu succes!");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Initializat erori din ticketSold :d!");
            }
        });
//        initMatches();
        System.out.println("Am iesit din ticketSold");
    }
}