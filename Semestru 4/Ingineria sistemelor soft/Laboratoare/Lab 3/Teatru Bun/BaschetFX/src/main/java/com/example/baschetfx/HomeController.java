package com.example.baschetfx;

import com.example.baschetgui.cs.models.Match;
import com.example.baschetgui.cs.models.TicketBooth;
import com.example.baschetgui.cs.services.AppService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class HomeController {
    AppService appService;
    ObservableList<Match> modelMatchTable = FXCollections.observableArrayList();
    /*ObservableList<User> modelFriendsTable = FXCollections.observableArrayList();
    ObservableList<Friendship> modelFrom = FXCollections.observableArrayList();
    ObservableList<Friendship> modelTo = FXCollections.observableArrayList();*/

    Stage currentStage;

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

    /*@FXML
    TableColumn<Match, String> tableColumnEmail;

    @FXML
    TableColumn<Friendship, String> tableColumnFrom;

    @FXML
    TableColumn<Friendship, LocalDateTime> tableColumnData;

    @FXML
    TableColumn<Friendship, String> tableColumnStatus;

    @FXML
    TableColumn<Friendship, String> tableColumnTo;

    @FXML
    TableColumn<Friendship, LocalDateTime> tableColumnData1;

    @FXML
    TableColumn<Friendship, String> tableColumnStatus1;

    @FXML
    ImageView searchIcon;*/

    @FXML
    ImageView refreshIcon;

    @FXML
    ImageView logoutIcon;

    public void setService(AppService networkService1, Stage currentStage1) {
        appService = networkService1;
        //appService.addObserver(this);
        currentTicketBooth = appService.getCurrentTicketBooth();
        currentStage = currentStage1;

        //labelLookWho.setVisible(false);
        //labelUhOh.setVisible(false);

        initMatches();
        //initModel1();

        greetings();
    }

    private void greetings() {

        welcomeGreeting = "Registered as " + currentTicketBooth.getName() + ".";
        textText.setText(welcomeGreeting);

        //fullUser.setText(currentTicketBooth.getFirstName() + " " + currentTicketBooth.getLastName());
    }

    /*private Comparator<User> sortNames () {
        Comparator<User> compareLastName = Comparator.comparing(User::getLastName);
        Comparator<User> compareFirstName = Comparator.comparing(User::getFirstName);
        Comparator<User> compareAll = compareLastName.thenComparing(compareFirstName);

        return compareAll;
    }

    private Comparator<Friendship> sortDatesReversedForFriendships () {
        Comparator<Friendship> compareDate = Comparator.comparing(Friendship::getDateOfFriendshipRequest).reversed();
        Comparator<Friendship> compareAll = compareDate;

        return compareAll;
    }*/

    /*private void initModel1() throws FriendshipRepoValidator, UserRepoValidator {
        Iterable<Friendship> messages = appService.transformListReceivedReuqests(currentTicketBooth.getId());
        List<Friendship> messageTaskList = StreamSupport.stream(messages.spliterator(), false)
                .sorted(sortDatesReversedForFriendships())
                .collect(Collectors.toList());
        modelFrom.setAll(messageTaskList);

        if (messageTaskList.isEmpty()) {
            acceptButton.setVisible(false);
            rejectButton.setVisible(false);
            labelLookWho.setVisible(true);
        }
        else {
            acceptButton.setVisible(true);
            rejectButton.setVisible(true);
            labelLookWho.setVisible(false);
        }
    }*/

    /*private void initModel2() throws FriendshipRepoValidator, UserRepoValidator {
        Iterable<Friendship> messages = appService.transformListSentReuqests(currentTicketBooth.getId());
        List<Friendship> messageTaskList = StreamSupport.stream(messages.spliterator(), false)
                .sorted(sortDatesReversedForFriendships())
                .collect(Collectors.toList());
        modelTo.setAll(messageTaskList);
    }*/

    private void initMatches() {
        modelMatchTable.setAll(getMatchesList());
    }

    private void initMatchesSortedByAvailableSeats() {
        modelMatchTable.setAll(getMatchesSortedByAvailableSeatsList());
    }

    /*private void initMatches() throws UserRepoValidator, FriendshipRepoValidator {
        var x = getFriendsOfList(currentTicketBooth);

        modelFriendsTable.setAll(x);

        if  (x.size() == 0) {
            unfriendButton.setVisible(false);
            textFieldFriend.setVisible(false);
            searchIcon.setVisible(false);
            labelUhOh.setVisible(true);
        }
        else {
            unfriendButton.setVisible(true);
            textFieldFriend.setVisible(true);
            searchIcon.setVisible(true);
            labelUhOh.setVisible(false);
        }
    }*/

    private List<Match> getMatchesList() {
        Iterable<Match> matches = appService.getAllMatches();
        List<Match> matchesList = StreamSupport.stream(matches.spliterator(), false)
                //.sorted(sortNames())
                .collect(Collectors.toList());
        return matchesList;
    }

    private List<Match> getMatchesSortedByAvailableSeatsList() {
        Iterable<Match> matches = appService.getMatchesSortedByAvailableSeatsDesc();
        List<Match> matchesList = StreamSupport.stream(matches.spliterator(), false)
                //.sorted(sortNames())
                .collect(Collectors.toList());
        return matchesList;
    }

    /*private List<User> getFriendsOfList(User currentUser1) throws UserRepoValidator, FriendshipRepoValidator {
        Iterable<User> users = appService.getFriendsOf(currentUser1);
        List<User> usersList = StreamSupport.stream(users.spliterator(), false)
                .sorted(sortNames())
                .collect(Collectors.toList());
        return usersList;
    }*/

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

        /*tableColumnFrom.setCellValueFactory(new PropertyValueFactory<Friendship, String>("numeid1"));
        tableColumnData.setCellValueFactory(new PropertyValueFactory<Friendship, LocalDateTime>("dateOfFriendshipRequest"));
        tableColumnStatus.setCellValueFactory(new PropertyValueFactory<Friendship, String>("status"));
        tableFriendRequestsFrom.setItems(modelFrom);*/

        /*textFieldFriend.textProperty().addListener(o -> {
            try {
                handleFilterFriends(modelFriendsTable, textFieldFriend);
            } catch (UserRepoValidator e) {
                e.printStackTrace();
            } catch (FriendshipRepoValidator e) {
                e.printStackTrace();
            }
        });*/
    }

    /*private void handleFilter(ObservableList<User> modelCurent, TextField textField) throws UserRepoValidator {
        TextField currentTextField = textField;

        Predicate<User> firstNameFilter = n -> n.getFirstName().contains(currentTextField.getText());
//        Predicate<User> firstNameFilter = n -> n.getFirstName().contains(textFieldName.getCharacters());
        Predicate<User> lastNameFilter = n -> n.getLastName().contains(currentTextField.getText());
//        Predicate<User> lastNameFilter = n -> n.getLastName().contains(textFieldName.getCharacters());
        Predicate<User> firstNameSmallFilter = n -> n.getFirstName().toLowerCase().contains(currentTextField.getText());
        Predicate<User> firstNameCAPSFilter = n -> n.getFirstName().toUpperCase().contains(currentTextField.getText());
        Predicate<User> lastNameSmallFilter = n -> n.getLastName().toLowerCase().contains(currentTextField.getText());
        Predicate<User> lastNameCAPSFilter = n -> n.getLastName().toUpperCase().contains(currentTextField.getText());

        Predicate<User> allPredicates = firstNameFilter.
                or(lastNameFilter).
                or(firstNameCAPSFilter).
                or(firstNameSmallFilter).
                or(lastNameSmallFilter).
                or(lastNameCAPSFilter);

        modelCurent.setAll(getMatchesList()
                .stream()
                .filter(allPredicates)
                .collect(Collectors.toList()));
    }*/

    /*private void handleFilterFriends(ObservableList<User> modelCurent, TextField textField) throws UserRepoValidator, FriendshipRepoValidator {
        TextField currentTextField = textField;

        Predicate<User> firstNameFilter = n -> n.getFirstName().contains(currentTextField.getText());
//        Predicate<User> firstNameFilter = n -> n.getFirstName().contains(textFieldName.getCharacters());
        Predicate<User> lastNameFilter = n -> n.getLastName().contains(currentTextField.getText());
//        Predicate<User> lastNameFilter = n -> n.getLastName().contains(textFieldName.getCharacters());
        Predicate<User> firstNameSmallFilter = n -> n.getFirstName().toLowerCase().contains(currentTextField.getText());
        Predicate<User> firstNameCAPSFilter = n -> n.getFirstName().toUpperCase().contains(currentTextField.getText());
        Predicate<User> lastNameSmallFilter = n -> n.getLastName().toLowerCase().contains(currentTextField.getText());
        Predicate<User> lastNameCAPSFilter = n -> n.getLastName().toUpperCase().contains(currentTextField.getText());

        Predicate<User> allPredicates = firstNameFilter.
                or(lastNameFilter).
                or(firstNameCAPSFilter).
                or(firstNameSmallFilter).
                or(lastNameSmallFilter).
                or(lastNameCAPSFilter);

        modelCurent.setAll(getFriendsOfList(currentTicketBooth)
                .stream()
                .filter(allPredicates)
                .collect(Collectors.toList()));
    }*/

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

   /* @FXML
    public void handleRemoveFriend(ActionEvent ev) {
        // TODO
        User selectedUser = tableFriends.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            currentStage.getScene().setCursor(Cursor.WAIT);

            try {
                appService.deleteFriendship(currentTicketBooth.getId(), selectedUser.getId());
                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Remove friend", "Your friendship with " + selectedUser.getFirstName() + " " + selectedUser.getLastName() + " has just been deleted!");
            } catch (Exception e) {
                MessageAlert.showErrorMessage(null, e.getMessage());
            } finally {
            }
            currentStage.getScene().setCursor(Cursor.DEFAULT);

        }
        else {
            MessageAlert.showErrorMessage(null, "There is no selected user!");
        }
    }*/
/*
    @FXML
    public void handleAddNewFriendship(ActionEvent actionEvent) throws UserRepoValidator {
        Friendship selectedFriendship = tableFriendRequestsFrom.getSelectionModel().getSelectedItem();

        if (selectedFriendship != null) {
            currentStage.getScene().setCursor(Cursor.WAIT);

            try {
                appService.answerRequest(currentTicketBooth.getId(), selectedFriendship.getId1(), "approved");
                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Add friend", "Your friendships with " + appService.getUserByID(selectedFriendship.getId2()).getFirstName() + " " + appService.getUserByID(selectedFriendship.getId2()).getFirstName()  + " has just been confirmed!");
            } catch (Exception e) {
                //MessageAlert.showErrorMessage(null, e.getMessage());
                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Add friend", "Your friendships with " + appService.getUserByID(selectedFriendship.getId2()).getFirstName() + " " + appService.getUserByID(selectedFriendship.getId2()).getFirstName()  + " has just been confirmed!");
                currentStage.getScene().setCursor(Cursor.DEFAULT);
            } finally {
                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Add friend", "Your friendships with " + appService.getUserByID(selectedFriendship.getId2()).getFirstName() + " " + appService.getUserByID(selectedFriendship.getId2()).getFirstName()  + " has just been confirmed!");
                currentStage.getScene().setCursor(Cursor.DEFAULT);
            }
            currentStage.getScene().setCursor(Cursor.DEFAULT);

        }
        else {
            MessageAlert.showErrorMessage(null, "There is no selected friendship request!");
        }
    }*/
/*
    @FXML
    public void handleRejectNewFriendship(ActionEvent actionEvent) throws UserRepoValidator {
        Friendship selectedFriendship = tableFriendRequestsFrom.getSelectionModel().getSelectedItem();

        if (selectedFriendship != null) {
            currentStage.getScene().setCursor(Cursor.WAIT);

            try {
                appService.answerRequest(currentTicketBooth.getId(), selectedFriendship.getId1(), "rejected");
                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Add friend",  appService.getUserByID(selectedFriendship.getId2()).getFirstName() + " " + appService.getUserByID(selectedFriendship.getId2()).getFirstName()  + "'s friend request has just been removed!");
            } catch (Exception e) {
//                MessageAlert.showErrorMessage(null, e.getMessage());
                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Add friend",  appService.getUserByID(selectedFriendship.getId2()).getFirstName() + " " + appService.getUserByID(selectedFriendship.getId2()).getFirstName()  + "'s friend request has just been removed!");
                currentStage.getScene().setCursor(Cursor.DEFAULT);
            } finally {
                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Add friend",  appService.getUserByID(selectedFriendship.getId2()).getFirstName() + " " + appService.getUserByID(selectedFriendship.getId2()).getFirstName()  + "'s friend request has just been removed!");
                currentStage.getScene().setCursor(Cursor.DEFAULT);
            }
            currentStage.getScene().setCursor(Cursor.DEFAULT);

        }
        else {
            MessageAlert.showErrorMessage(null, "There is no selected friendship request!");
        }
    }*/
/*
    @FXML
    public void handleRejectSentFriendship(ActionEvent actionEvent) {
        Friendship selectedFriendship = tableFriendRequestsTo.getSelectionModel().getSelectedItem();

        if (selectedFriendship != null) {
            currentStage.getScene().setCursor(Cursor.WAIT);

            try {
                appService.answerRequest(selectedFriendship.getId2(), currentTicketBooth.getId(), "rejected");
                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Adaugare prieten", "Cererea de prietenie catre " + appService.getUserByID(selectedFriendship.getId2()).getFirstName() + " " + appService.getUserByID(selectedFriendship.getId2()).getFirstName()  + " a fost stearsa!");
            } catch (Exception e) {
                MessageAlert.showErrorMessage(null, e.getMessage());
            } finally {
            }
            currentStage.getScene().setCursor(Cursor.DEFAULT);

        }
        else {
            MessageAlert.showErrorMessage(null, "Nu exista o prietenie selectata!");
        }
    }*/

    @FXML
    public void handleRefreshAllTables() {
//        currentStage.getScene().setCursor(Cursor.WAIT);

        initMatches();

        //currentStage.getScene().setCursor(Cursor.DEFAULT);
        //MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Refresh", "Tabele actualizate cu succes!");
    }

    @FXML
    public void handleSortNormal() {
//        currentStage.getScene().setCursor(Cursor.WAIT);

        initMatches();

        //currentStage.getScene().setCursor(Cursor.DEFAULT);
        //MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Refresh", "Tabele actualizate cu succes!");
    }

    @FXML
    public void handleSortSeats() {
//        currentStage.getScene().setCursor(Cursor.WAIT);

        initMatchesSortedByAvailableSeats();

        //currentStage.getScene().setCursor(Cursor.DEFAULT);
        //MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Refresh", "Tabele actualizate cu succes!");
    }

    @FXML
    public void handleLogOut() throws InterruptedException, IOException {
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 530, 350);
        stage.setTitle("Log in");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        currentStage.close();

        LoginController loginController = fxmlLoader.getController();
        loginController.setService(appService, stage);
    }
/*
    @FXML
    public void handeFriends( ) throws MessageRepoValidator, UserRepoValidator, FriendshipRepoValidator {
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("friend-view.fxml"));

        //FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("app-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 900, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Spicee");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        currentStage.close();

        FriendsController friendsController = fxmlLoader.getController();
        friendsController.setService(appService, stage);
    }*/
/*
    @FXML
    public void handleMessages(ActionEvent actionEvent) throws UserRepoValidator, FriendshipRepoValidator, MessageRepoValidator {
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("message-view.fxml"));

        //FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("app-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 900, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Spicee");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        currentStage.close();

        MessageController messageController = fxmlLoader.getController();
        messageController.setService(appService, stage);
    }*/
/*
    @FXML
    public void handleReports(ActionEvent actionEvent) throws UserRepoValidator, FriendshipRepoValidator, MessageRepoValidator {
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("report-view.fxml"));

        //FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("app-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 900, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Spicee");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        currentStage.close();

        AppController appController = fxmlLoader.getController();
        appController.setService(appService, stage);
    }*/

    @FXML
    public void handleExit(ActionEvent actionEvent){
        currentStage.close();
    }
/*
    @Override
    public void update() {
        try {
            initModel();
        } catch (UserRepoValidator e) {
            e.printStackTrace();
        }
        try {
            initMatches();
        } catch (UserRepoValidator e) {
            e.printStackTrace();
        } catch (FriendshipRepoValidator e) {
            e.printStackTrace();
        }
        try {
            initModel1();
        } catch (FriendshipRepoValidator e) {
            e.printStackTrace();
        } catch (UserRepoValidator e) {
            e.printStackTrace();
        }
        try {
            initModel2();
        } catch (FriendshipRepoValidator e) {
            e.printStackTrace();
        } catch (UserRepoValidator e) {
            e.printStackTrace();
        }
    }
*/
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
}