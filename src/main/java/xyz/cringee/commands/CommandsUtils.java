package xyz.cringee.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class CommandsUtils {
    public static Component createFailureText(String msg) {
        return Component.text(msg, NamedTextColor.RED);
    }

    public static Component createSuccessText(String message) {
        return Component.text(message, NamedTextColor.GREEN);
    }
}
