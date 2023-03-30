package me.mrdev.bs.listeners;

import me.mrdev.bs.BlocksSumo;
import me.mrdev.bs.arena.ArenaState;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class WoolListener implements Listener {

    private BlocksSumo plugin;

    public WoolListener(BlocksSumo plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItemDrop().getItemStack();
        if(plugin.getArenaManager().isPlayerInArena(player) && plugin.getArenaManager().getPlayerArena(player).getState() == ArenaState.INGAME) {
            if(item.getType() == Material.WOOL || item.getType() == Material.SHEARS) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if(plugin.getArenaManager().isPlayerInArena(player) && plugin.getArenaManager().getPlayerArena(player).getState() == ArenaState.INGAME) {
            if (event.getBlock().getType() == Material.WOOL) {
                ItemStack item = player.getInventory().getItemInHand();
                if(item.getType() == Material.WOOL) {
                    item.setAmount(64);
                }
                Block block = event.getBlock();
                plugin.getServer().getScheduler().runTaskLater(plugin, () -> block.setData(DyeColor.values()[ThreadLocalRandom.current().nextInt(DyeColor.values().length)].getWoolData()), 5L);
            }
        }
    }

}
