package org.ppodds.core.game.tetromino;

import javafx.scene.layout.Pane;
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
    public boolean moveDown() {
        Pane[] checkPos = new Pane[4];
        switch (state) {
            case UP:
                if (y+1 == 19) {
                    setOnBoard();
                    return true;
                }
                for (int i=0;i<4;i++) {
                    checkPos[i] = game.getBlockInGame(x-1+i, y+1);
                    if (checkPos[i] != null) {
                        setOnBoard();
                        return true;
                    }
                }
                down();
                return false;
            case DOWN:
                if (y+2 == 19) {
                    setOnBoard();
                    return true;
                }
                for (int i=0;i<4;i++) {
                    checkPos[i] = game.getBlockInGame(x-1+i, y+2);
                    if (checkPos[i] != null) {
                        setOnBoard();
                        return true;
                    }
                }
                down();
                return false;
            case LEFT:
                if (y+3 == 19) {
                    setOnBoard();
                    return true;
                }
                checkPos[1] = game.getBlockInGame(x, y+3);
                if (checkPos[1] == null) {
                    down();
                    return false;
                }
                setOnBoard();
                return true;
            case RIGHT:
                if (y+3 == 19) {
                    setOnBoard();
                    return true;
                }
                checkPos[2] = game.getBlockInGame(x+1, y+3);
                if (checkPos[2] == null) {
                    down();
                    return false;
                }
                setOnBoard();
                return true;
            default:
                return false;
        }
    }



    @Override
    public void moveLeft() {

    }

    @Override
    public void moveRight() {

    }

    @Override
    protected void setOnBoard() {
        switch (state) {
            case UP:
                for (int i=0;i<4;i++)
                    game.setBlockInGame(x-1+i, y, blocks[i]);
                break;
            case DOWN:
                for (int i=0;i<4;i++)
                    game.setBlockInGame(x-1+i, y+1, blocks[i]);
                break;
            case LEFT:
                for (int i=0;i<4;i++)
                    game.setBlockInGame(x, y-1+i, blocks[i]);
                break;
            case RIGHT:
                for (int i=0;i<4;i++)
                    game.setBlockInGame(x+1, y-1+i, blocks[i]);
                break;
        }
    }
}
