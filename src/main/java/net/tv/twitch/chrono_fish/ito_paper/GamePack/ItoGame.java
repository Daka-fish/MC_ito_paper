package net.tv.twitch.chrono_fish.ito_paper.GamePack;

import java.util.ArrayList;

public class ItoGame {

    private String theme;
    private ArrayList<ItoPlayer> itoPlayers;

    private final ThemeManager themeManager;

    public ItoGame(){
        theme = "テーマを設定してください";
        themeManager = new ThemeManager();
    }

    public void setTheme(String theme) {this.theme = theme;}
    public String getTheme() {return theme;}

    public ArrayList<ItoPlayer> getPlayers() {return itoPlayers;}

    public ThemeManager getThemeManager() {return themeManager;}
}
