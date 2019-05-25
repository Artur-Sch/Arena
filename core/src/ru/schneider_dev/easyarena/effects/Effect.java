package ru.schneider_dev.easyarena.effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.schneider_dev.easyarena.Unit;
import ru.schneider_dev.easyarena.UnitFactory;

import java.io.Serializable;

public abstract class Effect implements Serializable {
    Unit unit;
    TextureRegion texture;
    int rounds;

    public void start(Unit unit, int rounds) {
        this.unit = unit;
        this.rounds = rounds;
    }

    public void render(SpriteBatch batch, int len) {
        if (!isEnded()) {
            if (unit.getUnitType() == UnitFactory.UnitType.ORK_POISON) {
                batch.draw(texture, unit.getPosition().x + len + unit.getTexture().getRegionWidth() / 2 - unit.getTextureHp().getRegionWidth() / 2,
                                    unit.getPosition().y + unit.getTexture().getRegionHeight() - 20);

            } else if (unit.getUnitType() == UnitFactory.UnitType.TROLL_STUN) {
                batch.draw(texture, unit.getPosition().x + len + unit.getTexture().getRegionWidth() / 2 - unit.getTextureHp().getRegionWidth() / 2,
                                    unit.getPosition().y + unit.getTexture().getRegionHeight() - 60);

            } else {
                batch.draw(texture, unit.getPosition().x + len + unit.getTexture().getRegionWidth() / 2 - unit.getTextureHp().getRegionWidth() / 2,
                                    unit.getPosition().y + unit.getTexture().getRegionHeight() + 5);
            }
        }
    }

    public void tick() {
        this.rounds--;
    }

    public boolean isEnded() {
        return this.rounds == 0;
    }

    public abstract void end();
}
