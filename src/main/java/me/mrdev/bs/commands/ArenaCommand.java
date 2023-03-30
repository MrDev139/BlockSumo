package me.mrdev.bs.commands;

import me.mrdev.bs.BlocksSumo;
import me.mrdev.bs.arena.Arena;
import me.mrdev.bs.arena.ArenaManager;
import me.mrdev.bs.arena.ArenaState;
import me.mrdev.bs.events.ArenaJoinEvent;
import me.mrdev.bs.events.ArenaLeaveEvent;
import me.mrdev.bs.game.GamePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaCommand implements CommandExecutor {

    private BlocksSumo plugin;
    private ArenaManager manager;

    public ArenaCommand(BlocksSumo plugin) {
        this.plugin = plugin;
        this.manager = plugin.getArenaManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            String[] help = {"Available commands:" , "/arena list" , "/arena setup" , "/arena join <arena>" , "/arena leave" ,  "/arena modify <arena name>"};
            sender.sendMessage(help);
            return true;
        }else if(args.length == 1) {
            String sub = args[0];
            if(sub.equalsIgnoreCase("setup")) {
                if(!(sender instanceof Player)) {
                    sender.sendMessage("Sorry you need to be ingame to setup arenas");
                }else {
                    Player player = (Player) sender;
                    plugin.getSumoUtils().getInvUtils().prepareSetup(player);
                }
            }else if(sub.equalsIgnoreCase("list")) {
                sender.sendMessage("Loaded arenas :");
                manager.getLoadedArenas().forEach(a -> sender.sendMessage(a.getName() + " | " + a.getPlayers().size() + " | " + a.getState().toString().toLowerCase()));
            }else if(sub.equalsIgnoreCase("leave")) {
                if(sender instanceof Player) {
                    Player player = (Player) sender;
                    if(manager.isPlayerInArena(player)) {
                        Arena left = manager.getPlayerArena(player);
                        //manager.removeArena(left , false);
                        left.getPlayers().remove(left.getGamePlayer(player));
                        //manager.addArena(left , false);
                        plugin.getServer().getPluginManager().callEvent( new ArenaLeaveEvent(player , left , false));
                    }else {
                        player.sendMessage("You are not in an arena!");
                    }
                }else {
                    sender.sendMessage("You are not a player lol");
                }
            }
        }else if(args.length == 2) {
            String sub = args[0];
            if(sub.equalsIgnoreCase("modify")) {
                String arenaName = args[1];
                if(manager.exists(arenaName)) {
                    if(sender instanceof Player) {
                        Player player = (Player) sender;
                        player.sendMessage("The command will be implemented soon");
                    }else {
                        sender.sendMessage("Please login in order to modify it(GUI)");
                    }
                }else {
                    sender.sendMessage("Arena Doesn't exist!");
                }
            }else if(sub.equalsIgnoreCase("join")) {
                if(sender instanceof Player) {
                    Player player = (Player) sender;
                    String arenaName = args[1];
                    if (manager.exists(arenaName)) {
                        Arena arena = manager.getArena(arenaName); //arena to join
                        if (!arena.isFull() && arena.getState() != ArenaState.INGAME) {
                           if(manager.isPlayerInArena(player)) {
                               if(manager.getPlayerArena(player).equals(arena)) {
                                   player.sendMessage("You have already joined this arena");
                               }else {
                                   //start of "Arena leaving code"
                                   Arena leave = manager.getPlayerArena(player);
                                   //manager.getLoadedArenas().remove(leave);
                                   leave.getPlayers().remove(leave.getGamePlayer(player));
                                   plugin.getServer().getPluginManager().callEvent(new ArenaLeaveEvent(player , leave , false));
                                   //manager.addArena(leave , false);
                                   //end of leaving code

                                   //Start of joining code
                                   //manager.getLoadedArenas().remove(arena);
                                   arena.getPlayers().add(new GamePlayer(player , arena.getLives()));
                                   ArenaJoinEvent event = new ArenaJoinEvent(player , arena);
                                   if(event.isCancelled()) {
                                       arena.getPlayers().remove(arena.getGamePlayer(player));
                                   }
                                   //manager.addArena(arena , false);
                                   plugin.getServer().getPluginManager().callEvent(event);
                                   //end of joining code
                               }
                           }else {
                               //manager.getLoadedArenas().remove(arena);
                               arena.getPlayers().add(new GamePlayer(player , arena.getLives()));
                               ArenaJoinEvent event = new ArenaJoinEvent(player , arena);
                               if(event.isCancelled()) {
                                   arena.getPlayers().remove(arena.getGamePlayer(player));
                               }
                               //manager.addArena(arena , false);
                               plugin.getServer().getPluginManager().callEvent(event);
                           }
                        }else {
                            player.sendMessage("The arena is full or it has started!");
                        }
                    }else {
                        player.sendMessage("Arena doesn't exist!");
                    }
                }else {
                    sender.sendMessage("You have to be ingame to join an arena!");
                }
            }
        }
        return true;
    }

}
