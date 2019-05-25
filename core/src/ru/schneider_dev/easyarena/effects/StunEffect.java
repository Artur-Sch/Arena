package ru.schneider_dev.easyarena.effects;

import ru.schneider_dev.easyarena.Assets;
import ru.schneider_dev.easyarena.FlyingText;
import ru.schneider_dev.easyarena.Unit;
import ru.schneider_dev.easyarena.specialFxs.StunSpecialFX;

public class StunEffect extends Effect {
    //    PoisonSpecialFx psfx = new PoisonSpecialFx();
    StunSpecialFX stunSpecialFX = new StunSpecialFX();
//    int poisonDmg = 5;


    @Override
    public void start(Unit unit, int rounds) {
        super.start(unit, rounds);
        this.texture = Assets.getInstance().getAtlas().findRegion("effectStun");
        unit.getBattleScreen().getSpecialFXEmitter().setup(stunSpecialFX, unit, unit, 1.0f, 1f, 2f, 0.0f, true);
        unit.getBattleScreen().getInfoSystem().addMsg("STUN 2T", unit, FlyingText.Colors.RED);
        unit.setStunned(true);
        System.out.println(unit.getStunCounter() + " start");
    }

    @Override
    public void tick() {
        super.tick();
        unit.getBattleScreen().getInfoSystem().addMsg("STUN", unit, FlyingText.Colors.RED);
        unit.getBattleScreen().getSpecialFXEmitter().setup(stunSpecialFX, unit, unit, 1.0f, 1f, 2f, 0.0f, true);
        unit.setStunCounterWhith(-1);
        unit.setStunned(true);
        System.out.println(unit.getStunCounter() + " tick");
//        unit.changeHp(-(poisonDmg));
//        poisonDmg+=5;
    }

    @Override
    public void end() {
        System.out.println(unit.getStunCounter() + " end");
        if (unit.getStunCounter() > 0) {
            unit.setStunned(true);
        } else {
            unit.setStunned(false);
            unit.getBattleScreen().getInfoSystem().addMsg("STUN END", unit, FlyingText.Colors.WHITE);
        }
    }
}
