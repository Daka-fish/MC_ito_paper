package net.tv.twitch.chrono_fish.ito_paper.GamePack;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class GameEvent implements Listener {

    private final ItoGame itoGame;

    public GameEvent(ItoGame itoGame){ this.itoGame = itoGame;}

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        itoGame.getPlayers().add(new ItoPlayer(e.getPlayer()));
    }
}
