package org.ppodds.controllers;

import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import org.ppodds.App;
import org.ppodds.core.ResourceManager;

import java.net.URL;
import java.util.ResourceBundle;

public class GoodEnding implements Initializable {
    @FXML
    private ImageView imageView;
    @FXML
    private MediaView mediaView;
    @FXML
    private Pane handler;
    @FXML
    private Label storyText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Media ending = new Media(ResourceManager.getMedia("Ending.mp4").toString());
        MediaPlayer mediaPlayer = new MediaPlayer(ending);
        mediaPlayer.setAutoPlay(true);
        mediaView.setMediaPlayer(mediaPlayer);
        Platform.runLater(() -> handler.requestFocus());
        handler.setOnKeyPressed(e -> {
            System.out.println("A");
            if (e.getCode() == KeyCode.ESCAPE)
                mediaPlayer.setStopTime(mediaPlayer.getCurrentTime().add(Duration.millis(200)));
        });
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaView.setVisible(false);
            imageView.setVisible(true);
            mediaView.setDisable(true);
            storyText.setVisible(true);
        });
    }

}
