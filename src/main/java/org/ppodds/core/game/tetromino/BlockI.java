package org.ppodds.core.game.tetromino;

import javafx.scene.layout.GridPane;
import org.ppodds.core.game.Position;
import org.ppodds.core.game.SpinDirection;
import org.ppodds.core.game.Tetris;
import org.ppodds.core.game.TetrominoState;

public class BlockI extends Tetromino {
    public BlockI(Tetris game) {
        super(game);
        this.state = TetrominoState.UP;
        for (int i = 0; i < blocks.length; i++) {
            blocks[i].getStyleClass().add("block-I");
            blocksPos[i] = new Position(-1 + i, 0);
        }
        updateBlockPosition();
    }

    @Override
    public boolean spin(SpinDirection direction) {
        Position[] newBlocksPos = new Position[4];
        switch (direction) {
            case CLOCKWISE:
                switch (state) {
                    case UP:
                        for (int i = 0; i < 4; i++) {
                            newBlocksPos[i] = new Position( 1,  i - 1);
                        }
                        break;
                    case DOWN:
                        for (int i = 0; i < 4; i++) {
                            newBlocksPos[i] = new Position(0,  i - 1);
                        }
                        break;
                    case LEFT:
                        for (int i = 0; i < 4; i++) {
                            newBlocksPos[i] = new Position( i - 1, 0);
                        }
                        break;
                    case RIGHT:
                        for (int i = 0; i < 4; i++) {
                            newBlocksPos[i] = new Position( i - 1,  1);
                        }
                        break;
                }
                break;
            case COUNTERCLOCKWISE:
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
