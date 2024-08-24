package net.tv.twitch.chrono_fish.ito_paper.GamePack;

import net.tv.twitch.chrono_fish.ito_paper.Ito_paper;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.UUID;

public class ItoConfig {

    private final FileConfiguration config;
    private final Ito_paper ito_paper;
    private final ItoGame itoGame;

    public ItoConfig(Ito_paper ito_paper, ItoGame itoGame){
        this.ito_paper = ito_paper;
        this.itoGame = itoGame;
        File configFile = new File(ito_paper.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            ito_paper.saveDefaultConfig();
        }
        this.config = ito_paper.getConfig();
        ito_paper.saveConfig();
    }

    public String getGameMaster(){
        return config.getString("ito.gameMaster");
    }

    public void setGameMaster(UUID uuid){
        config.set("ito.gameMaster",uuid.toString());
        ito_paper.saveConfig();
    }
}
