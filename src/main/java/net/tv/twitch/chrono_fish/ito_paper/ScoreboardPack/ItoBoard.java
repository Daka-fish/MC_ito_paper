package net.tv.twitch.chrono_fish.ito_paper.ScoreboardPack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.tv.twitch.chrono_fish.ito_paper.GamePack.ItoGame;
import net.tv.twitch.chrono_fish.ito_paper.GamePack.ItoPlayer;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.*;

public class ItoBoard {

    private final Scoreboard board;
    private final Objective obj;
    private final ItoGame itoGame;
    private final ItoPlayer itoPlayer;

    private String yourNumber;
    private String orderOrPlayer;

    public ItoBoard(ItoGame itoGame, ItoPlayer itoPlayer){
        board = Bukkit.getScoreboardManager().getNewScoreboard();
        obj = board.registerNewObjective("sidebar", Criteria.DUMMY, Component.text("-Ito-").decorate(TextDecoration.BOLD));
        this.itoGame  = itoGame;
        this.itoPlayer = itoPlayer;
        yourNumber = " └ §e"+itoPlayer.getNumber();
        orderOrPlayer = "+参加者:";
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score s1 = obj.getScore("");
        Score s2 = obj.getScore("+あなたの数字:");
        Score s3 = obj.getScore(" ");

        Score number = obj.getScore(yourNumber);
        Score players = obj.getScore(orderOrPlayer);

        s1.setScore(0);
        s2.setScore(-1);
        number.setScore(-2);
        s3.setScore(-3);
        players.setScore(-4);

        for(int i = 1; i<10; i++){
            Score entry = obj.getScore(" └"+i+":");
            entry.setScore(-(i+4));
        }
    }

    public Scoreboard getBoard(){return board;}

    public void resetNumber(){
        board.resetScores(yourNumber);
        yourNumber = " └ §e"+itoPlayer.getNumber();
        obj.getScore(yourNumber).setScore(-2);
    }

    public void switchScore(boolean isRunning){
        board.resetScores(orderOrPlayer);
        if(isRunning){
            orderOrPlayer = "+宣言";
            obj.getScore(orderOrPlayer).setScore(-4);
        }else{
            orderOrPlayer = "+参加者:";
            obj.getScore(orderOrPlayer).setScore(-4);
        }
    }

    public void addPlayer(ItoPlayer itoPlayer){
        if(itoGame.isGameRunning()){

        }
    }
}
