package org.ppodds.core.game.ui;

import javafx.scene.control.TextArea;
import org.ppodds.core.util.Random;

public class Logger {
    private TextArea logArea;
    public Logger(TextArea logArea) {
        this.logArea = logArea;
    }
    private void writeLine(String message) {
        logArea.appendText(message + "\n");
    }
    public void writeDamageMessage(int damage) {
        Random random = new Random();
        String[] people = {"亞絲娜", "克萊茵"};
        writeLine(Random.choose(people) + "對閃耀魔眼進行攻擊，造成了 " + damage + " 點傷害!");
    }
    public void writeLossMessage() {
        writeLine("閃耀魔眼擊殺了桐人!");
    }
    public void writeSkillMessage(String skillName) {
        switch (skillName) {
            case "直劈":
                writeLine("閃耀魔眼發動了 " + skillName + " ，強力的斬擊使得場地的形狀改變了!");
                break;
        }
    }
}
