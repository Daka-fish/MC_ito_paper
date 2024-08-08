package net.tv.twitch.chrono_fish.ito_paper.GamePack;

import java.util.ArrayList;
import java.util.Collections;

public class ItoGame {

    private String theme;
    private final ArrayList<ItoPlayer> itoPlayers;
    private final ArrayList<ItoPlayer> field;

    private final NumberManager numberManager;
    private final ThemeManager themeManager;

    public ItoGame(){
        theme = "テーマを設定してください";
        itoPlayers = new ArrayList<>();
        field = new ArrayList<>();
        numberManager = new NumberManager();
        themeManager = new ThemeManager();
    }

    public void setTheme(String theme) {this.theme = theme;}
    public String getTheme() {return theme;}
    public ArrayList<ItoPlayer> getPlayers() {return itoPlayers;}
    public ArrayList<ItoPlayer> getField() {return field;}

    public ThemeManager getThemeManager() {return themeManager;}

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
