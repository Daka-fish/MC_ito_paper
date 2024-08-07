package net.tv.twitch.chrono_fish.ito_paper.InvPack;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class ItoInv {
    private final Component title = Component.text("");
    Inventory menu = Bukkit.createInventory(null,54,title);

    public Inventory getMenu() { return menu; }
}
