package ru.schneider_dev.easyarena.actions;

import ru.schneider_dev.easyarena.Calculator;
import ru.schneider_dev.easyarena.Unit;
import ru.schneider_dev.easyarena.effects.StunEffect;

public class StunMeleeAttackAction extends BaseAction {
    public StunMeleeAttackAction() {
        super("MELEE_ATTACK", "btnMeleeAttack");
    }
    @Override
    public boolean action(Unit me) {
        StunEffect stunEffect = new StunEffect();
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
            me.getTarget().setStunCounterWhith(2);
            stunEffect.start(me.getTarget(), 2);
            System.out.println(me.getTarget().getStunCounter() + " action");
            me.getTarget().addEffect(stunEffect);
            me.getTarget().changeHp(-dmg);
        } else {
            me.getTarget().evade();
        }
//        me.getBattleScreen().getSpecialFXEmitter().setup(me, me.getTarget(), 1.0f, 2f, 2f, true);

//        me.getBattleScreen().getSpecialFXEmitter().setup(me.getTarget(), me.getTarget(),3.0f, 2f,5f, true);
        return true;
    }
}
