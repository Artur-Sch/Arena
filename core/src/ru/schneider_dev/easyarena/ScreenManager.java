package ru.schneider_dev.easyarena;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ru.schneider_dev.easyarena.screens.BattleScreen;
import ru.schneider_dev.easyarena.screens.GameOverScreen;
import ru.schneider_dev.easyarena.screens.LoadingScreen;
import ru.schneider_dev.easyarena.screens.MenuScreen;
import ru.schneider_dev.easyarena.screens.NextBattleScreen;
import ru.schneider_dev.easyarena.screens.StartScreen;

/**
 * Created by User on 04.04.2018.
 */

public class ScreenManager {
    public enum ScreenType {
        MENU, BATTLE, NEXTBATTLE, GAMEOVER, STARTSCREEN, LOADSCREEN, RESULTSCREEN
    }

    private static final ScreenManager ourInstance = new ScreenManager();

    public static ScreenManager getInstance() {
        return ourInstance;
    }

    private EasyArena easyArena;
    private Viewport viewport;
    private BattleScreen battleScreen;
    private MenuScreen menuScreen;
    private NextBattleScreen nextBattleScreen;
    private GameOverScreen gameOverScreen;
    private StartScreen startScreen;
    private LoadingScreen loadingScreen;


    public Viewport getViewport() {
        return viewport;
    }

    public void init(EasyArena easyArena, SpriteBatch batch) {
        this.easyArena = easyArena;
        this.loadingScreen = new LoadingScreen(batch);
        this.startScreen = new StartScreen(batch);
        this.battleScreen = new BattleScreen(batch);
        this.menuScreen = new MenuScreen(batch);
        this.nextBattleScreen = new NextBattleScreen(batch);
        this.gameOverScreen = new GameOverScreen(batch);
        this.viewport = new FitViewport(1280, 720);
        this.viewport.update(1280, 720, true);
        this.viewport.apply();
    }

    public void onResize(int width, int height) {
        viewport.update(width, height, true);
        viewport.apply();
    }

    private ScreenManager() {
    }

    public void switchScreen(ScreenType type) {
        Screen screen = easyArena.getScreen();
        Assets.getInstance().clear();
        if (screen != null) {
            screen.dispose();
        }
        switch (type) {
            case BATTLE:
                Assets.getInstance().loadAssets(ScreenType.BATTLE);
                easyArena.setScreen(battleScreen);
                break;
            case MENU:
                Assets.getInstance().loadAssets(ScreenType.MENU);
                easyArena.setScreen(menuScreen);
                break;
            case NEXTBATTLE:
                Assets.getInstance().loadAssets(ScreenType.NEXTBATTLE);
                easyArena.setScreen(nextBattleScreen);
                break;
            case GAMEOVER:
                Assets.getInstance().loadAssets(ScreenType.GAMEOVER);
                easyArena.setScreen(gameOverScreen);
                break;
            case STARTSCREEN:
                Assets.getInstance().loadAssets(ScreenType.STARTSCREEN);
                easyArena.setScreen(startScreen);
                break;
            case LOADSCREEN:
                Assets.getInstance().loadAssets(ScreenType.LOADSCREEN);
                easyArena.setScreen(loadingScreen);
                break;
        }
    }

    public void dispose() {
        Assets.getInstance().getAssetManager().dispose();
    }


}
