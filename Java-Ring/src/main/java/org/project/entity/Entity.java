package org.project.entity;

import org.project.object.weapons.Weapon;

public interface Entity {
    void reset();

    void attack(Entity target);

    void heal(int health);

    void Special(Entity target);

    void takeDamage(int damage);



    //getters
    Weapon getWeapon();
    int getHP();
    int getMaxHP();
    int getLevel();
    boolean isDefending();
    boolean isDead();

    //setters
    void DefendStatusChange();

}