package xyz.cringee.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import xyz.cringee.data.Json;

public class deletePoint implements CommandExecutor {

    @Override
    public boolean onCommand(@Nullable CommandSender sender, @Nullable Command command, @Nullable String label, String[] args) {
        if(args.length == 1){
            if(Json.deletePoint(args[0])){
                if(sender instanceof Player){
                    sender.sendMessage("§aThe point was delete successfully");
                }else {
                    Bukkit.getLogger().info("The point was delete successfully");
                }
            }else {
                if(sender instanceof Player){
                    sender.sendMessage("§cThe point not found");
                }else {
                    Bukkit.getLogger().info("The point not found");
                }
            }
            return true;
        }
        return false;
    }
}
