package me.mrdev.bs.game;

import me.mrdev.bs.BlocksSumo;
import me.mrdev.bs.arena.Arena;
import me.mrdev.bs.arena.ArenaState;
import me.mrdev.bs.events.GamePlayerDeathEvent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class GameArena extends Arena {

    private BlocksSumo plugin;//Manager for each Arena

    public GameArena(BlocksSumo plugin , Arena arena) {
        super(arena);
        this.plugin = plugin;
    }

    public void prepareSpawns() {
       List<ChatColor> validColors = Arrays.stream(ChatColor.values())
                .filter(c -> c != ChatColor.ITALIC  && c != ChatColor.MAGIC && c != ChatColor.BOLD && c != ChatColor.RESET && c != ChatColor.STRIKETHROUGH && c != ChatColor.UNDERLINE)
                .collect(Collectors.toList());
        ArrayList<Location> spawns = (ArrayList<Location>) getSpawns().clone();
        getPlayers().forEach(p -> {
            Player player = p.getPlayer();
            ChatColor color = validColors.get(ThreadLocalRandom.current().nextInt(validColors.size()));
            p.setTeam(color);
            validColors.remove(color);
            Location location = spawns.get(ThreadLocalRandom.current().nextInt(spawns.size()));
            player.teleport(location);
            spawns.remove(location);
            player.setGameMode(GameMode.SURVIVAL);
            player.getInventory().clear();
            player.getInventory().addItem(new ItemStack(Material.WOOL , 64));
            player.getInventory().addItem(new ItemStack(Material.SHEARS));
            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT , 2 , 1);
        });
    }

    public void addSpectator(GamePlayer player) {
        if(player == null || !getPlayers().contains(player)) return;//if player isn't a specific arena player then nothing
        //plugin.getArenaManager().removeArena(this , false);
        player.getPlayer().getInventory().clear();
        player.removeLives(1);
        getSpectators().add(player);
        //plugin.getArenaManager().addArena(this , false);
        plugin.getServer().getPluginManager().callEvent(new GamePlayerDeathEvent(this , player.getPlayer()));
    }

    public void respawnSpectator(GamePlayer player) {
        if(player == null || !getSpectators().contains(player)) return;
        Player p = player.getPlayer();
        p.setFlying(false);
        p.setAllowFlight(false);
        p.teleport(getSpawns().get(ThreadLocalRandom.current().nextInt(getSpawns().size())));
        p.getActivePotionEffects().forEach(pt -> p.removePotionEffect(pt.getType()));
        p.getInventory().addItem(new ItemStack(Material.WOOL , 64));
        p.getInventory().addItem(new ItemStack(Material.SHEARS));
        p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT , 2 , -1);
        //plugin.getArenaManager().removeArena(this , false);
        getSpectators().remove(player);
        //plugin.getArenaManager().addArena(this , false);
    }

    public GamePlayer getLastStanding() {
        if(getAliveCount() == 1) {
            return getPlayers().stream()
                    .filter(p -> !isDead(p))
                    .findAny().orElse(null);
        }
        if(getAliveCount() == 0 && getPlayers().size() == 1) {
            return getPlayers().get(0);
        }
        return null;
    }

    public int getAliveCount() {
        return (int) getPlayers().stream()
                .filter(p -> !isDead(p))
                .count();
    }

    public void messagePlayers(String msg) {
        getPlayers().forEach(p -> p.getPlayer().sendMessage(msg));
    }


    public void clearPlayers() {
        getSpectators().forEach(p -> {
            Player player = p.getPlayer();
            player.getActivePotionEffects().forEach(pt -> player.removePotionEffect(pt.getType()));
            player.setAllowFlight(false);
            player.setFlying(false);
        });
        getSpectators().clear();
        getPlayers().forEach(p -> {
            Player player = p.getPlayer();
            player.getInventory().clear();
            player.teleport(player.getWorld().getSpawnLocation());//TODO: Add main lobby
        });
        getPlayers().clear();
        setState(ArenaState.WAITING);
    }

    public boolean isSpectator(GamePlayer player) {
        if(player == null) return false;
        return getSpectators().contains(player);
    }

    public boolean isDead(GamePlayer player) {
        if(player == null) return false;
        return isSpectator(player) && player.getLives() <= 0;
    }

}
