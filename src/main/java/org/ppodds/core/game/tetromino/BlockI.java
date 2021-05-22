package org.ppodds.core.game.tetromino;

import org.ppodds.core.game.Position;
import org.ppodds.core.game.SpinDirection;
import org.ppodds.core.game.Tetris;
import org.ppodds.core.game.TetrominoState;

public class BlockI extends Tetromino {
    public BlockI(Tetris game) {
        super(game);
        this.state = TetrominoState.UP;
        for (int i = 0; i < blocks.length; i++) {
            blocks[i].getStyleClass().add(".Block-I");
            blocksPos[i] = new Position(-1 + i, 0);
        }
        updateBlockPosition();
    }

    @Override
    public boolean spin(SpinDirection direction) {
        Position[] newPositions = new Position[4];
        switch (direction) {
            case CLOCKWISE:
                switch (state) {
                    case UP:
                        for (int i = 0; i < 4; i++) {
                            newPositions[i] = new Position(center.x + 1, center.y + i - 1);
                        }
                        break;
                    case DOWN:
                        for (int i = 0; i < 4; i++) {
                            newPositions[i] = new Position(center.x, center.y + i - 1);
                        }
                        break;
                    case LEFT:
                        for (int i = 0; i < 4; i++) {
                            newPositions[i] = new Position(center.x + i - 1, center.y);
                        }
                        break;
                    case RIGHT:
                        for (int i = 0; i < 4; i++) {
                            newPositions[i] = new Position(center.x + i - 1, center.y - 1);
                        }
                        break;
                }
                break;
            case COUNTERCLOCKWISE:
                break;
        }
        for (int i = 0; i < 4; i++) {
            if (game.getBlockOnBoard(newPositions[i]) != null)
                return true;
        }
        System.arraycopy(newPositions, 0, blocksPos, 0, 4);
        updateBlockPosition();
        return false;
    }


}
