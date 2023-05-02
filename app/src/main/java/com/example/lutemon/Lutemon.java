package com.example.lutemon;

import java.io.Serializable;

public class Lutemon implements Serializable {
    private String name, color, id;
    private int  image, attack, defense, experience, health, maxHealth, battles, wins, losses;

    public Lutemon(String name, String color, String id, int image, int attack, int defense, int maxHealth) {
        this.name = name;
        this.color = color;
        this.image = image;
        this.attack = attack;
        this.defense = defense;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.id = id;
        this.experience = 0;
        this.battles = 0;
        this.wins = 0;
        this.losses = 0;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getImage() {
        return image;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getExperience() {
        return experience;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public String getId() {
        return id;
    }

    public int getBattles() {
        return battles;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    public void train() {
        this.attack++;
        this.defense++;
        this.maxHealth++;
        this.health = this.maxHealth;
        this.experience++;
    }

    public void addWin() {
        this.battles++;
        this.wins++;
        this.attack++;
        this.defense++;
        this.maxHealth++;
        this.experience++;
    }
    public void addLoss() {
        this.battles++;
        this.losses++;
        this.attack = this.attack - this.experience;
        this.defense = this.defense - this.experience;
        this.maxHealth = this.maxHealth - this.experience;
        this.experience = 0;
    }
}