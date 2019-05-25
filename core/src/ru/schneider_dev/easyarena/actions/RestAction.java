package ru.schneider_dev.easyarena.actions;

import ru.schneider_dev.easyarena.FlyingText;
import ru.schneider_dev.easyarena.Unit;

public class RestAction extends BaseAction {
    public RestAction() {
        super("REST", "btnHeal");
    }

    @Override
    public boolean action(Unit me) {
        int pervHp = me.getHp();
        me.changeHp((int) (me.getMaxHp() * 0.15f));
        me.getBattleScreen().getInfoSystem().addMsg("HP +" + (me.getHp() - pervHp), me, FlyingText.Colors.GREEN);
        return true;
    }
}
