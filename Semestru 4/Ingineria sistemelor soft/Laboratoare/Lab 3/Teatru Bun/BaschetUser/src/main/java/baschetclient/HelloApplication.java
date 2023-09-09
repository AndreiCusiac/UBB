package baschetclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import protobuffprotocol.ProtoProxy;
import rpcprotocol.RpcProxy;
import services.IService;

import java.io.IOException;
import java.util.Properties;

public class HelloApplication extends Application {

    private Stage primaryStage;

    private static int defaultChatPort = 55555;
    private static String defaultServer = "localhost";

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {


        Properties props = new Properties();
        try {
//            props.load(new FileReader("bd.config"));
            props.load(HelloApplication.class.getResourceAsStream("/chatclient.properties"));
            System.out.println("Client properties set. ");
            props.list(System.out);
        } catch (IOException e) {
            System.out.println("Cannot find chatclient.properties "+e);
        }

        String serverIP = props.getProperty("chat.server.host", defaultServer);
        int serverPort = defaultChatPort;

        try {
            serverPort = Integer.parseInt(props.getProperty("chat.server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultChatPort);
        }

        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        IService server = new RpcProxy(serverIP, serverPort);
//        IService server = new ProtoProxy(serverIP, serverPort);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 530, 350);
        stage.setTitle("Log in");
        stage.setScene(scene);
        stage.setResizable(false);


        LoginController loginController = fxmlLoader.getController();
        loginController.setService(server, stage);

//        FXMLLoader fxmlLoaderHome = new FXMLLoader();

        FXMLLoader fxmlLoaderHome = new FXMLLoader(HelloApplication.class.getResource("home-view.fxml"));
//        fxmlLoaderHome.setLocation(getClass().getResource("home-view.fxml"));

        Parent croot = fxmlLoaderHome.load();

        HomeController homeController = fxmlLoaderHome.getController();

        homeController.setAppService(server);
        homeController.setMainParent(croot);

        loginController.setHomeController(homeController);
        loginController.setParent(croot);

        stage.show();
//        HomeController homeController =
//        LoginController loginController = fxmlLoader.getController();
//        loginController.setService(server, stage);
//        System.out.println( getClass().getClassLoader().getName());
//        FXMLLoader loader = new FXMLLoader(
//                getClass().getClassLoader().getResource("login-view.fxml"));
//        System.out.println("dupa FXMLoader");
//        Parent root=loader.load();
//
//        System.out.println("dupa load");
//
//        LoginController loginController = loader.<LoginController>getController();
//        loginController.setServer(server);


        /*FXMLLoader cloader = new FXMLLoader(
                getClass().getClassLoader().getResource("home-view.fxml"));
        Parent croot=cloader.load();


        HomeController homeController = cloader.<HomeController>getController();
        homeController.setAppService(server);

        loginController.setHomeController(homeController);
        loginController.setParent(croot);
        loginController.setCurrentStage(stage);

        stage.setTitle("Log in");
        stage.setScene(new Scene(root, 530, 350));
        stage.show();*/
    }

    public static void main(String[] args) {
        launch();
    }
}