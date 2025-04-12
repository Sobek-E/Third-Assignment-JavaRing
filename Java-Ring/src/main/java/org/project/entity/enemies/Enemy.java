package org.project.entity.enemies;

import org.project.object.weapons.Weapon;

public abstract class Enemy {
    protected int hp;
    protected int mp;
    protected Weapon weapon;

    public Enemy(int hp, int mp, Weapon weapon) {
        this.hp = hp;
        this.mp = mp;
        this.weapon = weapon;
    }

    public void takeDamage(int damage) {
        hp -= damage;
        System.out.println(getClass().getSimpleName() + " took " + damage + " damage!");
        if (hp <= 0) {
            System.out.println(getClass().getSimpleName() + " has been defeated!");
        }
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public int getHp() {
        return hp;
    }

    public int getMp() {
        return mp;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public abstract void attack(org.project.entity.players.Player player);

    public abstract void useSpecialAbility(org.project.entity.players.Player player);
}
