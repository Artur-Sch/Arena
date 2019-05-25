package ru.schneider_dev.easyarena.specialFxs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public abstract class SpecialFX {
    protected Vector2 positionFrom;
    protected Vector2 positionTo;
    protected float frameSpeed;
    protected float time;
    protected float maxTime;
    protected float scaleFrom;
    protected float scaleTo;
    protected int maxFrames;
    protected TextureRegion texture;
    protected TextureRegion[] regions;

    public SpecialFX() {
        positionFrom = new Vector2(0, 0);
        positionTo = new Vector2(0, 0);
        frameSpeed = 0.01f;// скорость анимации
        time = -100.0f;// время анимации
        maxTime = 0.0f;
        scaleFrom = 1.0f;
        scaleTo = 1.0f;
    }

    public boolean isActive() {
        return time > -100.0f;
    }

    public void setup(float xFrom, float yFrom, float xTo, float yTo, float maxTime, float scaleFrom, float scaleTo, float delay, boolean oneCycle) {
        positionFrom.set(xFrom, yFrom);
        positionTo.set(xTo, yTo);
        this.maxTime = maxTime;
        if (oneCycle) {
            frameSpeed = maxTime / maxFrames;
        } else {
            frameSpeed = 0.01f;
        }
        this.scaleFrom = scaleFrom;
        this.scaleTo = scaleTo;
        this.time = -delay;
    }


    public void render(SpriteBatch batch) {
        if (isActive() && time> 0.0f) {
            int currentFrame = (int) (time / frameSpeed) % maxFrames;
            float x = positionFrom.x + (time / maxTime) * (positionTo.x - positionFrom.x);
            float y = positionFrom.y + (time / maxTime) * (positionTo.y - positionFrom.y);
            float currentScale = scaleFrom + (time / maxTime) * (scaleTo - scaleFrom);
            float rotation = MathUtils.radiansToDegrees * MathUtils.atan2(positionTo.y -positionFrom.y, positionTo.x-positionFrom.x );
//            if (positionFrom.y > positionTo.y && (positionFrom.y - positionTo.y > 100)) {
//                rotation = -10.0f;
//            } else if (positionFrom.y < positionTo.y && (positionTo.y - positionFrom.y > 100)) {
//                rotation = +10.0f;
//            } else {
//                rotation = 0.0f;
//            }
            TextureRegion currentTexture = regions[currentFrame];
            batch.draw(regions[currentFrame], x - currentTexture.getRegionWidth()/2, y -currentTexture.getRegionHeight()/2,
                    currentTexture.getRegionWidth()/2,currentFrame/2,currentTexture.getRegionWidth(),currentTexture.getRegionHeight(),currentScale,currentScale,rotation);
        }
    }

    public void update(float dt) {
        if (isActive()) {
            time += dt;
            if (time > maxTime) {
                time = -100.0f;
            }
        }
    }

}
