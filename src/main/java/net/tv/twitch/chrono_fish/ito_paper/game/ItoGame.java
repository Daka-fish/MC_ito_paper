package net.tv.twitch.chrono_fish.ito_paper.game;

import net.tv.twitch.chrono_fish.ito_paper.Ito;
import net.tv.twitch.chrono_fish.ito_paper.ScoreboardPack.ItoBoard;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.ArrayList;
import java.util.Collections;

public class ItoGame {

    private final Ito ito;

    private final ItoConfig itoConfig;

    private String theme;
    private final ArrayList<ItoPlayer> itoPlayers;
    private final ArrayList<ItoPlayer> callList;
    private boolean gameRunning;

    private final ArrayList<String> themePool;
    private final ArrayList<Integer> numbers;

    public ItoGame(Ito ito){
        this.ito = ito;
        this.itoConfig = new ItoConfig(ito);
        this.theme = "テーマを設定してください";
        this.itoPlayers = new ArrayList<>();
        this.callList = new ArrayList<>();
        this.gameRunning = false;
        this.themePool = new ArrayList<>();
        this.numbers = new ArrayList<>();
    }

    public ItoConfig getItoConfig() {return itoConfig;}

    public void setTheme(String theme) {this.theme = theme;}
    public String getTheme() {return theme;}

    public ArrayList<ItoPlayer> getPlayers() {return itoPlayers;}
    public ArrayList<ItoPlayer> getCallList() {return callList;}

    public boolean isGameRunning() {return gameRunning;}
    public void setGameRunning(boolean gameRunning) {this.gameRunning = gameRunning;}

    public ArrayList<String> getThemePool() {return themePool;}

    public void setNumbers(){for(int i=0; i<100; i++) numbers.add(i+1);}

    public void addTheme(String theme){themePool.add(theme);}

    public void sendMessage(String message){itoPlayers.forEach(itoPlayer -> itoPlayer.sendMessage("[ito]"+message));}

    public ItoPlayer getItoPlayer(Player player){
        for(ItoPlayer itoPlayer : itoPlayers){
            if(itoPlayer.getPlayer().equals(player)){
                return itoPlayer;
            }
        }
        return null;
    }

    public int getCallOrder(ItoPlayer itoPlayer){return callList.indexOf(itoPlayer)+1;}

    public void setNumber(ItoPlayer itoPlayer){
        int number = numbers.get(0);
        itoPlayer.setNumber(number);
        numbers.remove(0);
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
            sendMessage("ゲーム成功");
        }else{
            sendMessage("ゲーム失敗");
        }
        gameRunning = false;
        itoConfig.addTheme(theme);
    }

    public void openNumber(){
        sendMessage("数字を開示します");
        new OpenNumberTask(this).runTaskTimer(ito,0,20);
    }

    public void join(ItoPlayer itoPlayer){
        itoPlayer.sendMessage("§9itoに参加しました");
        itoPlayer.setItoBoard(new ItoBoard(this, itoPlayer));
    }

    public void leave(ItoPlayer itoPlayer){
        itoPlayer.getPlayer().getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        itoPlayer.sendMessage("§9観戦者になりました");
    }

    public void start(){
        setNumbers();
        Collections.shuffle(themePool);
        Collections.shuffle(numbers);
        setTheme(themePool.get(0));
        setGameRunning(true);
        getCallList().clear();
        sendMessage("ゲームを開始します、テーマは【§a"+getTheme()+"§f】です");
        getPlayers().forEach(itoPlayer -> {
            setNumber(itoPlayer);
            itoPlayer.setHasCall(false);
            itoPlayer.getItoBoard().reloadTheme();
            itoPlayer.getItoBoard().reloadCallOrder();
            itoPlayer.getItoBoard().reloadNumber();
            itoPlayer.getPlayer().getInventory().addItem(new ItemStack(Material.STICK));
        });
        themePool.remove(getTheme());
    }

    public void finish(){
        openNumber();
        //check();
    }
}
