package org.ppodds.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    private MediaView mediaView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AudioClip backgroundMusic = new AudioClip(ResourceManager.getAudio("Menu.mp3").toString());
        backgroundMusic.setCycleCount(AudioClip.INDEFINITE);
        backgroundMusic.setVolume(0.8);
        backgroundMusic.play();
        gameStart.setOnAction(event -> {
            backgroundMusic.stop();
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
    }

}
