package org.ppodds.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import org.ppodds.App;

import java.net.URL;
import java.util.ResourceBundle;

public class Thank implements Initializable {
    @FXML
    ImageView yesButton;
    @FXML
    ImageView noButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        yesButton.setOnMouseClicked((e) -> App.setRoot("Start"));
    }
}
