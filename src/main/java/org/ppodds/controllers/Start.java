package org.ppodds.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.application.Application;
import org.ppodds.App;
import org.ppodds.core.ResourceManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Start implements Initializable {
    @FXML
    Button gameStart;
    @FXML
    MediaView mediaView;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gameStart.setOnAction(event -> {
            Media linkstart = new Media(ResourceManager.getMedia("LinkStart.mp4").toString());
            MediaPlayer mediaPlayer = new MediaPlayer(linkstart);
            mediaPlayer.setAutoPlay(true);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaView.setVisible(true);
            mediaPlayer.setOnEndOfMedia(() -> {
                try {
                    App.setRoot("PreStory");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
        });
    }

}
