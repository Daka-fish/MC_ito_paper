package net.tv.twitch.chrono_fish.ito_paper.GamePack;

import org.bukkit.entity.Player;

public class ItoPlayer {
    private final ItoGame itoGame;
    private final Player player;
    private int number;

    public ItoPlayer(ItoGame itoGame, Player player){
        this.itoGame = itoGame;
        this.player = player;
        number = -1;
    }

    public Player getPlayer() {return player;}

    public int getNumber() {return number;}
    public void setNumber(int number) {this.number = number;}

    public void addTheme(String theme){
        itoGame.getThemeManager().getThemePool().add(theme);
    }
}
