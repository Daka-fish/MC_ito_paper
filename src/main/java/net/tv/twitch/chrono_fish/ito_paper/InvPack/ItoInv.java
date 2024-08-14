package net.tv.twitch.chrono_fish.ito_paper.InvPack;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ItoInv {
    private final Component menuTitle = Component.text("メニュー");
    private final Inventory menu = Bukkit.createInventory(null,54, menuTitle);

    public ItoInv(){
        ItemStack stick = new ItemStack(Material.STICK);
        ItemMeta swordMeta = stick.getItemMeta();
        swordMeta.displayName(Component.text("ゲームを開始する"));
        ArrayList<Component> permissionLore = new ArrayList<>();
        permissionLore.add(Component.text("権限が必要です"));
        swordMeta.lore(permissionLore);
        stick.setItemMeta(swordMeta);

        ItemStack gunpowder = new ItemStack(Material.GUNPOWDER);
        ItemMeta gunpowderMeta = gunpowder.getItemMeta();
        gunpowderMeta.displayName(Component.text("閉じる"));
        gunpowder.setItemMeta(gunpowderMeta);

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
        snowMeta.displayName(Component.text("テーマを提出する"));
        snowball.setItemMeta(snowMeta);

        ItemStack bread = new ItemStack(Material.BREAD);
        ItemMeta breadMeta = bread.getItemMeta();
        breadMeta.displayName(Component.text("コールする"));
        bread.setItemMeta(breadMeta);

        menu.setItem(11,stick);
        menu.setItem(13,paper);
        menu.setItem(15,torch);
        menu.setItem(38,snowball);
        menu.setItem(40,bread);
        menu.setItem(42,gunpowder);
    }

    public Inventory getMenu() {return menu;}
    public Component getMenuTitle() {return menuTitle;}

    public void openThemeInv(Player player){
        Inventory anvilInventory = player.getServer().createInventory(player, InventoryType.ANVIL, Component.text("テーマを入力"));
        ItemStack snowball = new ItemStack(Material.SNOWBALL);
        ItemMeta snowMeta = snowball.getItemMeta();
        snowMeta.displayName(Component.text(""));
        snowball.setItemMeta(snowMeta);
        anvilInventory.setItem(0,snowball);
        player.openInventory(anvilInventory);
    }
}
