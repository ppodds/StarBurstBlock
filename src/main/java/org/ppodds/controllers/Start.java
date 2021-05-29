package org.ppodds.controllers;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import org.ppodds.App;
import org.ppodds.core.ResourceManager;
import org.ppodds.core.Setting;

import java.net.URL;
import java.util.ResourceBundle;

public class Start implements Initializable {
    private static MediaPlayer backgroundMusic;
    @FXML
    private Button gameStart;
    @FXML
    private Button help;
    @FXML
    private Button options;
    @FXML
    private MediaView mediaView;
    @FXML
    private Pane mainPane;

    public static MediaPlayer getBackgroundMusic() {
        return backgroundMusic;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (backgroundMusic == null) {
            backgroundMusic = new MediaPlayer(new Media(ResourceManager.getAudio("Menu.mp3").toString()));
            backgroundMusic.setCycleCount(AudioClip.INDEFINITE);
            backgroundMusic.setVolume(Setting.backgoundMusicVolumn);
            backgroundMusic.play();
        } else if (backgroundMusic.getStatus() == MediaPlayer.Status.STOPPED) {
            backgroundMusic.setVolume(Setting.backgoundMusicVolumn);
            backgroundMusic.play();
        }
        gameStart.setOnMouseEntered(e -> playSE("Switch.mp3"));
        help.setOnMouseEntered(e -> playSE("Switch.mp3"));
        options.setOnMouseEntered(e -> playSE("Switch.mp3"));
        gameStart.setOnAction(event -> {
            playSE("Confirm.mp3");
            backgroundMusic.stop();
            gameStart.setVisible(false);
            help.setVisible(false);
            options.setVisible(false);
            Media linkstart = new Media(ResourceManager.getMedia("LinkStart.mp4").toString());
            MediaPlayer mediaPlayer = new MediaPlayer(linkstart);
            mediaPlayer.setVolume(Setting.mediaVolumn);
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
            playSE("Confirm.mp3");
            help.setOnAction(null);
            options.setOnAction(null);
            gameStart.setOnAction(null);
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), mainPane);
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);
            fadeTransition.setOnFinished(event -> App.setRoot("Help"));
            fadeTransition.play();
        });
        options.setOnAction((e) -> {
            playSE("Confirm.mp3");
            options.setOnAction(null);
            help.setOnAction(null);
            gameStart.setOnAction(null);
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), mainPane);
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);
            fadeTransition.setOnFinished(event -> App.setRoot("Options"));
            fadeTransition.play();
        });
    }

    private void playSE(String s) {
        AudioClip se = new AudioClip(ResourceManager.getAudio(s).toString());
        se.setVolume(Setting.soundEffectVolumn);
        se.play();
    }

}
