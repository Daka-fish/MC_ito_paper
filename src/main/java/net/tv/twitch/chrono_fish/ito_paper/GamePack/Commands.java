package net.tv.twitch.chrono_fish.ito_paper.GamePack;

import net.tv.twitch.chrono_fish.ito_paper.InvPack.ItoInv;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player snd = (Player) sender;
            if(command.getName().equalsIgnoreCase("menu")){
                snd.openInventory(new ItoInv().getMenu());
            }
        }
        return false;
    }
}
