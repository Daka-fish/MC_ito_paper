package net.tv.twitch.chrono_fish.ito_paper.GamePack;

import net.tv.twitch.chrono_fish.ito_paper.ScoreboardPack.ItoBoard;
import org.bukkit.entity.Player;

public class ItoPlayer {
    private final ItoGame itoGame;
    private final Player player;
    private int number;
    private int callOrder;
    private boolean hasCall;
    private boolean isInGame;

    private ItoBoard itoBoard;

    public ItoPlayer(ItoGame itoGame, Player player){
        this.itoGame = itoGame;
        this.player = player;
        this.number = -1;
        this.callOrder = -1;
        this.hasCall = false;
        this.isInGame = true;
        this.itoBoard = new ItoBoard(itoGame, this);
        player.setScoreboard(itoBoard.getBoard());
    }

    public Player getPlayer() {return player;}

    public void setItoBoard(ItoBoard itoBoard) {
        this.itoBoard = itoBoard;
        player.setScoreboard(itoBoard.getBoard());
    }

    public ItoBoard getItoBoard(){return itoBoard;}

    public int getNumber() {return number;}
    public void setNumber(int number) {this.number = number;}

    public int getCallOrder() {return callOrder;}
    public void setCallOrder(int callOrder) {this.callOrder = callOrder;}

    public boolean hasCall() {return hasCall;}
    public void setHasCall(boolean hasCall) {this.hasCall = hasCall;}

    public boolean isInGame() {return isInGame;}
    public void setInGame(boolean inGame) {isInGame = inGame;}

    public void sendTheme(String theme){
        itoGame.getThemeManager().getThemePool().add(theme);
        itoGame.putLogger(player.getName()+" send a theme, "+theme+".");
        player.sendMessage("テーマを送信しました("+theme+")");
    }

    public void call(){itoGame.getField().add(this);}
}
