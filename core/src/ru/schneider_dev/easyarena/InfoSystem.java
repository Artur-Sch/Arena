package ru.schneider_dev.easyarena;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class InfoSystem {
    private int msgCount;
    private FlyingText[] msgs;

    public InfoSystem() {
        this.msgs = new FlyingText[20];
        for (int i = 0; i < msgs.length; i++) {
            msgs[i] = new FlyingText();
        }
    }

    public void addMsg(String text, Unit unit, FlyingText.Colors color) {
        addMsg(text, unit.getPosition().x + unit.getRect().getWidth() / 2, unit.getPosition().y + unit.getRect().getHeight() / 2, color);
    }

    public void addMsg(String text, float x, float y, FlyingText.Colors color) {
        for (int i = 0; i < msgs.length; i++) {
            if (!msgs[i].isActive()) {
                msgs[i].setup(text, x, y - msgCount * 20, color);
                break;
            }
        }
        msgCount++;
    }

    public void render(SpriteBatch batch, BitmapFont font) {
        for (int i = 0; i < msgs.length; i++) {
            if (msgs[i].isActive()) {
                msgs[i].render(batch, font);
            }
        }
    }

    public void update(float dt) {
        msgCount = 0;
        for (int i = 0; i < msgs.length; i++) {
            if (msgs[i].isActive()) {
                msgs[i].update(dt);
            }
        }
    }
}
