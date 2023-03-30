package me.mrdev.bs;

import me.mrdev.bs.arena.ArenaManager;
import me.mrdev.bs.arena.data.ArenaYamlManager;
import me.mrdev.bs.commands.ArenaCommand;
import me.mrdev.bs.config.ArenaFile;
import me.mrdev.bs.listeners.*;
import me.mrdev.bs.utils.SumoUtils;
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
        getServer().getPluginManager().registerEvents(new WoolListener(this),this);
        getServer().getPluginManager().registerEvents(new SetupListener(this),this);
        getServer().getPluginManager().registerEvents(new JoinListener(this),this);
        getServer().getPluginManager().registerEvents(new DamageListener(this),this);
        getServer().getPluginManager().registerEvents(new GameListener(this) , this);
        getServer().getPluginManager().registerEvents(new WorldListener(this) , this);
        getCommand("arena").setExecutor(new ArenaCommand(this));
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
