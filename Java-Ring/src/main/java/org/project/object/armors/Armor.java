package org.project.object.armors;

public abstract class Armor {
    protected int defense;

    public Armor(int defense) {
        this.defense = defense;
    }

    public int reduceDamage(int damage) {
        int reduced = damage - defense;
        return Math.max(reduced, 0);
    }
}
