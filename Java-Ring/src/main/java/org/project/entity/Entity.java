package org.project.entity;

public interface Entity {
    void takeDamage(int damage);
    boolean isAlive();

    void setDefending(boolean defending);
    boolean isDefending();
}
