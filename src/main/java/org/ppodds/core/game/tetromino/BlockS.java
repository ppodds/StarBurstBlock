package org.ppodds.core.game.tetromino;

import org.ppodds.core.game.Position;
import org.ppodds.core.game.SpinDirection;
import org.ppodds.core.game.Tetris;
import org.ppodds.core.game.TetrominoState;

public class BlockS extends Tetromino {
    public BlockS(Tetris game) {
        super(game);
        this.state = TetrominoState.UP;
        for (int i = 0; i < blocks.length; i++)
            blocks[i].getStyleClass().add("block-S");
        for (int i = 0; i < 2; i++)
            blocksPos[i] = new Position(i - 1, 0);
        for (int i = 0; i < 2; i++)
            blocksPos[i + 2] = new Position(i, -1);
        updateBlockPosition();
    }

    @Override
    public boolean spin(SpinDirection direction) {
        Position[] newBlocksPos = new Position[4];
        switch (direction) {
            case CLOCKWISE:
                switch (state) {
                    case UP:
                        for (int i = 0; i < 2; i++) {
                            newBlocksPos[i] = new Position(0, i - 1);
                        }
                        for (int i = 0; i < 2; i++) {
                            newBlocksPos[i + 2] = new Position(1, i);
                        }
                        break;
                    case DOWN:
                        for (int i = 0; i < 2; i++) {
                            newBlocksPos[i] = new Position(-1, i - 1);
                        }
                        for (int i = 0; i < 2; i++) {
                            newBlocksPos[i + 2] = new Position(0, i);
                        }
                        break;
                    case LEFT:
                        for (int i = 0; i < 2; i++) {
                            newBlocksPos[i] = new Position(i, -1);
                        }
                        for (int i = 0; i < 2; i++) {
                            newBlocksPos[i + 2] = new Position(i - 1, 0);
                        }
                        break;
                    case RIGHT:
                        for (int i = 0; i < 2; i++) {
                            newBlocksPos[i] = new Position(i - 1, 1);
                        }
                        for (int i = 0; i < 2; i++) {
                            newBlocksPos[i + 2] = new Position(i, 0);
                        }
                        break;
                }
                break;
            case COUNTERCLOCKWISE:
                switch (state) {
                    case UP:
                        for (int i = 0; i < 2; i++) {
                            newBlocksPos[i] = new Position(-1, i - 1);
                        }
                        for (int i = 0; i < 2; i++) {
                            newBlocksPos[i + 2] = new Position(0, i);
                        }
                        break;
                    case DOWN:
                        for (int i = 0; i < 2; i++) {
                            newBlocksPos[i] = new Position(0, i - 1);
                        }
                        for (int i = 0; i < 2; i++) {
                            newBlocksPos[i + 2] = new Position(1, i);
                        }
                        break;
                    case LEFT:
                        for (int i = 0; i < 2; i++) {
                            newBlocksPos[i] = new Position(i - 1, 1);
                        }
                        for (int i = 0; i < 2; i++) {
                            newBlocksPos[i + 2] = new Position(i, 0);
                        }
                        break;
                    case RIGHT:
                        for (int i = 0; i < 2; i++) {
                            newBlocksPos[i] = new Position(i, -1);
                        }
                        for (int i = 0; i < 2; i++) {
                            newBlocksPos[i + 2] = new Position(i - 1, 0);
                        }
                        break;
                }
                break;
        }
        if (spinCheck(newBlocksPos))
            return true;
        System.arraycopy(newBlocksPos, 0, blocksPos, 0, 4);
        updateBlockPosition();
        changeState(direction);
        return false;
    }

}
