package me.mrdev.bs.listeners;

import me.mrdev.bs.BlocksSumo;
import me.mrdev.bs.arena.Arena;
import me.mrdev.bs.arena.ArenaState;
import me.mrdev.bs.events.ArenaEndEvent;
import me.mrdev.bs.events.ArenaStartEvent;
import me.mrdev.bs.events.GamePlayerDeathEvent;
import me.mrdev.bs.game.GameArena;
import me.mrdev.bs.game.GamePlayer;
import me.mrdev.bs.game.tasks.EndingTask;
import me.mrdev.bs.game.tasks.RespawnTask;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GameListener implements Listener {

    private BlocksSumo plugin;

    public GameListener(BlocksSumo plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onStart(ArenaStartEvent event) {
        Arena arena = event.getArena();
        GameArena manager = new GameArena(plugin , arena);
        manager.prepareSpawns();
    }

    @EventHandler
    public void onEnd(ArenaEndEvent event) {
        Arena arena = event.getArena();
        arena.getPlayers().forEach(gp -> {
            Player p =  gp.getPlayer();
            p.sendMessage(event.getWinner().getName() + " Has won the game!!");
            p.sendTitle( "GAME OVER" , "Thanks for testing my plugin");
        });
        arena.setState(ArenaState.ENDING);
        new EndingTask(plugin , arena , 5).start();
    }

    @EventHandler
    public void onDeath(GamePlayerDeathEvent event) {
        Player player = event.getSpectator();
        GameArena arena = event.getArena();
        GamePlayer p = arena.getGamePlayer(player);
        player.teleport(arena.getSpectatorloc());
        player.getInventory().clear();
        player.setAllowFlight(true);
        player.setFlying(true);
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS , 3 , 2, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY , Integer.MAX_VALUE , 2, false, false));
        player.playSound(player.getLocation() , Sound.BAT_DEATH , 2 , 0);
        if(arena.isDead(p)) {
            arena.messagePlayers(player.getName() + " is eliminated");
        }else {
            if(p.getLastDamager() != null) {
                arena.messagePlayers(player.getName() + " got kicked into the void by " + p.getLastDamager().getName() + ", lives remaining " + p.getLives());
            }else {
                arena.messagePlayers(player.getName() + " fell into the void, lives remaining " + p.getLives());
            }
           new RespawnTask(plugin , player , 5 ).start();
        }
        if(arena.getAliveCount() == 1) {
            plugin.getServer().getPluginManager().callEvent(new ArenaEndEvent(arena , arena.getLastStanding().getPlayer()));
        }
        if(arena.getAliveCount() == 0 && arena.getPlayers().size() == 1) {
            plugin.getServer().getPluginManager().callEvent(new ArenaEndEvent(arena , arena.getLastStanding().getPlayer()));
        }
    }

}
