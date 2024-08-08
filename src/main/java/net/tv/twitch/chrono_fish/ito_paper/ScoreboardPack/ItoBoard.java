package net.tv.twitch.chrono_fish.ito_paper.ScoreboardPack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.tv.twitch.chrono_fish.ito_paper.GamePack.ItoPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ItoBoard {

    private final Scoreboard board;
    private final Objective obj;
    private final ItoPlayer itoPlayer;

    private final String yourNumber;

    public ItoBoard(ItoPlayer itoPlayer){
        board = Bukkit.getScoreboardManager().getNewScoreboard();
        obj = board.registerNewObjective("sidebar", Criteria.DUMMY, Component.text("-Ito-").decorate(TextDecoration.BOLD));
        this.itoPlayer = itoPlayer;
        yourNumber = "└ "+Component.text(String.valueOf(itoPlayer.getNumber())).color(TextColor.color(255,255,0));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score s1 = obj.getScore("+あなたの数字:");
        Score s2 = obj.getScore("+宣言:");
        Score number = obj.getScore(yourNumber);

        s1.setScore(-1);
        number.setScore(-2);
        s2.setScore(-3);
    }

    public Scoreboard getBoard(){return board;}
}
