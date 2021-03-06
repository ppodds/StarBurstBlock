package org.ppodds.core.game.tetromino;

import org.ppodds.core.game.Position;
import org.ppodds.core.game.Tetris;

public class BlockO extends Tetromino {
    public BlockO(Tetris game) {
        super(game);
        this.state = TetrominoState.UP;
        for (int i = 0; i < blocks.length; i++)
            blocks[i].getStyleClass().add("block-O");
        for (int i = 0; i < 2; i++)
            blocksPos[i] = new Position(i, 0);
        for (int i = 0; i < 2; i++)
            blocksPos[i + 2] = new Position(i, 1);
        updateBlockPosition();
    }

    @Override
    public SpinStatus spin(SpinDirection direction) {
        changeState(direction);
        return new SpinStatus(SpinCheckStatus.SUCCESS);
    }

}
