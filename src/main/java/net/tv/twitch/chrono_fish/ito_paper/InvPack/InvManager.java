package net.tv.twitch.chrono_fish.ito_paper.InvPack;

import net.tv.twitch.chrono_fish.ito_paper.GamePack.ItoGame;
import net.tv.twitch.chrono_fish.ito_paper.GamePack.ItoPlayer;
import net.tv.twitch.chrono_fish.ito_paper.Manager.ThemeManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;

public class InvManager {
    private final ItoGame itoGame;

    public InvManager(ItoGame itoGame){this.itoGame = itoGame;}

    public void invAction(Player player, Material material){
        ThemeManager themeManager = itoGame.getThemeManager();
        switch(material){
            case STICK:
                player.closeInventory();
                if(player.hasPermission("ito_paper.menu")){
                    if(!themeManager.getThemePool().isEmpty()){
                        ArrayList<String> themes = themeManager.getThemePool();
                        Collections.shuffle(themes);
                        itoGame.setTheme(themes.get(0));
                        itoGame.setGameRunning(true);
                        itoGame.setNumbers();
                        itoGame.getField().clear();
                        itoGame.getIto_paper().putLogger(player.getName()+" starts the game with "+itoGame.getTheme()+".");
                        itoGame.getPlayers().forEach(itoPlayer -> {
                            itoPlayer.getItoBoard().reloadNumber();
                            itoPlayer.getItoBoard().reloadTheme();
                        });
                        itoGame.sendMessage("ゲームを開始します、テーマは【§a"+itoGame.getTheme()+"§f】です");
                        themeManager.getThemePool().remove(itoGame.getTheme());
                        return;
                    }else{
                        player.sendMessage("§cテーマプールが空です");
                    }
                }else{
                    player.sendMessage("§c権限がありません");
                }
                break;

            case PAPER:
                player.closeInventory();
                StringBuilder themesMessage = new StringBuilder("+テーマ一覧("+themeManager.getThemePool().size()+")");
                themeManager.getThemePool().forEach(theme -> themesMessage.append("\n・").append(theme));
                itoGame.sendMessage(themesMessage.toString());
                break;

            case TORCH:
                player.closeInventory();
                if(itoGame.isGameRunning()){
                    if(itoGame.getField().size() < itoGame.getPlayers().size()){
                        player.sendMessage("§cコールの数が不足しています(あと§a"+(itoGame.getPlayers().size()-itoGame.getField().size())+"§c人)");
                        return;
                    }
                    itoGame.check();
                }else{
                    player.sendMessage("§cゲームが開始されていません");
                }
                break;

            case SNOWBALL:
                player.closeInventory();
                if(player.hasPermission("ito_paper.menu") && itoGame.isGameRunning()){
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
                    player.sendMessage("権限がないか、進行中のゲームがありません");
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

            case GUNPOWDER:
                player.closeInventory();
                break;
        }
    }
}
