package org.project.entity.enemies;

import org.project.entity.Entity;
import org.project.object.weapons.Weapon;
import java.lang.Math;

import static java.lang.Math.min;

public abstract class Enemy implements Entity {
    Weapon weapon;
    private int lvl;
    private int hp;
    private final int MaxHP;
    private boolean IsDefending=false;
    protected boolean IsDead=false;

    //constructor
    public Enemy(int lvl,double hpMult, Weapon weapon) {
        this.lvl=lvl;
        this.hp = (int)(lvl*hpMult)*100;
        this.MaxHP = this.hp;
        this.weapon = weapon;
    }

    //resets player's status each round
    @Override
    public void reset()
    {
        this.IsDefending=false;
    }

    //taking damage
    @Override
    public void takeDamage(int damage) {
        if(!IsDefending) {
            hp -= damage;
        }
        else {
            hp -= damage/(int)(Math.sqrt(lvl)*2);
        }
        if(this.hp <= 0) {
            IsDead=true;
            this.hp = 0;
        }
    }

    //attack
    @Override
    public void attack(Entity target) {
        int damage = (int)((getWeapon().getDamage())*(Math.sqrt(getLevel())));
        target.takeDamage(damage);
    }

    //heal
    @Override
    public void heal(int health) {
        hp = min(hp + health, MaxHP);
    }


    //getter methods
    @Override
    public boolean isDefending(){
        return IsDefending;
    }
    @Override
    public int getHP() {
        return hp;
    }
    public int getLevel(){
        return lvl;
    }
    public int getMaxHP() {
        return MaxHP;
    }
    public Weapon getWeapon() {
        return weapon;
    }
    public boolean isDead() {
        return IsDead;
    }
    public boolean isResurrected()
    {
        return false;
    }
    //getting enemy status
    abstract public String getStatus();

    //setter methods
    public void setLevel(int lvl){
        this.lvl=lvl;
    }
    public void DefendStatusChange() {
        IsDefending = !IsDefending;
    }

}