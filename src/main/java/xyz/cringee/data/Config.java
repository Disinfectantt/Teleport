package xyz.cringee.data;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Config {
    private static final String path = "./plugins/Teleport/";
    private static final String filename = "config.yml";
    private static FileConfiguration config;

    public static void config() throws IOException{
        Files.createDirectories(Paths.get(path));
        File configFile = new File(path + filename);
        if(!configFile.exists() && !configFile.isDirectory()){
            boolean created = configFile.createNewFile();
            if(created) {
                Bukkit.getLogger().info("Created " + path + filename);
            }
        }
        config = YamlConfiguration.loadConfiguration(configFile);
        config.addDefault("BlueMap", false);
        config.options().copyDefaults(true);
        config.save(configFile);
    }

    public static FileConfiguration getConfig() {
        return config;
    }
}
