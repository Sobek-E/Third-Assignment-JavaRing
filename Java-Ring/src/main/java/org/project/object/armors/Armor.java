package org.project.object.armors;

import org.project.object.Object;

public abstract class Armor implements Object {
    private int defense;
    private int maxDefense;
    private int durability;
    private int maxDurability;
    private boolean isBroke;
    //constructor
    public Armor(int defense, int durability) {
        this.defense = defense;
        this.durability = durability;
    }

    //checks if armor is broken
    public void checkBreak() {
        if (durability <= 0 && !(this instanceof Nothing)) {
            System.out.println("your armor is broken");
            isBroke = true;
            defense = 0;
        }
    }

    //repair mechanism
    public void repair() {
        isBroke = false;
        defense = maxDefense;
        durability = maxDurability;
    }


    //getters
    public int getDefense() {
        return defense;
    }
    public int getDurability() {
        return durability;
    }
    public int getMaxDefense() {
        return maxDefense;
    }
    public boolean isBroke() {
        return isBroke;
    }

    //setter
    public void setDurability(int durability) {
        this.durability = durability;
    }
}