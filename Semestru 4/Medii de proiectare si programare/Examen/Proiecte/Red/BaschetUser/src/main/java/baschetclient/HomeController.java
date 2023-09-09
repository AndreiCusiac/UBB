package baschetclient;

import javafx.application.Platform;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Card;
import models.Match;
import models.Player;
import services.IObserver;
import services.IService;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class HomeController implements Initializable, IObserver {
    IService appService;

    private Boolean gameUnderway;
    ObservableList<Card> modelTable = FXCollections.observableArrayList();
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

    Player currentPlayer;

    ArrayList<Card> currentCards;
    String welcomeGreeting;

    @FXML
    Text textText;

    @FXML
    Label textLetters;

    @FXML
    TextField guessWord;

    @FXML
    Label textCards;

    @FXML
    Text fullUser;

    @FXML
    Button unfriendButton;

    @FXML
    Button guessButton;

    @FXML
    Button startGame;

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
    TableView<Card> tableCards;

    /*@FXML
    TableView<User> tableFriends;*/

    @FXML
    TableColumn<Card, String> tableColumnId;

    @FXML
    TableColumn<Card, String> tableColumnColor;

    @FXML
    TableColumn<Card, Integer> tableColumnValue;

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

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public IService getAppService() {
        return appService;
    }

    public void setAppService(IService appService) {
        this.appService = appService;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setService(IService networkService1, Stage currentStage1) throws Exception {
        appService = networkService1;
        //appService.addObserver(this);
        currentStage = currentStage1;

        //labelLookWho.setVisible(false);
        //labelUhOh.setVisible(false);

        //initMatches();
        //initModel1();

        gameUnderway = false;

        greetings();
    }

    public void initDisplay() throws Exception {
        Platform.runLater(()-> {
            try {
                //initTable();
                //giveCard.setDisable(true);
                greetings();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //initialize();
    }

    private void greetings() {

        welcomeGreeting = "Registered as " + currentPlayer.getName() + ".";
        textText.setText(welcomeGreeting);

        textLetters.setText("Not in game");

        //fullUser.setText(currentTicketBooth.getFirstName() + " " + currentTicketBooth.getLastName());
    }



    private void initTable() throws Exception {
        modelTable.setAll(currentCards);
    }

    private void initCardsGiven(ArrayList<Card> cards) throws Exception {
        System.out.println("Am intrat in initCardsGiven with " + cards);
        modelTable.setAll(StreamSupport.stream(cards.spliterator(), false)
                .collect(Collectors.toList()));
    }
//
//    private void initMatchesSortedByAvailableSeats() throws Exception {
//        modelTable.setAll(getMatchesSortedByAvailableSeatsList());
//    }

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
        tableColumnId.setCellValueFactory(new PropertyValueFactory<Card, String>("id"));
        tableColumnColor.setCellValueFactory(new PropertyValueFactory<Card, String>("color"));
        tableColumnValue.setCellValueFactory(new PropertyValueFactory<Card, Integer>("value"));
//        tableColumnPriceMatch.setCellValueFactory(new PropertyValueFactory<Match, Float>("ticketPrice"));
//        tableColumnTotalSeatsMatch.setCellValueFactory(new PropertyValueFactory<Match, Integer>("totalSeats"));
//        tableColumnAvailableSeatsMatch.setCellValueFactory(new PropertyValueFactory<Match, Integer>("availableSeats"));
//        tableColumnSoldOutMatch.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getSoldOut()));
//        tableColumnSoldOutMatch.setCellFactory(new Callback<TableColumn<Match, String>, TableCell<Match, String>>() {
//            @Override
//            public TableCell<Match, String> call(TableColumn<Match, String> param) {
//                    return new TableCell<Match, String>() {
//                        @Override
//                        protected void updateItem(String item, boolean empty) {
//                            if (!empty) {
//                                int currentIndex = indexProperty()
//                                        .getValue() < 0 ? 0
//                                        : indexProperty().getValue();
//                                String clmStatus = param
//                                        .getTableView().getItems()
//                                        .get(currentIndex).getSoldOut();
//                                if (clmStatus.equals("sold out")) {
//                                    setTextFill(Color.RED);
//                                    setStyle("-fx-font-weight: bold");
//                                    setText(clmStatus);
//                                }
//                                else{
//                                    setTextFill(Color.BLACK);
//                                    setText(clmStatus);
//                                }
//                            }
//                        }
//                    };
//            }
//        });
        tableCards.setItems(modelTable);
    }

    @FXML
    public void handleGuessWord(ActionEvent ev) {
        // TODO

        String guess = guessWord.getText();

        if (guess != null) {
//            currentStage.getScene().setCursor(Cursor.WAIT);

            try {
                appService.giveGuess(currentPlayer.getName(), guess);
                //MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Status vânzare", name + " a achiziționat " + seats + " bilete la meciul " + selectedCard.getId() + ".");
            } catch (Exception e) {
                MessageAlert.showErrorMessage(null, e.getMessage());
            } finally {
            }

            //currentStage.getScene().setCursor(Cursor.DEFAULT);
        }
        else {
            MessageAlert.showErrorMessage(null, "There is no guessed word!");
        }
    }

    @FXML
    public void handleStartGame(ActionEvent ev) {
        // TODO

        try {
            appService.startGame(currentPlayer.getName());
            MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Status joc", "Joc inceput!");
        } catch (Exception e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
    }

    @FXML
    public void handleRefreshAllTables() throws Exception {
//        currentStage.getScene().setCursor(Cursor.WAIT);

            initTable();

        //currentStage.getScene().setCursor(Cursor.DEFAULT);
        //MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Refresh", "Tabele actualizate cu succes!");
    }

    @FXML
    public void handleSortNormal() throws Exception {
//        currentStage.getScene().setCursor(Cursor.WAIT);


            initTable();

        //currentStage.getScene().setCursor(Cursor.DEFAULT);
        //MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Refresh", "Tabele actualizate cu succes!");
    }

    @FXML
    public void handleSortSeats() throws Exception {
//        currentStage.getScene().setCursor(Cursor.WAIT);

        //initMatchesSortedByAvailableSeats();

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
            appService.logout(currentPlayer, this);
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
/*
        loginController.setHomeController(this);
        loginController.setParent(mainParent);*/

        FXMLLoader fxmlLoaderHome = new FXMLLoader(HelloApplication.class.getResource("home-view.fxml"));
//        fxmlLoaderHome.setLocation(getClass().getResource("home-view.fxml"));

        Parent croot = fxmlLoaderHome.load();

        HomeController homeController = fxmlLoaderHome.getController();

        homeController.setAppService(this.appService);
        homeController.setMainParent(croot);

        loginController.setHomeController(homeController);
        loginController.setParent(croot);

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
                initTable();
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
                //initMatchesGiven(matches);
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
    public void gameStarted(String letters) {

        System.out.println("Am ajuns in gameStarted cu urmatorii letters: " + letters);
        Platform.runLater(()-> {
            try {

                textLetters.setText(letters);
                startGame.setDisable(true);
//                initCardsGiven(cardsReceived);
//
//
//                String otherPlayers = "";
//
//                for (Player x : playersInGame) {
//                    otherPlayers += x.getName();
//                    otherPlayers += " ";
//                }
//
//                textLetters.setText(otherPlayers);
//
//                String cards = "";
//
//                for (Card x : cardsReceived) {
//                    String card = x.getColor() + "." + x.getValue().toString();
//                    cards += card;
//                    cards += " ";
//                }
//
//                textCards.setText(cards);
//
//                currentCards = cardsReceived;
//
//                initTable();
//
//                gameUnderway = true;
//
//                initButtons();

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Initializat erori din gameStarted :d!");
            }
        });
//        initMatches();
        System.out.println("Am iesit din gameStarted");
    }

    @Override
    public void movesMade(String s) throws Exception {

        System.out.println("Am ajuns in movesMade cu urmatorii rez: " + s);
        Platform.runLater(()-> {
            try {
                var k = s.charAt(0);

                if (s.equals(k + "/" + k)) {
                    MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Status ghici", "Cuvant ghicit!");
                } else {
                    if (k == '0') {
                        MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Status ghici", "Nicio litera ghicita!");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Initializat erori din movesMade :d!");
            }
        });
//        initMatches();
        System.out.println("Am iesit din movesMade");
    }

    private void initButtons() {
//        startGame.setDisable(false);
//        guessButton.setDisable(true);
    }
}