package me.mrdev.bs.commands.arena;

import me.mrdev.bs.BlocksSumo;
import me.mrdev.bs.arena.Arena;
import me.mrdev.bs.arena.ArenaManager;
import me.mrdev.bs.arena.ArenaState;
import me.mrdev.bs.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InfoCommand extends SubCommand<CommandSender> {

    public InfoCommand(BlocksSumo plugin) {
        super(plugin);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length != 1) {
            sender.sendMessage("Correct args are /arena info <name>");
            return;
        }

        String name = args[0];
        ArenaManager manager = getPlugin().getArenaManager();
        if(!manager.exists(name)) {
            sender.sendMessage("Arena doesn't exist");
            return;
        }

        Arena info = manager.getArena(name);
        String[] data = {"name: " + info.getName(), "state: " + info.getState().toString().toLowerCase(), "min: " + info.getMin(), "max: " + info.getMax() , "lives: " + info.getLives() , "players: " + info.getPlayers().size()};
        sender.sendMessage(data);
        info.getPlayers().forEach(p -> sender.sendMessage(p.getPlayer().getName() + " | " + p.getLives() + " | " + (p.getTeam() != null ? p.getTeam().name().toLowerCase() : "NOT ASSIGNED")));

    }

    @Override
    public String getName() {
        return "info";
    }
}
