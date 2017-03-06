package com.got.krith.gameofthrones.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by krith on 04/03/17.
 */

public class King {

    public Map<String, Integer> strengthMap;
    public Map<String, Integer> battleStrengthMap;
    private int id;
    private String name;
    private int rating;
    private String strength;
    private int battlesWon;
    private int battlesLost;
    private String strengthBattleType;

    public King() {
        strengthMap = new HashMap<>();
        battleStrengthMap = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public int getBattlesWon() {
        return battlesWon;
    }

    public void setBattlesWon(int battlesWon) {
        this.battlesWon = battlesWon;
    }

    public int getBattlesLost() {
        return battlesLost;
    }

    public void setBattlesLost(int battlesLost) {
        this.battlesLost = battlesLost;
    }

    public String getStrengthBattleType() {
        return strengthBattleType;
    }

    public void setStrengthBattleType(String strengthBattleType) {
        this.strengthBattleType = strengthBattleType;
    }
}
