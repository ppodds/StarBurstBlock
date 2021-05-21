package org.ppodds.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import org.ppodds.core.game.Tetris;

import java.net.URL;
import java.util.ResourceBundle;

public class Game implements Initializable, EventHandler<KeyEvent> {
    @FXML
    private ProgressBar bossHpBar;
    @FXML
    private GridPane gamePane;
    @FXML
    private GridPane hintPane;
    @FXML
    private TextArea logArea;

    private Tetris game;
    private boolean gameStarted = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gamePane.requestFocus();
        game = Tetris.createNewGame(gamePane, hintPane);
    }

    @Override
    public void handle(KeyEvent event) {
        if (gameStarted)
            switch (event.getCode()) {
                case UP:
                    break;
                case DOWN:
                    break;
                case LEFT:
                    break;
                case RIGHT:
                    break;
                case SPACE:
                    break;
                case C:
                    break;
                default:
                    break;
            }
    }
}
