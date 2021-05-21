package org.ppodds.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Game implements Initializable {
    @FXML
    ProgressBar bossHpBar;
    @FXML
    GridPane gamePane;
    @FXML
    GridPane hintPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
