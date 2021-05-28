package org.ppodds.controllers;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import javafx.util.Pair;
import org.ppodds.App;
import org.ppodds.core.ResourceManager;
import org.ppodds.core.Setting;
import org.ppodds.core.game.story.StoryData;

import java.net.URL;
import java.util.ResourceBundle;

public class BadEnding implements Initializable, EventHandler<MouseEvent> {
    @FXML
    private ImageView storyImage;
    @FXML
    private Label storyText;

    private StoryData story;
    private FadeTransition ft;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        story = ResourceManager.getStory("BadEnding");
        storyImage.setImage(ResourceManager.getPlainImage("Black"));
        ft = new FadeTransition(Duration.seconds(1), storyImage);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.setOnFinished(e -> {
            storyImage.setImage(ResourceManager.getPlainImage("Black"));
            storyText.setVisible(true);
            nextStep(1);
            AudioClip die = new AudioClip(ResourceManager.getAudio("Die.mp3").toString());
            die.setVolume(Setting.soundEffectVolumn);
            die.play();
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
    }

    @Override
    public void handle(MouseEvent event) {
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
            App.setRoot("Start");
        }
    }
}
