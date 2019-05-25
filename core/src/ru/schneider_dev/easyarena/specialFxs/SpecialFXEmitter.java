package ru.schneider_dev.easyarena.specialFxs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.schneider_dev.easyarena.Unit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 06.04.2018.
 */

public class SpecialFXEmitter {
    private List<SpecialFX> fxs;

    public SpecialFXEmitter() {
        this.fxs = new ArrayList<SpecialFX>();
    }

    public void setup(SpecialFX specialFX, Unit me, Unit target, float maxTime, float scaleFrom, float scaleTo, float delay, boolean oneCycle) {
        if (!specialFX.isActive()) {
            specialFX.setup(me.getPosition().x + me.getTexture().getRegionWidth() / 2, me.getPosition().y + me.getTexture().getRegionHeight() / 2,
                    target.getPosition().x + target.getTexture().getRegionWidth() / 2, target.getPosition().y + target.getTexture().getRegionHeight() / 2,
                    maxTime, scaleFrom, scaleTo, delay, oneCycle);
        }
        fxs.add(specialFX);
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < fxs.size(); i++) {
            if (fxs.get(i).isActive()) {
                fxs.get(i).render(batch);
            }
        }
    }

    public void update(float dt) {
        for (int i = 0; i < fxs.size(); i++) {
            if (fxs.get(i).isActive()) {
                fxs.get(i).update(dt);
            }
        }
    }
}
