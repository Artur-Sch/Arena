package ru.schneider_dev.easyarena.effects;

import ru.schneider_dev.easyarena.Assets;
import ru.schneider_dev.easyarena.FlyingText;
import ru.schneider_dev.easyarena.Unit;

public class RegenerationEffect extends Effect {


    @Override
    public void start(Unit unit, int rounds) {
        super.start(unit, rounds);
        this.texture = Assets.getInstance().getAtlas().findRegion("effectRegen");
        unit.getBattleScreen().getInfoSystem().addMsg("Regeneration", unit, FlyingText.Colors.GREEN);
    }

    @Override
    public void tick() {
        super.tick();
        int pervHp = unit.getHp();
        unit.changeHp((int) (unit.getMaxHp() * 0.05f));
        unit.getBattleScreen().getInfoSystem().addMsg("Regeneration +" + (unit.getHp() - pervHp), unit, FlyingText.Colors.GREEN);
//        unit.heal(0.05f);
    }

    @Override
    public void end() {
        unit.getBattleScreen().getInfoSystem().addMsg("Regeneration END", unit, FlyingText.Colors.WHITE);
    }
}
