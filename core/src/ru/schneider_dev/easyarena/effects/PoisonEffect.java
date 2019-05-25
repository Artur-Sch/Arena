package ru.schneider_dev.easyarena.effects;

import ru.schneider_dev.easyarena.Assets;
import ru.schneider_dev.easyarena.FlyingText;
import ru.schneider_dev.easyarena.Unit;
import ru.schneider_dev.easyarena.specialFxs.PoisonSpecialFx;

public class PoisonEffect extends Effect {
    PoisonSpecialFx psfx = new PoisonSpecialFx();
    int poisonDmg = 5;


    @Override
    public void start(Unit unit, int rounds) {
        super.start(unit, rounds);
        this.texture = Assets.getInstance().getAtlas().findRegion("effectPoison");
        unit.getBattleScreen().getInfoSystem().addMsg("Poison", unit, FlyingText.Colors.RED);
    }

    @Override
    public void tick() {
        super.tick();
        unit.getBattleScreen().getSpecialFXEmitter().setup(psfx, unit, unit, 2.0f, 1f, 2f, 0.0f, true);
        unit.getBattleScreen().getInfoSystem().addMsg("Poison dmg", unit, FlyingText.Colors.RED);
        unit.changeHp(-(poisonDmg));
        poisonDmg += 5;
    }

    @Override
    public void end() {
        unit.getBattleScreen().getInfoSystem().addMsg("Poison END", unit, FlyingText.Colors.WHITE);
    }
}
