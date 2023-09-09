package com.example.baschetgui;

import com.example.baschetgui.cs.models.Loc;
import com.example.baschetgui.cs.repositories.*;
import com.example.baschetgui.cs.services.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, InterruptedException {

        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }

        SpectatorRepository spectatorDbRepository = new SpectatorDbRepository(props);
        SpectatorRepository spectatorDbRepositoryH = new SpectatorHbRepository(props);
        SpectatorService spectatorService = new SpectatorService(spectatorDbRepositoryH);

        RegizorRepository regizorDbRepository = new RegizorDbRepository(props);
        RegizorRepository regizorDbRepositoryH = new RegizorHbRepository(props);
        RegizorService regizorService = new RegizorService(regizorDbRepositoryH);

        SpectacolRepository spectacolDbRepositoryH = new SpectacolHbRepository(props);
        SpectacolService spectacolService = new SpectacolService(spectacolDbRepositoryH);

        LocRepository locDbRepositoryH = new LocHbRepository(props);
        LocService locService = new LocService(locDbRepositoryH);

        /*int j;
        int nr = 1;

        for (j = 1; j <= 8; j++) {
            String name = "seat1";
            String seat = name + Integer.toString(j);

            Loc loc = new Loc(seat, false, false);
            loc.setId(Integer.toString(nr));
            locDbRepositoryH.save(loc);
            nr++;
        }
        for (j = 1; j <= 16; j++) {
            String name = "seat2";

            String seat = name + Integer.toString(j);

            Loc loc = new Loc(seat, false, false);
            loc.setId(Integer.toString(nr));
            locDbRepositoryH.save(loc);
            nr++;
        }
        for (j = 1; j <= 16; j++) {
            String name = "seat3";

            String seat = name + Integer.toString(j);

            Loc loc = new Loc(seat, false, false);
            loc.setId(Integer.toString(nr));
            locDbRepositoryH.save(loc);
            nr++;
        }
        for (j = 1; j <= 14; j++) {
            String name = "seat4";

            String seat = name + Integer.toString(j);

            Loc loc = new Loc(seat, false, false);
            loc.setId(Integer.toString(nr));
            locDbRepositoryH.save(loc);
            nr++;
        }*/

        AppService appService = new AppService(regizorService, spectatorService, spectacolService, locService);

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("LoginWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Log in");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        LoginController loginController = fxmlLoader.getController();
        loginController.setService(appService, stage);

        // C:\Users\Andrei\Desktop\UBB\Semestru 4\Ingineria sistemelor soft\Laboratoare\Lab 3
    }

    public static void main(String[] args) {
        launch();
    }
}