package org.ppodds.core.game.boss;

import javafx.scene.control.ProgressBar;
import org.ppodds.core.game.Position;
import org.ppodds.core.game.SkillBonus;
import org.ppodds.core.game.Tetris;
import org.ppodds.core.util.Random;

public class Boss {
    private static final String[] skillList = {"直劈", "橫砍", "蓄力"};
    private final Tetris game;
    private final ProgressBar hpBar;
    /**
     * boss 施放技能的 Timer
     */
    private int skillCounter = 0;
    private int opSkillCounter = 0;
    /**
     * 紀錄 Boss HP用
     */
    private int bossHP = 2000;

    public Boss(Tetris game, ProgressBar bossHPBar) {
        this.game = game;
        hpBar = bossHPBar;
    }

    public void damage(int lines, SkillBonus bonus) {
        if (lines > 0) {
            int damage = lines * 10;
            // TODO 之後再根據 bonus 做加成
            bossHP -= damage;
            hpBar.setProgress(bossHP / 2000f);
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
                int x = rand.nextInt(Tetris.boardWidth - 1);
                GarbageBlock[] garbageBlocks = new GarbageBlock[2];
                for (int i = 0; i < 2; i++) {
                    garbageBlocks[i] = new GarbageBlock(game, new Position(x + i, 0), 1, 5);
                }
                GarbageBlockGroup garbageBlockGroup = new GarbageBlockGroup(game, garbageBlocks);
                break;
            }
            case "橫砍": {
                /*
                隨機找個地方放個 5*2 方塊下去
                 */
                game.getLogger().writeSkillMessage("橫砍");
                java.util.Random rand = new java.util.Random();
                int x = rand.nextInt(Tetris.boardWidth - 5);
                GarbageBlock[] garbageBlocks = new GarbageBlock[5];
                for (int i = 0; i < 5; i++) {
                    garbageBlocks[i] = new GarbageBlock(game, new Position(x + i, 0), 1, 2);
                }
                GarbageBlockGroup garbageBlockGroup = new GarbageBlockGroup(game, garbageBlocks);
                break;
            }
            case "蓄力": {
                /*
                來加快方塊的下落速度好了
                 */
                if (opSkillCounter < 3) {
                    game.getLogger().writeSkillMessage("蓄力");
                    game.setBlockDownRate(game.getBlockDownRate() + 1);
                    opSkillCounter++;
                    break;
                } else {
                    useSkill();
                    break;
                }
            }

        }
    }

    public void addSkillCounter() {
        if (!game.isPaused()) {
            skillCounter++;
            if (skillCounter == 7) {
                skillCounter = 0;
                useSkill();
            }
        }
    }
}
