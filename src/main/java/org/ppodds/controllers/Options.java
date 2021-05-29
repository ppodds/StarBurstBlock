package org.ppodds.controllers;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import org.ppodds.App;
import org.ppodds.core.ResourceManager;
import org.ppodds.core.Setting;

import java.net.URL;
import java.util.ResourceBundle;

public class Options implements Initializable {
    @FXML
    AnchorPane background;
    @FXML
    Slider bgmVolumnSlider;
    @FXML
    Slider seVolumnSlider;
    @FXML
    Slider mediaVolumnSlider;
    @FXML
    Label bgmVolumnLabel;
    @FXML
    Label seVolumnLabel;
    @FXML
    Label mediaVolumnLabel;
    @FXML
    Button back;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bgmVolumnSlider.setValue((int) (Setting.backgoundMusicVolumn * 100));
        seVolumnSlider.setValue((int) (Setting.soundEffectVolumn * 100));
        mediaVolumnSlider.setValue((int) (Setting.mediaVolumn * 100));
        bgmVolumnLabel.setText(String.valueOf((int) (Setting.backgoundMusicVolumn * 100)));
        seVolumnLabel.setText(String.valueOf((int) (Setting.soundEffectVolumn * 100)));
        mediaVolumnLabel.setText(String.valueOf((int) (Setting.mediaVolumn * 100)));
        bgmVolumnSlider.valueProperty().addListener((observableValue, number, t1) -> {
            bgmVolumnLabel.setText(String.valueOf((int) bgmVolumnSlider.getValue()));
            Setting.backgoundMusicVolumn = bgmVolumnSlider.getValue() / 100;
            Start.getBackgroundMusic().setVolume(Setting.backgoundMusicVolumn);
        });
        seVolumnSlider.valueProperty().addListener((observableValue, number, t1) -> {
            seVolumnLabel.setText(String.valueOf((int) seVolumnSlider.getValue()));
            Setting.soundEffectVolumn = seVolumnSlider.getValue() / 100;
        });
        mediaVolumnSlider.valueProperty().addListener((observableValue, number, t1) -> {
            mediaVolumnLabel.setText(String.valueOf((int) mediaVolumnSlider.getValue()));
            Setting.mediaVolumn = mediaVolumnSlider.getValue() / 100;
        });
        bgmVolumnSlider.setOnMouseEntered(e -> playSE());
        seVolumnSlider.setOnMouseEntered(e -> playSE());
        mediaVolumnSlider.setOnMouseEntered(e -> playSE());
        back.setOnMouseEntered(e -> playSE());
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

    private void playSE() {
        AudioClip se = new AudioClip(ResourceManager.getAudio("Switch.mp3").toString());
        se.setVolume(Setting.soundEffectVolumn);
        se.play();
    }
}
