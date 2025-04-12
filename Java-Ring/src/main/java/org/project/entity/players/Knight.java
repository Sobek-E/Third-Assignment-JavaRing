package org.project.entity.players;

import org.project.entity.enemies.Enemy;
import org.project.object.armors.Armor;
import org.project.object.consumables.Consumable;
import org.project.object.weapons.Weapon;

public class Knight extends Player {
    private int rounds = 0;

    public Knight(String name, int hp, int mp, Weapon weapon, Armor armor, Consumable consumable) {
        super(name, hp, mp, weapon, armor, consumable);
    }

    @Override
    public void attack(Enemy enemy) {
        rounds++;
        super.attack(enemy);
    }

    @Override
    public void useSpecialAbility(Enemy enemy) {
        if (rounds >= 3) {
            int damage = weapon.getDamage() + 15;
            enemy.takeDamage(damage);
            System.out.println(name + " used a POWERFUL KICK and dealt " + damage + " damage!");
            rounds = 0;
        } else {
            System.out.println("Kick is on cooldown! Wait for " + (3 - rounds) + " more rounds.");
        }
    }
}
