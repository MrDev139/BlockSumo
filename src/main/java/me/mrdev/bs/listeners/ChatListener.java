package me.mrdev.bs.listeners;

import me.mrdev.bs.BlocksSumo;
import me.mrdev.bs.arena.Arena;
import me.mrdev.bs.arena.ArenaManager;
import me.mrdev.bs.arena.ArenaState;
import me.mrdev.bs.events.ArenaChatEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private BlocksSumo plugin;
    private ArenaManager manager;
    public ChatListener(BlocksSumo plugin) {
        this.plugin = plugin;
        this.manager = plugin.getArenaManager();
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if(manager.isPlayerInArena(player)) {
            Arena arena = manager.getPlayerArena(player);
            ArenaChatEvent cEvent = new ArenaChatEvent(arena, player, event.getMessage());
            plugin.getServer().getPluginManager().callEvent(cEvent);
            if(!cEvent.isCancelled()) {
                if (arena.getState() == ArenaState.WAITING || arena.getState() == ArenaState.STARTING) {
                    arena.getPlayers().forEach(p -> {
                        Player p1 = p.getPlayer();
                        p1.sendMessage(player.getName()  + " >> " + event.getMessage());
                    });
                    event.setCancelled(true);
                    return;
                }
                arena.getPlayers().forEach(p -> {
                    Player p1 = p.getPlayer();
                    p1.sendMessage(p.getTeam() + player.getName() + " >> " + ChatColor.RESET + event.getMessage());
                });
            }
            event.setCancelled(true);
            return;
        }
       plugin.getServer().broadcastMessage(player.getName() + " >> " + event.getMessage());
    }

}
