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
import xyz.cringee.models.PlayersPagination;
import xyz.cringee.models.Point;
import xyz.cringee.menu.gui.teleportMenu;

import java.util.List;

public class clickEvent implements Listener {
    @EventHandler
    public void inventoryClick(InventoryClickEvent e){
        String inventoryTitle = serializeComponent(e.getView().title());
        if(inventoryTitle.equalsIgnoreCase("Click on the point")){
            e.setCancelled(true);
            if(e.getCurrentItem() == null){
                return;
            }
            Component textComp = e.getCurrentItem().getItemMeta().displayName();
            String serializedText = serializeComponent(textComp);

            if (textComp == null) {
                return;
            }
            if (e.getCurrentItem().getType() == Material.GREEN_WOOL) {
                Point point = Json.findPoint(serializedText);
                if (point == null) {
                    return;
                }
                double x = point.getX();
                double y = point.getY();
                double z = point.getZ();
                Player player = (Player) e.getWhoClicked();
                player.closeInventory();
                List<World> listOfWorlds = Bukkit.getWorlds();
                player.teleport(new Location(listOfWorlds.get(0), x, y, z));
            }

            if (e.getCurrentItem().getType() == Material.ARROW){
                if(serializedText.contains("Forward")){
                    Player player = (Player) e.getWhoClicked();
                    Integer page = PlayersPagination.getPageForPlayer(player.getUniqueId());
                    page++;
                    PlayersPagination.setPageForPlayer(player.getUniqueId(), page);
                    teleportMenu.Menu(player);
                }
                if(serializedText.contains("Back")){
                    Player player = (Player) e.getWhoClicked();
                    Integer page = PlayersPagination.getPageForPlayer(player.getUniqueId());
                    page--;
                    PlayersPagination.setPageForPlayer(player.getUniqueId(), page);
                    teleportMenu.Menu(player);
                }
            }
        }
    }
    public String serializeComponent(Component comp){
        return PlainTextComponentSerializer.plainText().serialize(comp);
    }
}
