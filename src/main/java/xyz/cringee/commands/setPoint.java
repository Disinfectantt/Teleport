package xyz.cringee.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.cringee.data.Json;
import xyz.cringee.models.Point;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.logging.Logger;

public class setPoint implements CommandExecutor {
    private final static Logger logger = Logger.getLogger("Minecraft");
    private static final Json json = new Json();
    private final String successMsg = "The point was set successfully";
    private final String failMsg = "An error has occurred";
    private final String nameExistMsg = "The point name already exists";

    @Override
    public boolean onCommand(@Nullable CommandSender sender, @Nullable Command command, @Nullable String label, String[] args) {
        if (args.length == 0 || sender == null || command == null) {
            return false;
        }

        if (sender instanceof Player && args.length == 1) {
            processPlayer(sender, args);
            return true;
        }

        if (isXYZValid(args)) {
            processThreeArgs(sender, args);
            return true;
        }

        return false;
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    private boolean isXYZValid(String[] args) {
        return args.length == 4 && (isNumeric(args[0]) && isNumeric(args[1]) && isNumeric(args[2]) && !args[3].isEmpty());
    }

    private void processPlayer(CommandSender sender, String[] args) {
        String name = args[0];
        if (json.findPoint(name) != null) {
            sender.sendMessage(CommandsUtils.createFailureText(nameExistMsg));
        }
        Point point = getPoint((Player) sender, name);
        try {
            json.createPoint(point);
            sender.sendMessage(CommandsUtils.createSuccessText(successMsg));
        } catch (IOException e) {
            sender.sendMessage(CommandsUtils.createFailureText(failMsg));
        }
    }

    private static @NotNull Point getPoint(Player sender, String name) {
        Location location = sender.getLocation();
        DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance();
        decimalFormatSymbols.setDecimalSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#.##", decimalFormatSymbols);
        double x = Double.parseDouble(decimalFormat.format(location.x()));
        double y = Double.parseDouble(decimalFormat.format(location.y()));
        double z = Double.parseDouble(decimalFormat.format(location.z()));
        return new Point(x, y, z, name);
    }

    private void processThreeArgs(CommandSender sender, String[] args) {
        double x = Double.parseDouble(args[0]);
        double y = Double.parseDouble(args[1]);
        double z = Double.parseDouble(args[2]);
        Point point = new Point(x, y, z, args[3]);
        boolean flag = false;
        try {
            json.createPoint(point);
        } catch (IOException e) {
            flag = true;
        }
        if (sender instanceof ConsoleCommandSender) {
            if (flag)
                logger.info(failMsg);
            else
                logger.info(successMsg);
        } else {
            if (flag)
                sender.sendMessage(CommandsUtils.createFailureText(failMsg));
            else
                sender.sendMessage(CommandsUtils.createSuccessText(successMsg));
        }
    }

}
