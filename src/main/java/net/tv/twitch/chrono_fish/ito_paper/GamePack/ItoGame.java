package net.tv.twitch.chrono_fish.ito_paper.GamePack;

import net.tv.twitch.chrono_fish.ito_paper.Ito_paper;
import net.tv.twitch.chrono_fish.ito_paper.Manager.NumberManager;
import net.tv.twitch.chrono_fish.ito_paper.Manager.ThemeManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;

public class ItoGame {

    private final Ito_paper ito_paper;

    private String theme;
    private final ArrayList<ItoPlayer> itoPlayers;
    private final ArrayList<ItoPlayer> field;
    private boolean gameRunning;

    private final NumberManager numberManager;
    private final ThemeManager themeManager;

    public ItoGame(Ito_paper ito_paper){
        this.ito_paper = ito_paper;
        this.theme = "テーマを設定してください";
        this.itoPlayers = new ArrayList<>();
        this.field = new ArrayList<>();
        this.gameRunning = false;
        this.numberManager = new NumberManager();
        this.themeManager = new ThemeManager();
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

        broadcastItoPlayers("10秒後にリセットします");
        Bukkit.getScheduler().runTaskLater(ito_paper,()->{
            itoPlayers.forEach(itoPlayer -> {
                itoPlayer.setNumber(-1);
                itoPlayer.setCallOrder(-1);
                itoPlayer.setHasCall(false);
                broadcastItoPlayers("リセットしました");
            });
        },200);

        gameRunning = false;
    }
}
