package net.tv.twitch.chrono_fish.ito_paper.InvPack;

import net.kyori.adventure.text.Component;
import net.tv.twitch.chrono_fish.ito_paper.GamePack.ItoGame;
import net.tv.twitch.chrono_fish.ito_paper.GamePack.ItoPlayer;
import net.tv.twitch.chrono_fish.ito_paper.GamePack.ThemeManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;

public class InvEvent implements Listener {
    private final ItoGame itoGame;
    private final ThemeManager themeManager;
    private final ItoInv itoInv;

    public InvEvent(ItoGame itoGame){
        this.itoGame = itoGame;
        this.themeManager = itoGame.getThemeManager();
        itoInv = new ItoInv();
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getClickedInventory() != null){
            if(e.getInventory().getType().equals(InventoryType.ANVIL)){
                if(e.getCurrentItem() != null){
                    ItemStack clickedItem = e.getCurrentItem();
                    if(clickedItem.getType().equals(Material.SNOWBALL) && e.getRawSlot() == 2){
                        Player player = (Player) e.getWhoClicked();
                        ItemStack snowball = e.getInventory().getItem(0);
                        String theme = clickedItem.getItemMeta().getDisplayName();
                        if(theme.equals("")){
                            player.sendMessage("空のテーマは提出できません");
                            return;
                        }
                        e.getInventory().remove(snowball);
                        player.closeInventory();
                        itoGame.findItoPlayer(player).submitTheme(theme);
                        player.sendMessage("テーマを提出しました("+theme+")");
                    }
                }
                return;
            }
            if(e.getCurrentItem() != null && e.getView().title().equals(itoInv.getMenuTitle())){
                e.setCancelled(true);
                Player player = (Player) e.getWhoClicked();
                ItemStack currentItem = e.getCurrentItem();

                switch(currentItem.getType()){
                    case STICK:
                        player.closeInventory();
                        if(player.hasPermission("ito_paper.menu")){
                            if(itoGame.getTheme().equalsIgnoreCase(itoGame.getDefaultTheme())){
                                player.sendMessage("テーマを設定してください");
                                return;
                            }
                            itoGame.setGameRunning(true);
                            itoGame.setNumbers();
                            itoGame.getPlayers().forEach(itoPlayer -> {
                                itoPlayer.getItoBoard().resetNumber();
                                itoPlayer.getItoBoard().switchScore(itoGame.isGameRunning());
                                itoPlayer.getPlayer().sendMessage("ゲームを開始します。テーマは【"+itoGame.getTheme()+"】です");
                            });
                        }else{
                            player.sendMessage("権限がありません");
                        }
                        break;

                    case PAPER:
                        player.closeInventory();
                        ArrayList<String> themes = themeManager.getThemePool();
                        if(player.hasPermission("ito_paper.menu") && !themes.isEmpty()){
                            Collections.shuffle(themes);
                            String theme = themes.get(0);
                            itoGame.setTheme(theme);
                            itoGame.getThemeManager().getThemePool().remove(theme);
                            player.sendMessage("テーマは【§a"+theme+"§f】に決定しました");
                        }else{
                            player.sendMessage("権限が無いか、テーマプールが空です");
                        }
                        break;

                    case SNOWBALL:
                        player.closeInventory();
                        itoInv.openThemeInv(player);
                        break;

                    case BREAD:
                        player.closeInventory();
                        if(itoGame.isGameRunning()){
                            ItoPlayer itoPlayer = itoGame.findItoPlayer(player);
                            itoPlayer.call();
                            player.sendMessage("コールしました(コールした順番:-1)");
                        }
                        break;

                    case GUNPOWDER:
                        player.closeInventory();
                        break;
                }

            }
        }
    }
}
