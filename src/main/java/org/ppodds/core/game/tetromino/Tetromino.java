package org.ppodds.core.game.tetromino;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.ppodds.core.ResourceManager;
import org.ppodds.core.game.Position;
import org.ppodds.core.game.SpinDirection;
import org.ppodds.core.game.Tetris;
import org.ppodds.core.game.TetrominoState;

import java.util.Random;

public abstract class Tetromino {

    /**
     * Pane[] 用來存放一整個 Tetromino 裡面有的 4 個 Pane
     * 每個對應的 Pane 對 center 的相對座標會被記錄在 Position[] 對應的index中
     */
    protected Pane[] blocks = new Pane[4];
    protected Position[] blocksPos = new Position[4];
    protected TetrominoState state;
    /**
     * 用來取得當前遊戲狀態用
     */
    protected Tetris game;

    /**
     * 碰撞計算中心位置
     * 若碰撞計算中心位於格線上，則以其左上角格子做基準
     */
    protected Position center;

    public Position getCenter() {
        return center;
    }

    /**
     * 建立新方塊用
     *
     * 子類別在繼承時需要在建構子中設定 blockPos 並 call updateBlockPosition
     *
     * @param game Tetris主體物件，用來取得當前遊戲狀態資料
     */
    public Tetromino(Tetris game) {
        this.game = game;
        this.center = new Position(4, 1);
        GridPane gamePane = game.getGamePane();
        for (int i = 0; i < blocks.length; i++) {
            blocks[i] = new Pane();
            blocks[i].getStylesheets().add(ResourceManager.getStyleSheet("Block").toString());
            gamePane.getChildren().add(blocks[i]);
        }
    }

    /**
     * 旋轉 Tetromino ，撞牆的話就停在原地
     *
     * 子類別在實作內容必須維護 blockPos 和 state 的變化，以保證其他功能能正常執行
     * 旋轉時也應考慮是否會撞牆，或超出邊界
     * 結束時需 call updateBlockPosition 以在顯示盤面反映變化
     *
     * @param direction 旋轉的方向
     * @return 是否撞牆
     */
    public abstract boolean spin(SpinDirection direction);

    /**
     * 將方塊下移一格，如果不能下移的話就會被固定在面板上
     *
     * @return 是否觸底，被固定到面板上
     */
    public boolean moveDown() {
        for (int i = 0; i < 4; i++) {
            // 觸底檢查
            if (center.y + blocksPos[i].y + 1 == Tetris.boardHeight) {
                setOnBoard();
                return true;
            }
            Pane checkBlock = game.getBlockOnBoard(center.x + blocksPos[i].x, center.y + blocksPos[i].y + 1);
            if (checkBlock != null) {
                setOnBoard();
                return true;
            }
        }
        down();
        return false;
    }

    /**
     * 將方塊左移一格，如果不能左移就停在原地
     *
     * @return 是否撞牆
     */
    public boolean moveLeft() {
        for (int i = 0; i < 4; i++) {
            // 觸左檢查
            if (center.x + blocksPos[i].x - 1 < 0) {
                return true;
            }
            Pane checkBlock = game.getBlockOnBoard(center.x + blocksPos[i].x - 1, center.y + blocksPos[i].y);
            if (checkBlock != null) {
                return true;
            }
        }
        left();
        return false;
    }

    public boolean moveRight() {
        for (int i = 0; i < 4; i++) {
            // 觸右檢查
            if (center.x + blocksPos[i].x + 1 == Tetris.boardWidth) {
                return true;
            }
            Pane checkBlock = game.getBlockOnBoard(center.x + blocksPos[i].x + 1, center.y + blocksPos[i].y);
            if (checkBlock != null) {
                return true;
            }
        }
        right();
        return false;
    }

    /**
     * 將方塊下移到落地為止
     */
    public void hardDrop() {
        boolean done = false;
        while (!done)
            done = moveDown();
    }

    /**
     * 將方塊直接下移，不做檢查，主要是為簡化多餘程式而寫
     */
    protected void down() {
        center.y++;
        for (int i = 0; i < 4; i++) {
            Tetris.setRowIndexByCenterY(blocks[i], center.y + blocksPos[i].y);
        }
    }

    /**
     * 將方塊直接左移，不做檢查，主要是為簡化多餘程式而寫
     */
    protected void left() {
        center.x--;
        for (int i = 0; i < 4; i++) {
            Tetris.setColumnIndexByCenterX(blocks[i], center.x + blocksPos[i].x);
        }
    }

    /**
     * 將方塊直接右移，不做檢查，主要是為簡化多餘程式而寫
     */
    protected void right() {
        center.x++;
        for (int i = 0; i < 4; i++) {
            Tetris.setColumnIndexByCenterX(blocks[i], center.x + blocksPos[i].x);
        }
    }

    /**
     * 此方法並不會改變 board 的值，因此僅為顯示用途，並不會作為碰撞計算依據
     * 若需要讓他被碰撞計算，應使用 setOnBoard 方法
     */
    protected void updateBlockPosition() {
        for (int i = 0; i < blocks.length; i++) {
            Tetris.setColumnIndexByCenterX(blocks[i], blocksPos[i].x + center.x);
            Tetris.setRowIndexByCenterY(blocks[i], blocksPos[i].y + center.y);
        }
    }

    /**
     * 檢查新的 Block 位置是否會導致 Tetromino 超出邊界或是碰撞
     * 用於 spin 時的檢查
     * @param newBlocksPos 旋轉過後新的 Block 相對位置
     * @return 是否會產生碰撞或超出邊界
     */
    protected boolean spinCheck(Position[] newBlocksPos) {
        for (int i = 0; i < 4; i++) {
            Position newPosition = center.plus(newBlocksPos[i]);
            // 邊界檢查
            if (newPosition.y < 0 || newPosition.y == Tetris.boardHeight || newPosition.x == Tetris.boardWidth || newPosition.x < 0) {
                return true;
            }
            if (game.getBlockOnBoard(newPosition) != null)
                return true;
        }
        return false;
    }
    /**
     * 將方塊固定到面板上，固定後會被計算碰撞
     * 使用於方塊落底
     */
    protected void setOnBoard() {
        for (int i = 0; i < 4; i++)
            game.setBoardByPosition(blocks[i], center.plus(blocksPos[i]));
        game.controlling = null;
    }

    /**
     * 依照旋轉的方向改變 Tetromino 的狀態
     * @param direction 旋轉方向
     */
    protected void changeState(SpinDirection direction) {
        switch (direction) {
            case CLOCKWISE:
                switch (state) {
                    case UP:
                        state = TetrominoState.RIGHT;
                        break;
                    case DOWN:
                        state = TetrominoState.LEFT;
                        break;
                    case LEFT:
                        state = TetrominoState.UP;
                        break;
                    case RIGHT:
                        state = TetrominoState.DOWN;
                        break;
                }
                break;
            case COUNTERCLOCKWISE:
                switch (state) {
                    case UP:
                        state = TetrominoState.LEFT;
                        break;
                    case DOWN:
                        state = TetrominoState.RIGHT;
                        break;
                    case LEFT:
                        state = TetrominoState.DOWN;
                        break;
                    case RIGHT:
                        state = TetrominoState.UP;
                        break;
                }
                break;
        }

    }

    /**
     * 生成隨機方塊
     *
     * @param game Tetris主體物件，用來取得當前遊戲狀態資料
     * @return Tetromino的子類別物件，可能為各種不同的Block
     */
    public static Tetromino generateRandomTetromino(Tetris game) {
        Random random = new Random();
        char[] types = {'I', 'J', 'L', 'O', 'S', 'T', 'Z'};
        switch (types[random.nextInt(7)]) {
            case 'I':
                return new BlockI(game);
            case 'J':
                return new BlockJ(game);
            case 'L':
                return new BlockL(game);
            case 'O':
                return new BlockO(game);
            case 'S':
                return new BlockS(game);
            case 'T':
                return new BlockT(game);
            case 'Z':
                return new BlockZ(game);
        }
        return null;
    }
}


