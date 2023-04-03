package me.mrdev.bs.commands;

import me.mrdev.bs.BlocksSumo;
import org.bukkit.command.CommandSender;

public abstract class SubCommand<T extends CommandSender> {

    private BlocksSumo plugin;

    public SubCommand(BlocksSumo plugin) {
        this.plugin = plugin;
    }

    public abstract void execute(T sender, String[] args);
    public abstract String getName();

    protected BlocksSumo getPlugin() {
        return plugin;
    }


}
