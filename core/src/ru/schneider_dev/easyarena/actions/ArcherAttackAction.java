package ru.schneider_dev.easyarena.actions;

import ru.schneider_dev.easyarena.Calculator;
import ru.schneider_dev.easyarena.Unit;
import ru.schneider_dev.easyarena.specialFxs.ArrowSpecialFx;

/**
 * Created by User on 04.04.2018.
 */

public class ArcherAttackAction extends BaseAction {
    public ArcherAttackAction() {
        super("ARCHER_ATTACK", "btnArcherAttack");
    }

    @Override
    public boolean action(Unit me) {
        ArrowSpecialFx arrowSpecialFx = new ArrowSpecialFx();
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
            int dmg = Calculator.getArcherDamage(me, me.getTarget());
            me.getBattleScreen().getSpecialFXEmitter().setup(arrowSpecialFx,me, me.getTarget(), 0.5f, 1f, 1f,0, false);
            me.getTarget().changeHp(-dmg);
        } else {
            me.getTarget().evade();
        }
//        me.getBattleScreen().getSpecialFXEmitter().setup(me, me.getTarget(), 1.0f, 2f, 2f, true);

//        me.getBattleScreen().getSpecialFXEmitter().setup(me.getTarget(), me.getTarget(),3.0f, 2f,5f, true);
        return true;
    }
}
