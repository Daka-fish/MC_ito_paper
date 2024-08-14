package net.tv.twitch.chrono_fish.ito_paper.GamePack;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;

public class ItoGame {

    private String theme;
    private final ArrayList<ItoPlayer> itoPlayers;
    private final ArrayList<ItoPlayer> field;
    private boolean gameRunning;

    private final NumberManager numberManager;
    private final ThemeManager themeManager;

    public ItoGame(){
        theme = "テーマを設定してください";
        itoPlayers = new ArrayList<>();
        field = new ArrayList<>();
        gameRunning = false;
        numberManager = new NumberManager();
        themeManager = new ThemeManager();
    }

    public void setTheme(String theme) {this.theme = theme;}
    public String getTheme() {return theme;}
    public ArrayList<ItoPlayer> getPlayers() {return itoPlayers;}
    public ArrayList<ItoPlayer> getField() {return field;}
    public boolean isGameRunning() {return gameRunning;}
    public void setGameRunning(boolean gameRunning) {this.gameRunning = gameRunning;}

    public ThemeManager getThemeManager() {return themeManager;}

    public void broadcastItoPlayers(String message){
        itoPlayers.forEach(itoPlayer -> {
            itoPlayer.getPlayer().sendMessage(message);
        });
    }

    public ItoPlayer findItoPlayer(Player player){
        ItoPlayer itoPlayer = null;
        for (ItoPlayer ip : itoPlayers) {
            if (ip.getPlayer().equals(player)) {
                itoPlayer = ip;
                break;
            }
        }
        if(itoPlayer == null){
            return new ItoPlayer(this,player);
        }
        return itoPlayer;
    }

    public void setNumbers(){
        ArrayList<Integer> numbers = numberManager.getNumbers();
        Collections.shuffle(numbers);
        int i = 0;
        for(ItoPlayer itoPlayer : itoPlayers){
            itoPlayer.setNumber(numbers.get(i));
            i++;
        }
    }

    public void check(){
        ArrayList<Integer> sortNumber = new ArrayList<>();
        for(ItoPlayer itoPlayer : field){
            sortNumber.add(itoPlayer.getNumber());
        }
        ArrayList<Integer> notSortNumber = sortNumber;
        Collections.sort(sortNumber);
        if(sortNumber.equals(notSortNumber)){
            broadcastItoPlayers("成功");
        }else{
            broadcastItoPlayers("失敗");
        }
        gameRunning = false;
    }
}
