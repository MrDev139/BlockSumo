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
    private ArrayList<SubCommand<? extends CommandSender>> subCommands = new ArrayList<>();

    public ArenaBaseCommand(BlocksSumo plugin) {
        this.plugin = plugin;
        this.manager = plugin.getArenaManager();
        subCommands.add(new JoinCommand(plugin));
        subCommands.add(new LeaveCommand(plugin));
        subCommands.add(new ListCommand(plugin));
        subCommands.add(new InfoCommand(plugin));
        subCommands.add(new ModifyCommand(plugin));
        subCommands.add(new SetupCommand(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            sender.sendMessage("Heya! available arena commands are");
            subCommands.forEach(sub -> sender.sendMessage("/arena " + sub.getName()));
            return true;
        }

        String subStr = args[0];
        SubCommand<? extends CommandSender> subCmd = subCommands.stream().filter(sub -> sub.getName().equalsIgnoreCase(subStr)).findAny().orElse(null);

        if(subCmd != null) {
            ParameterizedType type = (ParameterizedType) subCmd.getClass().getGenericSuperclass();
            if(type.getActualTypeArguments()[0] == Player.class) {
                SubCommand<Player> subPlayer = (SubCommand<Player>) subCmd;
                if(!(sender instanceof Player)) {
                    sender.sendMessage(subCmd.getName() + " cmd is only for players!");
                    return true;
                }
                subPlayer.execute((Player) sender, Arrays.copyOfRange(args, 1, args.length));
                return true;
            }
            SubCommand<CommandSender> subSender = (SubCommand<CommandSender>) subCmd;
            subSender.execute(sender, Arrays.copyOfRange(args, 1, args.length));
           return true;
        }

        sender.sendMessage("Invalid subCommand!");
        return true;
    }

}
