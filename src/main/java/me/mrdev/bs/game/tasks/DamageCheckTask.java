package me.mrdev.bs.game.tasks;

import me.mrdev.bs.BlocksSumo;
import me.mrdev.bs.arena.Arena;
import me.mrdev.bs.game.GameArena;
import me.mrdev.bs.game.GamePlayer;
import org.bukkit.entity.Player;

public class DamageCheckTask extends TaskTimer {

    private BlocksSumo plugin;
    private GamePlayer player;

    private GameArena arena;
    private Player lastDamager;
    private long time;

    public DamageCheckTask(BlocksSumo plugin, GamePlayer player, long time) {
        super(plugin);
        this.plugin = plugin;
        this.player = player;
        this.time = time;
        this.lastDamager = player.getLastDamager();
        this.arena = new GameArena(plugin, plugin.getArenaManager().getPlayerArena(player));
    }
    @Override
    public void execute() { //check if the player is damaged by another one for n duration of time then remove last damager
        if(time == 0 || arena.isSpectator(player)) {
            player.setLastDamager(null);
            cancel();
        }

        if(lastDamager == null || player.getLastDamager() == null) {
           cancel();
        }

        if(!lastDamager.getUniqueId().equals(player.getLastDamager().getUniqueId())) {
            cancel();
        }

        time--;
    }
}
