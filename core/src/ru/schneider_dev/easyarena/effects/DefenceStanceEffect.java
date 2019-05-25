package ru.schneider_dev.easyarena.effects;

import ru.schneider_dev.easyarena.Assets;
import ru.schneider_dev.easyarena.FlyingText;
import ru.schneider_dev.easyarena.Unit;

public class DefenceStanceEffect extends Effect {


    @Override
    public void start(Unit unit, int rounds) {
        super.start(unit, rounds);
        this.texture = Assets.getInstance().getAtlas().findRegion("effectDefence");
        unit.getStats().setDefence(unit.getStats().getDefence() + 5);
        unit.getBattleScreen().getInfoSystem().addMsg("Shields UP!! +5", unit, FlyingText.Colors.GREEN);
    }


    @Override
    public void end() {
        unit.getStats().setDefence(unit.getStats().getDefence() - 5);
        unit.getBattleScreen().getInfoSystem().addMsg("Shields DOWN!! -5", unit, FlyingText.Colors.WHITE);
    }
}
