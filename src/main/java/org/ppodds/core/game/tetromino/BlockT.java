package org.ppodds.core.game.tetromino;

import org.ppodds.core.game.SpinDirection;
import org.ppodds.core.game.Tetris;

public class BlockT extends Tetromino {
    public BlockT(Tetris game) {
        super(game);
    }

    @Override
    public boolean spin(SpinDirection direction) {
        switch (direction) {
            case CLOCKWISE:
                break;
            case COUNTERCLOCKWISE:
                break;
        }
        return false;
    }

}
