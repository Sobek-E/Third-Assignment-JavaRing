package org.project.location;

public class Location {
    private String name;

    public Location(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void enter() {
        System.out.println("You have entered " + name + "...");
    }
}
