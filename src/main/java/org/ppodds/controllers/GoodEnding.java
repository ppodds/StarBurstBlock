package org.ppodds.controllers;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import javafx.util.Pair;
import org.ppodds.App;
import org.ppodds.core.ResourceManager;
import org.ppodds.core.game.story.StoryData;

import java.net.URL;
import java.util.ResourceBundle;

public class GoodEnding implements Initializable {
    @FXML
    private ImageView storyImage;
    @FXML
    private MediaView mediaView;
    @FXML
    private Pane handler;
    @FXML
    private Label storyText;
    private StoryData story;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Media ending = new Media(ResourceManager.getMedia("GoodEnding.mp4").toString());
        MediaPlayer mediaPlayer = new MediaPlayer(ending);
        mediaPlayer.setAutoPlay(true);
        mediaView.setMediaPlayer(mediaPlayer);
        Platform.runLater(() -> handler.requestFocus());
        handler.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE)
                mediaPlayer.setStopTime(mediaPlayer.getCurrentTime().add(Duration.millis(200)));
        });
        mediaPlayer.setOnEndOfMedia(() -> {
            storyImage.setVisible(true);
            storyText.setVisible(true);
            mediaView.setVisible(false);
            story = ResourceManager.getStory("GoodEnding");
            nextStep();
            storyText.setOnMouseClicked((e) -> nextStep());
            storyImage.setOnMouseClicked((e) -> nextStep());
            mediaView.setDisable(true);
        });
    }

    private void nextStep() {
        if (story.hasNext()) {
            Pair<Image, String> data = story.getNext();
            storyImage.setImage(data.getKey());
            storyText.setText(data.getValue());
        } else {
            App.setRoot("Thank");
        }
    }

}
