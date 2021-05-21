package org.ppodds.core.game.tetromino;

import org.ppodds.core.game.SpinDirection;
import org.ppodds.core.game.Tetris;
import org.ppodds.core.game.TetrominoState;

public class BlockL extends Tetromino{
    public BlockL(TetrominoState state, Tetris game) {
        super(state, game);
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
    public boolean moveDown() {

        return false;
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
