package me.mrdev.bs.arena;

import me.mrdev.bs.BlocksSumo;
import me.mrdev.bs.arena.data.ArenaYamlManager;
import me.mrdev.bs.game.GamePlayer;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class ArenaManager {

    private List<Arena> arenas;
    private ArenaYamlManager yamlManager;

    public ArenaManager(BlocksSumo plugin) {
        this.yamlManager = plugin.getArenaYmlmanager();
        this.arenas = yamlManager.loadArenas();
    }

    public Arena getArena(UUID id) {
        return arenas.stream().filter(a -> a.getID() == id).findAny().orElse(null);
    }

    public Arena getArena(String name) {
        return arenas.stream().filter(a -> a.getName().equals(name)).findAny().orElse(null);
    }

    public Arena getPlayerArena(Player player) {
        return arenas.stream().filter(a -> a.getGamePlayer(player) != null).findAny().orElse(null);
    }

    public Arena getPlayerArena(GamePlayer player) {
        return arenas.stream().filter(a -> a.getPlayers().contains(player)).findAny().orElse(null);
    }

    public boolean exists(UUID id) {
        return arenas.stream().anyMatch(a -> a.getID() == id);
    }

    public boolean exists(String name) {
        return arenas.stream().anyMatch(a -> a.getName().equals(name));
    }

    public boolean isPlayerInArena(GamePlayer player) {
        return getPlayerArena(player) != null;
    }

    public boolean isPlayerInArena(Player player) {
        return getPlayerArena(player) != null;
    }

    public List<Arena> getLoadedArenas() {
        return arenas;
    }

    public void addArena(Arena arena , boolean save) { //if temporary (won't be saved after reload/restart)
        if(save) {
            try {
                yamlManager.saveArena(arena);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        arenas.add(arena);
    }

    public void removeArena(Arena arena , boolean file) {
        if(file) {
            //TODO:Remove arena from arenas.yml
        }
        arenas.remove(arena);
    }
 }
