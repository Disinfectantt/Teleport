package xyz.cringee.menu.gui;

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
import net.kyori.adventure.text.Component;

import java.io.IOException;
import java.util.List;

public class teleportMenu {
    public static void Menu(Player player){
        Inventory menu = Bukkit.createInventory(player, 54, Component.text("Click on the point", TextColor.color(0,102,26), TextDecoration.BOLD));
        try {
            Json.loadPoints();
        }catch (IOException e){
            Bukkit.getLogger().warning("Exception: " + e);
        }

        int pages = numberOfPages();
        int currentPage = PlayersPagination.getPageForPlayer(player.getUniqueId());
        List<Point> points = Json.returnSomePoints(currentPage);

        if(points.size() == 0){
            menu.setItem(31, getBlock("Empty", Material.RED_WOOL, 255, 64, 0));
        }else{
            if (currentPage < 0){
                currentPage = 0;
            }
            if (currentPage > pages){
                currentPage = pages;
            }

            if(pages > 0 && currentPage != pages){
                menu.setItem(50, getBlock("Forward " + currentPage + " / " + pages, Material.ARROW, 255, 255, 255));
            }
            if(currentPage > 0){
                menu.setItem(48, getBlock("Back " + currentPage + " / " + pages, Material.ARROW, 255, 255, 255));
            }

            for (int i=0; i<points.size(); i++) {
                menu.setItem(i, getBlock(points.get(i).getName(), Material.GREEN_WOOL, 255, 255, 255));
            }
        }
        player.openInventory(menu);
    }

    public static int numberOfPages(){
        return Json.returnAllPoints().size()/46;
    }

    public static ItemStack getBlock(String name, Material material, int r, int g, int b){
        ItemStack item = new ItemStack(material, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(Component.text(name, TextColor.color(r,g,b)));
        item.setItemMeta(itemMeta);
        return item;
    }

}
