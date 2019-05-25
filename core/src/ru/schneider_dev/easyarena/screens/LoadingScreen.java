package ru.schneider_dev.easyarena.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.schneider_dev.easyarena.Assets;

/**
 * Created by User on 07.04.2018.
 */

public class LoadingScreen implements Screen {
    private SpriteBatch batch;
    private Texture texture;
    private Texture backgroundTexture;

    public LoadingScreen(SpriteBatch batch) {
        this.batch = batch;
        Pixmap pixmap = new Pixmap(1280, 20, Pixmap.Format.RGB888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();
        this.texture = new Texture(pixmap);

    }

    @Override
    public void show() {
        backgroundTexture = Assets.getInstance().getAssetManager().get("load_background.png", Texture.class);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0);
        batch.draw(texture, 0, 50, 1280 * Assets.getInstance().getAssetManager().getProgress(), 20);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
