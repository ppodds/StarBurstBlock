package org.ppodds.core.game.tetromino;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.ppodds.core.ResourceManager;
import org.ppodds.core.game.SpinDirection;
import org.ppodds.core.game.Tetris;
import org.ppodds.core.game.TetrominoState;

import java.util.Random;

public abstract class Tetromino extends Pane {
    protected Pane[] blocks = new Pane[4];
    protected TetrominoState state;

    /**
     * 碰撞計算中心位置
     * 若碰撞計算中心位於格線上，則以其左上角格子做基準
     */
    protected int x;
    protected int y;
    /**
     * 用來取得當前遊戲狀態用
     */
    protected Tetris game;

    /**
     * 建立新方塊用
     * @param state 方塊旋轉的狀態
     * @param game Tetris主體物件，用來取得當前遊戲狀態資料
     */
    public Tetromino(TetrominoState state, Tetris game) {
        this.state = state;
        this.game = game;
        for (int i=0;i<blocks.length;i++) {
            blocks[i] = new Pane();
            blocks[i].getStylesheets().add(ResourceManager.getStyleSheet("Block").toString());
        }
    }

    public abstract void spin(SpinDirection direction);
    /**
     * 將方塊下移一格，如果不能下移的話就會被固定在面板上
     * @return 是否觸底，被固定到面板上
     */
    public abstract boolean moveDown();
    public abstract void moveLeft();
    public abstract void moveRight();

    /**
     * 將方塊下移到落地為止
     */
    public void hardDrop() {
        boolean done = false;
        while (!done)
            done = moveDown();
    };
    /**
     * 將方塊直接下移，不做檢查，主要是為簡化多餘程式而寫
     */
    protected void down() {
        y++;
        for (Pane block : blocks) {
            GridPane.setColumnIndex(block, y+1);
        }
    }
    /**
     * 將方塊直接左移，不做檢查，主要是為簡化多餘程式而寫
     */
    protected void left() {
        x--;
        for (Pane block : blocks) {
            GridPane.setRowIndex(block, x+1);
        }
    }
    /**
     * 將方塊直接右移，不做檢查，主要是為簡化多餘程式而寫
     */
    protected void right() {
        x++;
        for (Pane block : blocks) {
            GridPane.setRowIndex(block, x+1);
        }
    }

    /**
     * 將方塊固定到面板上
     */
    protected abstract void setOnBoard();

    /**
     * 生成隨機方塊
     * @param game Tetris主體物件，用來取得當前遊戲狀態資料
     * @return Tetromino的子類別物件，可能為各種不同的Block
     */
    public static Tetromino generateRandomTetromino(Tetris game) {
        Random random = new Random();
        char[] types = {'I', 'J', 'L', 'O', 'S', 'T', 'Z'};
        TetrominoState[] states = {TetrominoState.UP, TetrominoState.DOWN, TetrominoState.LEFT, TetrominoState.RIGHT};
        TetrominoState randomState = states[random.nextInt(4)];
        switch (types[random.nextInt(7)]) {
            case 'I':
                return new BlockI(randomState, game);
            case 'J':
                return new BlockJ(randomState, game);
            case 'L':
                return new BlockL(randomState, game);
            case 'O':
                return new BlockO(randomState, game);
            case 'S':
                return new BlockS(randomState, game);
            case 'T':
                return new BlockT(randomState, game);
            case 'Z':
                return new BlockZ(randomState, game);
        }
        return null;
    }
}


