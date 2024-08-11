package net.tv.twitch.chrono_fish.ito_paper.GamePack;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;

public class ItoGame {

    private String theme;
    private final String defaultTheme = "テーマを設定してください";
    private final ArrayList<ItoPlayer> itoPlayers;
    private final ArrayList<ItoPlayer> field;
    private boolean GameRunning;

    private final NumberManager numberManager;
    private final ThemeManager themeManager;

    public ItoGame(){
        theme = defaultTheme;
        itoPlayers = new ArrayList<>();
        field = new ArrayList<>();
        GameRunning = false;
        numberManager = new NumberManager();
        themeManager = new ThemeManager();
    }

    public String getDefaultTheme() {return defaultTheme;}
    public void setTheme(String theme) {this.theme = theme;}
    public String getTheme() {return theme;}
    public ArrayList<ItoPlayer> getPlayers() {return itoPlayers;}
    public ArrayList<ItoPlayer> getField() {return field;}
    public boolean isGameRunning() {return GameRunning;}
    public void setGameRunning(boolean gameRunning) {GameRunning = gameRunning;}

    public ThemeManager getThemeManager() {return themeManager;}

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

    public boolean check(){
        ArrayList<Integer> sortNumber = new ArrayList<>();
        for(ItoPlayer itoPlayer : field){
            sortNumber.add(itoPlayer.getNumber());
        }
        ArrayList<Integer> notSortNumber = sortNumber;
        Collections.sort(sortNumber);
        return sortNumber.equals(notSortNumber);
    }
}
