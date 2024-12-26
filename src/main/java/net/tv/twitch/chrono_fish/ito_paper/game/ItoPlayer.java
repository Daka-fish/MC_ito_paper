package net.tv.twitch.chrono_fish.ito_paper.game;

import net.tv.twitch.chrono_fish.ito_paper.ScoreboardPack.ItoBoard;
import org.bukkit.entity.Player;

public class ItoPlayer {

    private final ItoGame itoGame;

    private final Player player;
    private final String name;

    private int number;
    private boolean hasCall;
    private boolean hasOpen;

    private ItoBoard itoBoard;

    public ItoPlayer(ItoGame itoGame, Player player){
        this.itoGame = itoGame;
        this.player = player;
        this.name = player.getName();

        this.number = -1;
        this.hasCall = false;
        this.hasOpen = false;

        this.itoBoard = new ItoBoard(itoGame, this);
        player.setScoreboard(itoBoard.getBoard());
    }

    public Player getPlayer() {return player;}

    public String getName() {return name;}

    public void setItoBoard(ItoBoard itoBoard) {
        this.itoBoard = itoBoard;
        player.setScoreboard(itoBoard.getBoard());
    }

    public ItoBoard getItoBoard(){return itoBoard;}

    public int getNumber() {return number;}
    public void setNumber(int number) {this.number = number;}

    public boolean hasCall() {return hasCall;}
    public void setHasCall(boolean hasCall) {this.hasCall = hasCall;}

    public boolean isHasOpen() {return hasOpen;}
    public void setHasOpen(boolean hasOpen) {this.hasOpen = hasOpen;}

    public void sendTheme(String theme){
        if(theme.isEmpty()){
            player.sendMessage("§c空のテーマは提出できません");
            return;
        }
        itoGame.addTheme(theme);
        player.sendMessage("テーマを送信しました("+theme+")");
    }

    public void sendMessage(String message){if(player != null) player.sendMessage(message);}

    public void call(){
        itoGame.getCallList().add(this);
        itoGame.sendMessage("§e"+player.getName()+"§fがコールしました(コールした順番:"+(itoGame.getCallList().size())+")");
        itoBoard.reloadCallOrder();
        setHasCall(true);
    }

    public void openNumber(){
        if(!hasOpen){
            itoGame.sendMessage(name+"の番号: "+number);
            setHasOpen(true);
        }else{
            sendMessage("§c既に開示しています");
        }
    }
}
