package xyz.cringee.models;

import java.util.HashMap;
import java.util.UUID;

public class PlayersPagination {
    private static final HashMap<UUID, Integer> playersPages = new HashMap<>();

    public static Integer getPageForPlayer(UUID uuid){
        return playersPages.get(uuid);
    }

    public static void setPageForPlayer(UUID uuid, Integer page){
        playersPages.put(uuid, page);
    }

    public static boolean containsPlayer(UUID uuid){
        return playersPages.containsKey(uuid);
    }
}
