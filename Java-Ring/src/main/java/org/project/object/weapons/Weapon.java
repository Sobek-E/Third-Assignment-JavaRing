package org.project.object.weapons;

import org.project.entity.Entity;
import org.project.object.Object;


public abstract class Weapon implements Object {
    private int damage;
    private int lvl = 1;

    //constructor
    public Weapon(int damage) {
        this.damage = damage;
    }
    //using weapon
    @Override
    public void use(Entity target) {
        target.takeDamage(damage);
    }

    //leveling up the weapon
    public  void levelUp(){
        lvl++;
        setDamage((int)(getDamage()*1.1));
    };


    //getters
    public int getDamage() {
        return damage;
    }
    public int getLvl()
    {
        return lvl;
    }

    //setter
    public void setDamage(int damage) {
        this.damage = damage;
    }
}