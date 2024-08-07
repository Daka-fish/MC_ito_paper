package net.tv.twitch.chrono_fish.ito_paper.InvPack;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItoInv {
    private final Component title = Component.text("メニュー");
    private final Inventory menu = Bukkit.createInventory(null,54,title);

    public ItoInv(){
        ItemStack stick = new ItemStack(Material.STICK);
        ItemMeta swordMeta = stick.getItemMeta();
        swordMeta.displayName(Component.text("ゲームを開始します"));
        stick.setItemMeta(swordMeta);

        ItemStack gunpowder = new ItemStack(Material.GUNPOWDER);
        ItemMeta gunpowderMeta = gunpowder.getItemMeta();
        gunpowderMeta.displayName(Component.text("閉じる"));
        gunpowder.setItemMeta(gunpowderMeta);

        menu.setItem(11,stick);
        menu.setItem(42,gunpowder);
    }

    public Inventory getMenu() {return menu;}
    public Component getTitle() {return title;}
}
