package org.ppodds.core.game;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.ppodds.core.game.tetromino.Tetromino;

public class Tetris {
    private GridPane gamePane;
    private GridPane hintPane;
    private Tetromino controlling;
    private Tetromino holding;
    private Pane[][] board = new Pane[10][19];

    private Tetris(GridPane gamePane, GridPane hintPane) {
        this.gamePane = gamePane;
        this.hintPane = hintPane;
    }
    public static Tetris createNewGame(GridPane gamePane, GridPane hintPane) {
        return new Tetris(gamePane, hintPane);
    }
    public void createNewTetromino() {
        controlling = Tetromino.generateRandomTetromino(this);
        gamePane.getChildren().add(controlling);
    }
    public Pane getBlockInGame(int row, int column) {
        return board[row][column];
    }
    public void setBlockInGame(int row, int column, Pane block) { board[row][column] = block; }
}
