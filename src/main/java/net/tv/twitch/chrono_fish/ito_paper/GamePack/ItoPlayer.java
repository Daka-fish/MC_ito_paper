package net.tv.twitch.chrono_fish.ito_paper.GamePack;

import net.tv.twitch.chrono_fish.ito_paper.ScoreboardPack.ItoBoard;
import org.bukkit.entity.Player;

public class ItoPlayer {
    private final ItoGame itoGame;
    private final Player player;
    private int number;

    private final ItoBoard itoBoard;

    public ItoPlayer(ItoGame itoGame, Player player){
        this.itoGame = itoGame;
        this.player = player;
        number = -1;
        this.itoBoard = new ItoBoard(this);
        player.setScoreboard(itoBoard.getBoard());
    }

    public Player getPlayer() {return player;}
    public ItoBoard getItoBoard(){return itoBoard;}

    public int getNumber() {return number;}
    public void setNumber(int number) {this.number = number;}

    public void submitTheme(String theme){itoGame.getThemeManager().getThemePool().add(theme);}
    public void call(){itoGame.getField().add(this);}
}
