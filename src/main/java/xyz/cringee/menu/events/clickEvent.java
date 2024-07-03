package xyz.cringee.menu.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import xyz.cringee.data.Json;
import xyz.cringee.menu.gui.teleportMenu;
import xyz.cringee.models.PlayersPagination;
import xyz.cringee.models.Point;

import java.util.List;
import java.util.Objects;

public class clickEvent implements Listener {
    private static final PlayersPagination playersPagination = new PlayersPagination();
    private static final teleportMenu teleportMenu = new teleportMenu();
    private static final Json json = new Json();

    @EventHandler
    public void inventoryClick(InventoryClickEvent e) {
        String inventoryTitle = serializeComponent(e.getView().title());
        if (inventoryTitle.equalsIgnoreCase("Click on the point")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) {
                return;
            }
            Component textComp = e.getCurrentItem().getItemMeta().displayName();
            if (textComp == null) {
                return;
            }
            String serializedText = serializeComponent(textComp);
            if (e.getCurrentItem().getType() == Material.GREEN_WOOL) {
                teleportProcess(e, serializeComponent(Objects.requireNonNull(e.getCurrentItem().lore()).getFirst()));
            }
            if (e.getCurrentItem().getType() == Material.ARROW) {
                arrowsProcess(serializedText, e);
            }
        }
    }

    private String serializeComponent(Component comp) {
        return PlainTextComponentSerializer.plainText().serialize(comp);
    }

    private void teleportProcess(InventoryClickEvent e, String serializedText) {
        Point point = json.findPoint(serializedText);
        if (point == null) {
            return;
        }
        double x = point.getX();
        double y = point.getY();
        double z = point.getZ();
        Player player = (Player) e.getWhoClicked();
        player.closeInventory();
        List<World> listOfWorlds = Bukkit.getWorlds();
        player.teleport(new Location(listOfWorlds.getFirst(), x, y, z));
    }

    private void arrowsProcess(String serializedText, InventoryClickEvent e) {
        if (serializedText.contains("Forward")) {
            Player player = (Player) e.getWhoClicked();
            Integer page = playersPagination.getPageForPlayer(player.getUniqueId());
            page++;
            playersPagination.setPageForPlayer(player.getUniqueId(), page);
            teleportMenu.Menu(player);
        }
        if (serializedText.contains("Back")) {
            Player player = (Player) e.getWhoClicked();
            Integer page = playersPagination.getPageForPlayer(player.getUniqueId());
            page--;
            playersPagination.setPageForPlayer(player.getUniqueId(), page);
            teleportMenu.Menu(player);
        }
    }
}
