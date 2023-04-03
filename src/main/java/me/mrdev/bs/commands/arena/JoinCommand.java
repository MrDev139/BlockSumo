package me.mrdev.bs.commands.arena;

import me.mrdev.bs.BlocksSumo;
import me.mrdev.bs.arena.Arena;
import me.mrdev.bs.arena.ArenaManager;
import me.mrdev.bs.arena.ArenaState;
import me.mrdev.bs.commands.SubCommand;
import me.mrdev.bs.events.ArenaJoinEvent;
import me.mrdev.bs.events.ArenaLeaveEvent;
import me.mrdev.bs.game.GamePlayer;
import org.bukkit.entity.Player;

public class JoinCommand extends SubCommand<Player> {

    public JoinCommand(BlocksSumo plugin) {
        super(plugin);
    }

    @Override
    public void execute(Player player, String... args) {
        // /join <name>
        if(args.length == 0) {
            player.sendMessage("You still gotta type the name of the arena /arena join <name>");
            //TODO: random arena join based on state && num of players algo
            return;
        }

        if(args.length > 1) {
            player.sendMessage("the correct args for the cmd are /arena join <name>");
            return;
        }

        ArenaManager manager = getPlugin().getArenaManager();
        String name = args[0];

        if(!manager.exists(name)) {
            player.sendMessage("The arena doesn't exist");
            return;
        }

        Arena join = manager.getArena(name);
        if(join.isFull() && join.getState() == ArenaState.INGAME || join.getState() == ArenaState.ENDING) {
            player.sendMessage("The game is full or has already started!");
            return;
        }

        if(manager.isPlayerInArena(player)) {
           Arena leave = manager.getPlayerArena(player);

           if(join.getID().equals(leave.getID())) {
               player.sendMessage("You have already joined!");
               return;
           }

           leave.getPlayers().remove(leave.getGamePlayer(player));
           getPlugin().getServer().getPluginManager().callEvent(new ArenaLeaveEvent(player , leave , false));
        }

        join.getPlayers().add(new GamePlayer(player, join.getLives()));

        ArenaJoinEvent event = new ArenaJoinEvent(player , join);

        if(event.isCancelled()) {
            join.getPlayers().remove(join.getGamePlayer(player));
        }

        getPlugin().getServer().getPluginManager().callEvent(event);

    }

    @Override
    public String getName() {
        return "join";
    }
}
