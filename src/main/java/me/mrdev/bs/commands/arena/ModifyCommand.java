package me.mrdev.bs.commands.arena;

import me.mrdev.bs.BlocksSumo;
import me.mrdev.bs.commands.SubCommand;
import org.bukkit.entity.Player;

public class ModifyCommand extends SubCommand<Player> {

    public ModifyCommand(BlocksSumo plugin) {
        super(plugin);
    }

    @Override
    public void execute(Player sender, String... args) {

    }

    @Override
    public String getName() {
        return "modify";
    }
}
