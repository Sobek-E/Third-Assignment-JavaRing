package org.project.object.consumables;

public abstract class Consumable {
    protected int healAmount;

    public Consumable(int healAmount) {
        this.healAmount = healAmount;
    }

    public int getHealAmount() {
        return healAmount;
    }
}
