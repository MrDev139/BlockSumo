package me.mrdev.bs.listeners;

import me.mrdev.bs.BlocksSumo;
import me.mrdev.bs.arena.Arena;
import me.mrdev.bs.arena.ArenaState;
import me.mrdev.bs.events.ArenaEndEvent;
import me.mrdev.bs.game.GameArena;
import me.mrdev.bs.game.GamePlayer;
import me.mrdev.bs.game.tasks.DamageCheckTask;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {

    private BlocksSumo plugin;

    public DamageListener(BlocksSumo plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if(event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                event.setCancelled(true); //cause both ingame and in lobby the p isn't supposed to take fall damage
            }else if(event.getCause() == EntityDamageEvent.DamageCause.VOID) {
                if(plugin.getArenaManager().isPlayerInArena(player)) {
                    GameArena arena = new GameArena(plugin, plugin.getArenaManager().getPlayerArena(player));
                    GamePlayer p = arena.getGamePlayer(player);
                        if(arena.getState() == ArenaState.INGAME && !arena.isSpectator(p)) {
                            arena.addSpectator(p);
                        }else {
                            player.teleport(arena.getLobby());
                        }
                        event.setCancelled(true);
                }
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player damager = (Player) event.getDamager();
            Player victim = (Player) event.getEntity();
            if(plugin.getArenaManager().isPlayerInArena(damager) && plugin.getArenaManager().isPlayerInArena(victim)) {
                GameArena darena = new GameArena(plugin, plugin.getArenaManager().getPlayerArena(damager));
                GameArena varena = new GameArena(plugin, plugin.getArenaManager().getPlayerArena(victim));
                if(darena.getID().equals(varena.getID()) && darena.getState() == ArenaState.INGAME) { //if both are in the same arena
                    event.setDamage(0.0);
                    GamePlayer gvictim = darena.getGamePlayer(victim);
                    gvictim.setLastDamager(damager);
                    new DamageCheckTask(plugin, gvictim, 10).start();
                }else {
                    event.setCancelled(true);
                }
            }else {
                event.setCancelled(true);
            }
        }else {
            event.setCancelled(true);
        }
    }

}
