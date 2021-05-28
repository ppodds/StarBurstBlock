package org.ppodds.controllers;

import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import org.ppodds.core.game.tetromino.SpinDirection;
import org.ppodds.core.game.Tetris;

import java.net.URL;
import java.util.ResourceBundle;

public class Game implements Initializable {
    @FXML
    private ProgressBar bossHpBar;
    @FXML
    private GridPane gamePane;
    @FXML
    private GridPane hintPane;
    @FXML
    private TextArea logArea;
    @FXML
    private HBox mainPane;
    @FXML
    private AnchorPane background;
    @FXML
    private Group alert;
    @FXML
    private ImageView yesButton;

    private Tetris game;
    private boolean gameStarted = false;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), background);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setOnFinished(event -> {
            yesButton.setOnMouseClicked((e) -> {
                gameStarted = true;
                mainPane.setOpacity(1);
                alert.setVisible(false);
                game = Tetris.createNewGame(gamePane, hintPane, bossHpBar, logArea);
            });
            gamePane.setOnKeyPressed(e -> {
                if (gameStarted && !game.isPaused()) {
                    if (game.getControlling() == null)
                        game.createNewTetromino();
                    switch (e.getCode()) {
                        case UP:
                        case X:
                            game.getControlling().spin(SpinDirection.CLOCKWISE);
                            break;
                        case Z:
                            game.getControlling().spin(SpinDirection.COUNTERCLOCKWISE);
                            break;
                        case DOWN:
                            game.getControlling().moveDown();
                            break;
                        case LEFT:
                            game.getControlling().moveLeft();
                            break;
                        case RIGHT:
                            game.getControlling().moveRight();
                            break;
                        case SPACE:
                            game.getControlling().hardDrop();
                            break;
                        case C:
                            game.holdCurrentTetromino();
                            break;
                        default:
                            break;
                    }
                    // Debug
                    if (e.isControlDown()) {
                        switch (e.getCode()) {
                            case G:
                                // You are God now
                                game.gameOver(true);
                                break;
                            case L:
                                game.getBoss().damage(10, null);
                                break;
                        }
                    }
                }
            });
        });
        fadeTransition.play();
        // 避免失去focus
        gamePane.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            gamePane.requestFocus();
        });
    }
}
