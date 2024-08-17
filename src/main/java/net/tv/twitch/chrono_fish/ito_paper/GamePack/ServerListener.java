package net.tv.twitch.chrono_fish.ito_paper.GamePack;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
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
            Sign sign = (Sign) e.getBlock().getState();
            if(e.line(0).equals(Component.text("ENTER THE THEME"))){
                StringBuilder theme = new StringBuilder();
                for(int i=1; i<4; i++) theme.append(e.getLine(i));
                itoGame.getItoPlayer(e.getPlayer()).sendTheme(theme.toString());
                e.getPlayer().sendMessage("テーマを送信しました("+theme+")");
                e.setLine(1,"");
                e.setLine(2,"");
                e.setLine(3,"");
            }
        }
    }
}
