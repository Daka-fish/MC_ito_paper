package net.tv.twitch.chrono_fish.ito_paper.GamePack;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;

public class ServerListener implements Listener {

    private final ItoGame itoGame;

    public ServerListener(ItoGame itoGame){ this.itoGame = itoGame;}

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        ItoPlayer joined = new ItoPlayer(itoGame, e.getPlayer());
        itoGame.getPlayers().add(joined);
        e.getPlayer().giveExp(1);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        ArrayList<ItoPlayer> itoPlayers = itoGame.getPlayers();
        itoPlayers.remove(itoGame.getItoPlayer(e.getPlayer()));
    }

    @EventHandler
    public void onSign(SignChangeEvent e){
        if(e.getBlock().getType().equals(Material.ACACIA_SIGN)){
            StringBuilder theme = new StringBuilder();
            for(int i=0; i<4; i++) theme.append(e.getLine(i));
            ItoPlayer itoPlayer = itoGame.getItoPlayer(e.getPlayer());
            itoPlayer.sendTheme(theme.toString());
            e.getPlayer().sendMessage("テーマを送信しました("+theme+")");
            itoGame.putLogger(itoPlayer.getPlayer().getName()+" send a theme, "+theme+".");
            e.line(0,Component.text(""));
            e.line(1,Component.text(""));
            e.line(2,Component.text(""));
            e.line(3,Component.text(""));
        }
    }
}
