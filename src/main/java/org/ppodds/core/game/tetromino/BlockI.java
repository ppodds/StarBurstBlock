package org.ppodds.core.game.tetromino;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.ppodds.core.ResourceManager;
import org.ppodds.core.game.SpinDirection;
import org.ppodds.core.game.Tetris;
import org.ppodds.core.game.TetrominoState;

public class BlockI extends Tetromino {

    public BlockI(TetrominoState state, Tetris game) {
        super(state, game);
        for (int i=0;i<blocks.length;i++)
            blocks[i].getStyleClass().add(".Block-I");
    }

    @Override
    public void spin(SpinDirection direction) {
        switch (direction) {
            case LEFT:
                break;
            case RIGHT:
                break;
        }
    }

    @Override
    public void moveDown() {
        Pane pane = game.getPaneInGame(x, y+1);
        if (pane != null) {
            setOnBoard();
        }
        else
            GridPane.setColumnIndex(pane, ++y);
    }

    @Override
    public void hardDrop() {

    }

    @Override
    public void moveLeft() {

    }

    @Override
    public void moveRight() {

    }

    @Override
    protected void setOnBoard() {

    }
}
