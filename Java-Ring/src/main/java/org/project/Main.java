package org.project;

import org.project.entity.enemies.Dragon;
import org.project.entity.enemies.Enemy;
import org.project.entity.enemies.Goblin;
import org.project.entity.enemies.Skeleton;
import org.project.entity.players.Assassin;
import org.project.entity.players.Knight;
import org.project.entity.players.Player;
import org.project.entity.players.Wizard;
import org.project.location.Location;
import org.project.object.armors.KnightArmor;
import org.project.object.armors.Nothing;
import org.project.object.consumables.Flask;
import org.project.object.weapons.Punch;
import org.project.object.weapons.Sword;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Location> locations = levelConstructor();
        Scanner scanner = new Scanner(System.in);

        // Character Creation
        Player player = null;
        do {
            System.out.println("\n========================================");
            System.out.println("          CREATE YOUR HERO             ");
            System.out.println("========================================");
            System.out.println("1. Wizard - Master of arcane magic");
            System.out.println("2. Assassin - Deadly and precise");
            System.out.println("3. Knight - Strong and resilient");
            System.out.println("========================================");
            System.out.print("Choose your class (1-3): ");
            int classNumber = scanner.nextInt();

            System.out.print("Enter your hero's name: ");
            String name = scanner.next();

            switch (classNumber) {
                case 1:
                    player = new Wizard(name, new Punch(Wizard.BaseAttack), new Nothing());
                    System.out.println("\n>> " + name + " the Wizard enters the fray!");
                    break;
                case 2:
                    player = new Assassin(name, new Punch(Assassin.BaseAttack), new Nothing());
                    System.out.println("\n>> " + name + " the Assassin emerges from the shadows!");
                    break;
                case 3:
                    player = new Knight(name, new Punch(Knight.BaseAttack), new Nothing());
                    System.out.println("\n>> " + name + " the Knight charges into battle!");
                    break;
                default:
                    System.out.println("Invalid class selection. Please choose agian");
            }
        }while(player==null);


        //start playing
        boolean playing = true;
        while(playing) {

            //choosing a location
            showLocations(locations);
            System.out.print("\nChoose your destination (0 to quit): ");
            int locationIndex = scanner.nextInt() - 2;

            if (locationIndex == -2) {
                System.out.println("\n>> Farewell, adventurer!");
                break;
            }

            enterLocation(locations, locationIndex,player);

            //if we entered the shop go back to location selecting menu
            if(locationIndex == -1)
                continue;


            //location loop
            Location currentLocation = locations.get(locationIndex);
            int round=0;
            boolean inLocation = true;
            while(inLocation) {

                //Round Header
                System.out.println("\n========================================");
                System.out.printf("          ROUND %-3d - %-15s%n", ++round, currentLocation.getName());
                System.out.println("========================================");
                System.out.println(player.getStatus());

                //Player's turn
                player.reset();
                displayCombatOptions();
                int option = scanner.nextInt();
                switch(option) {
                    case 1://Attack
                        enemyList(locations, locationIndex);
                        System.out.print("\nChoose your target: ");
                        Enemy enemy = currentLocation.getEnemies().get(scanner.nextInt() - 1);
                        player.attack(enemy);
                        System.out.printf("\n>> You strike the %s for %d damage!%n",
                                enemy.getClass().getSimpleName(),
                                player.getWeapon().getDamage());

                        if (enemy.isDead()) {
                            int gainedExp = enemy.getLevel() * (enemy.getMaxHP() / 5);
                            int gainedCoins = enemy.getLevel() * (enemy.getMaxHP() / 5);
                            System.out.printf(">> You defeated the %s! Gained %d XP and %d coins.%n",
                                    enemy.getClass().getSimpleName(), gainedExp, gainedCoins);
                            player.collectExp(gainedExp);
                            player.setCoin(player.getCoin() + gainedCoins);

                            if (!(enemy instanceof Skeleton) || enemy.isResurrected()) {
                                currentLocation.getEnemies().remove(enemy);
                            }
                        }
                        break;

                    case 2:// Heal
                        int healAmount = player.getMaxHP() / 8;
                        player.heal(healAmount);
                        System.out.printf("\n>> You healed yourself for %d HP!%n", healAmount);
                        break;
                    case 3:// Defense
                        player.DefendStatusChange();
                        System.out.printf("\n>> You are now %sdefending.%n",
                                player.isDefending() ? "" : "not ");
                        break;
                    case 4:// Special Ability
                        enemyList(locations, locationIndex);
                        System.out.print("\nChoose your target: ");
                        enemy = currentLocation.getEnemies().get(scanner.nextInt() - 1);
                        player.Special(enemy);
                        System.out.printf("\n>> You use your special ability on the %s!%n",
                                enemy.getClass().getSimpleName());

                        if (enemy.isDead()) {
                            int gainedExp = enemy.getLevel() * (enemy.getMaxHP() / 5);
                            int gainedCoins = enemy.getLevel() * (enemy.getMaxHP() / 5);
                            System.out.printf(">> You defeated the %s! Gained %d XP and %d coins.%n",
                                    enemy, gainedExp, gainedCoins);
                            player.collectExp(gainedExp);
                            player.setCoin(player.getCoin() + gainedCoins);

                            if (!(enemy instanceof Skeleton) || enemy.isResurrected()) {
                                currentLocation.getEnemies().remove(enemy);
                            }
                        }
                    case 5:
                        // Fill Mana
                        int manaGain = player.getMaxMp() / 5;
                        player.setMp(player.getMp() + manaGain);
                        System.out.printf("\n>> You focus and restore %d MP!%n", manaGain);
                        break;
                    case 6:// Leave Location
                        System.out.println("\n>> You prepare to retreat at the start of next round...");
                        inLocation = false;
                        break;
                    default:
                        System.out.println("\n>> Invalid option! You hesitate...");
                }

                // Check if location is cleared
                if (locationClearedCheck(currentLocation)) {
                    inLocation = false;
                    locations.remove(currentLocation);
                    break;
                }

                //Enemies' turn
                for(Enemy enemy : currentLocation.getEnemies()) {
                    //Skeleton Resurrection check
                    if (enemy.isDead() && enemy instanceof Skeleton) {
                        enemy.Special(player);
                        System.out.printf("\n>> The %s's bones rattle as it reforms!%n",
                                enemy.getClass().getSimpleName());
                        continue;
                    }


                    enemy.reset();
                    int random = (int) (Math.random() * 100) % 20;
                    if (random <= 9) {// Attack

                        //Dragon Special
                        if(enemy instanceof Dragon && player.isDefending())
                        {
                            enemy.Special(player);
                            System.out.printf("\n>> The %s Pierced through your guard!%n", enemy);


                        }else {
                            System.out.printf("\n>> The %s attacks you!%n", enemy);
                            enemy.attack(player);
                        }
                        if (player.isDead()) {
                            System.out.println("\n>> Your vision fades to black...");
                            inLocation = false;
                            playing = false;
                            break;
                        }
                    } else if (random <= 14) { // Defend
                        System.out.printf("\n>> The %s raises its guard!%n", enemy);
                        enemy.DefendStatusChange();
                    } else if (random <= 19 ) { // Heal
                        int healAmount = enemy.getMaxHP() / 10;
                        System.out.printf("\n>> The %s heals itself for %d HP!%n",
                                enemy.getClass().getSimpleName(), healAmount);
                        enemy.heal(healAmount);
                    }
                }
            }
        }

        //Death Message
        if (!playing) {
            System.out.println("\n========================================");
            System.out.println("            GAME OVER                 ");
            System.out.println("========================================");
            System.out.println("Your hero " + player.getName() + " has fallen in battle.");
            System.out.println("Final stats:");
            System.out.println(player.getStatus());
            System.out.println("========================================");
        }
    }


    private static void displayCombatOptions() {
        System.out.println("\nCOMBAT OPTIONS:");
        System.out.println("1. Basic Attack");
        System.out.println("2. Heal (12.5% of max HP)");
        System.out.println("3. Toggle Defense");
        System.out.println("4. Special Ability");
        System.out.println("5. Focus (Restore 33% MP)");
        System.out.println("6. Retreat");
        System.out.print("Choose your action: ");
    }

    private static ArrayList<Location> levelConstructor() {

        //list of all levels and its enemies

        ArrayList<Location> locations = new ArrayList<>();
        ArrayList<Enemy> enemies;
        Location location;
        //level 1
        enemies = addEnemy(1,1,0,0,0,0);
        location = new Location(enemies,"Jungle 1");
        locations.add(location);
        //level 2
        enemies = addEnemy(1,2,1,1,0,0);
        location = new Location(enemies,"Jungle 2");
        locations.add(location);
        //level 3
        enemies = addEnemy(3,1,1,1,0,0);
        location = new Location(enemies,"Jungle 3");
        locations.add(location);
        //level 4
        enemies = addEnemy(0,0,2,2,0,0);
        location = new Location(enemies,"Jungle 4");
        locations.add(location);
        //level 5
        enemies = addEnemy(2,1,0 ,0, 1,1);
        location = new Location(enemies,"Jungle 5");
        locations.add(location);
        //level 6
        enemies = addEnemy(3,1,1,2,0,0);
        location = new Location(enemies,"Desert 1");
        locations.add(location);
        //level 7
        enemies = addEnemy(3,2,2,1,0,0);
        location = new Location(enemies,"Desert 2");
        locations.add(location);
        //level 8
        enemies = addEnemy(2,3,1,2,0,0);
        location = new Location(enemies,"Desert 3");
        locations.add(location);
        //level 9
        enemies = addEnemy(0,0,3,2,0,0);
        location = new Location(enemies,"Desert 4");
        locations.add(location);
        //level 10
        enemies = addEnemy(0,0,2,2,1,2);
        location = new Location(enemies,"Desert 5");
        locations.add(location);
        //level 11
        enemies = addEnemy(0,0,4,2,0,0);
        location = new Location(enemies,"Mountain 1");
        locations.add(location);
        //level 12
        enemies = addEnemy(8,2,0,0,0,0);
        location = new Location(enemies,"Mountain 2");
        locations.add(location);
        //level 13
        enemies = addEnemy(3,3,2,2,0,0);
        location = new Location(enemies,"Mountain 3");
        locations.add(location);
        //level 14
        enemies = addEnemy(1,5,3,3,0,0);
        location = new Location(enemies,"Mountain 4");
        locations.add(location);
        //level 15
        enemies = addEnemy(3,3,1,2,1,3);
        location = new Location(enemies,"Mountain 5");
        locations.add(location);
        //level 16
        enemies = addEnemy(4,4,2,3,0,0);
        location = new Location(enemies,"Island 1");
        locations.add(location);
        //level 17
        enemies = addEnemy(2,2,3,4,0,0);
        location = new Location(enemies,"Island 2");
        locations.add(location);
        //level 18
        enemies = addEnemy(3,3,1,6,0,0);
        location = new Location(enemies,"Island 3");
        locations.add(location);
        //level 19
        enemies = addEnemy(1,10,3,4,0,0);
        location = new Location(enemies,"Island 4");
        locations.add(location);
        //level 20
        enemies = addEnemy(2,6,2,4,1,5);
        location = new Location(enemies,"Island 5");
        locations.add(location);




        return locations;
    }

    private static ArrayList<Enemy> addEnemy(int numS,int lvlS,int numG,int lvlG,int numD,int lvlD) {

        //creating an enemy list
        ArrayList<Enemy> enemies = new ArrayList<>();
        for(int i=1; i<=numS; i++)
            enemies.add(new Skeleton(lvlS,new Punch(Skeleton.BaseAttack)));
        for(int i=1; i<=numG; i++)
            enemies.add(new Goblin(lvlG,new Punch(Goblin.BaseAttack)));
        for(int i=1; i<=numD; i++)
            enemies.add(new Dragon(lvlD,new Punch(Dragon.BaseAttack)));
        return enemies;
    }


    private static boolean locationClearedCheck(Location currentLocation) {

        //it checks whether a location is cleared or not
        if(currentLocation.getEnemies().isEmpty())
        {
            System.out.println("\nYou have cleared this location!");
            return true;
        }
        return false;
    }

    public static void showLocations(List<Location> locations) {
        //shows the list of locations
        System.out.println("\n=== Available Locations ===");
        System.out.println("1. Shop");
        for (int i = 0; i < locations.size(); i++) {
            System.out.println((i + 2) + ". " + locations.get(i).getName() +
                    " (" + locations.get(i).getEnemies().size() + " enemies)");
        }
    }

    public static void enterLocation(List<Location> locations,int locationIndex,Player player) {
        //enter a location or shop message
        if (locationIndex == -1) {
            enterShop(player);
        } else {
            System.out.println("\nYou enter " + locations.get(locationIndex).getName());
            enemyList(locations, locationIndex);
        }
    }

    private static void enterShop(Player player) {

        //shop of the game
        //exchanging coins for goods
        Scanner input = new Scanner(System.in);
        System.out.println("\n=== Welcome to the Shop ===");
        System.out.println(player.getStatus());
        System.out.println("\nAvailable items:");
        System.out.println("1. Health Flask (25 coins) - Restores 25% HP");
        System.out.println("2. Armor Services");
        System.out.println("   - New Armor: 150 coins");
        System.out.println("   - Repair: 75 coins");
        System.out.println("3. Sword Upgrade");
        System.out.println("   - New Sword: 85 coins");
        System.out.println("   - Upgrade: 85 coins (+10% damage)");
        System.out.println("4. Exit Shop");
        System.out.print("Choose an option: ");
        int select = input.nextInt();
        switch(select)
        {
            case 1:
                System.out.println("\nEach flask restores 25% of your max HP.");
                if (player.getCoin() >= 25) {
                    player.setCoin(player.getCoin() - 25);
                    new Flask().use(player);
                    System.out.println("You drank a health flask!");
                } else {
                    System.out.println("You don't have enough coins.");
                }
                break;
            case 2:
                if (player.getArmor() instanceof Nothing) {
                    System.out.println("\nA new armor costs 150 coins.");
                    if (player.getCoin() >= 150) {
                        player.setCoin(player.getCoin() - 150);
                        player.setArmor(new KnightArmor(player.getMaxHP() / 3));
                        System.out.println("You equipped new armor!");
                    } else {
                        System.out.println("You don't have enough coins.");
                    }
                } else {
                    System.out.println("\nArmor repair costs 75 coins.");
                    if (player.getCoin() >= 75) {
                        player.setCoin(player.getCoin() - 75);
                        player.getArmor().repair();
                        System.out.println("Your armor has been repaired!");
                    } else {
                        System.out.println("You don't have enough coins.");
                    }
                }
                break;
            case 3:
                if (player.getWeapon() instanceof Punch) {
                    System.out.println("\nA new sword costs 85 coins.");
                    if (player.getCoin() >= 85) {
                        player.setCoin(player.getCoin() - 85);
                        player.setWeapon(new Sword(player.getWeapon().getDamage() * 3));
                        System.out.println("You equipped a new sword!");
                    } else {
                        System.out.println("You don't have enough coins.");
                    }
                } else {
                    System.out.println("\nSword upgrade costs 85 coins.");
                    if (player.getCoin() >= 85) {
                        player.setCoin(player.getCoin() - 85);
                        player.getWeapon().levelUp();
                        System.out.printf("Your sword has been upgraded to lvl.%d" , player.getLevel());
                    } else {
                        System.out.println("You don't have enough coins.");
                    }
                }
                break;
            case 4:
                System.out.println("You leave the shop.");
                break;
        }
    }

    public static void enemyList(List<Location> locations,int locationIndex) {

        //shows the list of all enemies
        System.out.println("\nEnemies in this location:");
        System.out.println("--------------------------");
        int i = 1;
        for (Enemy enemy : locations.get(locationIndex).getEnemies()) {
            System.out.println(i++ + ". " + enemy.getStatus());
        }
        System.out.println("--------------------------");
    }
}