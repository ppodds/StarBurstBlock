package org.ppodds.core.game.boss;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.ppodds.core.ResourceManager;
import org.ppodds.core.game.Position;
import org.ppodds.core.game.Tetris;
import org.ppodds.core.game.ui.GamePane;

public class GarbageBlock { // TODO 看來 GarbageBlock也要改成 Group 的形式，否則掉下的先後順序會產生錯誤
    private final Tetris game;
    private final Pane block;
    private Position position;
    private final Timeline dropAnimation;
    private boolean animationFinished = false; // TODO 如果玩家的方塊在 Boss 方塊落下時剛好在方塊要落下的位置也會產生錯誤。

    public boolean isAnimationFinished() {
        return animationFinished;
    }

    public GarbageBlock(Tetris game, Position position) {
        this.block = new Pane();
        this.game = game;
        game.getGamePane().getChildren().add(this.block);
        this.position = position;
        block.getStylesheets().add(ResourceManager.getStyleSheet("Block").toString());
        block.getStyleClass().add("block-garbage");
        GamePane.setColumnIndexByCenterX(this.block, position.x);
        GamePane.setRowIndexByCenterY(this.block, position.y);
        dropAnimation = new Timeline(new KeyFrame(Duration.millis(50), e -> {
            Position positionNow = getPosition();
            if (positionNow.y + 1 == Tetris.boardHeight) {
                set();
            }
            if (game.getBlockOnBoard(positionNow.x, positionNow.y+1) != null) {
                set();
            }
            else {
                down();
            }
        }));
        dropAnimation.setCycleCount(Timeline.INDEFINITE);
        dropAnimation.play();
    }
    // TODO 不知道為甚麼，Boss丟下來的方塊在顯示效果和碰撞上有問題，待修正
    private void set() {
        if (position.y < 0)
            game.gameOver(false);
        else {
            game.setBlockOnBoard(this.block, this.position.x, this.position.y);
            game.getBoss().damage(game.eliminate(), null);
        }
        dropAnimation.stop();
        animationFinished = true;
    }

    private void down() {
        position.y++;
        GamePane.setRowIndexByCenterY(this.block, position.y);
    }

    public Position getPosition() {
        return position;
    }
}
