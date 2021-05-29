package org.ppodds.controllers;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import org.ppodds.App;
import org.ppodds.core.ResourceManager;
import org.ppodds.core.Setting;

import java.net.URL;
import java.util.ResourceBundle;

public class Help implements Initializable {
    @FXML
    private Button back;
    @FXML
    private AnchorPane background;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        back.setOnMouseEntered(e -> {
            AudioClip se = new AudioClip(ResourceManager.getAudio("Switch.mp3").toString());
            se.setVolume(Setting.soundEffectVolumn);
            se.play();
        });
        back.setOnAction((e) -> {
            AudioClip se = new AudioClip(ResourceManager.getAudio("Confirm.mp3").toString());
            se.setVolume(Setting.soundEffectVolumn);
            se.play();
            back.setOnAction(null);
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), background);
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);
            fadeTransition.setOnFinished(event -> App.setRoot("Start"));
            fadeTransition.play();
        });
    }

}
