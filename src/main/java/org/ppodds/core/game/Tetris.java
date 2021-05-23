package org.ppodds.core.game;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.ppodds.core.game.tetromino.Tetromino;

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
     */
    private final GridPane gamePane;
    private final GridPane hintPane;
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

    private Tetris(GridPane gamePane, GridPane hintPane) {
        this.gamePane = gamePane;
        this.hintPane = hintPane;
    }

    /**
     * 建立新遊戲
     *
     * @param gamePane 用來顯示當前遊戲狀態的盤面
     * @param hintPane 用來顯示提醒的盤面
     * @return Tetris 物件，用來管理遊戲
     */
    public static Tetris createNewGame(GridPane gamePane, GridPane hintPane) {
        return new Tetris(gamePane, hintPane);
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
    public void setBoardByPosition(Pane pane, Position position) {
        this.board[position.y][position.x] = pane;
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

}
