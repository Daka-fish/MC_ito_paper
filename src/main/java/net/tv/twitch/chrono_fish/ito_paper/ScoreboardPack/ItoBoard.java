package net.tv.twitch.chrono_fish.ito_paper.ScoreboardPack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.tv.twitch.chrono_fish.ito_paper.GamePack.ItoGame;
import net.tv.twitch.chrono_fish.ito_paper.GamePack.ItoPlayer;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.*;

public class ItoBoard {

    private final ItoGame itoGame;

    private final Scoreboard board;
    private final Objective obj;
    private final ItoPlayer itoPlayer;

    private String theme;
    private String yourNumber;
    private String callOrder;

    public ItoBoard(ItoGame itoGame, ItoPlayer itoPlayer){

        this.itoGame = itoGame;
        this.itoPlayer = itoPlayer;

        board = Bukkit.getScoreboardManager().getNewScoreboard();
        obj = board.registerNewObjective("sidebar", Criteria.DUMMY, Component.text("-Ito-").decorate(TextDecoration.BOLD));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        theme = " └ §7§l"+"UNKNOWN";
        yourNumber = " └ §7§l"+itoPlayer.getNumber();
        callOrder = " └ §7§l"+itoPlayer.getCallOrder()+" ";

        Score empty1 = obj.getScore("");
        Score empty2 = obj.getScore(" ");
        Score empty3 = obj.getScore("  ");
        Score empty4 = obj.getScore("   ");

        Score s1 = obj.getScore("+テーマ:");
        Score s2 = obj.getScore("+あなたの数字:");
        Score s3 = obj.getScore("+コールした順番:");

        Score numberScore = obj.getScore(yourNumber);
        Score themeScore = obj.getScore(theme);
        Score callScore = obj.getScore(callOrder);

        empty1.setScore(0);
        s1.setScore(-1);
        themeScore.setScore(-2);
        empty2.setScore(-3);
        s2.setScore(-4);
        numberScore.setScore(-5);
        empty3.setScore(-6);
        s3.setScore(-7);
        callScore.setScore(-8);
        empty4.setScore(-9);
    }

    public Scoreboard getBoard(){return board;}

    public void reloadTheme(){
        board.resetScores(theme);
        theme = " └ §a"+ itoGame.getTheme();
        obj.getScore(theme).setScore(-2);
    }

    public void reloadNumber(){
        board.resetScores(yourNumber);
        yourNumber = " └ §e"+ itoPlayer.getNumber();
        obj.getScore(yourNumber).setScore(-5);
    }

    public void reloadCallOrder(){
        board.resetScores(callOrder);
        callOrder = " └ §e"+(itoPlayer.getCallOrder())+" ";
        obj.getScore(callOrder).setScore(-8);
    }
}
