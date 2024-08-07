package net.tv.twitch.chrono_fish.ito_paper;

import net.tv.twitch.chrono_fish.ito_paper.GamePack.Commands;
import net.tv.twitch.chrono_fish.ito_paper.GamePack.ItoGame;
import org.bukkit.plugin.java.JavaPlugin;

public final class Ito_paper extends JavaPlugin {

    @Override
    public void onEnable() {
        ItoGame itoGame = new ItoGame();
        getCommand("menu").setExecutor(new Commands());
    }
}
