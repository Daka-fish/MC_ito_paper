package net.tv.twitch.chrono_fish.ito_paper.Listener;

import net.tv.twitch.chrono_fish.ito_paper.game.ItoGame;
import net.tv.twitch.chrono_fish.ito_paper.Manager.InvManager;
import net.tv.twitch.chrono_fish.ito_paper.InvPack.ItoInv;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InvListener implements Listener {
    private final InvManager invManager;
    private final ItoInv itoInv;

    public InvListener(ItoGame itoGame){
        this.invManager = new InvManager(itoGame);
        itoInv = new ItoInv();
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getClickedInventory() != null){
            if(e.getCurrentItem() != null && e.getView().title().equals(itoInv.getMenuTitle())){
                e.setCancelled(true);
                Player player = (Player) e.getWhoClicked();
                ItemStack currentItem = e.getCurrentItem();
                invManager.invAction(player,currentItem.getType());
            }
        }
    }
}
