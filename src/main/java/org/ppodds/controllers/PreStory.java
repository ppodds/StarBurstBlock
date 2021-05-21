package org.ppodds.controllers;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javafx.util.Pair;
import org.ppodds.App;
import org.ppodds.core.ResourceManager;
import org.ppodds.core.StoryData;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PreStory implements Initializable, EventHandler<MouseEvent> {
    @FXML
    ImageView storyImage;
    @FXML
    Label storyText;

    private StoryData story;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        story = ResourceManager.getStory("PreStory");
        storyImage.setImage(ResourceManager.getPlainImage("White"));
        FadeTransition ft = new FadeTransition(Duration.seconds(3), storyImage);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                storyImage.setImage(ResourceManager.getPlainImage("Black"));
                storyText.setVisible(true);
                nextStep(1);
            }
        });
        ft.play();
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            nextStep(1);
        }
    }

    private void nextStep(float fadeDuration) {
        if (story.hasNext()) {
            FadeTransition ft = new FadeTransition(Duration.seconds(fadeDuration), storyImage);
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    Pair<Image, String> data = story.getNext();
                    storyImage.setImage(data.getKey());
                    storyText.setText(data.getValue());
                    FadeTransition ft = new FadeTransition(Duration.seconds(fadeDuration), storyImage);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.play();
                }
            });
            ft.play();
        }
        else {
            try {
                App.setRoot("Game");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
