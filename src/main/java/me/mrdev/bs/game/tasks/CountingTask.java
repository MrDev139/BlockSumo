package me.mrdev.bs.game.tasks;

import me.mrdev.bs.BlocksSumo;
import me.mrdev.bs.arena.Arena;
import me.mrdev.bs.arena.ArenaState;
import me.mrdev.bs.events.ArenaStartEvent;
import org.bukkit.ChatColor;

public class CountingTask extends TaskTimer{

    private long time;
    private BlocksSumo plugin;
    private Arena arena;

    public CountingTask(Arena arena, BlocksSumo plugin , long time) {
        super(plugin);
        this.time = time;
        this.plugin = plugin;
        this.arena = arena;
    }

    @Override
    public void execute() {
        if(time == 0) {
            arena.setState(ArenaState.INGAME);
            arena.getPlayers().forEach(p -> p.getPlayer().sendMessage(ChatColor.YELLOW + "Game started!"));
            plugin.getServer().getPluginManager().callEvent(new ArenaStartEvent(arena));
            cancel();
        }else if(arena.getPlayers().size() < arena.getMin()) {
            arena.setState(ArenaState.WAITING);
            arena.getPlayers().forEach(p-> p.getPlayer().sendMessage("Cancelled!! not enough players!"));
            cancel();
        }else if(time <= 10) {
            arena.getPlayers().forEach(p-> p.getPlayer().sendMessage(ChatColor.YELLOW + "Game Starting in " + ChatColor.RED + time));
        }
        time--;
    }
}
