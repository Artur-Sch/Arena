package ru.schneider_dev.easyarena;


import com.boontaran.DataManager;

public class Data {
    private DataManager dataManager;

    private static final String PREFERENCE_NAME = "arena_data";
    private static final String MAX_SCORE = "maxScore";

    public Data() {
        dataManager = new DataManager(PREFERENCE_NAME);
    }

    public int getMaxScore() {
        return dataManager.getInt(MAX_SCORE, 0);
    }

    public void setMaxScore(int maxScore) {
        dataManager.saveInt(MAX_SCORE, maxScore);
    }

}
