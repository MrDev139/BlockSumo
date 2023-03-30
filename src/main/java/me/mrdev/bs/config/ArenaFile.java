package me.mrdev.bs.config;

import me.mrdev.bs.BlocksSumo;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ArenaFile {

    private BlocksSumo plugin;
    private File file;
    private FileConfiguration config;

    public ArenaFile(BlocksSumo plugin) {
        this.file = new File(plugin.getDataFolder() + "/arenas.yml");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public File getArenasFile() {
        return file;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void saveConfig() throws IOException {
        config.options().copyDefaults(true);
        config.save(file);
    }
}
