package org.ppodds.core.game.ui;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class GamePane {
    /**
     * 設定已在遊戲顯示盤面上的方塊 x 座標
     *
     * @param node    要設定的方塊
     * @param centerX 新的 x 座標
     */
    public static void setColumnIndexByCenterX(Node node, int centerX) {
        GridPane.setColumnIndex(node, centerX + 1);
    }

    /**
     * 設定已在遊戲顯示盤面上的方塊 y 座標
     *
     * @param node    要設定的方塊
     * @param centerY 新的 y 座標
     */
    public static void setRowIndexByCenterY(Node node, int centerY) {
        GridPane.setRowIndex(node, centerY + 1);
    }
}
