package net.tv.twitch.chrono_fish.ito_paper.GamePack;

import net.tv.twitch.chrono_fish.ito_paper.Ito;
import net.tv.twitch.chrono_fish.ito_paper.ScoreboardPack.ItoBoard;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.ArrayList;
import java.util.Collections;

public class ItoGame {

    private final Ito ito;

    private final ItoConfig itoConfig;

    private String theme;
    private final ArrayList<ItoPlayer> itoPlayers;
    private final ArrayList<ItoPlayer> field;
    private String gameMasterUUID;
    private boolean gameRunning;

    private final ArrayList<String> themePool;
    private final ArrayList<Integer> numbers;

    public ItoGame(Ito ito){
        this.ito = ito;
        this.itoConfig = new ItoConfig(ito);
        this.theme = "テーマを設定してください";
        this.itoPlayers = new ArrayList<>();
        this.field = new ArrayList<>();
        this.gameRunning = false;
        if(!itoConfig.getGameMaster().equalsIgnoreCase("default")){
            this.gameMasterUUID = itoConfig.getGameMaster();
        }

        this.themePool = new ArrayList<>();
        this.numbers = new ArrayList<>();
        for(int i=0; i<100; i++) numbers.add(i+1);
    }

    public Ito getIto_paper() {return ito;}
    public ItoConfig getItoConfig() {return itoConfig;}
    public void setTheme(String theme) {this.theme = theme;}
    public String getTheme() {return theme;}
    public ArrayList<ItoPlayer> getPlayers() {return itoPlayers;}
    public ArrayList<ItoPlayer> getField() {return field;}
    public boolean isGameRunning() {return gameRunning;}
    public void setGameRunning(boolean gameRunning) {this.gameRunning = gameRunning;}
    public String getGameMaster() {return gameMasterUUID;}
    public void setGameMaster(Player gameMaster) {this.gameMasterUUID = gameMaster.getUniqueId().toString();}


    public ArrayList<String> getThemePool() {return themePool;}

    public void sendMessage(String message){
        itoPlayers.forEach(itoPlayer -> {
            if(itoPlayer.isInGame()){
                itoPlayer.getPlayer().sendMessage(message);
            }
        });
    }

    public ItoPlayer getItoPlayer(Player player){
        for(ItoPlayer ip : itoPlayers){
            if(ip.getPlayer().equals(player)){
                return ip;
            }
        }
        return null;
    }

    public void setNumbers(){
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
        new OpenNumberTask(this).runTaskTimer(ito,0,20);
    }

    public void join(ItoPlayer itoPlayer){
        itoPlayer.setInGame(true);
        itoPlayer.getPlayer().sendMessage("§9itoに参加しました");
        itoPlayer.setItoBoard(new ItoBoard(this, itoPlayer));
    }

    public void leave(ItoPlayer itoPlayer){
        itoPlayer.setInGame(false);
        itoPlayer.getPlayer().getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        itoPlayer.getPlayer().sendMessage("§9観戦者になりました");
    }
}
