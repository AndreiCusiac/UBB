package com.example.curs9;

import com.example.curs9.ubbcluj.map.domain.MyModels.Friendship;
import com.example.curs9.ubbcluj.map.domain.MyValidators.FriendshipRepoValidator;
import com.example.curs9.ubbcluj.map.utils.MessageAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.curs9.ubbcluj.map.domain.MyModels.User;
import com.example.curs9.ubbcluj.map.domain.MyValidators.UserRepoValidator;
import com.example.curs9.ubbcluj.map.service.NetworkService;
import javafx.scene.text.Text;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AppController {

    NetworkService networkService;
    ObservableList<User> modelUsersTable = FXCollections.observableArrayList();
    ObservableList<User> modelFriendsTable = FXCollections.observableArrayList();
    ObservableList<Friendship> model1 = FXCollections.observableArrayList();

    User currentUser;
    String welcomeGreeting;

    @FXML
    TextField textFieldName;

    @FXML
    TextField textFieldFriend;

    @FXML
    Text textText;

    @FXML
    private Label welcomeText;

    @FXML
    private CheckBox checkBoxActive;

    @FXML
    TableView<User> tableView;

    @FXML
    TableView<User> tableFriends;

    @FXML
    TableView<User> tableFriendRequestsFrom;

    @FXML
    TableView<User> tableFriendRequestsTo;

    @FXML
    TableColumn<User, String> tableColumnNumeFriend;

    @FXML
    TableColumn<User, String> tableColumnPrenumeFriend;

    @FXML
    TableColumn<User, String> tableColumnEmailFriend;

    @FXML
    TableColumn<User, String> tableColumnNume;

    @FXML
    TableColumn<User, String> tableColumnPrenume;

    @FXML
    TableColumn<User, String> tableColumnEmail;

    @FXML
    TableColumn<User, String> tableColumnFrom;

    @FXML
    TableColumn<User, String> tableColumnData;

    @FXML
    TableColumn<User, String> tableColumnStatus;

    @FXML
    TableColumn<User, String> tableColumnTo;

    @FXML
    TableColumn<User, String> tableColumnData1;

    @FXML
    TableColumn<User, String> tableColumnStatus1;

    public void setService(NetworkService networkService1) throws UserRepoValidator, FriendshipRepoValidator {
        networkService = networkService1;
        networkService.addObserver(this);
        currentUser = networkService.getCurrentUser();
        initModel();
        initFriends();

        greetings();
    }

    private void greetings() {

        welcomeGreeting = "Welcome, " + currentUser.getFirstName() + "!";
        textText.setText(welcomeGreeting);
    }

    private void initModel() throws UserRepoValidator {
        modelUsersTable.setAll(getUsersList());
    }

    private void initFriends() throws UserRepoValidator, FriendshipRepoValidator {
        modelFriendsTable.setAll(getFriendsOfList(currentUser));
    }

    private List<User> getUsersList() throws UserRepoValidator {
        Iterable<User> users = networkService.getUsers();
        List<User> usersList = StreamSupport.stream(users.spliterator(), false)
                .collect(Collectors.toList());
        return usersList;
    }

    private List<User> getFriendsOfList(User currentUser1) throws UserRepoValidator, FriendshipRepoValidator {
        Iterable<User> users = networkService.getFriendsOf(currentUser1);
        List<User> usersList = StreamSupport.stream(users.spliterator(), false)
                .collect(Collectors.toList());
        return usersList;
    }

    @FXML
    public void initialize() {
        tableColumnNume.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
        tableColumnPrenume.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        tableView.setItems(modelUsersTable);

        textFieldName.textProperty().addListener(o -> {
            try {
                handleFilter(modelUsersTable);
            } catch (UserRepoValidator e) {
                e.printStackTrace();
            }
        });

        tableColumnNumeFriend.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
        tableColumnPrenumeFriend.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
        tableColumnEmailFriend.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        tableFriends.setItems(modelFriendsTable);

        textFieldFriend.textProperty().addListener(o -> {
            try {
                handleFilterFriends(modelFriendsTable);
            } catch (UserRepoValidator e) {
                e.printStackTrace();
            } catch (FriendshipRepoValidator e) {
                e.printStackTrace();
            }
        });
    }

    private void handleFilter(ObservableList<User> modelCurent) throws UserRepoValidator {
        Predicate<User> firstNameFilter = n -> n.getFirstName().contains(textFieldName.getText());
//        Predicate<User> firstNameFilter = n -> n.getFirstName().contains(textFieldName.getCharacters());
        Predicate<User> lastNameFilter = n -> n.getLastName().contains(textFieldName.getText());
//        Predicate<User> lastNameFilter = n -> n.getLastName().contains(textFieldName.getCharacters());
        Predicate<User> firstNameSmallFilter = n -> n.getFirstName().toLowerCase().contains(textFieldName.getText());
        Predicate<User> firstNameCAPSFilter = n -> n.getFirstName().toUpperCase().contains(textFieldName.getText());
        Predicate<User> lastNameSmallFilter = n -> n.getLastName().toLowerCase().contains(textFieldName.getText());
        Predicate<User> lastNameCAPSFilter = n -> n.getLastName().toUpperCase().contains(textFieldName.getText());

        Predicate<User> allPredicates = firstNameFilter.
                or(lastNameFilter).
                or(firstNameCAPSFilter).
                or(firstNameSmallFilter).
                or(lastNameSmallFilter).
                or(lastNameCAPSFilter);

        modelCurent.setAll(getUsersList()
                .stream()
                .filter(allPredicates)
                .collect(Collectors.toList()));
    }

    private void handleFilterFriends(ObservableList<User> modelCurent) throws UserRepoValidator, FriendshipRepoValidator {
        Predicate<User> firstNameFilter = n -> n.getFirstName().contains(textFieldName.getText());
//        Predicate<User> firstNameFilter = n -> n.getFirstName().contains(textFieldName.getCharacters());
        Predicate<User> lastNameFilter = n -> n.getLastName().contains(textFieldName.getText());
//        Predicate<User> lastNameFilter = n -> n.getLastName().contains(textFieldName.getCharacters());
        Predicate<User> firstNameSmallFilter = n -> n.getFirstName().toLowerCase().contains(textFieldName.getText());
        Predicate<User> firstNameCAPSFilter = n -> n.getFirstName().toUpperCase().contains(textFieldName.getText());
        Predicate<User> lastNameSmallFilter = n -> n.getLastName().toLowerCase().contains(textFieldName.getText());
        Predicate<User> lastNameCAPSFilter = n -> n.getLastName().toUpperCase().contains(textFieldName.getText());

        Predicate<User> allPredicates = firstNameFilter.
                or(lastNameFilter).
                or(firstNameCAPSFilter).
                or(firstNameSmallFilter).
                or(lastNameSmallFilter).
                or(lastNameCAPSFilter);

        modelCurent.setAll(getFriendsOfList(currentUser)
                .stream()
                .filter(allPredicates)
                .collect(Collectors.toList()));
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    public void handleAddNewFriend(ActionEvent ev) {
        // TODO
        User selectedUser = tableView.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            try {
                networkService.makeRequest(networkService.getCurrentUser().getId(), selectedUser.getId());
                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Adaugare prieten", selectedUser.getFirstName() + " " + selectedUser.getLastName() + " tocmai a primit o cerere de prietenie din partea ta!");
            } catch (Exception e) {
                MessageAlert.showErrorMessage(null, e.getMessage());
            }
        }
        else {
            MessageAlert.showErrorMessage(null, "Nu exista un user selectat!");
        }
    }

    @FXML
    public void handleRemoveFriend(ActionEvent ev) {
        // TODO
        User selectedUser = tableView.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            try {
                networkService.deleteFriendsip(currentUser.getId(), selectedUser.getId());
                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Stergere prieten", "Prietenia cu " + selectedUser.getFirstName() + " " + selectedUser.getLastName() + " tocmai a fost eliminata!");
            } catch (Exception e) {
                MessageAlert.showErrorMessage(null, e.getMessage());
            }
        }
        else {
            MessageAlert.showErrorMessage(null, "Nu exista un user selectat!");
        }
    }

    public void onActionActive(ActionEvent actionEvent) {
        if (checkBoxActive.isSelected()) {
            welcomeText.setText("Ego este activ!");
        }
        else
        {
            welcomeText.setText("Popescu nu este activ!");
        }
    }
}