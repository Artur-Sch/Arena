package ru.schneider_dev.easyarena;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.schneider_dev.easyarena.actions.ArcherAttackAction;
import ru.schneider_dev.easyarena.actions.BaseAction;
import ru.schneider_dev.easyarena.actions.DefenceStanceAction;
import ru.schneider_dev.easyarena.actions.MagicAttackAction;
import ru.schneider_dev.easyarena.actions.MeleeAttackAction;
import ru.schneider_dev.easyarena.actions.PoisonMeleeAttackAction;
import ru.schneider_dev.easyarena.actions.RegenerationAction;
import ru.schneider_dev.easyarena.actions.RestAction;
import ru.schneider_dev.easyarena.actions.StunMeleeAttackAction;
//import com.sch.game.screens.BattleScreen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import static com.sch.game.screens.NextBattleScreen.nextBattleLevel;

/**
 * Created by User on 04.04.2018.
 */

public class UnitFactory implements Serializable {

    public enum UnitType {
        KNIGHT, ARCHER_ELF, MAGE_ELF, AXE_KNIGHT, ORK_POISON, TROLL_STUN, TROLL_UNDEFENCE
    }

    transient private Map<UnitType, Unit> data;
    transient private List<Autopilot> aiBank;
    transient private List<BaseAction> actions;
//    int counterLevel;
//    public static int counter;

    public List<BaseAction> getActions() {
        return actions;
    }

    public UnitFactory() {
        this.createActions();
        this.aiBank = new ArrayList<Autopilot>();
        this.aiBank.add(new Autopilot() {
            @Override
            public boolean turn(Unit me) {
                if (!me.getBattleScreen().canIMakeTurn()) {
                    return false;
                }
                Unit target = null;
                int counter = 0;
                do {
                    target = me.getBattleScreen().getUnits().get((int) (Math.random() * me.getBattleScreen().getUnits().size()));
                    counter++;
                    if (counter > 1000) {
                        break;
                    }
                } while (me.isMyTeammate(target) || !target.isAlive());
                me.setTarget(target);
                int r = (int) (Math.random() * 2);
                me.getActions().get(r).action(me);
                return true;
            }
        });
        this.createUnitPatterns();
    }

    public void createActions() {
        this.actions = new ArrayList<BaseAction>();
        actions.add(new MeleeAttackAction());
        actions.add(new ArcherAttackAction());
        actions.add(new MagicAttackAction());
        actions.add(new DefenceStanceAction());
        actions.add(new RestAction());
        actions.add(new RegenerationAction());
        actions.add(new PoisonMeleeAttackAction());
        actions.add(new StunMeleeAttackAction());

    }

    public void createUnitPatterns() {
        data = new HashMap<UnitType, Unit>();
        Stats knightStats = new Stats(1, 20, 5, 30, 7, 0, 1.0f, 0.2f, 1.0f, 0.8f, 0.1f,10);

        TextureRegion textureKnight = Assets.getInstance().getAtlas().findRegion("knightGolden");
        TextureRegion[][] framesKnight = textureKnight.split(187, 143);
        Unit knight = new Unit(UnitType.KNIGHT, framesKnight, knightStats);
        knight.getActions().add(actions.get(0));
        knight.getActions().add(actions.get(3));
        knight.getActions().add(actions.get(4));
        data.put(UnitType.KNIGHT, knight);

        Stats axeKnightStats = new Stats(1, 20, 5, 25, 7, 0, 1.0f, 0.2f, 1.0f, 0.8f, 0.1f,11);

        TextureRegion textureAxeKnight = Assets.getInstance().getAtlas().findRegion("knightAxe");
        TextureRegion[][] framesAxeKnight = textureAxeKnight.split(149, 143);
        Unit axeKnight = new Unit(UnitType.AXE_KNIGHT, framesAxeKnight, axeKnightStats);
        axeKnight.getActions().add(actions.get(0));
        axeKnight.getActions().add(actions.get(3));
        axeKnight.getActions().add(actions.get(4));
        data.put(UnitType.AXE_KNIGHT, axeKnight);

        Stats elfArcherStats = new Stats(1, 10, 20, 15, 2, 0, 0.2f, 1.0f, 1.0f, 0.2f, 0.2f,15);

        TextureRegion textureElfArcher = Assets.getInstance().getAtlas().findRegion("elfArcher");
        TextureRegion[][] framesElfArcher = textureElfArcher.split(155, 135);
        Unit elfArcher = new Unit(UnitType.ARCHER_ELF, framesElfArcher, elfArcherStats);
        elfArcher.getActions().add(actions.get(1));
        elfArcher.getActions().add(actions.get(4));

        data.put(UnitType.ARCHER_ELF, elfArcher);

        Stats elfMageStats = new Stats(1, 10, 10, 10, 2, 20, 0.2f, 0.2f, 1.0f, 0.2f, 1.0f,14);

        TextureRegion textureElfMage = Assets.getInstance().getAtlas().findRegion("elfMagician");
        TextureRegion[][] framesElfMage = textureElfMage.split(150, 124);
        Unit elfMage = new Unit(UnitType.MAGE_ELF, framesElfMage, elfMageStats);
        elfMage.getActions().add(actions.get(2));
        elfMage.getActions().add(actions.get(5));
        data.put(UnitType.MAGE_ELF, elfMage);


        Stats poisonOrkStats = new Stats(1, 15, 15, 25, 2, 0, 1.0f, 0.2f, 1.0f, 0.2f, 0.1f,20);

//        TextureRegion texturePoisonOrk = Assets.getInstance().getAtlas().findRegion("deadpool");
//        TextureRegion[][] framesPoisonOrk = texturePoisonOrk.split(141, 170);
        TextureRegion texturePoisonOrk = Assets.getInstance().getAtlas().findRegion("orkPoison");
        TextureRegion[][] framesPoisonOrk = texturePoisonOrk.split(181, 149);
        Unit poisonOrk = new Unit(UnitType.ORK_POISON, framesPoisonOrk, poisonOrkStats);
        poisonOrk.getActions().add(actions.get(6));
        poisonOrk.getActions().add(actions.get(4));
        data.put(UnitType.ORK_POISON, poisonOrk);


        Stats trollStunStats = new Stats(1, 15, 5, 30, 2, 0, 1.0f, 0.2f, 1.0f, 0.2f, 0.1f,21);

        TextureRegion textureTrollStun = Assets.getInstance().getAtlas().findRegion("trollStun");
        TextureRegion[][] framesTrollStun = textureTrollStun.split(245, 190);
        Unit trollStun = new Unit(UnitType.TROLL_STUN, framesTrollStun, trollStunStats);
        trollStun.getActions().add(actions.get(7));
        trollStun.getActions().add(actions.get(4));
        data.put(UnitType.TROLL_STUN, trollStun);

        Stats trollUndefenceStats = new Stats(1, 15, 5, 30, 2, 0, 1.0f, 0.2f, 1.0f, 0.2f, 0.1f,19);

        TextureRegion textureTrollUndefence = Assets.getInstance().getAtlas().findRegion("troll2RedDef");
        TextureRegion[][] framesTrollUndefence = textureTrollUndefence.split(273, 217);
        Unit trollUndefence = new Unit(UnitType.TROLL_STUN, framesTrollUndefence, trollUndefenceStats);
        trollUndefence.getActions().add(actions.get(0));
        trollUndefence.getActions().add(actions.get(4));
        data.put(UnitType.TROLL_UNDEFENCE, trollUndefence);
    }

    public void reloadUnit(Unit unit) {
        Unit unitPattern = data.get(unit.getUnitType());
        unit.reload(unitPattern.getFrames(), unitPattern.getActions());
        if (unit.isAi()) {
            unit.setAutopilot(aiBank.get(0));
        }
    }


    public Unit createUnit(UnitType unitType, int level, boolean flip, boolean ai) {
        Unit unitPattern = data.get(unitType);
        Unit unit = new Unit(unitType, unitPattern.getFrames(), (Stats) unitPattern.getStats().clone());
        unit.setLevelTo(level);
        unit.setActions(unitPattern.getActions());
        if (ai) {
            unit.setAutopilot(aiBank.get(0));
        }
        unit.setFlip(flip);
        return unit;
    }

//    public void changeRandomUnitTexture(UnitType unitType , Unit unit) {
//        int r = (int) (Math.random()* 3);
//        if (unitType == UnitType.ARCHER_ELF) {
//            switch (r) {
//                case 0:
//                    unit.setTexture(Assets.getInstance().getAssetManager().get("units/elf1.png", Texture.class));
//                    break;
//                case 1:
//                    unit.setTexture(Assets.getInstance().getAssetManager().get("units/elf2.png", Texture.class));
//                    break;
//                case 2:
//                    unit.setTexture(Assets.getInstance().getAssetManager().get("units/elf3.png", Texture.class));
//                    break;
//            }
//        } else if (unitType == UnitType.MAGE_ELF) {
//            switch (r) {
//                case 0:
//                    unit.setTexture(Assets.getInstance().getAssetManager().get("units/ork1.png", Texture.class));
//                    break;
//                case 1:
//                    unit.setTexture(Assets.getInstance().getAssetManager().get("units/ork2.png", Texture.class));
//                    break;
//                case 2:
//                    unit.setTexture(Assets.getInstance().getAssetManager().get("units/ork3.png", Texture.class));
//                    break;
//            }
//        } else if (unitType == UnitType.AXE_KNIGHT) {
//            switch (r) {
//                case 0:
//                    unit.setTexture(Assets.getInstance().getAssetManager().get("units/Troll1.png", Texture.class));
//                    break;
//                case 1:
//                    unit.setTexture(Assets.getInstance().getAssetManager().get("units/Troll2.png", Texture.class));
//                    break;
//                case 2:
//                    unit.setTexture(Assets.getInstance().getAssetManager().get("units/Troll3.png", Texture.class));
//                    break;
//            }
//        } else if (unitType == UnitType.KNIGHT) {
//            switch (r) {
//                case 0:
//                    unit.setTexture(Assets.getInstance().getAssetManager().get("units/knight1.png", Texture.class));
//                    break;
//                case 1:
//                    unit.setTexture(Assets.getInstance().getAssetManager().get("units/knight1.png", Texture.class));
//                    break;
//                case 2:
//                    unit.setTexture(Assets.getInstance().getAssetManager().get("units/knight2.png", Texture.class));
//                    break;
//            }
//        }
//    }


    public UnitType randomUniType() {
        UnitType unitType = null;
        int r = (int) (Math.random() * 7);
        switch (r) {
            case 0:
                unitType = UnitType.ARCHER_ELF;
                break;
            case 1:
                unitType = UnitType.KNIGHT;
                break;
            case 2:
                unitType = UnitType.AXE_KNIGHT;
                break;
            case 3:
                unitType = UnitType.MAGE_ELF;
                break;
            case 4:
                unitType = UnitType.ORK_POISON;
                break;
            case 5:
                unitType = UnitType.TROLL_STUN;
                break;
            case 6:
                unitType = UnitType.TROLL_UNDEFENCE;
                break;
        }
        return unitType;
    }

    public void createPlayerUnits(int count, Hero hero, int level, boolean ai) {
        for (int i = 0; i < count; i++) {
            Unit unit = createUnit(randomUniType(), level, false, false);
            if (ai) {
                unit.setFlip(true);
                unit.setAutopilot(aiBank.get(0));
            }
            unit.setHero(hero);
            hero.getUnits().add(unit);
        }
    }
}
