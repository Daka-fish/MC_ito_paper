package net.tv.twitch.chrono_fish.ito_paper.GamePack;

import net.tv.twitch.chrono_fish.ito_paper.Ito_paper;
import net.tv.twitch.chrono_fish.ito_paper.Manager.NumberManager;
import net.tv.twitch.chrono_fish.ito_paper.Manager.ThemeManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ItoGame {

    private final Ito_paper ito_paper;

    private Location gameLocation;

    private String theme;
    private final ArrayList<ItoPlayer> itoPlayers;
    private final ArrayList<ItoPlayer> field;
    private boolean gameRunning;

    private final NumberManager numberManager;
    private final ThemeManager themeManager;

    public ItoGame(Ito_paper ito_paper){
        this.ito_paper = ito_paper;
        this.gameLocation = new Location(ito_paper.getServer().getWorld("world"), 8.5,63,24);
        this.theme = "テーマを設定してください";
        this.itoPlayers = new ArrayList<>();
        this.field = new ArrayList<>();
        this.gameRunning = false;
        this.numberManager = new NumberManager();
        this.themeManager = new ThemeManager();
    }

    public Ito_paper getIto_paper() {return ito_paper;}
    public void setTheme(String theme) {this.theme = theme;}
    public String getTheme() {return theme;}
    public ArrayList<ItoPlayer> getPlayers() {return itoPlayers;}
    public ArrayList<ItoPlayer> getField() {return field;}
    public boolean isGameRunning() {return gameRunning;}
    public void setGameRunning(boolean gameRunning) {this.gameRunning = gameRunning;}

    public ThemeManager getThemeManager() {return themeManager;}

    public void sendMessage(String message){
        itoPlayers.forEach(itoPlayer -> {
            itoPlayer.getPlayer().sendMessage(message);
        });
    }

    public ItoPlayer getItoPlayer(Player player){
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
            int number = numbers.get(i);
            itoPlayer.setNumber(number);
            i++;
        }
    }

    public void check(){
        boolean success = true;
        for(int i=1; i<itoPlayers.size(); i++){
            if(itoPlayers.get(i).getNumber()<itoPlayers.get(i-1).getNumber()){
                success = false;
                break;
            }
        }
        if(success){
            sendMessage("成功");
        }else{
            sendMessage("失敗");
        }
        gameRunning = false;
        field.sort(Comparator.comparingInt(ItoPlayer::getNumber));
    }
}
