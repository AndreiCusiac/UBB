package com.example.curs9;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private CheckBox checkBoxActive;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void onActionActive(ActionEvent actionEvent) {
        if (checkBoxActive.isSelected()) {
            welcomeText.setText("Popescu este activ!");
        }
        else
        {
            welcomeText.setText("Popescu nu este activ!");
        }
    }
}