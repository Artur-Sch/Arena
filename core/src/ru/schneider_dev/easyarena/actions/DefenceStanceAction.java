package ru.schneider_dev.easyarena.actions;

import ru.schneider_dev.easyarena.Unit;
import ru.schneider_dev.easyarena.effects.DefenceStanceEffect;

public class DefenceStanceAction extends BaseAction {
    public DefenceStanceAction() {
        super("DEFENCE", "btnDefence");
    }

    @Override
    public boolean action(Unit me) {
        DefenceStanceEffect dse = new DefenceStanceEffect();
        dse.start(me, 3);
        me.addEffect(dse);
        return true;
    }
}
