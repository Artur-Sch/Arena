package ru.schneider_dev.easyarena.actions;

import ru.schneider_dev.easyarena.Calculator;
import ru.schneider_dev.easyarena.Unit;
import ru.schneider_dev.easyarena.specialFxs.FireBallSpecialFx;

/**
 * Created by User on 04.04.2018.
 */

public class MagicAttackAction extends BaseAction {
    public MagicAttackAction() {
        super("MAGIC_ATTACK", "btnMagicAttack");
    }

    @Override
    public boolean action(Unit me) {
        FireBallSpecialFx fireBallSpecialFx = new FireBallSpecialFx();
        FireBallSpecialFx fireBombExplosion = new FireBallSpecialFx();
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
        int dmg = Calculator.getMagicDamage(me, me.getTarget());
        me.getTarget().changeHp(-dmg);
        me.getBattleScreen().getSpecialFXEmitter().setup(fireBallSpecialFx, me, me.getTarget(), 1.0f, 1f, 1f, 0.0f, true);
        me.getBattleScreen().getSpecialFXEmitter().setup(fireBombExplosion, me.getTarget(), me.getTarget(), 1.0f, 1f, 3f, 1.0f, true);
        return true;
    }
}
