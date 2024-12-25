package net.tv.twitch.chrono_fish.ito_paper.Manager;

import net.tv.twitch.chrono_fish.ito_paper.game.ItoGame;
import net.tv.twitch.chrono_fish.ito_paper.game.ItoPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;

public class InvManager {
    private final ItoGame itoGame;

    public InvManager(ItoGame itoGame){this.itoGame = itoGame;}

    public void invAction(Player player, Material material){
        ArrayList<String> themePool = itoGame.getThemePool();
        boolean isGameMaster = itoGame.getItoConfig().getGameMaster().equals(player.getUniqueId().toString());
        switch(material){
            case DIAMOND_SWORD:
                player.closeInventory();
                if(isGameMaster){
                    if(!themePool.isEmpty() && itoGame.getPlayers().size() >= itoGame.getItoConfig().getRequiredPlayers()){
                        Collections.shuffle(themePool);
                        itoGame.setTheme(themePool.get(0));
                        itoGame.setGameRunning(true);
                        itoGame.setNumbers();
                        itoGame.getCallList().clear();
                        itoGame.sendMessage("ゲームを開始します、テーマは【§a"+itoGame.getTheme()+"§f】です");
                        itoGame.getPlayers().forEach(itoPlayer -> {
                            itoPlayer.setHasCall(false);
                            itoPlayer.getItoBoard().reloadTheme();
                            itoPlayer.getItoBoard().reloadCallOrder();
                            itoPlayer.getItoBoard().reloadNumber();
                            if(!itoPlayer.getPlayer().getUniqueId().toString().equalsIgnoreCase(itoGame.getGameMaster())){
                                itoPlayer.getPlayer().getInventory().addItem(new ItemStack(Material.STICK));
                            }
                        });
                        themePool.remove(itoGame.getTheme());
                        return;
                    }else{
                        player.sendMessage("§cテーマプールが空か、プレイヤーの数が足りません");
                    }
                }else{
                    player.sendMessage("§c権限がありません");
                }
                break;

            case PAPER:
                player.closeInventory();
                if(isGameMaster){
                    StringBuilder themesMessage = new StringBuilder("+テーマ一覧("+themePool.size()+")");
                    themePool.forEach(theme -> themesMessage.append("\n・").append(theme));
                    itoGame.sendMessage(themesMessage.toString());
                }else{
                    player.sendMessage("§c権限がありません");
                }
                break;

            case TORCH:
                player.closeInventory();
                if(isGameMaster){
                    if(itoGame.isGameRunning()){
                        if(itoGame.getCallList().size() < itoGame.getPlayers().size()){
                            player.sendMessage("§cコールの数が不足しています(あと§a"+(itoGame.getPlayers().size()-itoGame.getCallList().size())+"§c人)");
                            return;
                        }
                        itoGame.openNumber();
                    }else{
                        player.sendMessage("§cゲームが開始されていません");
                    }
                }else{
                    player.sendMessage("§c権限がありません");
                }
                break;

            case SNOWBALL:
                player.closeInventory();
                if(isGameMaster && itoGame.isGameRunning()){
                    if(!themePool.isEmpty()){
                        Collections.shuffle(themePool);
                        itoGame.setTheme(themePool.get(0));
                        itoGame.getPlayers().forEach(itoPlayer -> itoPlayer.getItoBoard().reloadTheme());
                        themePool.remove(itoGame.getTheme());
                        itoGame.sendMessage("テーマが【§a"+itoGame.getTheme()+"§f】に変更されました(残り:"+themePool.size()+")");
                    }else{
                        player.sendMessage("§cテーマプールが空のため変更できません");
                    }
                }else{
                    player.sendMessage("§c権限がないか、進行中のゲームがありません");
                }
                break;

            case BREAD:
                player.closeInventory();
                if(itoGame.isGameRunning()){
                    ItoPlayer itoPlayer = itoGame.getItoPlayer(player);
                    if(!itoPlayer.hasCall()){
                        itoPlayer.call();
                        itoGame.sendMessage("§e"+player.getName()+"§fがコールしました(コールした順番:"+(itoGame.getCallList().size())+")");
                        itoPlayer.getItoBoard().reloadCallOrder();
                        itoPlayer.setHasCall(true);
                        return;
                    }else{
                        player.sendMessage("§c既にコールしています");
                    }
                }else{
                    player.sendMessage("§cゲームが開始されていません");
                }
                break;

            default:
                player.sendMessage("§cInvalid action!");
                break;
        }
    }
}
