package org.ppodds.core.game.boss;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;
import org.ppodds.core.game.Position;
import org.ppodds.core.game.SkillBonus;
import org.ppodds.core.game.Tetris;
import org.ppodds.core.game.ui.GamePane;
import org.ppodds.core.game.ui.Logger;
import org.ppodds.core.util.Random;

import java.util.ArrayList;

public class Boss {
    private final Tetris game;
    /**
     * boss 施放技能的 Timer
     */
    private final Timeline skillTimer;
    private final ProgressBar hpBar;
    private static final String[] skillList = {"直劈", "橫砍", "蓄力"};
    /**
     * 紀錄 Boss HP用
     */
    private int bossHP = 3000;
    public Boss(Tetris game, ProgressBar bossHPBar) {
        this.game = game;
        hpBar = bossHPBar;
        skillTimer = new Timeline(new KeyFrame(Duration.seconds(20), (e) -> {
            if (!game.isPaused())
                useSkill();
        }));
        skillTimer.setCycleCount(Timeline.INDEFINITE);
        skillTimer.play();
    }
    public void damage(int lines, SkillBonus bonus) {
        if (lines > 0) {
            int damage = lines * 10;
            // TODO 之後再根據 bonus 做加成
            bossHP -= damage;
            hpBar.setProgress(bossHP / 3000f);
            game.getLogger().writeDamageMessage(damage);
        }
        if (bossHP < 0) {
            game.gameOver(true);
        }
    }

    private void useSkill() {
        switch (Random.choose(skillList)) {
            case "直劈": {
                /*
                找一行基準行和它右邊的一行丟幾個方塊下去
                 */
                game.getLogger().writeSkillMessage("直劈");
                java.util.Random rand = new java.util.Random();
                int x1 = rand.nextInt(Tetris.boardWidth - 1);
                int x2 = x1 + 1;
                int y = 5;
                for (int i=0;i<y;i++) {
                    GarbageBlock block1 = new GarbageBlock(game, new Position(x1, y-i));
                    GarbageBlock block2 = new GarbageBlock(game, new Position(x2, y-i));
                }
                break;
            }
            case "橫砍": {
                /*
                隨機找個地方放個 5*2 方塊下去
                 */
                game.getLogger().writeSkillMessage("橫砍");
                java.util.Random rand = new java.util.Random();
                int x = rand.nextInt(Tetris.boardWidth - 5);
                for (int i=0;i<5;i++) {
                    GarbageBlock block1 = new GarbageBlock(game, new Position(x+i, 0));
                    GarbageBlock block2 = new GarbageBlock(game, new Position(x+i, 1));
                }
                break;
            }
            case "蓄力": {
                /**
                 * 來加快方塊的下落速度好了
                 */
                game.getLogger().writeSkillMessage("蓄力");
                game.setBlockDownRate(game.getBlockDownRate() + 1);
                break;
            }

        }
    }
}
