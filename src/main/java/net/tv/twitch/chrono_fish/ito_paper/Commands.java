package net.tv.twitch.chrono_fish.ito_paper;

import net.tv.twitch.chrono_fish.ito_paper.GamePack.ItoGame;
import net.tv.twitch.chrono_fish.ito_paper.GamePack.ItoPlayer;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;

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
                        itoGame.putLogger(snd.getName()+" becomes the game master");
                        snd.getInventory().addItem(new ItemStack(Material.STICK));
                        return false;
                    }

                    if(args[0].equalsIgnoreCase("leave")){
                        ItoPlayer itoPlayer = itoGame.getItoPlayer(snd);
                        itoGame.leave(itoPlayer);
                        itoGame.putLogger(itoPlayer.getPlayer().getName()+" becomes a observer");
                        return false;
                    }

                    if(args[0].equalsIgnoreCase("join")){
                        ItoPlayer itoPlayer = null;
                        for(ItoPlayer ip : itoGame.getObservers()){
                            if(ip.getPlayer().equals(snd)){
                                itoPlayer = ip;
                                break;
                            }
                        }
                        if(itoPlayer == null){
                            snd.sendMessage("§c既に参加しています");
                            return false;
                        }
                        itoGame.join(itoPlayer);
                        itoGame.putLogger(itoPlayer.getPlayer().getName()+" has joined to ito.");
                        return false;
                    }

                    if(args[0].equalsIgnoreCase("console")){
                        if(itoGame.getGameMaster().equalsIgnoreCase(itoGame.getItoPlayer(snd).getPlayer().getUniqueId().toString())){
                            itoGame.setConsole(!itoGame.isConsole());
                            if(itoGame.isConsole()){
                                snd.sendMessage("§7コンソールをオンにしました");
                            }else{
                                snd.sendMessage("§7コンソールをオフにしました");
                            }
                            itoGame.getItoConfig().setConsole(itoGame.isConsole());
                        }else{
                            snd.sendMessage("§c権限がありません");
                        }
                        return false;
                    }
                }
            }

        }
        return false;
    }
}
