package org.ppodds.core.game.boss;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.ppodds.core.ResourceManager;
import org.ppodds.core.game.Position;
import org.ppodds.core.game.Tetris;
import org.ppodds.core.game.ui.GamePane;

public class GarbageBlock {
    /**
     * 用來取得當前遊戲狀態用
     */
    private final Tetris game;
    private final Timeline dropAnimation;
    /**
     * Pane[] 用來存放一整個 Tetromino 裡面有的 4 個 Pane
     * 每個對應的 Pane 對 center 的相對座標會被記錄在 Position[] 對應的index中
     */
    private Pane[] blocks;
    private Position[] blocksPos;
    /**
     * 碰撞計算中心位置
     * 一律以最左上角格子做基準
     */
    private Position center;
    private boolean animationFinished = false;

    /**
     * 新建一個 GarbageBlock
     *
     * @param game   Tetris主體物件，用來取得當前遊戲狀態資料
     * @param center GarbageBlock 中心位置
     * @param width  GarbageBlock 寬度
     * @param height GarbageBlock 高度
     */
    public GarbageBlock(Tetris game, Position center, int width, int height) {
        int amount = width * height;
        blocks = new Pane[amount];
        this.blocksPos = new Position[amount];
        this.game = game;
        this.center = center;
        int i = 0;
        for (int x = 0; x < width; x++) {
            for (int y = height - 1; y >= 0; y--) {
                blocks[i] = new Pane();
                blocks[i].getStylesheets().add(ResourceManager.getStyleSheet("Block").toString());
                blocks[i].getStyleClass().add("block-garbage");
                blocksPos[i] = new Position(x, y);
                i++;
            }
        }
        game.getGamePane().getChildren().addAll(blocks);
        updateBlockPosition();
        dropAnimation = new Timeline(new KeyFrame(Duration.millis(50), e -> {
            game.setPaused(true);
            moveDown(game);
        }));
        dropAnimation.setCycleCount(Timeline.INDEFINITE);
        dropAnimation.play();
    }

    public boolean isAnimationFinished() {
        return animationFinished;
    }

    /**
     * 將方塊下移一格，如果不能下移的話就會被固定在面板上
     *
     * @return 是否觸底，被固定到面板上
     */
    private boolean moveDown(Tetris game) {
        for (int i = 0; i < blocks.length; i++) {
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
     * 將方塊固定到面板上，固定後會被計算碰撞
     * 使用於方塊落底
     */
    private void setOnBoard() {
        for (int i = 0; i < blocks.length; i++) {
            Position newPosition = center.plus(blocksPos[i]);
            if (newPosition.y < 0) {
                game.gameOver(false);
                break;
            } else {
                game.setBlockOnBoard(blocks[i], newPosition);
            }
        }
        dropAnimation.stop();
        animationFinished = true;
        game.setPaused(false);
    }

    /**
     * 將方塊直接下移，不做檢查，主要是為簡化多餘程式而寫
     */
    private void down() {
        center.y++;
        for (int i = 0; i < blocks.length; i++) {
            GamePane.setRowIndexByCenterY(blocks[i], center.y + blocksPos[i].y);
        }
    }

    /**
     * 更新方塊的當前位置
     * <p>
     * Warring:
     * 此方法並不會改變 board 的值，因此僅為顯示用途，並不會作為碰撞計算依據
     * 若需要讓他被碰撞計算，應使用 setOnBoard 方法
     */
    public void updateBlockPosition() {
        for (int i = 0; i < blocks.length; i++) {
            GamePane.setColumnIndexByCenterX(blocks[i], blocksPos[i].x + center.x);
            GamePane.setRowIndexByCenterY(blocks[i], blocksPos[i].y + center.y);
        }
    }
}
