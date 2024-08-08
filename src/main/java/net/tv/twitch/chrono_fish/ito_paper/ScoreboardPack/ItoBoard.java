package net.tv.twitch.chrono_fish.ito_paper.ScoreboardPack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.tv.twitch.chrono_fish.ito_paper.GamePack.ItoPlayer;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.*;

public class ItoBoard {

    private final Scoreboard board;
    private final Objective obj;
    private final ItoPlayer itoPlayer;

    private String yourNumber;

    public ItoBoard(ItoPlayer itoPlayer){
        board = Bukkit.getScoreboardManager().getNewScoreboard();
        obj = board.registerNewObjective("sidebar", Criteria.DUMMY, Component.text("-Ito-").decorate(TextDecoration.BOLD));
        this.itoPlayer = itoPlayer;
        yourNumber = "  └ §e"+itoPlayer.getNumber();
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score s1 = obj.getScore("+あなたの数字:");
        Score s2 = obj.getScore("+宣言:");
        Score s3 = obj.getScore("");
        Score number = obj.getScore(yourNumber);

        s1.setScore(-1);
        number.setScore(-2);
        s3.setScore(-3);
        s2.setScore(-4);

        for(int i = 1; i<10; i++){
            Score entry = obj.getScore("  └"+i+":");
            entry.setScore(-(i+4));
        }
    }

    public Scoreboard getBoard(){return board;}

    public void resetNumber(){
        board.resetScores(yourNumber);
        yourNumber = "   └ §e"+itoPlayer.getNumber();
        obj.getScore(yourNumber).setScore(-2);

    }
}
