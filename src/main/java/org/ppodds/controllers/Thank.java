package org.ppodds.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import org.ppodds.App;
import org.ppodds.core.ResourceManager;
import org.ppodds.core.Setting;

import java.net.URL;
import java.util.ResourceBundle;

public class Thank implements Initializable {
    @FXML
    ImageView yesButton;
    @FXML
    ImageView noButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        yesButton.setOnMouseEntered(e -> {
            playSE("Switch.mp3");
        });
        yesButton.setOnMouseClicked((e) -> {
            playSE("Confirm.mp3");
            App.setRoot("Start");
        });
        noButton.setOnMouseEntered(e -> {
            playSE("Switch.mp3");
        });
        noButton.setOnMouseClicked(e -> {
            playSE("Confirm.mp3");
        });
    }

    private void playSE(String s) {
        AudioClip se = new AudioClip(ResourceManager.getAudio(s).toString());
        se.setVolume(Setting.soundEffectVolumn);
        se.play();
    }
}
