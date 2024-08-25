package net.tv.twitch.chrono_fish.ito_paper.GamePack;

import net.tv.twitch.chrono_fish.ito_paper.Ito_paper;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

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
        return config.getString("ito.gameMaster.uuid");
    }

    public void setGameMaster(Player player){
        config.set("ito.gameMaster.uuid",player.getUniqueId().toString());
        config.set("ito.gameMaster.name",player.getName());
        ito_paper.saveConfig();
    }
}
