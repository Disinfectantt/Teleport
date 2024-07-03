package xyz.cringee.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import xyz.cringee.data.Json;

import java.util.logging.Logger;

public class deletePoint implements CommandExecutor {
    private final static Logger logger = Logger.getLogger("Minecraft");
    private final static Json json = new Json();

    @Override
    public boolean onCommand(@Nullable CommandSender sender, @Nullable Command command, @Nullable String label, String[] args) {
        if (args.length == 1) {
            if (json.deletePoint(args[0])) {
                String sucMsg = "The point was delete successfully";
                if (sender instanceof Player) {
                    sender.sendMessage(CommandsUtils.createSuccessText(sucMsg));
                } else {
                    logger.info(sucMsg);
                }
            } else {
                String failMsg = "The point not found";
                if (sender instanceof Player) {
                    sender.sendMessage(CommandsUtils.createSuccessText(failMsg));
                } else {
                    logger.info(failMsg);
                }
            }
            return true;
        }
        return false;
    }
}
