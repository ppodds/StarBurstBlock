package org.ppodds.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    private HBox handler;

    private Tetris game;
    private boolean gameStarted = false;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gameStarted = true;
        gamePane.setOnKeyPressed(event -> {
            if (gameStarted) {
                if (game.getControlling() == null)
                    game.createNewTetromino();
                switch (event.getCode()) {
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
                if (event.isControlDown()) {
                    switch (event.getCode()) {
                        case G:
                            // You are God now
                            game.gameOver(true);
                            break;
                        case L:
                            game.damage(10, null);
                            break;
                    }
                }
            }
        });
        if (gameStarted) {
            game = Tetris.createNewGame(gamePane, hintPane, bossHpBar, logArea);
        }


    }

}
