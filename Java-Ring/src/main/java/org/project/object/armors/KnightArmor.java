package org.project.object.armors;

import org.project.entity.Entity;


public class KnightArmor extends Armor {
    public KnightArmor(int defense) {
        super(defense, 150);
    }

    @Override
    public void use(Entity target) {
        super.setDurability(super.getDurability()-(super.getMaxDefense()/8));
        checkBreak();
    }

    @Override
    public String toString() {
        return "Knight Armor";
    }
}