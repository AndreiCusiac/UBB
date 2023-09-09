package com.example.filter_sem9;
import com.example.controller.NotaController;
import com.example.service.ServiceManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;



public class TestSem9 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("notaView.fxml"));

        //FXMLLoader loader = new FXMLLoader(TestSem8.class.getResource("notaView.fxml"));
        AnchorPane root=loader.load();

        NotaController ctrl=loader.getController();
        ctrl.setService(new ServiceManager());

        primaryStage.setScene(new Scene(root, 1200, 300));
        primaryStage.setTitle("Hello World");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
