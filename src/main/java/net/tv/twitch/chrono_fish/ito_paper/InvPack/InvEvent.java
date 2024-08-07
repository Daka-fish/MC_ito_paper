package net.tv.twitch.chrono_fish.ito_paper.InvPack;

import net.tv.twitch.chrono_fish.ito_paper.GamePack.ItoGame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InvEvent implements Listener {
    private final ItoGame itoGame;

    public InvEvent(ItoGame itoGame){
        this.itoGame = itoGame;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getView().title().equals(new ItoInv().getMenu())){
            if(e.getCurrentItem() != null){
                ItemStack currentItem = e.getCurrentItem();
                switch(currentItem.getType()){
                    case PAPER:
                        break;
                    case GUNPOWDER:
                        break;
                }
            }
        }
    }
}
