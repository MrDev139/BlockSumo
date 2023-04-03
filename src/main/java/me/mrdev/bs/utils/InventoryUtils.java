package me.mrdev.bs.utils;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

public class InventoryUtils {

    public void prepareSetup(Player player) {
        Inventory inventory = Bukkit.createInventory(null , 27 , "Arena Setup");
        for (int i = 0; i < inventory.getContents().length ; i++) {
            Wool wool = new Wool();
            wool.setColor(DyeColor.RED);
            ItemStack item = wool.toItemStack();
            item.setAmount(1);
            ItemMeta meta = item.getItemMeta();
            switch (i) {
                case 10:
                    meta.setDisplayName("Set Name");
                    break;
                case 11:
                    meta.setDisplayName("Set Min");
                    break;
                case 12:
                    meta.setDisplayName("Set Max");
                    break;
                case 13:
                    meta.setDisplayName("Add Spawn");
                    break;
                case 14:
                    meta.setDisplayName("Set Lobby");
                    break;
                case 15:
                    meta.setDisplayName("Set Spectator Spawn");
                    break;
                case 16:
                    meta.setDisplayName("Save Arena");
                    break;
                case 4:
                    meta.setDisplayName("Set Lives");
                    break;
                default:
                    item = new ItemStack(Material.STAINED_GLASS_PANE);
                    meta.setDisplayName(" ");
            }
            item.setItemMeta(meta);
            inventory.setItem(i , item);
        }
        player.openInventory(inventory);
    }

}
