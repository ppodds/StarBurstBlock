package org.ppodds.core.game;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.ppodds.core.game.tetromino.Tetromino;
import org.ppodds.core.game.ui.GamePane;
import org.ppodds.core.game.ui.Logger;

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
     * bossHpBar 用來顯示 Boss HP 狀態
     * logger 用來紀錄戰鬥訊息
     */
    private final GridPane gamePane;
    private final GridPane hintPane;
    private final ProgressBar bossHpBar;
    private final Logger logger;
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
     * 紀錄 Boss HP用
     */
    private int bossHP = 3000;


    private Tetris(GridPane gamePane, GridPane hintPane, ProgressBar bossHpBar, TextArea logArea) {
        this.gamePane = gamePane;
        this.hintPane = hintPane;
        this.bossHpBar = bossHpBar;
        this.logger = new Logger(logArea);
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
            next = Tetromino.generateRandomTetromino(this);
            controlling.joinGame();
        }
        assert next != null;
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

    public void damage(int lines, SkillBonus bonus) {
        if (lines > 0) {
            int damage = lines * 10;
            // TODO 之後再根據 bonus 做加成
            bossHP -= damage;
            bossHpBar.setProgress(bossHP / 3000f);
            logger.writeDamageMessage(damage);
        }
    }

}
