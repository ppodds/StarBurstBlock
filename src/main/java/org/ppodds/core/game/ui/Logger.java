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
        if (damage >= 40) {
            if (Random.choose(people).equals("亞絲娜"))
                writeLine("亞絲娜使用了星屑飛濺，對閃耀魔眼造成了 " + damage + " 點傷害!");
            else
                writeLine("克萊茵使用了辻風，對閃耀魔眼造成了 " + damage + " 點傷害!");
        }
        else if (damage >= 20) {
            if (Random.choose(people).equals("亞絲娜"))
                writeLine("亞絲娜使用了平行刺擊，對閃耀魔眼造成了 " + damage + " 點傷害!");
            else
                writeLine("克萊茵使用了緋扇，對閃耀魔眼造成了 " + damage + " 點傷害!");
        }
        else {
            writeLine(Random.choose(people) + "對閃耀魔眼進行攻擊，造成了 " + damage + " 點傷害!");
        }
    }
    public void writeLossMessage() {
        writeLine("閃耀魔眼擊殺了桐人!");
    }
    public void writeSkillMessage(String skillName) {
        switch (skillName) {
            case "直劈":
                writeLine("閃耀魔眼發動了 " + skillName + " ，強力的斬擊使得場地的形狀改變了!");
                break;
            case "橫砍":
                writeLine("閃耀魔眼發動了 " + skillName + " ，強力的斬擊使得場地的形狀改變了!");
                break;
            case "蓄力":
                writeLine("閃耀魔眼開始蓄力了，攻擊速度變得越來越快...");
                break;
        }
    }
}
