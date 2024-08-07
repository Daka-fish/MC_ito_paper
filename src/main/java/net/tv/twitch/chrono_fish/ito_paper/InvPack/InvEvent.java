package net.tv.twitch.chrono_fish.ito_paper.InvPack;

import net.tv.twitch.chrono_fish.ito_paper.GamePack.ItoGame;
import net.tv.twitch.chrono_fish.ito_paper.GamePack.ThemeManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;

public class InvEvent implements Listener {
    private final ItoGame itoGame;
    private final ThemeManager themeManager;

    public InvEvent(ItoGame itoGame){
        this.itoGame = itoGame;
        this.themeManager = itoGame.getThemeManager();
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getClickedInventory() != null){
            if(e.getCurrentItem() != null && e.getView().title().equals(new ItoInv().getTitle())){
                e.setCancelled(true);
                Player player = (Player) e.getWhoClicked();
                ItemStack currentItem = e.getCurrentItem();

                switch(currentItem.getType()){
                    case STICK:
                        player.closeInventory();
                        if(player.hasPermission("ito_paper.menu")){
                            player.sendMessage("ゲームを開始します");
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
                            player.sendMessage("テーマは【"+theme+"】に決定しました");
                        }else{
                            player.sendMessage("権限が無いか、テーマプールが空です");
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
