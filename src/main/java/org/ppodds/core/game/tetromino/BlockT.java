package org.ppodds.core.game.tetromino;

import org.ppodds.core.game.Position;
import org.ppodds.core.game.Tetris;

public class BlockT extends Tetromino {
    public BlockT(Tetris game) {
        super(game);
        this.state = TetrominoState.UP;
        for (int i = 0; i < 4; i++)
            blocks[i].getStyleClass().add("block-T");
        // 找不到參數只好手刻了
        blocksPos[0] = new Position(0, -1);
        for (int i = 0; i < 3; i++)
            blocksPos[i + 1] = new Position(i - 1, 0);
        updateBlockPosition();
    }

    @Override
    public SpinStatus spin(SpinDirection direction) {
        Position[] newBlocksPos = new Position[4];
        switch (direction) {
            case CLOCKWISE:
                switch (state) {
                    case UP:
                        newBlocksPos[0] = new Position(1, 0);
                        for (int i = 0; i < 3; i++) {
                            newBlocksPos[i + 1] = new Position(0, i - 1);
                        }
                        break;
                    case DOWN:
                        newBlocksPos[0] = new Position(-1, 0);
                        for (int i = 0; i < 3; i++) {
                            newBlocksPos[i + 1] = new Position(0, i - 1);
                        }
                        break;
                    case LEFT:
                        newBlocksPos[0] = new Position(0, -1);
                        for (int i = 0; i < 3; i++)
                            newBlocksPos[i + 1] = new Position(i - 1, 0);
                        break;
                    case RIGHT:
                        newBlocksPos[0] = new Position(0, 1);
                        for (int i = 0; i < 3; i++) {
                            newBlocksPos[i + 1] = new Position(i - 1, 0);
                        }
                        break;
                }
                break;
            case COUNTERCLOCKWISE:
                switch (state) {
                    case UP:
                        newBlocksPos[0] = new Position(-1, 0);
                        for (int i = 0; i < 3; i++) {
                            newBlocksPos[i + 1] = new Position(0, i - 1);
                        }
                        break;
                    case DOWN:
                        newBlocksPos[0] = new Position(1, 0);
                        for (int i = 0; i < 3; i++) {
                            newBlocksPos[i + 1] = new Position(0, i - 1);
                        }
                        break;
                    case LEFT:
                        newBlocksPos[0] = new Position(0, 1);
                        for (int i = 0; i < 3; i++) {
                            newBlocksPos[i + 1] = new Position(i - 1, 0);
                        }
                        break;
                    case RIGHT:
                        newBlocksPos[0] = new Position(0, -1);
                        for (int i = 0; i < 3; i++)
                            newBlocksPos[i + 1] = new Position(i - 1, 0);
                        break;
                }
                break;
        }
        SpinStatus spinCheckResult = spinCheck(newBlocksPos, 0);
        if (spinCheckResult.getSpinCheckStatus() == SpinCheckStatus.FAIL)
            return spinCheckResult;
        toLeftOrRight(newBlocksPos, spinCheckResult);
        System.arraycopy(newBlocksPos, 0, blocksPos, 0, 4);
        updateBlockPosition();
        changeState(direction);
        return spinCheckResult;
    }

}
