package org.ppodds.controllers;

import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.ppodds.App;
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
    Label bgmVolumnLabel;
    @FXML
    Label seVolumnLabel;
    @FXML
    Button back;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bgmVolumnSlider.setValue((int)(Setting.backgoundMusicVolumn * 100));
        seVolumnSlider.setValue((int)(Setting.soundEffectVolumn * 100));
        bgmVolumnLabel.setText(String.valueOf((int)(Setting.backgoundMusicVolumn * 100)));
        seVolumnLabel.setText(String.valueOf((int)(Setting.soundEffectVolumn * 100)));
        bgmVolumnSlider.valueProperty().addListener((observableValue, number, t1) -> {
            bgmVolumnLabel.setText(String.valueOf((int)bgmVolumnSlider.getValue()));
            Setting.backgoundMusicVolumn = bgmVolumnSlider.getValue()/100;
            Start.getBackgroundMusic().setVolume(Setting.backgoundMusicVolumn);
        });
        seVolumnSlider.valueProperty().addListener((observableValue, number, t1) -> {
            seVolumnLabel.setText(String.valueOf((int)seVolumnSlider.getValue()));
            Setting.soundEffectVolumn = seVolumnSlider.getValue()/100;
        });
        back.setOnAction((e) -> {
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), background);
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);
            fadeTransition.setOnFinished(event -> App.setRoot("Start"));
            fadeTransition.play();
        });
    }
}
