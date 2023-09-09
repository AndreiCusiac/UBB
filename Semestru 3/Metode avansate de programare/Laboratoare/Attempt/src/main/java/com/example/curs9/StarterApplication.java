package com.example.curs9;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.curs9.ubbcluj.map.MyRepos.FriendshipDbRepo;
import com.example.curs9.ubbcluj.map.MyRepos.FriendshipRepo;
import com.example.curs9.ubbcluj.map.MyRepos.UserDbRepo;
import com.example.curs9.ubbcluj.map.MyRepos.UserRepo;
import com.example.curs9.ubbcluj.map.domain.MyValidators.FriendshipRepoValidator;
import com.example.curs9.ubbcluj.map.domain.MyValidators.FriendshipValidator;
import com.example.curs9.ubbcluj.map.domain.MyValidators.UserRepoValidator;
import com.example.curs9.ubbcluj.map.domain.MyValidators.UserValidator;
import com.example.curs9.ubbcluj.map.service.NetworkService;

import java.io.IOException;

public class StarterApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, UserRepoValidator, FriendshipRepoValidator {
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("Spicee");
        stage.setScene(scene);
        stage.show();

        /*
        String url = "jdbc:postgresql://localhost:5432/Andreii";
        String user = "postgres";
        String pass = "Trafic11";
        String usersTableName = "\"users\"";
        String friendshipsTableName = "\"friendships\"";
        String messagesTableName= "\"message\"";
        */

        /*
        String url = "jdbc:postgresql://localhost:5432/lab4";
        String user = "postgres";
        String pass = "1234";
        String usersTableName = "\"Users\"";
        String friendshipsTableName = "\"Friendships\"";
        */

        String url = "jdbc:postgresql://localhost:5432/lab4";
        String user = "postgres";
        String pass = "1234";
        String usersTableName = "\"Users\"";
        String friendshipsTableName = "\"Friendships\"";

        UserValidator userValidator = new UserValidator();
        FriendshipValidator friendshipValidator = new FriendshipValidator();
        UserRepo userRepo = new UserRepo();

        UserDbRepo userDbRepo = null;
        try {
            userDbRepo = new UserDbRepo(url, user, pass, usersTableName);
        } catch (UserRepoValidator e) {
            System.out.println(e.getMessage());
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        FriendshipRepo friendshipRepo = new FriendshipRepo();

        FriendshipDbRepo friendshipDbRepo = null;
        try {
            friendshipDbRepo = new FriendshipDbRepo(url, user, pass, friendshipsTableName);
        } catch (FriendshipRepoValidator e) {
            System.out.println(e.getMessage());
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }

        NetworkService networkService = new NetworkService(userValidator, friendshipValidator, userDbRepo, friendshipDbRepo);
        networkService.getUsers().forEach(System.out::println);
        //networkService.getFriendships().forEach(System.out::println);

        LoginController loginController = fxmlLoader.getController();
        loginController.setService(networkService, stage);
    }

    public static void main(String[] args) {
        launch();
    }

    /*
    <?xml version="1.0" encoding="UTF-8"?>

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Button?>
<?import javafx.scene.control.CheckBox?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.VBox?>

<VBox alignment="CENTER" prefHeight="139.0" prefWidth="152.0" spacing="20.0" xmlns:fx="http://javafx.com/fxml/1" xmlns="http://javafx.com/javafx/17" fx:controller="com.example.curs9.HelloController">
    <padding>
        <Insets bottom="20.0" left="20.0" right="20.0" top="20.0" />
    </padding>

    <Label fx:id="welcomeText" />
   <CheckBox fx:id="checkBoxActive" mnemonicParsing="false" onAction="#onActionActive" text="CheckBoxActive" />
    <Button onAction="#onHelloButtonClick" text="Hello!" />
</VBox>


     */
}