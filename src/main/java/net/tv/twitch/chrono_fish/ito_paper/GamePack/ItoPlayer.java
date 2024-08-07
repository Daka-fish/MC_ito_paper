package net.tv.twitch.chrono_fish.ito_paper.GamePack;

import org.bukkit.entity.Player;

public class ItoPlayer {
    private final Player player;
    private int number;

    public ItoPlayer(Player player){
        this.player = player;
        number = -1;
    }

    public Player getPlayer() {return player;}

    public int getNumber() {return number;}
    public void setNumber(int number) {this.number = number;}
}
