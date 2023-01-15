package xyz.cringee.models;

import java.util.UUID;

public class Point {
    double x, y, z;
    String name, id;

    public Point(double x, double y, double z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
        this.id = UUID.randomUUID().toString();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
