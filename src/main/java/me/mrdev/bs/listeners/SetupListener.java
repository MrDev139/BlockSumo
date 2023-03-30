package me.mrdev.bs.listeners;

import me.mrdev.bs.BlocksSumo;
import me.mrdev.bs.arena.Arena;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

import java.io.IOException;
import java.util.*;

public class SetupListener implements Listener {

    private HashMap<UUID, Boolean> waiters;
    private HashMap<UUID, Arena> clickers;
    private HashMap<UUID, Inventory> invs;
    private HashMap<UUID, String> actions;
    private HashMap<UUID, Boolean> completers;
    private BlocksSumo plugin;
    public SetupListener(BlocksSumo plugin) {
        this.plugin = plugin;
        clickers = new HashMap<>();
        waiters = new HashMap<>();
        invs = new HashMap<>();
        completers = new HashMap<>();
        actions = new HashMap<>();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        UUID id = player.getUniqueId();
        if (event.getClickedInventory().getName().equals("Arena Setup")) {

            if(!clickers.containsKey(id)) {
                clickers.put(id, new Arena(null, UUID.randomUUID()));
            }

            if(!completers.containsKey(id)) {
                completers.put(id, false);
            }

            Arena arena = clickers.get(player.getUniqueId());
            ItemStack current = event.getCurrentItem();

            if (current != null && current.getItemMeta() != null) {
                //action = current.getItemMeta().getDisplayName();
                actions.put(id, current.getItemMeta().getDisplayName());

                if(!invs.containsKey(id)) {
                    invs.put(id, event.getClickedInventory());
                }

                if(!waiters.containsKey(id)) {
                    waiters.put(id, false);
                }
                Inventory inv = invs.get(id);
                player.playSound(player.getLocation(), Sound.CLICK , 2 , 1);
                switch (actions.get(id)) {
                    case "Set Name":
                        waiters.put(id, true);
                        player.sendMessage("Please type the name of the arena");
                        player.closeInventory();
                        break;
                        case "Set Min":
                            waiters.put(id, true);
                            player.sendMessage("Please enter the Min amount of players for the arena(number)");
                            player.closeInventory();
                            break;
                        case "Set Max":
                            waiters.put(id, true);
                            player.sendMessage("Please enter the Max amount of players for the arena(number)");
                            player.closeInventory();
                            break;
                        case "Add Spawn":
                            waiters.put(id, true);
                            player.sendMessage("Please stand where you want to add a spawn and send in chat \"Done\"");
                            player.closeInventory();
                            break;
                        case "Set Lobby":
                            waiters.put(id, true);
                            player.sendMessage("Please stand where you want to set the lobby and send in chat \"Done\"");
                            player.closeInventory();
                            break;
                        case "Set Spectator Spawn":
                            waiters.put(id, true);
                            player.sendMessage("Please go to where you want to set the spectator location and send in chat \"Done\"");
                            player.closeInventory();
                        case "Save Arena":
                                if (!isArenaComplete(arena)) {
                                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 2);
                                    addNote(inv, 16, ChatColor.DARK_PURPLE + "Setup not complete!");
                                    break;
                                }
                            player.sendMessage("Saving the arena...");
                            plugin.getArenaManager().addArena(arena, true);
                            player.sendMessage("Successfully saved the Arena!");
                            //complete = true;
                            completers.put(id, true);
                            player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 2);
                            player.closeInventory();
                            break;
                    }
                }
                event.setCancelled(true);
            }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID id = player.getUniqueId();
        if(clickers.containsKey(id)) {
            Arena arena = clickers.get(id);
            Inventory inv = invs.get(id);
            boolean complete = completers.get(id);
            boolean waitingChat = waiters.get(id);
            if(arena != null && inv != null && !complete) {
                if (waitingChat) {
                    String message = event.getMessage();
                    switch (actions.get(id)) {
                        case "Set Name":
                            if(!plugin.getArenaManager().exists(message.trim())) {
                                arena.setName(message.trim());
                                addNote(inv,10, "Current name: " + arena.getName());
                            }else {
                                player.sendMessage(message.trim() + " already exists!!");
                            }
                            waiters.put(id, false);
                            break;
                        case "Set Min":
                            if(!plugin.getSumoUtils().getMathUtils().isInteger(message)) {
                                player.sendMessage("Invalid number! Try Again");
                            }else {
                                int min = Integer.parseInt(message);
                                if(arena.getMax() != 0 && min > arena.getMax()) {
                                    player.sendMessage("Min amount can't be bigger than Max amount! Please try again!!");
                                }else {
                                    arena.setMin(min);
                                    addNote(inv, 11, "Current min: " + arena.getMin());
                                    }
                                }
                            waiters.put(id, false);
                            break;
                        case "Set Max":
                            if(!plugin.getSumoUtils().getMathUtils().isInteger(message)) {
                                player.sendMessage("Invalid number! Try Again");
                            }else {
                                int max = Integer.parseInt(message);
                                if(max >= arena.getMin()) {
                                    arena.setMax(max);
                                    addNote(inv, 12, "Current Max: " + arena.getMax());
                                }else {
                                    player.sendMessage("Max can't be smaller than arena's min!! Try again!");
                                }
                            }
                            waiters.put(id, false);
                            break;
                        case "Add Spawn":
                            if(message.equals("Done")) {
                                    if(arena.getSpawns() == null) {
                                        arena.setSpawns(new ArrayList<>());
                                    }
                                    arena.getSpawns().add(player.getLocation()); //i have to get the correct size for comparison with the max
                                    if (arena.getSpawns().size() <= arena.getMax()) {
                                        DyeColor color = arena.getSpawns().size() >= arena.getMax() ? DyeColor.GREEN : DyeColor.YELLOW;
                                        addNote(inv, new Wool(color).toItemStack(1), 13, "Added Spawns: " + arena.getSpawns().size());
                                    } else {
                                        arena.getSpawns().remove(player.getLocation());
                                        player.sendMessage("Max amount of spawns reached!! you can increase it by increasing the max amount of players!!");
                                    }
                            }else {
                                player.sendMessage("Invalid message! Try again");
                            }
                            waiters.put(id, false);
                            break;
                        case "Set Lobby":
                            if(message.equals("Done")) {
                                arena.setLobby(player.getLocation());
                                addNote(inv, 14, "Lobby is set");
                            }else {
                                player.sendMessage("Invalid message! Try again");
                            }
                            waiters.put(id, false);
                            break;
                        case "Set Spectator Spawn":
                            if(message.equals("Done")) {
                                arena.setSpectatorloc(player.getLocation());
                                addNote(inv, 15, "Spectator location is set");
                            }else {
                                player.sendMessage("Invalid message! Try again");
                            }
                            waiters.put(id, false);
                            break;
                    }
                    player.openInventory(inv);
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onInvClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        UUID id = player.getUniqueId();
        if(event.getInventory().getName().equals("Arena Setup")) {
            if(!completers.get(id)) {
                if (!waiters.get(id)) {
                    player.sendMessage("Arena setup cancelled");
                    cancel(id);
                }
            }else {
                player.sendMessage("Arena setup Completed!!");
                cancel(id);
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        UUID id = event.getPlayer().getUniqueId();
        if(clickers.containsKey(id)) {
            cancel(id);
        }
    }

    private boolean isArenaComplete(Arena arena) {
        return arena.getName() != null && arena.getMin() != 0 && arena.getMax() != 0 && !arena.getSpawns().isEmpty() && arena.getLobby() != null && arena.getSpectatorloc() != null;
    }

    private void addNote(Inventory inv, int slot, String... notes) {
        ItemStack item = new Wool(DyeColor.GREEN).toItemStack(1);
        ItemMeta meta = inv.getItem(slot).getItemMeta();;
        meta.setLore(Arrays.asList(notes));
        item.setItemMeta(meta);
        inv.setItem(slot , item);
    }

    private void addNote(Inventory inv, ItemStack invItem, int slot, String... notes) {
        ItemMeta meta = inv.getItem(slot).getItemMeta();
        meta.setLore(Arrays.asList(notes));
        invItem.setItemMeta(meta);
        inv.setItem(slot, invItem);
    }

    private void cancel(UUID id) {
        invs.remove(id);
        clickers.remove(id);
        completers.remove(id);
    }

}
