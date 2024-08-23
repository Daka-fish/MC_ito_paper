package net.tv.twitch.chrono_fish.ito_paper;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextDecoration;
import net.tv.twitch.chrono_fish.ito_paper.GamePack.ItoGame;
import net.tv.twitch.chrono_fish.ito_paper.GamePack.ItoPlayer;
import net.tv.twitch.chrono_fish.ito_paper.InvPack.ItoInv;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Rotatable;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.BookMeta;
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

            if(command.getName().equalsIgnoreCase("ito")){
                if(args.length >= 1){
                    if(args[0].equalsIgnoreCase("start")){
                        for(ItoPlayer itoPlayer : itoGame.getPlayers()){
                            itoPlayer.getPlayer().getInventory().addItem(new ItemStack(Material.STICK));
                        }
                        itoGame.setGameRunning(true);
                        snd.sendMessage("ゲームを開始します");
                    }

                    if(args[0].equalsIgnoreCase("gm")){
                        snd.getInventory().addItem(new ItemStack(Material.STICK));
//                        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
//                        BookMeta meta = (BookMeta) book.getItemMeta();
//                        meta.setTitle("Game Book");
//                        meta.setAuthor("You are the Game Master");
//                        meta.addPages(Component.text("\n\n+Itoを開始します")
//                                    .decorate(TextDecoration.UNDERLINED)
//                                    .clickEvent(ClickEvent.runCommand("/ito start"))
//                                    .hoverEvent(HoverEvent.showText(Component.text("クリックでゲームを開始します")))
//                                .append((Component.text("\n\n参加者一覧を表示します")
//                                        .decorate(TextDecoration.UNDERLINED)
//                                        .clickEvent(ClickEvent.runCommand("/ito list"))
//                                        .hoverEvent(HoverEvent.showText(Component.text("クリックで参加者一覧を表示します"))))));
//                        book.setItemMeta(meta);
//                        snd.getInventory().addItem(book);
                    }

                    if(args[0].equalsIgnoreCase("leave")){
                        ItoPlayer itoPlayer = itoGame.getItoPlayer(snd);
                        itoPlayer.getPlayer().getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
                        snd.sendMessage("§c"+"itoから退室しました");
                        itoGame.getPlayers().remove(itoPlayer);
                        itoGame.putLogger(itoPlayer.getPlayer().getName()+" has left from ito.");
                        return false;
                    }

                    if(args[0].equalsIgnoreCase("join")){
                        ItoPlayer itoPlayer = new ItoPlayer(itoGame,snd);
                        snd.sendMessage("itoに参加しました");
                        itoGame.getPlayers().add(itoPlayer);
                        itoGame.putLogger(itoPlayer.getPlayer().getName()+" has joined to ito.");
                        return false;
                    }
                }
            }

        }
        return false;
    }
}
