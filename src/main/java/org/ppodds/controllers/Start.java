package org.ppodds.controllers;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import org.ppodds.App;
import org.ppodds.core.ResourceManager;

import java.net.URL;
import java.util.ResourceBundle;

public class Start implements Initializable {
    @FXML
    private Button gameStart;
    @FXML
    private Button help;
    @FXML
    private MediaView mediaView;
    @FXML
    private ImageView background;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AudioClip backgroundMusic = new AudioClip(ResourceManager.getAudio("Menu.mp3").toString());
        backgroundMusic.setCycleCount(AudioClip.INDEFINITE);
        backgroundMusic.setVolume(0.3);
        backgroundMusic.play();
        gameStart.setOnAction(event -> {
            backgroundMusic.stop();
            gameStart.setVisible(false);
            help.setVisible(false);
            Media linkstart = new Media(ResourceManager.getMedia("LinkStart.mp4").toString());
            MediaPlayer mediaPlayer = new MediaPlayer(linkstart);
            mediaPlayer.setAutoPlay(true);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaView.setVisible(true);
            mediaView.requestFocus();
            mediaView.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ESCAPE)
                    mediaPlayer.setStopTime(mediaPlayer.getCurrentTime().add(Duration.millis(200)));
            });
            mediaPlayer.setOnEndOfMedia(() -> App.setRoot("PreStory"));
        });
        help.setOnAction((e) -> {
            backgroundMusic.stop();
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), background.getParent());
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);
            fadeTransition.setOnFinished(event -> App.setRoot("Help"));
            fadeTransition.play();
        });
    }

}
