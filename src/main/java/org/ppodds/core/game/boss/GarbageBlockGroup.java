package org.ppodds.core.game.boss;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import org.ppodds.core.game.Tetris;

public class GarbageBlockGroup {
    private GarbageBlock[] garbageBlocks;
    private final Tetris game;
    private final Timeline checkEliminate;
    public GarbageBlockGroup(Tetris game, GarbageBlock[] garbageBlocks) {
        this.game = game;
        this.garbageBlocks = garbageBlocks;
        checkEliminate = new Timeline(new KeyFrame(Duration.millis(50), e -> {
            checkEliminate();
        }));
    }
    private void checkEliminate() {
        boolean check = true;
        for (int i=0;i < garbageBlocks.length;i++) {
            if(!garbageBlocks[i].isAnimationFinished()) {
                check = false;
                break;
            }
        }
        if (check) {
            game.getBoss().damage(game.eliminate(), null);
        }
    }
}
