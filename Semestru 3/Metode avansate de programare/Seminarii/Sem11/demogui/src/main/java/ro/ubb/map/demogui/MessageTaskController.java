

package ro.ubb.map.demogui;




import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import ro.ubb.map.demogui.controller.EditMessageTaskController;
import ro.ubb.map.demogui.controller.MessageAlert;
import ro.ubb.map.demogui.domain.MessageTask;
import ro.ubb.map.demogui.services.MessageTaskService;
import ro.ubb.map.demogui.utils.events.MessageTaskChangeEvent;
import ro.ubb.map.demogui.utils.observer.Observer;


import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MessageTaskController implements Observer<MessageTaskChangeEvent> {
    MessageTaskService service;
    ObservableList<MessageTask> model = FXCollections.observableArrayList();


    @FXML
    TableView<MessageTask> tableView;
    @FXML
    TableColumn<MessageTask,String> tableColumnDesc;
    @FXML
    TableColumn<MessageTask,String> tableColumnFrom;
    @FXML
    TableColumn<MessageTask,String> tableColumnTo;
    @FXML
    TableColumn<MessageTask,String> tableColumnData;

    public void setMessageTaskService(MessageTaskService messageTaskService) {
        service = messageTaskService;
        service.addObserver(this);
        initModel();
    }

    @FXML
    public void initialize() {
        // TODO
        tableColumnDesc.setCellValueFactory(new PropertyValueFactory<MessageTask, String>("description"));
        tableColumnFrom.setCellValueFactory(new PropertyValueFactory<MessageTask, String>("from"));
        tableColumnTo.setCellValueFactory(new PropertyValueFactory<MessageTask, String>("to"));
        tableColumnData.setCellValueFactory(new PropertyValueFactory<MessageTask, String>("date"));
        tableView.setItems(model);
    }

    private void initModel() {
        Iterable<MessageTask> messages = service.getAll();
        List<MessageTask> messageTaskList = StreamSupport.stream(messages.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(messageTaskList);

    }

    @Override
    public void update(MessageTaskChangeEvent messageTaskChangeEvent) {
        initModel();
    }

    public void handleDeleteMessage(ActionEvent actionEvent) {
        // TODO

        MessageTask selectedMessage = tableView.getSelectionModel().getSelectedItem();

        if (selectedMessage != null) {
            service.deleteMessageTask(selectedMessage);
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Info stergere", "Stergerea s-a realizat cu succes!");
        }
        else {
            MessageAlert.showErrorMessage(null, "Nu exista un mesaj selectat!");
        }
    }


    @FXML
    public void handleUpdateMessage(ActionEvent ev) {
        // TODO
        MessageTask selectedMessage = tableView.getSelectionModel().getSelectedItem();

        if (selectedMessage != null) {
            showMessageTaskEditDialog(selectedMessage);
        }
        else {
            MessageAlert.showErrorMessage(null, "Nu exista un mesaj selectat!");
        }
    }

    @FXML
    public void handleAddMessage(ActionEvent ev) {
        // TODO
        showMessageTaskEditDialog(null);
    }

    public void showMessageTaskEditDialog(MessageTask messageTask) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/editmessagetask-view.fxml"));

            AnchorPane root = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editz Message");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            EditMessageTaskController editMessageViewController = loader.getController();
            editMessageViewController.setService(service, dialogStage, messageTask);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
