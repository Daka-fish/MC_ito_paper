package net.tv.twitch.chrono_fish.ito_paper.GamePack;

import net.tv.twitch.chrono_fish.ito_paper.Ito_paper;
import net.tv.twitch.chrono_fish.ito_paper.Manager.NumberManager;
import net.tv.twitch.chrono_fish.ito_paper.Manager.ThemeManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;

public class ItoGame {

    private final Ito_paper ito_paper;

    private final ItoConfig itoConfig;

    private String theme;
    private final ArrayList<ItoPlayer> itoPlayers;
    private final ArrayList<ItoPlayer> field;
    private String gameMasterUUID;
    private boolean gameRunning;
    private boolean console;

    private final NumberManager numberManager;
    private final ThemeManager themeManager;

    public ItoGame(Ito_paper ito_paper){
        this.ito_paper = ito_paper;
        this.itoConfig = new ItoConfig(ito_paper,this);
        this.theme = "テーマを設定してください";
        this.itoPlayers = new ArrayList<>();
        this.field = new ArrayList<>();
        this.gameRunning = false;
        this.console = true;
        this.numberManager = new NumberManager();
        this.themeManager = new ThemeManager();
        if(!itoConfig.getGameMaster().equalsIgnoreCase("default")){
            this.gameMasterUUID = itoConfig.getGameMaster();
        }
    }

    public Ito_paper getIto_paper() {return ito_paper;}
    public ItoConfig getItoConfig() {return itoConfig;}
    public void setTheme(String theme) {this.theme = theme;}
    public String getTheme() {return theme;}
    public ArrayList<ItoPlayer> getPlayers() {return itoPlayers;}
    public ArrayList<ItoPlayer> getField() {return field;}
    public boolean isGameRunning() {return gameRunning;}
    public void setGameRunning(boolean gameRunning) {this.gameRunning = gameRunning;}
    public String getGameMaster() {return gameMasterUUID;}
    public void setGameMaster(Player gameMaster) {this.gameMasterUUID = gameMaster.getUniqueId().toString();}
    public void setConsole(boolean console) {this.console = console;}

    public ThemeManager getThemeManager() {return themeManager;}

    public void sendMessage(String message){
        itoPlayers.forEach(itoPlayer -> {
            itoPlayer.getPlayer().sendMessage(message);
        });
    }

    public void putLogger(String message){
        if(console){
            ito_paper.putLogger(message);
        }
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
            itoPlayer = new ItoPlayer(this,player);
            itoPlayers.add(itoPlayer);
            return itoPlayer;
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
            sendMessage("+成功");
            for(ItoPlayer itoPlayer : field){
                sendMessage("\n"+itoPlayer.getNumber()+" :"+itoPlayer.getPlayer().getName());
                itoPlayer.getPlayer().giveExp(5);
            }
        }else{
            sendMessage("+失敗");
            for(ItoPlayer itoPlayer : field){
                sendMessage("\n"+itoPlayer.getNumber()+" :"+itoPlayer.getPlayer().getName());
                itoPlayer.getPlayer().giveExp(1);
            }
        }
        gameRunning = false;
    }
}
