package net.tv.twitch.chrono_fish.ito_paper.InvPack;

import net.kyori.adventure.text.Component;
import net.tv.twitch.chrono_fish.ito_paper.GamePack.ItoGame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InvEvent implements Listener {
    private final ItoGame itoGame;

    public InvEvent(ItoGame itoGame){this.itoGame = itoGame;}

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
                        player.sendMessage("ゲームを開始します");
                        break;

                    case PAPER:
                        player.closeInventory();
                        player.sendMessage("現在のテーマ：【"+itoGame.getTheme()+"】");
                        break;

                    case GUNPOWDER:
                        player.closeInventory();
                        break;
                }

            }
        }
    }
}
