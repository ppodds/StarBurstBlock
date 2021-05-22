package org.ppodds.core.game;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.ppodds.core.game.tetromino.Tetromino;

public class Tetris {
    /**
     * 當前玩家按鍵能控制的 Tetromino
     */
    public Tetromino controlling;

    /**
     * gamePane 用來顯示當前遊戲狀態
     * hintPane 用來顯示提醒(如下一個 Tetromino )
     */
    private final GridPane gamePane;
    private final GridPane hintPane;

    /**
     * holding 的 Tetromino
     */
    private Tetromino holding;

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
     * 紀錄盤面的大小用
     * 方便程式編寫與維護
     */
    public static final int boardHeight = 20;
    public static final int boardWidth = 10;

    /**
     * 設定盤面上的特定格子為 pane
     *
     * @param pane     要設定的盤面
     * @param position 要設定的位置 (盤面上的絕對位置)
     */
    public void setBoardByPosition(Pane pane, Position position) {
        this.board[position.y][position.x] = pane;
    }

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

    /**
     * 建立新的 Tetromino
     * Warring: 此方法會改變 controlling 的 Tetromino
     */
    public void createNewTetromino() {
        controlling = Tetromino.generateRandomTetromino(this);
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
     * 設定已在顯示盤面上的方塊 x 座標
     *
     * @param node    要設定的方塊
     * @param centerX 新的 x 座標
     */
    public static void setColumnIndexByCenterX(Node node, int centerX) {
        GridPane.setColumnIndex(node, centerX + 1);
    }

    /**
     * 設定已在顯示盤面上的方塊 y 座標
     *
     * @param node    要設定的方塊
     * @param centerY 新的 y 座標
     */
    public static void setRowIndexByCenterY(Node node, int centerY) {
        GridPane.setRowIndex(node, centerY + 1);
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
