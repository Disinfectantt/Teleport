package xyz.cringee.menu.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.cringee.models.PlayersPagination;

import java.util.UUID;

public class onPlayerJoinEvent implements Listener {
    private static final PlayersPagination playersPagination = new PlayersPagination();

    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        if (!playersPagination.containsPlayer(uuid)) {
            playersPagination.setPageForPlayer(uuid, 0);
        }
    }

}
