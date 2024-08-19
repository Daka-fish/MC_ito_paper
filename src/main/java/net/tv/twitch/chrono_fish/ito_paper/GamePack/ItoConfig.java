package net.tv.twitch.chrono_fish.ito_paper.GamePack;

import net.tv.twitch.chrono_fish.ito_paper.Ito_paper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

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

    public Location getSignLocation(){
        return new Location(Bukkit.getWorld("world"),
                config.getDouble("ito.lobby.x"),
                config.getDouble("ito.lobby.y"),
                config.getDouble("ito.lobby.z"));
    }

    public void setSignLocation(Location location){
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        config.set("ito.lobby.x",x);
        config.set("ito.lobby.y",y);
        config.set("ito.lobby.z",z);
        ito_paper.saveConfig();
    }

    public boolean equalGameLocation(Location location){
        Location loc1 = itoGame.getGameLocation();
        System.out.println(loc1);
        System.out.println(location);
        double tolerance = 1.5;
        return loc1.getWorld().equals(location.getWorld())
                && Math.abs(loc1.getBlockX() - location.getBlockX()) < tolerance
                && Math.abs(loc1.getBlockY() - location.getBlockY()) < tolerance
                && Math.abs(loc1.getBlockZ() - location.getBlockZ()) < tolerance;
    }
}
