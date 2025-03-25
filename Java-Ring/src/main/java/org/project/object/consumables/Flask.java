package org.project.object.consumables;

import org.project.object.Object;

// TODO: UPDATE IMPLEMENTATION
public abstract class Consumable implements Object {
    private boolean consumed;

    public void Consume() {
        consumed = true;
    }
}