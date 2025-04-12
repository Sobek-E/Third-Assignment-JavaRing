package org.project.object;

import org.project.entity.Entity;

public interface Object {
    void use(Entity target);

    default String getName() {
        return this.getClass().getSimpleName();
    }
}
