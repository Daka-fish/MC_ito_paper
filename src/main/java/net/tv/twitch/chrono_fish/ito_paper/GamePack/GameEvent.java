package net.tv.twitch.chrono_fish.ito_paper.GamePack;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;

public class GameEvent implements Listener {

    private final ItoGame itoGame;

    public GameEvent(ItoGame itoGame){ this.itoGame = itoGame;}

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        ItoPlayer joined = new ItoPlayer(itoGame, e.getPlayer());
        itoGame.getPlayers().add(joined);
        e.getPlayer().giveExp(1);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        ArrayList<ItoPlayer> itoPlayers = itoGame.getPlayers();
        itoPlayers.remove(itoGame.findItoPlayer(e.getPlayer()));
    }
}
