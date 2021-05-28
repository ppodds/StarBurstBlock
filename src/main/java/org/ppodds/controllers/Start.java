package org.ppodds.controllers;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import org.ppodds.core.transition.AudioFadeOut;

import java.net.URL;
import java.util.ResourceBundle;

public class Start implements Initializable {
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

    private static MediaPlayer backgroundMusic;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (backgroundMusic == null) {
            backgroundMusic = new MediaPlayer(new Media(ResourceManager.getAudio("Menu.mp3").toString()));
            backgroundMusic.setCycleCount(AudioClip.INDEFINITE);
            backgroundMusic.setVolume(Setting.backgoundMusicVolumn);
            backgroundMusic.play();
        }
        gameStart.setOnAction(event -> {
            backgroundMusic.stop();
            gameStart.setVisible(false);
            help.setVisible(false);
            options.setVisible(false);
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
//            AudioFadeOut.play(backgroundMusic, Duration.seconds(2));
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), mainPane);
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);
            fadeTransition.setOnFinished(event -> App.setRoot("Help"));
            fadeTransition.play();
        });
        options.setOnAction((e) -> {
//            AudioFadeOut.play(backgroundMusic, Duration.seconds(2));
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), mainPane);
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);
            fadeTransition.setOnFinished(event -> App.setRoot("Options"));
            fadeTransition.play();
        });
    }

}
