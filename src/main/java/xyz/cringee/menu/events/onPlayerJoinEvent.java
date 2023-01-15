package xyz.cringee.menu.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.cringee.models.PlayersPagination;

import java.util.UUID;

public class onPlayerJoinEvent implements Listener {

    @EventHandler
    public void playerJoin(PlayerJoinEvent e){
        UUID uuid = e.getPlayer().getUniqueId();
        if(!PlayersPagination.containsPlayer(uuid)){
            PlayersPagination.setPageForPlayer(uuid, 0);
        }
    }

}
