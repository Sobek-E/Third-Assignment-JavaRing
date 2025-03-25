package org.project.object.weapons;

import org.project.entity.Entity;

import java.util.ArrayList;

public class Sword extends Weapon{


    //constructor
    public Sword(int damage) {
        super(damage);
    }


    @Override
    public String toString() {
        return "Sword lvl." + super.getLvl() + " Damage: " + super.getDamage();
    }

}