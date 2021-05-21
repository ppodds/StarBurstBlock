package org.ppodds.core.game.tetromino;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.ppodds.core.ResourceManager;
import org.ppodds.core.game.SpinDirection;
import org.ppodds.core.game.Tetris;
import org.ppodds.core.game.TetrominoState;

import java.util.Random;

public abstract class Tetromino extends Pane {
    protected Pane[] blocks = new Pane[4];
    protected TetrominoState state;
    protected int x;
    protected int y;
    protected Tetris game;

    public Tetromino(TetrominoState state, Tetris game) {
        this.state = state;
        this.game = game;
        for (int i=0;i<blocks.length;i++) {
            blocks[i] = new Pane();
            blocks[i].getStylesheets().add(ResourceManager.getStyleSheet("Block").toString());
        }
    }

    public abstract void spin(SpinDirection direction);
    public abstract void moveDown();
    public abstract void hardDrop();
    public abstract void moveLeft();
    public abstract void moveRight();

    protected abstract void setOnBoard();

    public static Tetromino generateRandomTetromino(Tetris game) {
        Random random = new Random();
        char[] types = {'I', 'J', 'L', 'O', 'S', 'T', 'Z'};
        TetrominoState[] states = {TetrominoState.UP, TetrominoState.DOWN, TetrominoState.LEFT, TetrominoState.RIGHT};
        TetrominoState randomState = states[random.nextInt(4)];
        switch (types[random.nextInt(7)]) {
            case 'I':
                return new BlockI(randomState, game);
            case 'J':
                return new BlockJ(randomState, game);
            case 'L':
                return new BlockL(randomState, game);
            case 'O':
                return new BlockO(randomState, game);
            case 'S':
                return new BlockS(randomState, game);
            case 'T':
                return new BlockT(randomState, game);
            case 'Z':
                return new BlockZ(randomState, game);
        }
        return null;
    }
}


