package org.project;

import org.project.entity.enemies.Skeleton;
import org.project.entity.players.Knight;
import org.project.location.Location;
import org.project.object.armors.KnightArmor;
import org.project.object.consumables.Flask;
import org.project.object.weapons.Sword;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Java-Ring!");
        System.out.print("Enter your character's name: ");
        String name = scanner.nextLine();

        Knight player = new Knight(name, 100, 30, new Sword(), new KnightArmor(), new Flask());
        Location location = new Location("Dark Forest");
        location.enter();

        Skeleton enemy = new Skeleton(50, 20, new Sword());

        while (player.isAlive() && enemy.isAlive()) {
            System.out.println("\nYour move! (1) Attack (2) Heal (3) Kick");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> player.attack(enemy);
                case 2 -> player.heal();
                case 3 -> player.useSpecialAbility(enemy);
                default -> System.out.println("Invalid choice.");
            }

            if (enemy.isAlive()) {
                enemy.attack(player);
            }
        }

        if (player.isAlive()) {
            System.out.println("You defeated the enemy!");
        } else {
            System.out.println("You were slain... Game over.");
        }
    }
}
