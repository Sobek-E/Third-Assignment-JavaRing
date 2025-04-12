package org.project.entity.players;

import org.project.object.armors.Armor;
import org.project.object.consumables.Consumable;
import org.project.object.weapons.Weapon;

public abstract class Player {
    protected String name;
    protected int hp;
    protected int mp;
    protected Weapon weapon;
    protected Armor armor;
    protected Consumable consumable;

    public Player(String name, int hp, int mp, Weapon weapon, Armor armor, Consumable consumable) {
        this.name = name;
        this.hp = hp;
        this.mp = mp;
        this.weapon = weapon;
        this.armor = armor;
        this.consumable = consumable;
    }

    public void takeDamage(int damage) {
        int reducedDamage = armor != null ? armor.reduceDamage(damage) : damage;
        hp -= reducedDamage;
        System.out.println(name + " took " + reducedDamage + " damage! HP left: " + hp);
    }

    public void attack(org.project.entity.enemies.Enemy enemy) {
        System.out.println(name + " attacked " + enemy.getClass().getSimpleName() + "!");
        enemy.takeDamage(weapon.getDamage());
    }

    public void heal() {
        if (consumable != null) {
            hp += consumable.getHealAmount();
            System.out.println(name + " used a " + consumable.getClass().getSimpleName() + " and healed for " + consumable.getHealAmount());
        }
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public String getName() {
        return name;
    }

    public abstract void useSpecialAbility(org.project.entity.enemies.Enemy enemy);
}
