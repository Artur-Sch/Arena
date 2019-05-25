package ru.schneider_dev.easyarena;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by User on 04.04.2018.
 */

public class Assets {
    private static final Assets ourInstance = new Assets();

    public static Assets getInstance() {
        return ourInstance;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    private AssetManager assetManager;

    public TextureAtlas getAtlas() {
        return textureAtlas;
    }

    private TextureAtlas textureAtlas;

    private Assets() {
        assetManager = new AssetManager();
    }

    public void loadAssets(ScreenManager.ScreenType type) {
        switch (type) {
            case MENU:
                assetManager.load("rpg.pack", TextureAtlas.class);
                assetManager.load("menuBackground.png", Texture.class);
                assetManager.finishLoading();
                textureAtlas = assetManager.get("rpg.pack", TextureAtlas.class);
                break;
            case BATTLE:
//                assetManager.load("background.png", Texture.class);
                assetManager.load("fight.png", Texture.class);
                assetManager.load("rpg.pack", TextureAtlas.class);
                assetManager.finishLoading();
                textureAtlas = assetManager.get("rpg.pack", TextureAtlas.class);
                break;
            case NEXTBATTLE:
                assetManager.load("rpg.pack", TextureAtlas.class);
                assetManager.load("gameMenuBackground.png", Texture.class);
                assetManager.finishLoading();
                textureAtlas = assetManager.get("rpg.pack", TextureAtlas.class);
                break;
            case GAMEOVER:
                assetManager.load("rpg.pack", TextureAtlas.class);
                assetManager.load("gameMenuBackground.png", Texture.class);
                assetManager.finishLoading();
                textureAtlas = assetManager.get("rpg.pack", TextureAtlas.class);
                break;
            case STARTSCREEN:
                assetManager.load("rpg.pack", TextureAtlas.class);
                assetManager.load("menuBackground.png", Texture.class);
                assetManager.finishLoading();
                textureAtlas = assetManager.get("rpg.pack", TextureAtlas.class);
                break;
            case LOADSCREEN:
                assetManager.load("rpg.pack", TextureAtlas.class);
                assetManager.load("load_background.png", Texture.class);
                assetManager.finishLoading();
                textureAtlas = assetManager.get("rpg.pack", TextureAtlas.class);
                break;
        }
    }

    public void clear() {
        assetManager.clear();
    }


}
