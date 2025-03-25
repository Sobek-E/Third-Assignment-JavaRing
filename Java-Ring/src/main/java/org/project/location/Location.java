package org.project.location;

import org.project.entity.enemies.Enemy;

import java.util.ArrayList;

public class Location {
    private String name;

    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    public Location( ArrayList<Enemy> tempEnemies,String name) {
        enemies.addAll(tempEnemies);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
}