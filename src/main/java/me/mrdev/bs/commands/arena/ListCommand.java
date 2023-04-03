package me.mrdev.bs.commands.arena;

import me.mrdev.bs.BlocksSumo;
import me.mrdev.bs.commands.SubCommand;
import org.bukkit.command.CommandSender;

public class ListCommand extends SubCommand<CommandSender> {

    public ListCommand(BlocksSumo plugin) {
        super(plugin);
    }

    @Override
    public void execute(CommandSender sender, String... args) {
        sender.sendMessage("Loaded arenas :");
        if(getPlugin().getArenaManager().getLoadedArenas().isEmpty()) {
            sender.sendMessage("NONE");
            return;
        }
        getPlugin().getArenaManager().getLoadedArenas().forEach(a -> sender.sendMessage(a.getName()));
    }


    @Override
    public String getName() {
        return "list";
    }
}