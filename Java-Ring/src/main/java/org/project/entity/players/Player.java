package org.project.entity.players;

import org.project.entity.Entity;
import org.project.object.armors.Armor;
import org.project.object.armors.Nothing;
import org.project.object.weapons.Weapon;
import static java.lang.Math.max;
import static java.lang.Math.min;

// TODO: UPDATE IMPLEMENTATION
public  abstract class Player implements Entity {
    protected String name;
    Weapon weapon;
    Armor armor;
    //Health Point & Max Health Point
    private int hp;
    private int maxHP;
    //Mana Point & Max Mana Point
    private int mp;
    private int maxMp;
    //Exp & Max Exp & Hero Level
    private int exp =0;
    private int maxExp=100;
    private int level = 1;
    //coins for buying from Shop
    private int coin = 0;
    private boolean IsDefending=false;
    protected boolean IsDead=false;

    //constructor
    public Player(String name, int hp, int mp, Weapon weapon, Armor armor) {
        this.name = name;
        this.hp = hp;
        this.mp = mp;
        this.maxMp = mp;
        this.maxHP = hp;
        this.weapon = weapon;
        this.armor = armor;
    }

    //resets player's status each round
    @Override
    public void reset()
    {
        IsDefending=false;
    }

    //attack
    @Override
    public void attack(Entity target) {
        target.takeDamage(weapon.getDamage());
    }

    //taking damage
    @Override
    public void takeDamage(int damage) {

        //using armor
        if(!(getArmor() instanceof Nothing)) {
            getArmor().use(this);
            armor.checkBreak();
        }


        int actualDamage;

        if(!IsDefending) {
            actualDamage = max(damage - armor.getDefense(),0);
            hp -= actualDamage;
        }
        else
        {
            actualDamage = max(damage - (armor.getDefense()*2 + damage/3),0);
            hp -= actualDamage;

        }

        System.out.printf("%s takes %d damage!%n", name, actualDamage);



        if(hp <= 0) {
            IsDead = true;
            hp = 0;
        }

    }

    //healing mechanism
    @Override
    public void heal(int health) {
        hp += health;
        if (hp > maxHP) {
            hp = maxHP;
        }
    }

    //collecting exp
    public void collectExp(int exp) {
        if(this.exp + exp < maxExp) {
            this.exp += exp;
        }
        else
        {
            //leveling up
            while(this.exp + exp >= maxExp)
            {
                int temp = maxExp - this.exp;
                this.exp = 0;
                this.levelUP();
                exp = exp - temp;

            }

            this.exp += exp;

        }
        System.out.printf("%s reached level %d!%n", name, level);
        System.out.printf("New stats: HP %d/%d, MP %d/%d%n",
                hp, maxHP, mp, maxMp);
    }

    //leveling up mechanism
    public void levelUP(){
        this.level++;
        this.maxMp = (int)(maxMp*(1+Math.random()));
        this.maxHP = (int)(this.maxHP*1.1);
        this.hp = (int)(this.hp*1.1);
        this.mp = (int)(this.mp*1.1);
        this.maxExp = (int)(maxExp*1.1);

    }


    //getter methods
    @Override
    public boolean isDefending() {
        return IsDefending;
    }
    @Override
    public int getHP() {
        return hp;
    }
    public String getName() {
        return name;
    }
    public int getMaxHP() {
        return maxHP;
    }
    public Weapon getWeapon() {
        return weapon;
    }
    public int getMp()
    {
        return mp;
    }
    public int getMaxMp()
    {
        return maxMp;
    }
    public Armor getArmor() {
        return armor;
    }
    public int getLevel() {
        return level;
    }
    public boolean isDead() {
        return IsDead;
    }
    public int getCoin()
    {
        return coin;
    }
    //get status method
    public String getStatus(){
        return String.format("%s [Lvl.%d] | HP: %d/%d | MP: %d/%d | %s | %s | Coins: %d",
                name, level, hp, maxHP, mp, maxMp,
                armor.toString(), weapon.toString(), coin);
    }
    @Override
    public String toString(){
        return this.name;
    }

    //setters
    @Override
    public void DefendStatusChange() {
        IsDefending=!IsDefending;
    }
    public void setMp(int mp) {
        this.mp = min(mp,this.maxMp);
    }
    public void setCoin(int coin)
    {
        this.coin = coin;
    }
    public void setArmor(Armor armor)
    {
        this.armor = armor;
    }
    public void setWeapon(Weapon weapon)
    {
        this.weapon = weapon;
    }

}