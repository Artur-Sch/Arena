package ru.schneider_dev.easyarena.actions;

import ru.schneider_dev.easyarena.*;
import ru.schneider_dev.easyarena.effects.ReduceDefenceEffect;
import ru.schneider_dev.easyarena.effects.StunEffect;

public class MeleeAttackAction extends BaseAction {
    public MeleeAttackAction() {
        super("MELEE_ATTACK", "btnMeleeAttack");
    }

    @Override
    public boolean action(Unit me) {
        ReduceDefenceEffect reduceDefenceEffect = new ReduceDefenceEffect();
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
            if (me.getUnitType() == UnitFactory.UnitType.TROLL_UNDEFENCE) {
                reduceDefenceEffect.start(me.getTarget(), 2);
                me.getTarget().addEffect(reduceDefenceEffect);
            }
            } else {
            me.getTarget().evade();
        }
        return true;
    }
}
