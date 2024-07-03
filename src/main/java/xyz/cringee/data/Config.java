package xyz.cringee.data;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class Config {
    private static FileConfiguration config;
    private final static Logger logger = Logger.getLogger("Minecraft");

    public Config() {
        try {
            config();
        } catch (Exception e) {
            logger.warning("Failed to load config");
        }
    }

    public void config() throws IOException {
        String filename = "config.yml";
        String path = "./plugins/Teleport/";
        File configFile = Utils.createFileIfNotExists(path, filename, logger);
        config = YamlConfiguration.loadConfiguration(configFile);
        config.addDefault("BlueMap", false);
        config.options().copyDefaults(true);
        config.save(configFile);
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
