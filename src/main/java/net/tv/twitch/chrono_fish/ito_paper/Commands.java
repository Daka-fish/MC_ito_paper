package net.tv.twitch.chrono_fish.ito_paper;

import net.kyori.adventure.text.Component;
import net.tv.twitch.chrono_fish.ito_paper.GamePack.ItoGame;
import net.tv.twitch.chrono_fish.ito_paper.GamePack.ItoPlayer;
import net.tv.twitch.chrono_fish.ito_paper.InvPack.ItoInv;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.DisplaySlot;

public class Commands implements CommandExecutor {

    private ItoGame itoGame;

    public Commands(ItoGame itoGame){
        this.itoGame = itoGame;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player snd = (Player) sender;
            if(command.getName().equalsIgnoreCase("menu")) {
                if(args.length == 0){
                    snd.openInventory(new ItoInv().getMenu());
                    return false;
                }
            }
            if(command.getName().equalsIgnoreCase("sign")) {
                Location location = snd.getLocation();
                Block block = location.getBlock();
                block.setType(Material.ACACIA_SIGN);

                Sign sign = (Sign) block.getState();

                sign.line(0,Component.text("ENTER THE THEME"));
                sign.update();
                snd.sendMessage("識別子付きの看板を設置しました");
            }
            if(command.getName().equalsIgnoreCase("ito")){
                if(args.length == 1){
                    if(args[0].equalsIgnoreCase("leave")){
                        ItoPlayer itoPlayer = itoGame.getItoPlayer(snd);
                        itoGame.getPlayers().remove(itoPlayer);
                        itoPlayer.getPlayer().getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
                        snd.sendMessage("§c"+"itoから退室しました");
                        return false;
                    }
                    if(args[0].equalsIgnoreCase("join")){
                        ItoPlayer itoPlayer = new ItoPlayer(itoGame,snd);
                        itoGame.getPlayers().add(itoPlayer);
                        snd.sendMessage("itoに参加しました");
                        return false;
                    }
                }
            }
        }
        return false;
    }
}
