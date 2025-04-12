package org.project.object.weapons;

public abstract class Weapon {
    protected int damage;

    public Weapon(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
}
