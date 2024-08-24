package net.tv.twitch.chrono_fish.ito_paper.GamePack;

import net.kyori.adventure.text.Component;
import net.tv.twitch.chrono_fish.ito_paper.InvPack.ItoInv;
import org.bukkit.Material;
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
            e.line(0,Component.text(""));
            e.line(1,Component.text(""));
            e.line(2,Component.text(""));
            e.line(3,Component.text(""));
        }
    }

    @EventHandler
    public void onItoStick(PlayerInteractEvent e){
        if(itoGame.isGameRunning()){
            if(e.getItem() != null && e.getItem().getType().equals(Material.STICK)){
                if(itoGame.getItoPlayer(e.getPlayer()) != null){
                    if(e.getAction().equals(Action.LEFT_CLICK_BLOCK) || e.getAction().equals(Action.LEFT_CLICK_AIR)){
                        ItoPlayer itoPlayer = itoGame.getItoPlayer(e.getPlayer());
                        itoPlayer.getPlayer().openInventory(new ItoInv().getMenu());
                    }
                }
            }

        }else{
            if(e.getItem() != null && e.getItem().getType().equals(Material.STICK)){
                if(itoGame.getGameMaster() != null && itoGame.getGameMaster().equals(e.getPlayer())){
                    ItoPlayer itoPlayer = itoGame.getItoPlayer(e.getPlayer());
                    itoPlayer.getPlayer().openInventory(new ItoInv().getMenu());
                }
            }

        }
    }
}
