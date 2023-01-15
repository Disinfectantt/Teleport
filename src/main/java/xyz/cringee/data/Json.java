package xyz.cringee.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import xyz.cringee.bluemapAPI.BlueMapAPIMarkers;
import xyz.cringee.models.Point;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Json {
    private static ArrayList<Point> points = new ArrayList<>();
    private static final String path = "./plugins/Teleport/";
    private static final String jsonName = "points.json";

    public static boolean createPoint(Point point){
        try{
            points.add(point);
            savePoints();
            if(Config.getConfig().getBoolean("BlueMap")){
                BlueMapAPIMarkers.putMarkersSet(point);
            }
            return true;
        }catch (Exception e){
            Bukkit.getLogger().warning("Exception: " + e);
            return false;
        }
    }

    public static boolean deletePoint(String name){
        for (Point point : points) {
            if (point.getName().equals(name)) {
                points.remove(point);
                try {
                    savePoints();
                    if(Config.getConfig().getBoolean("BlueMap")){
                        BlueMapAPIMarkers.deleteMarkerSet(point);
                    }
                }catch (Exception e){
                    Bukkit.getLogger().warning("Exception: " + e);
                }
                return true;
            }
        }
        return false;
    }

    public static List<Point> returnAllPoints(){
        return points;
    }

    public static List<Point> returnSomePoints(int page){
        List<Point> list = new ArrayList<>();
        int index = page*45;
        int size;
        if (index == 0){
            size = Math.min(points.size(), 45);
        }else{
            size = Math.min(points.size(), index*2);
        }
        for (int i = index; i<size; i++) {
            list.add(points.get(i));
        }
        return list;
    }

    public static void loadPoints() throws IOException {
        createJson();
        File file = new File(path + jsonName);
        if(file.length() != 0){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Reader reader = new FileReader(file);
            Point[] n = gson.fromJson(reader, Point[].class);
            points = new ArrayList<>(Arrays.asList(n));
        }
    }

    public static void savePoints() throws IOException {
        createJson();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File file = new File(path + jsonName);
        Writer writer = new FileWriter(file, false);
        gson.toJson(points, writer);
        writer.flush();
        writer.close();
        loadPoints();
    }

    public static Point findPoint(String name){
        for (Point point : points) {
            if (point.getName().equals(name)) {
                return point;
            }
        }
        return null;
    }

    public static void createJson() throws IOException {
        Files.createDirectories(Paths.get(path));
        File f = new File(path + jsonName);
        if(!f.exists() && !f.isDirectory()){
            boolean created = f.createNewFile();
            if(created) {
                Bukkit.getLogger().info("Created " + path + jsonName);
            }
        }
    }
}
