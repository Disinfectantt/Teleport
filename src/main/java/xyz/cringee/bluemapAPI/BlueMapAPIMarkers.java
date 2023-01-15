package xyz.cringee.bluemapAPI;

import de.bluecolored.bluemap.api.BlueMapAPI;
import de.bluecolored.bluemap.api.BlueMapMap;
import de.bluecolored.bluemap.api.markers.MarkerSet;
import de.bluecolored.bluemap.api.markers.POIMarker;
import org.bukkit.Bukkit;
import org.bukkit.World;
import xyz.cringee.data.Json;
import xyz.cringee.models.Point;

import java.util.List;

public class BlueMapAPIMarkers {
    private static final MarkerSet markerSet = MarkerSet.builder()
            .label("Points")
            .build();
    public static void markers(){
        BlueMapAPI.onEnable(api -> {
            List<World> listOfWorlds = Bukkit.getWorlds();

            for (Point point : Json.returnAllPoints()){
                markerSet.getMarkers()
                        .put(point.getId(), createPOI(point));
            }

            api.getWorld(listOfWorlds.get(0)).ifPresent(world -> {
                for (BlueMapMap map : world.getMaps()) {
                    map.getMarkerSets().put("points", markerSet);
                }
            });
        });
    }

    public static POIMarker createPOI(Point point){
        return POIMarker.builder()
                .label(point.getName())
                .position(point.getX(), point.getY(), point.getZ())
                .maxDistance(7000)
                .minDistance(30)
                .build();
    }

    public static MarkerSet getMarkerSet() {
        return markerSet;
    }

    public static void putMarkersSet(Point point){
        getMarkerSet().put(point.getId(), BlueMapAPIMarkers.createPOI(point));
    }

    public static void deleteMarkerSet(Point point){
        getMarkerSet().put(point.getId(), BlueMapAPIMarkers.createPOI(point));
    }
}
