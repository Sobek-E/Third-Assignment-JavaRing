package org.project.entity.enemies;

import org.project.entity.Entity;
import org.project.object.weapons.Weapon;

import static java.lang.Math.min;

public class Skeleton extends Enemy {

    private boolean Resurrected = false;
    public final static int BaseAttack = 25;
    //constructor
    public Skeleton(int lvl, Weapon weapon) {
        super(lvl,1.25, weapon);
    }
    //Special Ability
    @Override
    public void Special(Entity target) {
        if(super.IsDead && !Resurrected)
        {
            if(super.getLevel() == 1)
                Resurrected = true;
            else {
                Resurrected = false;
                super.setLevel(super.getLevel()/2);
            }

            super.IsDead = false;
            this.heal(super.getMaxHP()/4);
        }
    }

    //get status shows resurrected
    @Override
    public String getStatus() {
        return "Skeleton" + " lvl." + getLevel()+ ": "
                + "HP (" + getHP() + "/" + getMaxHP() + ") |"
                + "Defending : " + "(" + isDefending() + ")"  + " | Resurrected: " + Resurrected;
    }
    @Override
    public String toString(){
        return "Skeleton" + " lvl." + getLevel();
    }




    //getter for resurrected
    @Override
    public boolean isResurrected()
    {
        return this.Resurrected;
    }

}