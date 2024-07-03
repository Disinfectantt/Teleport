package xyz.cringee.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import xyz.cringee.bluemapAPI.BlueMapAPIMarkers;
import xyz.cringee.models.Point;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

public class Json {
    private static final Map<String, Point> points = new LinkedHashMap<>();
    private final String path = "./plugins/Teleport/";
    private final String jsonName = "points.json";
    private final Config config = new Config();
    private final static Logger logger = Logger.getLogger("Minecraft");

    public void createPoint(Point point) throws IOException {
        points.put(point.getId(), point);
        savePoints();
        if (config.getConfig().getBoolean("BlueMap")) {
            BlueMapAPIMarkers.putMarkersSet(point);
        }
    }

    public boolean deletePoint(String name) {
        Point point = points.remove(name);
        if (point != null) {
            try {
                savePoints();
                if (config.getConfig().getBoolean("BlueMap")) {
                    BlueMapAPIMarkers.deleteMarkerSet(point);
                }
                return true;
            } catch (Exception e) {
                logger.warning("Exception: " + e);
                return false;
            }
        }
        return false;
    }

    public Collection<Point> returnAllPoints() {
        return points.values();
    }

    public List<Point> returnSomePoints(int page) {
        int index = page * 45;
        List<Point> pointList = new ArrayList<>(points.values());
        int endIndex = Math.min(pointList.size(), index + 45);
        return pointList.subList(index, endIndex);
    }

    public void loadPoints() throws IOException {
        File file = Utils.createFileIfNotExists(path, jsonName, logger);
        if (file.length() != 0) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Reader reader = new FileReader(file);
            Point[] n = gson.fromJson(reader, Point[].class);
            for (Point point : n) {
                points.put(point.getId(), point);
            }
        }
    }

    public void savePoints() throws IOException {
        File file = Utils.createFileIfNotExists(path, jsonName, logger);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Writer writer = new FileWriter(file, false);
        gson.toJson(points.values(), writer);
        writer.flush();
        writer.close();
    }

    public Point findPoint(String id) {
        return points.get(id);
    }
}
