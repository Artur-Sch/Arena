package ru.schneider_dev.easyarena.actions;

import ru.schneider_dev.easyarena.Calculator;
import ru.schneider_dev.easyarena.Unit;
import ru.schneider_dev.easyarena.effects.PoisonEffect;
import ru.schneider_dev.easyarena.specialFxs.PoisonSpecialFx;

public class PoisonMeleeAttackAction extends BaseAction {
    public PoisonMeleeAttackAction() {
        super("POISON_ATTACK", "btnMeleeAttack");
    }

    @Override
    public boolean action(Unit me) {
        PoisonEffect poe = new PoisonEffect();

        if (me.getTarget() == null) {
            return false;
        }
        if (me.isMyTeammate(me.getTarget())) {
            return false;
        }
        if (!me.getTarget().isAlive()) {
            return false;
        }
        me.setAttackAction(1.0f);
        me.setCurrentAnimation(Unit.AnimationType.ATTACK);
        if (!Calculator.isTargetEvaded(me, me.getTarget())) {
            int dmg = Calculator.getMeleeDamage(me, me.getTarget());
            me.getTarget().changeHp(-dmg);
            poe.start(me.getTarget(), 3);
//            me.getBattleScreen().getSpecialFXEmitter().setup(psfx, me.getTarget(), me.getTarget(), 2.0f, 1f, 2f, 1.0f, true);
            me.getTarget().addEffect(poe);
        } else {
            me.getTarget().evade();
        }
        return true;
    }
}
