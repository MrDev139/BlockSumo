package me.mrdev.bs.game.tasks;

import me.mrdev.bs.BlocksSumo;
import me.mrdev.bs.arena.Arena;
import me.mrdev.bs.arena.ArenaState;
import me.mrdev.bs.game.GameArena;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class EndingTask extends TaskTimer{

    private BlocksSumo plugin;
    private GameArena arena;

    private Arena clone;

    private long time;

    public EndingTask(BlocksSumo plugin, GameArena arena , long time ) {
        super(plugin);
        this.plugin = plugin;
        this.arena = arena;
        this.time = time;
    }

    public EndingTask(BlocksSumo plugin , Arena arena, long time) {
        super(plugin);
        this.plugin = plugin;
        this.arena = new GameArena(plugin, arena);
        this.clone = arena;
        this.time = time;
    }

    @Override
    public void execute() {
        if(time == 0) {
            arena.clearPlayers();
            cancel();
        }else {
            Player winner = new GameArena(plugin , arena).getLastStanding().getPlayer();
            winner.getWorld().spawnEntity(winner.getLocation() , EntityType.FIREWORK);
            winner.getWorld().spawnEntity(winner.getLocation().add(1, 0, 0) , EntityType.FIREWORK);
            winner.getWorld().spawnEntity(winner.getLocation().subtract(1 ,0 ,0) , EntityType.FIREWORK);
        }
        time--;
    }
}
