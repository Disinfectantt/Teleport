package xyz.cringee;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.cringee.bluemapAPI.BlueMapAPIMarkers;
import xyz.cringee.commands.deletePoint;
import xyz.cringee.commands.menu;
import xyz.cringee.commands.setPoint;
import xyz.cringee.data.Config;
import xyz.cringee.data.Json;
import xyz.cringee.menu.events.clickEvent;
import xyz.cringee.menu.events.onPlayerJoinEvent;

import java.io.IOException;
import java.util.logging.Logger;

public class Main extends JavaPlugin {
    private final Json json = new Json();
    private final Config config = new Config();
    private final Logger logger = Logger.getLogger("Minecraft");

    @Override
    public void onEnable() {
        try {
            config.config();
            json.loadPoints();
        } catch (IOException e) {
            logger.warning("Exception: " + e);
        }
        getCommand("tsetpoint").setExecutor(new setPoint());
        getCommand("tdeletepoint").setExecutor(new deletePoint());
        getCommand("tmenu").setExecutor(new menu());
        Bukkit.getPluginManager().registerEvents(new clickEvent(), this);
        Bukkit.getPluginManager().registerEvents(new onPlayerJoinEvent(), this);

        logger.info("Teleport enabled");

        if (config.getConfig().getBoolean("BlueMap")) {
            BlueMapAPIMarkers.markersOnEnable();
        }
    }

    @Override
    public void onDisable() {
        logger.info("Teleport disabled");
    }
}