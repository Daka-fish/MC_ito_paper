package net.tv.twitch.chrono_fish.ito_paper.Manager;

import net.tv.twitch.chrono_fish.ito_paper.game.ItoGame;
import net.tv.twitch.chrono_fish.ito_paper.game.ItoPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;

public class InvManager {
    private final ItoGame itoGame;

    public InvManager(ItoGame itoGame){this.itoGame = itoGame;}

    public void invAction(Player player, Material material){
        ArrayList<String> themePool = itoGame.getThemePool();
        switch(material){
            case DIAMOND_SWORD:
                player.closeInventory();
                if(!themePool.isEmpty() && itoGame.getPlayers().size() >= itoGame.getItoConfig().getRequiredPlayers()){
                    itoGame.start();
                    return;
                }else{
                    player.sendMessage("§cテーマプールが空か、プレイヤーの数が足りません");
                }
                break;

            case PAPER:
                player.closeInventory();
                StringBuilder themesMessage = new StringBuilder("+テーマ一覧("+themePool.size()+")");
                themePool.forEach(theme -> themesMessage.append("\n・").append(theme));
                itoGame.sendMessage(themesMessage.toString());
                break;

            case TORCH:
                player.closeInventory();
                if(itoGame.isGameRunning()){
                    if(itoGame.getCallList().size() >= itoGame.getPlayers().size()){
                        itoGame.finish();
                    }else{
                        player.sendMessage("§cコールの数が不足しています(あと§a"+(itoGame.getPlayers().size()-itoGame.getCallList().size())+"§c人)");
                    }
                }else{
                    player.sendMessage("§cゲームが開始されていません");
                }
                break;

            case SNOWBALL:
                player.closeInventory();
                if(itoGame.isGameRunning()){
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
                    player.sendMessage("§c進行中のゲームがありません");
                }
                break;

            case BREAD:
                player.closeInventory();
                if(itoGame.isGameRunning()){
                    ItoPlayer itoPlayer = itoGame.getItoPlayer(player);
                    if(!itoPlayer.hasCall()){
                        itoPlayer.call();
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
