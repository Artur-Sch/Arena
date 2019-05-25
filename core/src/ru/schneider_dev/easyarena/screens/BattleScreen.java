package ru.schneider_dev.easyarena.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import ru.schneider_dev.easyarena.Assets;
import ru.schneider_dev.easyarena.EasyArena;
import ru.schneider_dev.easyarena.GameSession;
import ru.schneider_dev.easyarena.Hero;
import ru.schneider_dev.easyarena.InfoSystem;
import ru.schneider_dev.easyarena.MyInputProcessor;
import ru.schneider_dev.easyarena.ScreenManager;
import ru.schneider_dev.easyarena.Unit;
import ru.schneider_dev.easyarena.UnitFactory;
import ru.schneider_dev.easyarena.actions.*;
import ru.schneider_dev.easyarena.specialFxs.SpecialFXEmitter;

import java.util.ArrayList;
import java.util.List;


public class BattleScreen implements Screen {
    private SpriteBatch batch;
    private BitmapFont font;
    private BitmapFont font36;
    private Texture background;
    private ArrayList<Unit> units;
    private int currentUnitIndex;
    private Unit currentUnit;
    private TextureRegion textureSelector;
    private InfoSystem infoSystem;
    private UnitFactory unitFactory;
    private Vector2[][] stayPoints;
    private float animationTimer;
    private Stage stage;//хранилище интерфейса, кнопки картинки бегунки, единая сцена
    private Skin skin;
    private MyInputProcessor myInputProcessor;
    private SpecialFXEmitter specialFXEmitter;
    private Hero player1;
    private Hero player2;
    private float animationWithoutGameOver;
    private Button button;
    public static int score;

    public Vector2[][] getStayPoints() {
        return stayPoints;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public boolean canIMakeTurn() {
        return animationTimer <= 0.0f;
    }

    public InfoSystem getInfoSystem() {
        return infoSystem;
    }

    public BattleScreen(SpriteBatch batch) {
        this.batch = batch;
    }


    public SpecialFXEmitter getSpecialFXEmitter() {
        return specialFXEmitter;
    }

    @Override
    public void show() {
        background = Assets.getInstance().getAssetManager().get("fight.png", Texture.class);
        specialFXEmitter = new SpecialFXEmitter();
        stayPoints = new Vector2[4][3];
        final int LEFT_STAYPOINT_X = 200;
        final int TOP_STAYPOINT_Y = 450;
        final int DISTANCE_BETWEEN_UNITS = 200;
        final int DISTANCE_BETWEEN_TEAMS = 150;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                int x = LEFT_STAYPOINT_X + i * DISTANCE_BETWEEN_UNITS + j * 20;
                if (i > 1) {
                    x += 100;
                }
                stayPoints[i][j] = new Vector2(x, TOP_STAYPOINT_Y - j * DISTANCE_BETWEEN_TEAMS);
            }
        }
        units = new ArrayList<Unit>();
        unitFactory = new UnitFactory();
        player1 = GameSession.getInstance().getPlayer();
        int startX = 0;
        int startY = 0;
        for (int i = 0; i < player1.getUnits().size(); i++) {
            if (i == 3) {
                startX = 1;
                startY = 0;
            }
            unitFactory.reloadUnit(player1.getUnits().get(i));
            player1.getUnits().get(i).setToMap(this, startX, startY);
            units.add(player1.getUnits().get(i));
            startY++;
        }
        player2 = GameSession.getInstance().getEnemyPlayer();
        startX = 2;
        startY = 0;
        for (int i = 0; i < player2.getUnits().size(); i++) {
            if (i == 3) {
                startX = 3;
                startY = 0;
            }
            unitFactory.reloadUnit(player2.getUnits().get(i));
            player2.getUnits().get(i).setToMap(this, startX, startY);
            units.add(player2.getUnits().get(i));
            startY++;
        }

        myInputProcessor = new MyInputProcessor();
        Gdx.input.setInputProcessor(myInputProcessor);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("zorque.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 1;
        parameter.shadowColor = Color.BLACK;
        parameter.shadowOffsetX = -2;
        parameter.shadowOffsetY = 2;
        parameter.color = Color.WHITE;
        font = generator.generateFont(parameter);
        parameter.size = 36;
        font36 = generator.generateFont(parameter);
        generator.dispose();
        infoSystem = new InfoSystem();
        textureSelector = Assets.getInstance().getAtlas().findRegion("selector");
        currentUnitIndex = 0;
        if (units.get(currentUnitIndex).isAlive()) {
            currentUnit = units.get(currentUnitIndex);
        } else {
            do {
                currentUnitIndex++;
                currentUnit = units.get(currentUnitIndex);
            } while (!units.get(currentUnitIndex).isAlive());
        }
        createGUI(unitFactory);
        InputMultiplexer im = new InputMultiplexer(stage, myInputProcessor);
        Gdx.input.setInputProcessor(im);
        animationTimer = 0.0f;
        animationWithoutGameOver = 1.0f;
    }


    public void createGUI(UnitFactory unitFactory) {
        stage = new Stage(ScreenManager.getInstance().getViewport(), batch);
        skin = new Skin(Assets.getInstance().getAtlas()); //оформление кнопок
//        skin.addRegions(Assets.getInstance().getAtlas());
        List<BaseAction> list = unitFactory.getActions();
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("btnGameMenu");
        textButtonStyle.down = skin.getDrawable("btnGameMenuPressed");
        textButtonStyle.font = font;
        skin.add("tbs", textButtonStyle);
        Button btnMenu = new TextButton("menu", skin, "tbs");
        btnMenu.setPosition(1160, 600);
        stage.addActor(btnMenu);
        btnMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                GameSession.getInstance().saveSession();
                ScreenManager.getInstance().switchScreen(ScreenManager.ScreenType.MENU);
            }
        });

        for (BaseAction o : list) {
            Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
            buttonStyle.up = skin.newDrawable(o.getTextureName(), Color.WHITE);
            skin.add(o.getName(), buttonStyle);
        }

        for (Unit o : units) {
            if (!o.isAi()) {
                Group actionPanel = new Group();
                Image image = new Image(Assets.getInstance().getAtlas().findRegion("actionPanel"));
                actionPanel.addActor(image);
                actionPanel.setPosition(1280 / 2 - 349 / 2, 5);
                actionPanel.setVisible(false);
                o.setActionPanel(actionPanel);
                stage.addActor(actionPanel);
                final Unit innerUnit = o;
                int counter = 0;
                for (BaseAction a : o.getActions()) {
                    final BaseAction ba = a;
                    Button btn = new Button(skin, a.getName());
                    btn.setPosition(10 + counter * 80, 10);
                    btn.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            if (!innerUnit.isAi()) {
//                            if (!currentUnit.isAi()) {
                                if (ba.action(innerUnit)) {
                                    nextTurn();
                                }
                            }
                        }
                    });
                    if (a instanceof RegenerationAction) {
                        button = btn;
                    }
                    actionPanel.addActor(btn);
                    counter++;
                }
            }
        }
    }

    public void updateMaxScore() {
        int newMaxScore = score;
        if (newMaxScore > EasyArena.data.getMaxScore()) {
            EasyArena.data.setMaxScore(newMaxScore);
        }
    }


    public boolean isHeroTurn() {
        return currentUnit.getAutopilot() == null;
    }

    public void nextTurn() {
        animationTimer = 1.0f;
        for (int i = 0; i < units.size(); i++) {
            if (units.get(i).getActionPanel() != null) {
                units.get(i).getActionPanel().setVisible(false);
            }
        }
        do {
            currentUnitIndex++;
            if (currentUnitIndex >= units.size()) {
                currentUnitIndex = 0;
            }
        } while (!units.get(currentUnitIndex).isAlive());
        currentUnit = units.get(currentUnitIndex);
        currentUnit.getTurn();
        if (currentUnit.getActionPanel() != null) {
            currentUnit.getActionPanel().setVisible(true);
        }
//        animationTimer = 1.0f;
    }

    @Override
    public void render(float dt) {

        update(dt);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.setColor(1, 1, 0, 0.8f);
        batch.draw(textureSelector, currentUnit.getPosition().x + currentUnit.getTexture().getRegionWidth() / 2 - textureSelector.getRegionWidth() / 2, currentUnit.getPosition().y);
        if (isHeroTurn() && currentUnit.getTarget() != null && currentUnit.getTarget().isAlive()) {
            batch.setColor(1, 0, 0, 1);
            batch.draw(textureSelector, currentUnit.getTarget().getPosition().x + currentUnit.getTarget().getTexture().getRegionWidth() / 2 - textureSelector.getRegionWidth() / 2, currentUnit.getTarget().getPosition().y);
        }

        batch.setColor(1, 1, 1, 1);
        for (int i = 0; i < units.size(); i++) {
            units.get(i).render(batch);
            if (units.get(i).isAlive()) {
                units.get(i).renderInfo(batch, font);
            }
            if (units.get(i).getHealPotionPoint() <= 0 && units.get(i).isHealPotionEnable() && !units.get(i).isAi()) {
                units.get(i).getActionPanel().removeActor(button);
                units.get(i).setHealPotionEnable(false);
            }
        }

        infoSystem.render(batch, font);
        specialFXEmitter.render(batch);
        font36.draw(batch, "Score:"+ player2.getTotalScore(), -550, 680, 1280, 1, false);
        batch.end();
        stage.draw();

    }

    public void update(float dt) {

        if (animationTimer > 0.0f) {
            animationTimer -= dt;
        }
        if (isHeroTurn() && canIMakeTurn()) {
            stage.act(dt);
            if (currentUnit.getActionPanel() != null) {
                currentUnit.getActionPanel().setVisible(true);
            }
        }
        if (currentUnit.isAlive()) {
            if (!currentUnit.isStunned()) {
                for (int i = 0; i < units.size(); i++) {
                    units.get(i).update(dt);
                    if (myInputProcessor.isTouchinArea(units.get(i).getRect()) && units.get(i).isAlive()) {
                        currentUnit.setTarget(units.get(i));
                    }
                }
            } else nextTurn();
        } else nextTurn();
        if (!isHeroTurn()) {
            if (currentUnit.isAlive()) {
                if (!currentUnit.isStunned()) {
                    if (currentUnit.getAutopilot().turn(currentUnit)) {
                        nextTurn();
                    }
                } else nextTurn();
            } else nextTurn();
        }
        infoSystem.update(dt);
//        if (myInputProcessor.isTouch()) {
//            specialFX.setup(myInputProcessor.getX(), myInputProcessor.getY());
//        }
        specialFXEmitter.update(dt);

        boolean isEnemyAreDead = true;
        boolean isMyTeamAreDead = true;
        for (int i = 0; i < units.size(); i++) {
            if (units.get(i).isAi() && units.get(i).isAlive()) {
                isEnemyAreDead = false;
                break;
            }
        }
        for (int i = 0; i < player1.getUnits().size(); i++) {
            if (player1.getUnits().get(i).isAlive()) {
                isMyTeamAreDead = false;
                break;
            }
        }
        if (isMyTeamAreDead) {
            if (animationWithoutGameOver > 0) {
                animationWithoutGameOver -= dt;
            }
            if (animationWithoutGameOver < 0) {
                score = player2.getTotalScore();
                updateMaxScore();
                ScreenManager.getInstance().switchScreen(ScreenManager.ScreenType.GAMEOVER);
            }

        }
        if (isEnemyAreDead) {
            if (animationTimer < 0) {
                score = player2.getTotalScore();
                updateMaxScore();
                ScreenManager.getInstance().switchScreen(ScreenManager.ScreenType.NEXTBATTLE);
            }
        }
        stage.act(dt);
    }

    @Override
    public void resize(int width, int height) {
        ScreenManager.getInstance().onResize(width, height);
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

    }
}
