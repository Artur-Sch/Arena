package ru.schneider_dev.easyarena.actions;

import ru.schneider_dev.easyarena.Unit;
import ru.schneider_dev.easyarena.effects.RegenerationEffect;

public class RegenerationAction extends BaseAction {


    public RegenerationAction() {
        super("REGENERATION", "btnRegen");
    }

    @Override
    public boolean action(Unit me) {
        if (me.getHealPotionPoint() > 0) {
            RegenerationEffect rge = new RegenerationEffect();
            rge.start(me, 3);
            me.addEffect(rge);
            me.setHealPotionPoint(me.getHealPotionPoint() - 1);
            return true;
        }
        return false;
    }
}
