package me.mrdev.bs.game.tasks;

import me.mrdev.bs.BlocksSumo;
import me.mrdev.bs.arena.Arena;
import me.mrdev.bs.game.GameArena;
import me.mrdev.bs.game.GamePlayer;
import org.bukkit.entity.Player;

public class RespawnTask extends TaskTimer{

    private GamePlayer player;
    private BlocksSumo plugin;
    private GameArena arena;
    private long time;

    public RespawnTask(BlocksSumo plugin , GamePlayer player , long time) {
        super(plugin);
        this.plugin = plugin;
        this.time = time;
        if(player != null && plugin.getArenaManager().isPlayerInArena(player)) {
            this.arena = new GameArena(plugin , plugin.getArenaManager().getPlayerArena(player));
            this.player = player;
        }else {
            cancel();
        }
    }

    public RespawnTask(BlocksSumo plugin , Player player , long time) {
        super(plugin);
        this.plugin = plugin;
        this.time = time;
        if(player != null && plugin.getArenaManager().isPlayerInArena(player)) {
            this.arena = new GameArena(plugin , plugin.getArenaManager().getPlayerArena(player));
            this.player = arena.getGamePlayer(player);
        }else {
            cancel();
        }
    }


    @Override
    public void execute() {
        if(time == 0) {
            arena.respawnSpectator(player);
            cancel();
        }else {
            player.getPlayer().sendTitle("You Died", "respawning in " + time);
        }
        time--;
    }
}
