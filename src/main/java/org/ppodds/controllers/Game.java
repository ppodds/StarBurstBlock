package org.ppodds.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
        game = Tetris.createNewGame(gamePane, hintPane);
        gameStarted = true;
        gamePane.setOnKeyPressed(event -> {
            if (gameStarted)
                switch (event.getCode()) {
                    case UP:
                        game.createNewTetromino();
                        break;
                    case DOWN:
                        game.controlling.moveDown();
                        break;
                    case LEFT:
                        game.controlling.moveLeft();
                        break;
                    case RIGHT:
                        game.controlling.moveRight();
                        break;
                    case SPACE:
                        game.controlling.hardDrop();
                        break;
                    case C:
                        break;
                    default:
                        break;
                }
        });
        gamePane.requestFocus();
        System.out.println(gamePane.isFocused());
    }

}
