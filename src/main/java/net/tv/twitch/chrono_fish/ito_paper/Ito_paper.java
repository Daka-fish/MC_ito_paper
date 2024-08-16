package net.tv.twitch.chrono_fish.ito_paper;

import net.tv.twitch.chrono_fish.ito_paper.GamePack.Commands;
import net.tv.twitch.chrono_fish.ito_paper.GamePack.ServerEvent;
import net.tv.twitch.chrono_fish.ito_paper.GamePack.ItoGame;
import net.tv.twitch.chrono_fish.ito_paper.InvPack.InvEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Ito_paper extends JavaPlugin {

    @Override
    public void onEnable() {
        ItoGame itoGame = new ItoGame(this);
        getCommand("menu").setExecutor(new Commands());
        Bukkit.getPluginManager().registerEvents(new InvEvent(itoGame),this);
        Bukkit.getPluginManager().registerEvents(new ServerEvent(itoGame),this);
    }
}
