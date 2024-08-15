package net.tv.twitch.chrono_fish.ito_paper.InvPack;

import net.tv.twitch.chrono_fish.ito_paper.GamePack.ItoGame;
import net.tv.twitch.chrono_fish.ito_paper.GamePack.ItoPlayer;
import net.tv.twitch.chrono_fish.ito_paper.GamePack.ThemeManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.FallingBlock;
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
                            player.sendMessage("§f空のテーマは提出できません");
                            return;
                        }
                        e.getInventory().remove(snowball);
                        player.closeInventory();
                        itoGame.findItoPlayer(player).submitTheme(theme);
                        player.sendMessage("テーマを提出しました("+theme+")");
                        Block block = player.getTargetBlockExact(5);
                        if(block.getType().equals(Material.ANVIL)||block.getType().equals(Material.DAMAGED_ANVIL)
                                ||block.getType().equals(Material.CHIPPED_ANVIL)){
                            block.setType(Material.AIR);
                        }
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
                            if(!themeManager.getThemePool().isEmpty()){
                                ArrayList<String> themes = themeManager.getThemePool();
                                Collections.shuffle(themes);
                                itoGame.setTheme(themes.get(0));
                                itoGame.setGameRunning(true);
                                itoGame.setNumbers();
                                itoGame.getField().clear();
                                itoGame.broadcastItoPlayers("ゲームを開始します、テーマは【§a"+itoGame.getTheme()+"§f】です");
                                itoGame.getPlayers().forEach(itoPlayer -> itoPlayer.getItoBoard().resetNumber());
                                themeManager.getThemePool().remove(itoGame.getTheme());
                            }else{
                                player.sendMessage("§fテーマプールが空です");
                            }
                        }else{
                            player.sendMessage("§f権限がありません");
                        }
                        break;

                    case PAPER:
                        player.closeInventory();
                        StringBuilder themesMessage = new StringBuilder("+テーマ一覧("+themeManager.getThemePool().size()+")");
                        themeManager.getThemePool().forEach(theme -> themesMessage.append("\n・").append(theme));
                        itoGame.broadcastItoPlayers(themesMessage.toString());
                        break;

                    case TORCH:
                        player.closeInventory();
                        if(itoGame.isGameRunning()){
                            itoGame.check();
                        }else{
                            player.sendMessage("§fゲームが開始されていません");
                        }
                        break;

                    case SNOWBALL:
                        player.closeInventory();
                        itoInv.openThemeInv(player);
                        BlockData anvilData = Material.DAMAGED_ANVIL.createBlockData();

                        FallingBlock fallingAnvil = player.getWorld().spawnFallingBlock(player.getLocation(), anvilData);

                        fallingAnvil.setHurtEntities(false);
                        fallingAnvil.setDropItem(false);
                        break;

                    case BREAD:
                        player.closeInventory();
                        if(itoGame.isGameRunning()){
                            ItoPlayer itoPlayer = itoGame.findItoPlayer(player);
                            if(!itoPlayer.hasCall()){
                                itoPlayer.call();
                                itoGame.broadcastItoPlayers("§e"+player.getName()+"§fがコールしました(コールした順番:"+(itoGame.getField().size())+")");
                                itoGame.getPlayers().forEach(ip ->ip.getItoBoard().addCaller());
                            }else{
                                player.sendMessage("§c既にコールしています");
                            }
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
