package ru.schneider_dev.easyarena;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class EasyArena extends Game {
    private SpriteBatch batch;
    public static Data data;

    @Override
    public void create() {
        data = new Data();
        batch = new SpriteBatch();
                ScreenManager.getInstance().init(this, batch);
        ScreenManager.getInstance().switchScreen(ScreenManager.ScreenType.LOADSCREEN);
        if (Assets.getInstance().getAssetManager().getProgress() >= 1) {
        ScreenManager.getInstance().switchScreen(ScreenManager.ScreenType.STARTSCREEN);
        }
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        this.getScreen().render(dt);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
