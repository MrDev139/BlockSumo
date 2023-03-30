package me.mrdev.bs.arena.data;

import me.mrdev.bs.BlocksSumo;
import me.mrdev.bs.arena.Arena;
import me.mrdev.bs.config.ArenaFile;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ArenaYamlManager {

    private BlocksSumo plugin;
    private  ArenaFile arenaFile;

    public ArenaYamlManager(BlocksSumo plugin) {
        this.plugin = plugin;
        arenaFile = plugin.getArenaFile();
    }

    public List<Arena> loadArenas() {
        List<Arena> arenas = new ArrayList<>();
        FileConfiguration config = arenaFile.getConfig();
        ConfigurationSection asection = config.getConfigurationSection("Arenas");
        if(asection != null && !asection.getKeys(true).isEmpty()) {
            for (String string : asection.getKeys(false)) {
                Arena arena = new Arena(null, UUID.fromString(string));
                arena.setName(asection.getString(string + ".Name"));
                arena.setMin(asection.getInt(string + ".Min"));
                arena.setMax(asection.getInt(string + ".Max"));
                arena.setLives(asection.getInt(string + ".Lives"));
                ConfigurationSection Ssection = asection.getConfigurationSection(string + ".Spawns");
                for (String spawn : Ssection.getKeys(false)) {
                    ArrayList<Location> spawns = new ArrayList<>();
                    for (int i = 0; i <= Ssection.getKeys(false).size(); i++) {
                        spawns.add(new Location(plugin.getServer().getWorld(Ssection.getString(spawn + ".world")), Ssection.getDouble(spawn + ".x"), Ssection.getDouble(spawn + ".y"), Ssection.getDouble(spawn + ".z"), (float) Ssection.getDouble(spawn + ".yaw"), (float) Ssection.getDouble(spawn + ".pitch")));
                    }
                    arena.setSpawns(spawns);
                }
                arena.setLobby(new Location(plugin.getServer().getWorld(asection.getString(string + ".Lobby.world")), asection.getDouble(string + ".Lobby.x"), asection.getDouble(string + ".Lobby.y"), asection.getDouble(string + ".Lobby.z")));
                arena.setSpectatorloc(new Location(plugin.getServer().getWorld(asection.getString(string + ".SpecLoc.world")), asection.getDouble(string + ".SpecLoc.x"), asection.getDouble(string + ".SpecLoc.y"), asection.getDouble(string + ".SpecLoc.z")));
                arenas.add(arena);
            }
        }
        return arenas;
    }

    public void saveArena(Arena arena) throws IOException {
        if(arena == null) return;
        String name = arena.getName();
        String ID = arena.getID().toString();
        int min = arena.getMin();
        int max = arena.getMax();
        List<Location> spawns = arena.getSpawns();
        Location lobby = arena.getLobby();
        Location specLoc = arena.getSpectatorloc();
        int lives = arena.getLives();
        FileConfiguration config = arenaFile.getConfig();
        config.addDefault("Arenas" , ID);
        config.addDefault("Arenas." + ID + ".Name" , name);
        config.addDefault("Arenas." + ID + ".Min" , min);
        config.addDefault("Arenas." + ID + ".Max" , max);
        config.addDefault("Arenas." + ID + ".Lives", lives);
        for (int i = 0; i < spawns.size() ; i++) {
            config.addDefault("Arenas." + ID + ".Spawns.spawn" + i , spawns.get(i).serialize());
        }
        config.addDefault("Arenas." + ID + ".Lobby", lobby.serialize());
        config.addDefault("Arenas." + ID + ".SpecLoc", specLoc.serialize());
        arenaFile.saveConfig();
    }

}
