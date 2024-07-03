package xyz.cringee;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import xyz.cringee.data.Json;
import xyz.cringee.data.Utils;
import xyz.cringee.models.Point;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    private Json json;
    private Point point;
    private String id;
    @TempDir
    Path tempDir;

    @BeforeEach
    public void setUp() throws Exception {
        json = new Json();
        point = new Point(1, 2, 3, "point1");
        id = point.getId();
        // Use reflection to set the path to our temp directory
        Field pathField = Json.class.getDeclaredField("path");
        pathField.setAccessible(true);
        pathField.set(json, tempDir.toString() + "/");

        Utils.createFileIfNotExists(tempDir.toString() + "/", "points.json", Logger.getLogger("Minecraft"));
    }

    @AfterEach
    public void tearDown() throws Exception {
        Field pointsField = Json.class.getDeclaredField("points");
        pointsField.setAccessible(true);
        ((java.util.Map) pointsField.get(json)).clear();
    }

    @Test
    public void testCreatePoint() throws IOException {
        json.createPoint(point);
        assertEquals(point, json.findPoint(id));
    }

    @Test
    public void testDeletePoint() throws IOException {
        json.createPoint(point);
        assertTrue(json.deletePoint(id));
        assertNull(json.findPoint(id));
    }

    @Test
    public void testReturnAllPoints() throws IOException {
        json.createPoint(point);
        assertEquals(1, json.returnAllPoints().size());
    }

    @Test
    public void testReturnSomePoints() throws IOException {
        for (int i = 0; i < 50; i++) {
            Point p = new Point(1, 2, 3, id + i);
            json.createPoint(p);
        }
        List<Point> points = json.returnSomePoints(0);
        assertEquals(45, points.size());
    }

    @Test
    public void testLoadPoints() throws Exception {
        json.createPoint(point);
        json.loadPoints();
        assertEquals(1, json.returnAllPoints().size());
        assertEquals(point.getName(), json.findPoint(id).getName());
    }

    @Test
    public void testSavePoints() throws Exception {
        json.createPoint(point);
        json.savePoints();

        // Create a new Json instance and load points
        Json newJsonInstance = new Json();
        // Set the path for the new instance
        Field pathField = Json.class.getDeclaredField("path");
        pathField.setAccessible(true);
        pathField.set(newJsonInstance, tempDir.toString() + "/");

        newJsonInstance.loadPoints();
        assertEquals(1, newJsonInstance.returnAllPoints().size());
        assertEquals(point.getName(), newJsonInstance.findPoint(id).getName());
    }

    @Test
    public void testFindPoint() throws IOException {
        json.createPoint(point);
        assertEquals(point, json.findPoint(id));
    }
}
