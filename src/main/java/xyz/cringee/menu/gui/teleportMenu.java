package xyz.cringee.menu.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.cringee.data.Json;
import xyz.cringee.models.PlayersPagination;
import xyz.cringee.models.Point;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class teleportMenu {
    private static final Json json = new Json();
    private final static Logger logger = Logger.getLogger("Minecraft");
    private static final PlayersPagination playersPagination = new PlayersPagination();

    public void Menu(Player player) {
        Inventory menu = Bukkit.createInventory(player, 54, Component.text("Click on the point", TextColor.color(0, 102, 26), TextDecoration.BOLD));
        try {
            json.loadPoints();
        } catch (IOException e) {
            logger.warning("Exception: " + e);
        }

        int pages = numberOfPages();
        int currentPage = playersPagination.getPageForPlayer(player.getUniqueId());
        List<Point> points = json.returnSomePoints(currentPage);

        if (points.isEmpty()) {
            menu.setItem(31, getBlock("Empty", Material.RED_WOOL, NamedTextColor.RED, null));
        } else {
            if (currentPage < 0) {
                currentPage = 0;
            }
            if (currentPage > pages) {
                currentPage = pages;
            }

            if (pages > 0 && currentPage != pages) {
                menu.setItem(50, getBlock("Forward " + currentPage + " / " + pages, Material.ARROW, NamedTextColor.WHITE, null));
            }
            if (currentPage > 0) {
                menu.setItem(48, getBlock("Back " + currentPage + " / " + pages, Material.ARROW, NamedTextColor.WHITE, null));
            }

            for (int i = 0; i < points.size(); i++) {
                menu.setItem(i, getBlock(points.get(i).getName(), Material.GREEN_WOOL, NamedTextColor.WHITE, points.get(i).getId()));
            }
        }
        player.openInventory(menu);
    }

    public int numberOfPages() {
        return json.returnAllPoints().size() / 46;
    }

    public ItemStack getBlock(String name, Material material, NamedTextColor color, String id) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(Component.text(name, color));
        if (id != null) {
            List<Component> lore = new ArrayList<>(1);
            Component c = Component.text(id, NamedTextColor.DARK_GRAY);
            lore.add(c);
            itemMeta.lore(lore);
        }
        item.setItemMeta(itemMeta);
        return item;
    }

}
