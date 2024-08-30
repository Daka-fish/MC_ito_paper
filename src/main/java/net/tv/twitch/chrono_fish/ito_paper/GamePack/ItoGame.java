package net.tv.twitch.chrono_fish.ito_paper.GamePack;

import net.tv.twitch.chrono_fish.ito_paper.Ito_paper;
import net.tv.twitch.chrono_fish.ito_paper.Manager.NumberManager;
import net.tv.twitch.chrono_fish.ito_paper.Manager.ThemeManager;
import net.tv.twitch.chrono_fish.ito_paper.ScoreboardPack.ItoBoard;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.ArrayList;
import java.util.Collections;

public class ItoGame {

    private final Ito_paper ito_paper;

    private final ItoConfig itoConfig;

    private String theme;
    private final ArrayList<ItoPlayer> itoPlayers;
    private final ArrayList<ItoPlayer> observers;
    private final ArrayList<ItoPlayer> field;
    private String gameMasterUUID;
    private boolean gameRunning;
    private boolean console;

    private final NumberManager numberManager;
    private final ThemeManager themeManager;

    public ItoGame(Ito_paper ito_paper){
        this.ito_paper = ito_paper;
        this.itoConfig = new ItoConfig(ito_paper);
        this.theme = "テーマを設定してください";
        this.itoPlayers = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.field = new ArrayList<>();
        this.gameRunning = false;
        this.console = itoConfig.getConsole();
        this.numberManager = new NumberManager();
        this.themeManager = new ThemeManager();
        if(!itoConfig.getGameMaster().equalsIgnoreCase("default")){
            this.gameMasterUUID = itoConfig.getGameMaster();
        }else{
            putLogger("Game Master is empty! it needs sending /ito gm");
        }
    }

    public Ito_paper getIto_paper() {return ito_paper;}
    public ItoConfig getItoConfig() {return itoConfig;}
    public void setTheme(String theme) {this.theme = theme;}
    public String getTheme() {return theme;}
    public ArrayList<ItoPlayer> getPlayers() {return itoPlayers;}
    public ArrayList<ItoPlayer> getObservers() {return observers;}
    public ArrayList<ItoPlayer> getField() {return field;}
    public boolean isGameRunning() {return gameRunning;}
    public void setGameRunning(boolean gameRunning) {this.gameRunning = gameRunning;}
    public String getGameMaster() {return gameMasterUUID;}
    public void setGameMaster(Player gameMaster) {this.gameMasterUUID = gameMaster.getUniqueId().toString();}
    public boolean isConsole() {return console;}
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
        for(ItoPlayer ip : itoPlayers){
            if(ip.getPlayer().equals(player)){
                itoPlayer = ip;
                break;
            }
        }
        if(itoPlayer == null){
            for(ItoPlayer ip : observers){
                if(ip.getPlayer().equals(player)){
                    itoPlayer = ip;
                    break;
                }
            }
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
            sendMessage("ゲーム成功、経験値をたくさん獲得！");
            for(ItoPlayer itoPlayer : field){
                itoPlayer.getPlayer().giveExp(5);
            }
        }else{
            sendMessage("ゲーム失敗、経験値をちょっと獲得！");
            for(ItoPlayer itoPlayer : field){
                sendMessage("\n"+itoPlayer.getNumber()+" :"+itoPlayer.getPlayer().getName());
                itoPlayer.getPlayer().giveExp(1);
            }
        }
        gameRunning = false;
        itoConfig.addTheme(theme);
    }

    public void openNumber(){
        sendMessage("数字を開示します");
        new OpenNumberTask(this).runTaskTimer(ito_paper,0,20);
    }

    public void join(ItoPlayer itoPlayer){
        itoPlayers.add(itoPlayer);
        observers.remove(itoPlayer);
        itoPlayer.setInGame(true);
        itoPlayer.getPlayer().sendMessage("§9itoに参加しました");
        itoPlayer.setItoBoard(new ItoBoard(this, itoPlayer));
    }

    public void leave(ItoPlayer itoPlayer){
        itoPlayers.remove(itoPlayer);
        observers.add(itoPlayer);
        itoPlayer.setInGame(false);
        itoPlayer.getPlayer().getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        itoPlayer.getPlayer().sendMessage("§9観戦者になりました");
    }
}
