package me.mrdev.bs.commands;

import me.mrdev.bs.BlocksSumo;
import me.mrdev.bs.arena.ArenaManager;
import me.mrdev.bs.commands.arena.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;

public class ArenaBaseCommand implements CommandExecutor {

    private BlocksSumo plugin;
    private ArenaManager manager;
    private ArrayList<SubCommand<CommandSender>> subCommands = new ArrayList<>();
    private ArrayList<SubCommand<Player>> playerSubCommands = new ArrayList<>();

    public ArenaBaseCommand(BlocksSumo plugin) {
        this.plugin = plugin;
        this.manager = plugin.getArenaManager();
        playerSubCommands.add(new JoinCommand(plugin));
        playerSubCommands.add(new LeaveCommand(plugin));
        subCommands.add(new ListCommand(plugin));
        subCommands.add(new InfoCommand(plugin));
        playerSubCommands.add(new ModifyCommand(plugin));
        playerSubCommands.add(new SetupCommand(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            sender.sendMessage("Heya! available arena commands are");
            subCommands.forEach(sub -> sender.sendMessage("/arena " + sub.getName()));
            return true;
        }

        String subStr = args[0];
        SubCommand<CommandSender> subCmd = subCommands.stream().filter(sub -> sub.getName().equalsIgnoreCase(subStr)).findAny().orElse(null);
        SubCommand<Player> playerSubCmd = playerSubCommands.stream().filter(sub -> sub.getName().equalsIgnoreCase(subStr)).findAny().orElse(null);

        if(playerSubCmd != null) {
            if(!(sender instanceof Player)) {
                sender.sendMessage("Hey this command is only for players!");
                return true;
            }
            playerSubCmd.execute((Player) sender, Arrays.copyOfRange(args, 1, args.length));
            return true;
        }

        if(subCmd != null) {
            subCmd.execute(sender, Arrays.copyOfRange(args, 1, args.length));
            return true;
        }

        sender.sendMessage("Invalid subCommand!");
        return true;
    }

}
