package xyz.cringee.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import xyz.cringee.menu.gui.teleportMenu;

public class menu implements CommandExecutor {

    @Override
    public boolean onCommand(@Nullable CommandSender sender, @Nullable Command command, @Nullable String label, String[] args) {
        if(sender instanceof Player){
            teleportMenu.Menu((Player) sender);
        }else{
            Bukkit.getLogger().info("You are not in minecraft");
        }
        return true;
    }
}