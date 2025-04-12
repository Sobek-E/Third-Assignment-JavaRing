package org.project.entity.enemies;

import org.project.entity.players.Player;
import org.project.object.weapons.Weapon;

public class Skeleton extends Enemy {
    private boolean resurrected = false;

    public Skeleton(int hp, int mp, Weapon weapon) {
        super(hp, mp, weapon);
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        if (hp <= 0 && !resurrected) {
            resurrected = true;
            hp = 30; // Skeleton resurrects with 30 HP
            System.out.println("The Skeleton has resurrected with " + hp + " HP!");
        }
    }

    @Override
    public void attack(Player player) {
        System.out.println("Skeleton attacks " + player.getName() + "!");
        player.takeDamage(weapon.getDamage());
    }

    @Override
    public void useSpecialAbility(Player player) {
        // No special ability
        System.out.println("Skeleton has no special abilities.");
    }
}
