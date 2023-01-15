package xyz.cringee.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import xyz.cringee.data.Json;
import xyz.cringee.models.Point;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class setPoint implements CommandExecutor {
    @Override
    public boolean onCommand(@Nullable CommandSender sender, @Nullable Command command, @Nullable String label, String[] args) {
        if(args.length == 0){
            return false;
        }

        if (sender instanceof Player && args.length == 1){
            String name = args[0];
            if(Json.findPoint(name) != null){
                isPlayerOrConsole(sender,"The name already exists",false);
                return true;
            }
            Location location = ((Player) sender).getLocation();
            DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance();
            decimalFormatSymbols.setDecimalSeparator('.');
            DecimalFormat decimalFormat = new DecimalFormat( "#.##", decimalFormatSymbols);
            double x = Double.parseDouble(decimalFormat.format(location.x()));
            double y = Double.parseDouble(decimalFormat.format(location.y()));
            double z = Double.parseDouble(decimalFormat.format(location.z()));
            Point point = new Point(x, y, z, name);
            if(Json.createPoint(point)){
                isPlayerOrConsole(sender,"The point was set successfully",true);
            }
            else{
                isPlayerOrConsole(sender,"An error has occurred",false);
            }
            return true;
        }

        if (isXYZValid(args)){
            String name = args[3];
            if(Json.findPoint(name) != null){
                isPlayerOrConsole(sender,"The name already exists",false);
                return true;
            }
            double x = Double.parseDouble(args[0]);
            double y = Double.parseDouble(args[1]);
            double z = Double.parseDouble(args[2]);
            Point point = new Point(x, y, z, name);
            if(Json.createPoint(point)){
                isPlayerOrConsole(sender,"The point was set successfully",true);
            }
            else{
                isPlayerOrConsole(sender,"An error has occurred",false);
            }
            return true;
        }
        return false;
    }

    public boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    public boolean isXYZValid(String[] args){
        return args.length == 4 && (isNumeric(args[0]) && isNumeric(args[1]) && isNumeric(args[2]) && !args[3].equals(""));
    }

    public void isPlayerOrConsole(CommandSender sender, String message, boolean goodOrNot){
        if (goodOrNot){
            if(sender instanceof Player){
                sender.sendMessage("§a" + message);
            }else {
                Bukkit.getLogger().info(message);
            }
        }else {
            if(sender instanceof Player){
                sender.sendMessage("§c" + message);
            }else {
                Bukkit.getLogger().info(message);
            }
        }
    }

}
