package ru.schneider_dev.easyarena;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by User on 05.04.2018.
 */

public class Hero implements Serializable {
    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    private int totalScore;
    private ArrayList<Unit> units;

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public Hero() {
        totalScore = 0;
        this.units = new ArrayList<Unit>();
    }



}

