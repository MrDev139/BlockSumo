package me.mrdev.bs.listeners;

import me.mrdev.bs.BlocksSumo;
import me.mrdev.bs.arena.Arena;
import me.mrdev.bs.arena.ArenaManager;
import me.mrdev.bs.arena.ArenaState;
import me.mrdev.bs.events.ArenaEndEvent;
import me.mrdev.bs.events.ArenaJoinEvent;
import me.mrdev.bs.events.ArenaLeaveEvent;
import me.mrdev.bs.game.GameArena;
import me.mrdev.bs.game.tasks.CountingTask;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener implements Listener {

    private BlocksSumo plugin;
    private ArenaManager manager;

    public JoinListener(BlocksSumo plugin) {
        this.plugin = plugin;
        this.manager = plugin.getArenaManager();
    }

    @EventHandler
    public void onJoin(ArenaJoinEvent event) {
        Arena arena = event.getArena();
        Player player = event.getJoiner();
        player.teleport(arena.getLobby());
        player.sendMessage("You joined " + arena.getName());
        event.getArena().getPlayers().forEach(p -> p.getPlayer().sendMessage(player.getName() + " Has joined! " + arena.getPlayers().size() + "/" + arena.getMax()));
        if(arena.getPlayers().size() >= arena.getMin() && arena.getState() == ArenaState.WAITING) {
            event.getArena().getPlayers().forEach(p -> p.getPlayer().sendMessage(ChatColor.YELLOW + "Started countdown!"));
            arena.setState(ArenaState.STARTING);
            new CountingTask(arena , plugin , 15).start();
        }
    }

    @EventHandler
    public void onLeave(ArenaLeaveEvent event) {
        if(!event.isDisconnected()) {
            event.getPlayer().sendMessage("You left " + event.getArena().getName());
        }else if(new GameArena(plugin , event.getArena()).getAliveCount() == 1) {
            GameArena garena = new GameArena(plugin , event.getArena()); //Messy will fix later
            plugin.getServer().getPluginManager().callEvent(new ArenaEndEvent(garena , garena.getLastStanding().getPlayer()));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(manager.isPlayerInArena(player)) {
            GameArena arena = new GameArena(plugin , plugin.getArenaManager().getPlayerArena(player));
            if(arena.isSpectator(arena.getGamePlayer(player))) {
                arena.getSpectators().remove(arena.getGamePlayer(player));
            }
            arena.getPlayers().remove(arena.getGamePlayer(player));
            plugin.getServer().getPluginManager().callEvent(new ArenaLeaveEvent(player, arena , true));
        }
    }

}
