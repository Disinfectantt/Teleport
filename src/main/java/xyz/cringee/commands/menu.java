package xyz.cringee.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import xyz.cringee.menu.gui.teleportMenu;

import java.util.logging.Logger;

public class menu implements CommandExecutor {
    private final static Logger logger = Logger.getLogger("Minecraft");
    private final static teleportMenu teleportMenu = new teleportMenu();

    @Override
    public boolean onCommand(@Nullable CommandSender sender, @Nullable Command command, @Nullable String label, String[] args) {
        if (sender == null)
            return false;
        if (sender instanceof Player) {
            teleportMenu.Menu((Player) sender);
        } else {
            logger.info("You are not in minecraft");
        }
        return true;
    }
}