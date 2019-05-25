package ru.schneider_dev.easyarena;
//
//import com.badlogic.gdx.Gdx;
//
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;

import java.util.ArrayList;

/**
 * Created by User on 07.04.2018.
 */

public class GameSession {
    private static final GameSession ourInstance = new GameSession();

    public static GameSession getInstance() {
        return ourInstance;
    }



    public Hero getPlayer() {
        return player;
    }

    public Hero getEnemyPlayer() {
        return enemyPlayer;
    }

    private GameSession() {
    }

    private Hero enemyPlayer;
    private Hero player;
    private UnitFactory unitFactory;
    private ArrayList<Hero> heroesList;

    public void startNewSession() {

        heroesList = new ArrayList<Hero>();
        heroesList.add(new Hero());
        heroesList.add(new Hero());
        this.player = heroesList.get(0);
        this.enemyPlayer = heroesList.get(1);
        unitFactory = new UnitFactory();
        makePlayerArmy(1);
        makeAiArmy(1);
    }


    public void makeAiArmy(int level) {
        if (enemyPlayer.getUnits().size() > 0) {
            enemyPlayer.getUnits().clear();
        }
        int r = (int) (Math.random() * 5) + 2;
        unitFactory.createPlayerUnits(r, enemyPlayer, level, true);

    }

    public void makePlayerArmy(int level) {
        int r = (int) (Math.random() * 5) + 2;
        unitFactory.createPlayerUnits(r, player, level, false);
    }

//    public void loadSession() {
//        ArrayList<Hero> loadHero;
//        try {
//            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Gdx.files.local("data.sav").file()));
////            loadHero  = (ArrayList<Hero>) ois.readObject();
//            ois.close();
////            player = loadHero.get(0);
////            player = loadHero.get(1);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

//    public void saveSession() {
//        try {
//            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Gdx.files.local("data.sav").file(),true));
//            oos.writeObject(heroesList);
//            oos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
}

