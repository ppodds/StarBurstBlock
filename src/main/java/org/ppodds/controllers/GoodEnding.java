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
    private FadeTransition ft;

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
            // TODO 這裡的動畫怪怪的 還有label的黑條太粗
            mediaView.setVisible(false);
            story = ResourceManager.getStory("GoodEnding");
            storyImage.setImage(ResourceManager.getPlainImage("White"));
            ft = new FadeTransition(Duration.seconds(1), storyImage);
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.setOnFinished(e -> {
                storyImage.setImage(ResourceManager.getPlainImage("Black"));
                storyText.setVisible(true);
                nextStep(1);
            });
            ft.play();
            storyImage.setOnKeyPressed(e -> {
                if (e.isControlDown() && e.getCode() == KeyCode.Q) {
                    if (ft.getStatus() == Animation.Status.RUNNING)
                        ft.jumpTo("end");
                    else
                        nextStep(1);
                }
            });
            storyImage.setVisible(true);
            storyText.setVisible(true);
            storyText.setOnMouseClicked(this::handle);
            storyImage.setOnMouseClicked(this::handle);
            mediaView.setDisable(true);
        });
    }
    private void handle(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            if (ft.getStatus() == Animation.Status.RUNNING)
                ft.jumpTo("end");
            else
                nextStep(1);
        }
    }

    private void nextStep(float fadeDuration) {
        if (story.hasNext()) {
            ft = new FadeTransition(Duration.seconds(fadeDuration), storyImage);
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.setOnFinished(e -> {
                Pair<Image, String> data = story.getNext();
                storyImage.setImage(data.getKey());
                storyText.setText(data.getValue());
                FadeTransition ft = new FadeTransition(Duration.seconds(fadeDuration), storyImage);
                ft.setFromValue(0);
                ft.setToValue(1);
                ft.play();
            });
            ft.play();
        } else {
            App.setRoot("Thank");
        }
    }

}
