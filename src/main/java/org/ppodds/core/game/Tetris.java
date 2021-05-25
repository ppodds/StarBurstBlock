package org.ppodds.core.game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.ppodds.App;
import org.ppodds.core.game.boss.Boss;
import org.ppodds.core.game.tetromino.Tetromino;
import org.ppodds.core.game.ui.GamePane;
import org.ppodds.core.game.ui.Logger;
import org.ppodds.core.util.Random;

public class Tetris {
    /**
     * 紀錄盤面的大小用
     * 方便程式編寫與維護
     */
    public static final int boardHeight = 20;
    public static final int boardWidth = 10;
    /**
     * gamePane 用來顯示當前遊戲狀態
     * hintPane 用來顯示提醒(如下一個 Tetromino )
     * boss 用來紀錄 Boss 狀態
     * logger 用來紀錄戰鬥訊息
     */
    private final GridPane gamePane;
    private final GridPane hintPane;
    private final Boss boss;
    private final Logger logger;
    /**
     * 方塊會定時下落的控制
     */
    private final Timeline refresh;
    /**
     * 遊戲結束的倒數計時
     */
    private final Timeline countDown;

    /**
     * 遊戲中被確定下來的方塊紀錄
     * 當計算方塊碰撞時會被檢查
     * x0 x1 x2 ...
     * [] [] [] y0
     * [] [] [] y1
     * [] [] [] y2
     * [] [] [] y3
     * ...
     */
    private final Pane[][] board = new Pane[20][10];
    /**
     * 當前玩家按鍵能控制的 Tetromino
     */
    private Tetromino controlling;
    /**
     * holding 的 Tetromino
     */
    private Tetromino holding;
    /**
     * 下一個 Tetromino
     */
    private Tetromino next;
    /**
     * 紀錄是否暫停
     */
    private boolean paused;

    public boolean isPaused() {
        return paused;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setPaused(boolean paused) {
        if (this.paused == paused) {
            return;
        }
        if (paused) {
            refresh.pause();
            countDown.pause();
        }
        else {
            refresh.play();
            countDown.play();
        }
        this.paused = paused;
    }

    private Tetris(GridPane gamePane, GridPane hintPane, ProgressBar bossHpBar, TextArea logArea) {
        this.gamePane = gamePane;
        this.hintPane = hintPane;
        this.logger = new Logger(logArea);
        this.boss = new Boss(this, bossHpBar);

        refresh = new Timeline(new KeyFrame(Duration.seconds(1f), (e) -> {
            if (controlling == null)
                createNewTetromino();
            controlling.moveDown();
        }));
        refresh.setCycleCount(Timeline.INDEFINITE);
        refresh.play();

        countDown = new Timeline(new KeyFrame(Duration.minutes(3), (e) -> {
            gameOver(true);
        }));
        countDown.play();
    }

    /**
     * 建立新遊戲
     *
     * @param gamePane 用來顯示當前遊戲狀態的盤面
     * @param hintPane 用來顯示提醒的盤面
     * @return Tetris 物件，用來管理遊戲
     */
    public static Tetris createNewGame(GridPane gamePane, GridPane hintPane, ProgressBar bossHpBar, TextArea logArea) {
        return new Tetris(gamePane, hintPane, bossHpBar, logArea);
    }

    public Tetromino getControlling() {
        return controlling;
    }

    public void resetControlling() {
        controlling = null;
    }

    /**
     * 設定盤面上的特定格子為 pane
     *
     * @param pane     要設定的盤面
     * @param position 要設定的位置 (盤面上的絕對位置)
     */
    public void setBlockOnBoard(Pane pane, Position position) {
        this.board[position.y][position.x] = pane;
    }
    /**
     * 設定盤面上的特定格子為 pane
     *
     * @param pane     要設定的盤面
     * @param posX 要設定的位置 x
     * @param posY 要設定的位置 y
     */
    public void setBlockOnBoard(Pane pane, int posX, int posY) {
        this.board[posY][posX] = pane;
    }

    /**
     * 建立新的 Tetromino
     * Warring: 此方法會改變 controlling 的 Tetromino
     */
    public void createNewTetromino() {
        if (next == null)
            next = Tetromino.generateRandomTetromino(this);
        if (controlling == null) {
            controlling = next;
            assert controlling != null;
            controlling.joinGame();
            next = Tetromino.generateRandomTetromino(this);
        }
    }

    /**
     * Hold 玩家當前操作的 Tetromino
     * <p>
     * Hold 完以後會初始化下落位置，會從頂部開始重新降落
     */
    public void holdCurrentTetromino() {
        if (holding == null) {
            if (controlling.hold()) {
                holding = controlling;
                controlling = null;
            }
        } else {
            if (controlling.hold()) {
                holding.release();
                Tetromino temp = holding;
                holding = controlling;
                controlling = temp;
            }
        }
    }

    /**
     * 用盤面的絕對座標取得已固定在盤面上的 Block
     *
     * @param posX x座標
     * @param posY y座標
     * @return 盤面該位置的 Block
     */
    public Pane getBlockOnBoard(int posX, int posY) {
        return board[posY][posX];
    }

    public Boss getBoss() {
        return boss;
    }

    /**
     * 消除 board上的方塊，並回傳消掉的行數
     *
     * 如果消去成功的話，上面的方塊會往下掉落
     *
     * Warring:
     * 此方法會連顯示在遊戲盤面的 Block 一起刪掉
     *
     * @return 消掉的行數
     */
    public int eliminate() {
        int count = 0;
        int[] downStep = new int[boardHeight];
        for (int y=0;y < boardHeight;y++) {
            boolean check = true;
            for (int x=0;x < boardWidth;x++) {
                if (getBlockOnBoard(x, y) == null) {
                    check = false;
                    break;
                }
            }
            if (check) {
                count++;
                for (int i=0;i < y;i++) {
                    downStep[i]++;
                }
                for (int x=0;x < boardWidth;x++) {
                    gamePane.getChildren().remove(getBlockOnBoard(x, y));
                    setBlockOnBoard(null, x, y);
                }
            }
        }
        for (int y=boardHeight-1;y >= 0;y--) {
            for (int x=0;x < boardWidth;x++) {
                Pane block = getBlockOnBoard(x, y);
                if (block != null) {
                    GamePane.setRowIndexByCenterY(block, y + downStep[y]);
                    setBlockOnBoard(null, x, y);
                    setBlockOnBoard(block, x, y+downStep[y]);
                }
            }
        }
        return count;
    }

    /**
     * 用盤面的絕對座標取得已固定在盤面上的 Block
     *
     * @param pos Position 物件，用來當作座標
     * @return 盤面該位置的 Block
     */
    public Pane getBlockOnBoard(Position pos) {
        return board[pos.y][pos.x];
    }

    /**
     * 取得顯示遊戲盤面的 pane
     *
     * @return 顯示遊戲盤面的 pane
     */
    public GridPane getGamePane() {
        return gamePane;
    }

    /**
     * 取得顯示遊戲提示的 pane
     *
     * @return 顯示遊戲提示的 pane
     */
    public GridPane getHintPane() {
        return hintPane;
    }


    /**
     * 遊戲結束的控制
     * @param win 是否勝利
     */
    public void gameOver(boolean win) {
        refresh.stop();
        countDown.stop();
        gamePane.setOnKeyPressed(null);
        if (win) {
            App.setRoot("GoodEnding");
        }
        else {
            logger.writeLossMessage();
        }
    }

}
