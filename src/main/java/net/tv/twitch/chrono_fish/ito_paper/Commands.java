package net.tv.twitch.chrono_fish.ito_paper;

import net.tv.twitch.chrono_fish.ito_paper.GamePack.ItoGame;
import net.tv.twitch.chrono_fish.ito_paper.GamePack.ItoPlayer;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Commands implements CommandExecutor {

    private final ItoGame itoGame;

    public Commands(ItoGame itoGame){this.itoGame = itoGame;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player snd = (Player) sender;

            if(command.getName().equalsIgnoreCase("ito")){
                if(args.length >= 1){
                    if(args[0].equalsIgnoreCase("gm")){
                        itoGame.setGameMaster(snd);
                        itoGame.getItoConfig().setGameMaster(snd);
                        snd.getInventory().addItem(new ItemStack(Material.STICK));
                        snd.sendMessage("[ito]§7あなたはゲームマスターになりました。棒を左クリックでゲームを開始してください。");
                        return false;
                    }

                    if(args[0].equalsIgnoreCase("help")){
                        snd.sendMessage("§7----- §6Ito Plugin Help §7-----");
                        snd.sendMessage("§6/ito gm §f- ゲームマスターを設定します。");
                        snd.sendMessage("§6/ito join §f- ゲームに参加します。");
                        snd.sendMessage("§6/ito leave §f- ゲームから離脱し、観戦者になります。");
                        snd.sendMessage("§6/ito console §f- コンソールをオン/オフします（ゲームマスターのみ）。");
                        snd.sendMessage("§6/ito help §f- コマンドのヘルプを表示します。");
                        return false;
                    }

                    if(args[0].equalsIgnoreCase("leave")){
                        ItoPlayer itoPlayer = itoGame.getItoPlayer(snd);
                        itoGame.leave(itoPlayer);
                        return false;
                    }

                    if(args[0].equalsIgnoreCase("join")){
                        ItoPlayer itoPlayer = itoGame.getItoPlayer(snd);
                        if(itoPlayer.isInGame()){
                            snd.sendMessage("§c既に参加しています");
                            return false;
                        }
                        itoGame.join(itoPlayer);
                        return false;
                    }
                }
            }

        }
        return false;
    }
}
