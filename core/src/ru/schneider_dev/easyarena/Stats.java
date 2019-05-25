package ru.schneider_dev.easyarena;

import java.io.Serializable;

/**
 * Created by User on 04.04.2018.
 */

public class Stats implements Cloneable, Serializable {
    private int baseStrength;
    private int baseAgility;
    private int baseEndurance;
    private int baseDefence;
    private int baseSpellPower;

    public int getScorePoints() {
        return scorePoints;
    }


    private int baseScorePoints;
    private int scorePoints;

    private int level;

    private int strength;
    private int agility;
    private int endurance;
    private int defence;
    private int spellPower;

    private float strengthPerLevel;
    private float agilityPerLevel;
    private float endurancePerLevel;
    private float defencePerLevel;
    private float spellPowerPerLevel;

    public int getBaseStrength() {
        return baseStrength;
    }

    public void setBaseStrength(int baseStrength) {
        this.baseStrength = baseStrength;
    }

    public int getBaseAgility() {
        return baseAgility;
    }

    public void setBaseAgility(int baseAgility) {
        this.baseAgility = baseAgility;
    }

    public int getBaseEndurance() {
        return baseEndurance;
    }

    public void setBaseEndurance(int baseEndurance) {
        this.baseEndurance = baseEndurance;
    }

    public int getBaseDefence() {
        return baseDefence;
    }

    public void setBaseDefence(int baseDefence) {
        this.baseDefence = baseDefence;
    }

    public int getBaseSpellPower() {
        return baseSpellPower;
    }

    public void setBaseSpellPower(int baseSpellPower) {
        this.baseSpellPower = baseSpellPower;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getEndurance() {
        return endurance;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getSpellPower() {
        return spellPower;
    }

    public void setSpellPower(int spellPower) {
        this.spellPower = spellPower;
    }

    public float getStrengthPerLevel() {
        return strengthPerLevel;
    }

    public void setStrengthPerLevel(float strengthPerLevel) {
        this.strengthPerLevel = strengthPerLevel;
    }

    public float getAgilityPerLevel() {
        return agilityPerLevel;
    }

    public void setAgilityPerLevel(float agilityPerLevel) {
        this.agilityPerLevel = agilityPerLevel;
    }

    public float getEndurancePerLevel() {
        return endurancePerLevel;
    }

    public void setEndurancePerLevel(float endurancePerLevel) {
        this.endurancePerLevel = endurancePerLevel;
    }

    public float getDefencePerLevel() {
        return defencePerLevel;
    }

    public void setDefencePerLevel(float defencePerLevel) {
        this.defencePerLevel = defencePerLevel;
    }

    public float getSpellPowerPerLevel() {
        return spellPowerPerLevel;
    }

    public void setSpellPowerPerLevel(float spellPowerPerLevel) {
        this.spellPowerPerLevel = spellPowerPerLevel;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Stats(int level, int baseStrength, int baseAgility, int baseEndurance, int baseDefence, int baseSpellPower, float strengthPerLevel, float agilityPerLevel, float endurancePerLevel, float defencePerLevel, float spellPowerPerLevel, int baseScorePoints) {
        this.level = level;
        this.baseStrength = baseStrength;
        this.baseAgility = baseAgility;
        this.baseEndurance = baseEndurance;
        this.baseDefence = baseDefence;
        this.baseSpellPower = baseSpellPower;
        this.strengthPerLevel = strengthPerLevel;
        this.agilityPerLevel = agilityPerLevel;
        this.endurancePerLevel = endurancePerLevel;
        this.defencePerLevel = defencePerLevel;
        this.spellPowerPerLevel = spellPowerPerLevel;
        this.baseScorePoints = baseScorePoints;
        recalculate(level);
    }

    public void recalculate(int level) {
        strength = (int) (baseStrength + (level - 1) * strengthPerLevel);
        agility = (int) (baseAgility + (level - 1) * agilityPerLevel);
        endurance = (int) (baseEndurance + (level - 1) * endurancePerLevel);
        spellPower = (int) (baseSpellPower + (level - 1) * spellPowerPerLevel);
        scorePoints = baseScorePoints + level;
        System.out.println(baseScorePoints);
        System.out.println(scorePoints);
//        scorePoints = baseScorePoints + level;
//        System.out.println(scorePoints);
    }

    @Override
    protected Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
