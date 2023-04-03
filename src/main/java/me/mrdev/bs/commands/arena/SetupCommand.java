package me.mrdev.bs.commands.arena;

import me.mrdev.bs.BlocksSumo;
import me.mrdev.bs.commands.SubCommand;
import org.bukkit.entity.Player;

public class SetupCommand extends SubCommand<Player> {

    public SetupCommand(BlocksSumo plugin) {
        super(plugin);
    }

    @Override
    public void execute(Player player, String... args) {
        if(args.length != 0) {
            player.sendMessage("the correct arguments for this cmd is /arena setup");
            return;
        }
        getPlugin().getSumoUtils().getInvUtils().prepareSetup(player);
    }

    @Override
    public String getName() {
        return "setup";
    }
}
