package me.mrdev.bs.listeners;

import me.mrdev.bs.BlocksSumo;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class WorldListener implements Listener {

    private BlocksSumo plugin;

    public WorldListener(BlocksSumo plugin) {
        this.plugin = plugin; //useful for future purposes
    }

    @EventHandler
    public void onSpawn(EntitySpawnEvent event) {
        if(!(event.getEntity() instanceof Player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

}
