package net.tv.twitch.chrono_fish.ito_paper.GamePack;

import net.tv.twitch.chrono_fish.ito_paper.ScoreboardPack.ItoBoard;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class GameEvent implements Listener {

    private final ItoGame itoGame;

    public GameEvent(ItoGame itoGame){ this.itoGame = itoGame;}

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        ItoPlayer joined = new ItoPlayer(itoGame, e.getPlayer());
        itoGame.getPlayers().add(joined);
        e.getPlayer().setScoreboard(new ItoBoard(joined).getBoard());
    }
}
