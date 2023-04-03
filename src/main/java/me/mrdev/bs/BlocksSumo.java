package me.mrdev.bs;

import me.mrdev.bs.arena.ArenaManager;
import me.mrdev.bs.arena.data.ArenaYamlManager;
import me.mrdev.bs.commands.ArenaBaseCommand;
import me.mrdev.bs.config.ArenaFile;
import me.mrdev.bs.listeners.*;
import me.mrdev.bs.utils.SumoUtils;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class BlocksSumo extends JavaPlugin {

    private ArenaFile arenaFile; //null before loaded (onEnable)
    private ArenaYamlManager Ymlmanager;
    private ArenaManager arenaManager;
    private SumoUtils sumoUtils;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        sumoUtils = new SumoUtils();
        arenaFile = new ArenaFile(this);
        getLogger().info("Successfully loaded two yaml files");
        Ymlmanager = new ArenaYamlManager(this);
        arenaManager = new ArenaManager(this);
        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new WoolListener(this),this);
        manager.registerEvents(new SetupListener(this),this);
        manager.registerEvents(new JoinListener(this),this);
        manager.registerEvents(new DamageListener(this),this);
        manager.registerEvents(new GameListener(this) , this);
        manager.registerEvents(new WorldListener(this) , this);
        manager.registerEvents(new ChatListener(this), this);
        getCommand("arena").setExecutor(new ArenaBaseCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public SumoUtils getSumoUtils() {
        return sumoUtils;
    }

    public ArenaFile getArenaFile() {
        return arenaFile;
    }

    public ArenaYamlManager getArenaYmlmanager() {
        return Ymlmanager;
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }

}
