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

public class Main extends JavaPlugin{
    @Override
    public void onEnable() {
        try {
            Config.config();
            Json.loadPoints();
        }catch (IOException e){
            Bukkit.getLogger().warning("Exception: " + e);
        }
        getCommand("tsetpoint").setExecutor(new setPoint());
        getCommand("tdeletepoint").setExecutor(new deletePoint());
        getCommand("tmenu").setExecutor(new menu());
        Bukkit.getPluginManager().registerEvents(new clickEvent(), this);
        Bukkit.getPluginManager().registerEvents(new onPlayerJoinEvent(), this);

        Bukkit.getLogger().info("Teleport enabled");

        if(Config.getConfig().getBoolean("BlueMap")){
            BlueMapAPIMarkers.markers();
        }
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Teleport disabled");
    }
}