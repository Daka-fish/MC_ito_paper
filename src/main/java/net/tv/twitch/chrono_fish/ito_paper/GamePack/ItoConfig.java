package net.tv.twitch.chrono_fish.ito_paper.GamePack;

import net.tv.twitch.chrono_fish.ito_paper.Ito_paper;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;

public class ItoConfig {

    private final FileConfiguration config;
    private final Ito_paper ito_paper;

    public ItoConfig(Ito_paper ito_paper){
        this.ito_paper = ito_paper;
        File configFile = new File(ito_paper.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            ito_paper.saveDefaultConfig();
        }
        this.config = ito_paper.getConfig();
        ito_paper.saveConfig();
    }

    public String getGameMaster(){return config.getString("ito.gameMaster.uuid");}

    public void setGameMaster(Player player){
        config.set("ito.gameMaster.uuid",player.getUniqueId().toString());
        config.set("ito.gameMaster.name",player.getName());
        ito_paper.saveConfig();
    }

    public void setConsole(boolean console){
        config.set("ito.console",console);
        ito_paper.saveConfig();
    }

    public boolean getConsole(){return config.getBoolean("ito.console");}

    public int getRequiredPlayers(){return config.getInt("ito.required-players");}

    public void addTheme(String theme){
        List<String> themes = config.getStringList("ito.themes");
        themes.add(theme);
        config.set("ito.themes",themes);
        ito_paper.saveConfig();
    }
}
