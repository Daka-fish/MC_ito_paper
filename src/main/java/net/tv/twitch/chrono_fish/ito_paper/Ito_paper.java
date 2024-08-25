package net.tv.twitch.chrono_fish.ito_paper;

import net.tv.twitch.chrono_fish.ito_paper.GamePack.ItoGame;
import net.tv.twitch.chrono_fish.ito_paper.Listener.ServerListener;
import net.tv.twitch.chrono_fish.ito_paper.Listener.InvListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Ito_paper extends JavaPlugin {

    @Override
    public void onEnable() {
        ItoGame itoGame = new ItoGame(this);
        getCommand("ito").setExecutor(new Commands(itoGame));
        Bukkit.getPluginManager().registerEvents(new InvListener(itoGame),this);
        Bukkit.getPluginManager().registerEvents(new ServerListener(itoGame),this);
    }

    public void putLogger(String message){getLogger().info(message);}
}
