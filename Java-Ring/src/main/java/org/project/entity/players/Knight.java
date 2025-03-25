package org.project.entity.players;

import org.project.entity.Entity;
import org.project.object.armors.Armor;
import org.project.object.weapons.Weapon;

// TODO: UPDATE IMPLEMENTATION
public class Knight extends Player {
    public static int BaseAttack = 90;

    //constructor
    public Knight(String name, Weapon weapon, Armor armor) {
        super(name, 1000, 100, weapon, armor);
    }
    //uses mana for special ability
    @Override
    public void Special(Entity target) {
        if(super.getMp() >= 50) {
            super.setMp(super.getMp() - 50);
            target.takeDamage(5*BaseAttack);
            System.out.printf("%s uses Holy Strike on %s!%n", getName(), target.getClass().getSimpleName());
        }
        else {
            System.out.println("Not enough mana to perform Holy Strike!");
        }
    }
    //leveling up mechanism
    @Override
    public void levelUP(){
        BaseAttack = BaseAttack + (int)(20*Math.sqrt(super.getLevel()+1));
        super.levelUP();
    }
}