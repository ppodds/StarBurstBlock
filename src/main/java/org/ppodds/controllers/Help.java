package org.ppodds.controllers;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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

public class Help implements Initializable {
    @FXML
    private Button back;
    @FXML
    private AnchorPane background;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        back.setOnAction((e) -> {
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), background);
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);
            fadeTransition.setOnFinished(event -> App.setRoot("Start"));
            fadeTransition.play();
        });
    }

}
