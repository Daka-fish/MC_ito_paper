package net.tv.twitch.chrono_fish.ito_paper.Manager;

import net.tv.twitch.chrono_fish.ito_paper.GamePack.ItoGame;
import net.tv.twitch.chrono_fish.ito_paper.GamePack.ItoPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;

public class InvManager {
    private final ItoGame itoGame;

    public InvManager(ItoGame itoGame){this.itoGame = itoGame;}

    public void invAction(Player player, Material material){
        ThemeManager themeManager = itoGame.getThemeManager();
        boolean isGameMaster = itoGame.getItoConfig().getGameMaster().equals(player.getUniqueId().toString());
        switch(material){
            case DIAMOND_SWORD:
                player.closeInventory();
                if(isGameMaster){
                    if(!themeManager.getThemePool().isEmpty() && itoGame.getPlayers().size() >= itoGame.getItoConfig().getRequiredPlayers()){
                        StringBuilder gameState = new StringBuilder("-ゲームスタート-\nテーマ: ");
                        ArrayList<String> themes = themeManager.getThemePool();
                        Collections.shuffle(themes);
                        itoGame.setTheme(themes.get(0));
                        itoGame.setGameRunning(true);
                        itoGame.setNumbers();
                        itoGame.getField().clear();
                        itoGame.putLogger(player.getName()+" starts the game with "+itoGame.getTheme()+".");
                        itoGame.sendMessage("ゲームを開始します、テーマは【§a"+itoGame.getTheme()+"§f】です");
                        gameState.append(itoGame.getTheme()).append("\n").append("プレイヤー: ");
                        itoGame.getPlayers().forEach(itoPlayer -> {
                            if(itoPlayer.isInGame()){
                                itoPlayer.setCallOrder(-1);
                                itoPlayer.setHasCall(false);
                                itoPlayer.getItoBoard().reloadTheme();
                                itoPlayer.getItoBoard().reloadCallOrder();
                                itoPlayer.getItoBoard().reloadNumber();
                                if(!itoPlayer.getPlayer().getUniqueId().toString().equalsIgnoreCase(itoGame.getGameMaster())){
                                    itoPlayer.getPlayer().getInventory().addItem(new ItemStack(Material.STICK));
                                }
                                gameState.append("\n").append(itoPlayer.getNumber()).append(" : ").append(itoPlayer.getPlayer().getName());
                            }else{
                                itoPlayer.getPlayer().sendMessage(gameState.toString());
                            }
                        });
                        themeManager.getThemePool().remove(itoGame.getTheme());
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
                    StringBuilder themesMessage = new StringBuilder("+テーマ一覧("+themeManager.getThemePool().size()+")");
                    themeManager.getThemePool().forEach(theme -> themesMessage.append("\n・").append(theme));
                    itoGame.sendMessage(themesMessage.toString());
                }else{
                    player.sendMessage("§c権限がありません");
                }
                break;

            case TORCH:
                player.closeInventory();
                if(isGameMaster){
                    if(itoGame.isGameRunning()){
                        if(itoGame.getField().size() < itoGame.getPlayers().size()){
                            player.sendMessage("§cコールの数が不足しています(あと§a"+(itoGame.getPlayers().size()-itoGame.getField().size())+"§c人)");
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
                    if(!itoGame.getThemeManager().getThemePool().isEmpty()){
                        ArrayList<String> themes = themeManager.getThemePool();
                        Collections.shuffle(themes);
                        itoGame.setTheme(themes.get(0));
                        itoGame.getPlayers().forEach(itoPlayer -> itoPlayer.getItoBoard().reloadTheme());
                        themeManager.getThemePool().remove(itoGame.getTheme());
                        itoGame.sendMessage("テーマが【§a"+itoGame.getTheme()+"§f】に変更されました(残り:"+themeManager.getThemePool().size()+")");
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
                        itoGame.sendMessage("§e"+player.getName()+"§fがコールしました(コールした順番:"+(itoGame.getField().size())+")");
                        itoPlayer.setCallOrder(itoGame.getField().size());
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
