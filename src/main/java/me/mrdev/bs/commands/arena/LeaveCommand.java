package me.mrdev.bs.commands.arena;

import me.mrdev.bs.BlocksSumo;
import me.mrdev.bs.arena.Arena;
import me.mrdev.bs.arena.ArenaManager;
import me.mrdev.bs.commands.SubCommand;
import me.mrdev.bs.events.ArenaLeaveEvent;
import org.bukkit.entity.Player;

public class LeaveCommand extends SubCommand<Player> {

    public LeaveCommand(BlocksSumo plugin) {
        super(plugin);
    }

    @Override
    public void execute(Player player, String... args) {
        ArenaManager manager = getPlugin().getArenaManager();
        if(!manager.isPlayerInArena(player)) {
            player.sendMessage("You're not in an arena!");
            return;
        }
        Arena leave = manager.getPlayerArena(player);
        leave.getPlayers().remove(leave.getGamePlayer(player));
        getPlugin().getServer().getPluginManager().callEvent( new ArenaLeaveEvent(player , leave , false));
    }

    @Override
    public String getName() {
        return "leave";
    }
}
