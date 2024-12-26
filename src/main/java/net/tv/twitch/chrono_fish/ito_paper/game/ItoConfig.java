package net.tv.twitch.chrono_fish.ito_paper.game;

import net.tv.twitch.chrono_fish.ito_paper.Ito;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.List;

public class ItoConfig {

    private final FileConfiguration config;
    private final Ito ito;

    public ItoConfig(Ito ito){
        this.ito = ito;
        File configFile = new File(ito.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            ito.saveDefaultConfig();
        }
        this.config = ito.getConfig();
        ito.saveConfig();
    }

    public int getRequiredPlayers(){return config.getInt("ito.required-players");}

    public void addTheme(String theme){
        List<String> themes = config.getStringList("ito.themes");
        themes.add(theme);
        config.set("ito.themes",themes);
        ito.saveConfig();
    }
}
