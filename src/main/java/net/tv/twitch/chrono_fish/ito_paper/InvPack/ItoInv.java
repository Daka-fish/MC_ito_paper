package net.tv.twitch.chrono_fish.ito_paper.InvPack;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ItoInv {
    private final Component menuTitle = Component.text("メニュー");
    private final Inventory menu = Bukkit.createInventory(null,9, menuTitle);

    public ItoInv(){
        ItemStack stick = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta swordMeta = stick.getItemMeta();
        swordMeta.displayName(Component.text("ゲームを開始する"));
        ArrayList<Component> permissionLore = new ArrayList<>();
        permissionLore.add(Component.text("権限が必要です"));
        swordMeta.lore(permissionLore);
        stick.setItemMeta(swordMeta);

        ItemStack paper = new ItemStack(Material.PAPER);
        ItemMeta paperMeta = paper.getItemMeta();
        paperMeta.displayName(Component.text("テーマ一覧を表示する"));
        paperMeta.lore(permissionLore);
        paper.setItemMeta(paperMeta);

        ItemStack torch = new ItemStack(Material.TORCH);
        ItemMeta torchMeta = torch.getItemMeta();
        torchMeta.displayName(Component.text("成功判定を行う"));
        torchMeta.lore(permissionLore);
        torch.setItemMeta(torchMeta);

        ItemStack snowball = new ItemStack(Material.SNOWBALL);
        ItemMeta snowMeta = snowball.getItemMeta();
        snowMeta.displayName(Component.text("テーマを変更する"));
        snowMeta.lore(permissionLore);
        snowball.setItemMeta(snowMeta);

        ItemStack bread = new ItemStack(Material.BREAD);
        ItemMeta breadMeta = bread.getItemMeta();
        breadMeta.displayName(Component.text("コールする"));
        bread.setItemMeta(breadMeta);

        menu.setItem(0,stick);
        menu.setItem(2,paper);
        menu.setItem(4,torch);
        menu.setItem(6,snowball);
        menu.setItem(8,bread);
    }

    public Inventory getMenu() {return menu;}
    public Component getMenuTitle() {return menuTitle;}
}
