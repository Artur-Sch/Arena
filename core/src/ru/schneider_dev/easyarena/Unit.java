package ru.schneider_dev.easyarena;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;

import ru.schneider_dev.easyarena.actions.BaseAction;
import ru.schneider_dev.easyarena.effects.Effect;
import ru.schneider_dev.easyarena.screens.BattleScreen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Unit implements Serializable {

    public enum AnimationType {
        IDLE(0), ATTACK(1), HURT(2), DIE(3);
        int number;

        AnimationType(int number) {
            this.number = number;
        }
    }


    transient private BattleScreen battleScreen;
    transient private Unit target;
    private Hero hero;
    transient private TextureRegion texture;
    transient private TextureRegion currentFrame;
    transient private TextureRegion textureHp;
    private UnitFactory.UnitType unitType;
    private int hp;
    private int maxHp;
    private int level;
    private Rectangle rect;
    private Autopilot autopilot;
    private boolean flip;
    private float attackAction;
    private float takeDamageAction;
    private float takeHealAction;
    private Stats stats;
    transient private Group actionPanel;
    private Vector2 position;
    private List<Effect> effects;
    transient private List<BaseAction> actions;
    private AnimationType currentAnimation;
    transient private TextureRegion[][] frames;
    private float animationTime;
    private float sleepTimer;
    private float frameSpeed;
    private float maxTime;
    private int maxFrame;
    private int maxAnimationType;
    private int animationFrame;
    private int healPotionPoint;
    private int stunCounter;
    private boolean isStunned;
    public int scorepoint;

    public boolean isHealPotionEnable() {
        return healPotionEnable;
    }

    public void setHealPotionEnable(boolean healPotionEnable) {
        this.healPotionEnable = healPotionEnable;
    }

    private boolean healPotionEnable;

    public UnitFactory.UnitType getUnitType() {
        return unitType;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public void setTexture(TextureRegion texture) {
        this.texture = texture;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Autopilot getAutopilot() {
        return autopilot;
    }

    public void setAutopilot(Autopilot autopilot) {
        this.autopilot = autopilot;
    }

    public void setActionPanel(Group actionPanel) {
        this.actionPanel = actionPanel;
    }

    public void setFlip(boolean flip) {
        this.flip = flip;
    }

    public void setAttackAction(float attackAction) {
        this.attackAction = attackAction;
    }

    public Unit getTarget() {
        return target;
    }

    public BattleScreen getBattleScreen() {
        return battleScreen;
    }

    public void setTarget(Unit target) {
        this.target = target;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public Stats getStats() {
        return stats;
    }

    public Group getActionPanel() {
        return actionPanel;
    }

    public List<BaseAction> getActions() {
        return actions;
    }

    public void setActions(List<BaseAction> actions) {
        this.actions = actions;
    }

    public Rectangle getRect() {
        return rect;
    }

    public Vector2 getPosition() {
        return position;
    }

    public TextureRegion getTextureHp() {
        return textureHp;
    }

    public void setCurrentAnimation(AnimationType currentAnimation) {
        this.currentAnimation = currentAnimation;
    }

    public TextureRegion[][] getFrames() {
        return frames;
    }

    public boolean isAi() {
        return autopilot != null;
    }

    public boolean isMyTeammate(Unit another) {
        return this.hero == another.hero;
    }

    public int getHealPotionPoint() {
        return healPotionPoint;
    }

    public void setHealPotionPoint(int healPotionPoint) {
        this.healPotionPoint = healPotionPoint;
    }

    public int getHp() {
        return hp;
    }

    public boolean isStunned() {
        return this.isStunned;
    }

    public void setStunned(boolean stunned) {
        isStunned = stunned;
    }

    public int getStunCounter() {
        return stunCounter;
    }

    public void setStunCounterWhith(int count) {
        this.stunCounter += count;
    }

    public Unit(UnitFactory.UnitType unitType, TextureRegion[][] frames, Stats stats) {
        this.unitType = unitType;
        this.effects = new ArrayList<Effect>();
        this.actions = new ArrayList<BaseAction>();
        this.position = new Vector2(0, 0);
        this.texture = frames[0][0];
        this.stats = stats;
        this.textureHp = Assets.getInstance().getAtlas().findRegion("hpBar");
        this.frames = frames;
        this.maxTime = 0.8f;
        this.maxFrame = this.frames[0].length;
        this.frameSpeed = maxTime / maxFrame;
        this.currentAnimation = AnimationType.IDLE;
        this.maxAnimationType = this.frames.length - 1;
        this.healPotionPoint = 2;
        this.healPotionEnable = true;
        this.isStunned = false;
        this.stunCounter = 0;
        this.scorepoint = stats.getScorePoints();
    }

    public void reload(TextureRegion[][] frames, List<BaseAction> actions) {
        this.textureHp = Assets.getInstance().getAtlas().findRegion("hpBar");
        this.frames = frames;
        this.texture = frames[0][0];
        this.actions = actions;
        this.effects.clear();
        this.maxTime = 0.7f;
        this.maxFrame = this.frames[0].length;
        this.frameSpeed = maxTime / maxFrame;
        this.currentAnimation = AnimationType.IDLE;
        this.maxAnimationType = this.frames.length - 1;

    }

    public void recalculateSecondaryStats() {
        this.maxHp = 5 * stats.getEndurance();
        this.hp = this.maxHp;
    }

    public boolean isFlip() {
        return flip;
    }

    public void setToMap(BattleScreen battleScreen, int cellX, int cellY) {
        this.battleScreen = battleScreen;
        this.position.set(battleScreen.getStayPoints()[cellX][cellY]);
        this.rect = new Rectangle(position.x, position.y, texture.getRegionWidth() - 15, texture.getRegionHeight() - 20);
    }

    public void evade() {
        battleScreen.getInfoSystem().addMsg("MISS", this, FlyingText.Colors.WHITE);
    }

    public void render(SpriteBatch batch) {
        if (takeDamageAction > 0) {
            batch.setColor(1f, 1f - takeDamageAction, 1f - takeDamageAction, 1f);
        }
        if (takeHealAction > 0) {
            batch.setColor(1f - takeHealAction, 1f, 1f - takeHealAction, 1f);
        }
        float dx = (30f * (float) Math.sin((1f - attackAction) * 3.14f));
//        перевернуть изображение
        if (flip) {
            dx *= -1;
        }
        if (!isAlive()) {
            currentAnimation = AnimationType.DIE;

/***
 * сюда надо дописать пополнение счета
 */

        }
        int n = currentAnimation.number;
        if (n > maxAnimationType) {
            n = 0;
        }
        currentFrame = frames[n][animationFrame];
        if (!isAlive() && sleepTimer < 0.0f) {
            currentFrame = frames[frames.length - 1][frames[frames.length - 1].length - 1];
        }
        if (flip) {
            currentFrame.flip(true, false);
        }
        batch.draw(currentFrame, position.x + dx, position.y, 0, 0, texture.getRegionWidth(), texture.getRegionHeight(), 1, 1, 0);
        if (flip) {
            currentFrame.flip(true, false);
        }
        batch.setColor(1f, 1f, 1f, 1f);
    }

    public void renderInfo(SpriteBatch batch, BitmapFont font) {
        int heightAdjustmentHpBar;
        batch.setColor(0.5f, 0, 0, 1);
        if (unitType == UnitFactory.UnitType.ORK_POISON) {
            heightAdjustmentHpBar = 25;
        } else if (unitType == UnitFactory.UnitType.TROLL_STUN) {
            heightAdjustmentHpBar = 60;
        } else {
            heightAdjustmentHpBar = 15;
        }
        batch.draw(textureHp, position.x + texture.getRegionWidth() / 2 - textureHp.getRegionWidth() / 2, position.y + texture.getRegionHeight() - heightAdjustmentHpBar);
        batch.setColor(0, 1, 0, 1);
        batch.draw(textureHp, position.x + texture.getRegionWidth() / 2 - textureHp.getRegionWidth() / 2, position.y + texture.getRegionHeight() - heightAdjustmentHpBar, (int) (((float) hp / (float) maxHp) * textureHp.getRegionWidth()), 20);
        batch.setColor(1, 1, 1, 1);
        font.draw(batch, String.valueOf(hp), position.x + texture.getRegionWidth() / 2 - textureHp.getRegionWidth() / 2, position.y + 18 + texture.getRegionHeight() - heightAdjustmentHpBar, textureHp.getRegionWidth(), 1, false);

        for (int i = effects.size() - 1; i >= 0; i--) {
            effects.get(i).render(batch, (i * 20));
        }
    }

    public void update(float dt) {
        animationTime += dt;
//        if (animationTime > maxTime) {
//            animationTime = 0;
//        }
        animationFrame = (int) (animationTime / frameSpeed);
        animationFrame = animationFrame % maxFrame;
        if (!isAlive()) {
            sleepTimer -= dt;
        }
        if (takeDamageAction > 0) {
            takeDamageAction -= dt;
            if (takeDamageAction <= 0) {
                currentAnimation = AnimationType.IDLE;
            }
        }
        if (takeHealAction > 0) {
            takeHealAction -= dt;
        }
        if (attackAction > 0) {
            attackAction -= dt;
            if (attackAction <= 0) {
                currentAnimation = AnimationType.IDLE;
            }
        }
    }

    public void getTurn() {
        for (int i = effects.size() - 1; i >= 0; i--) {
            effects.get(i).tick();
            if (effects.get(i).isEnded()) {
                effects.get(i).end();
                effects.remove(i);
            }
        }
    }

    public void changeHp(int value) {
        int prevHp = hp;
        hp += value;
        if (hp > maxHp) {
            hp = maxHp;
        }
        if (hp < 0) {
            hp = 0;
            sleepTimer = 0.5f;
            hero.setTotalScore(hero.getTotalScore() + scorepoint);
        }
        if (value > 0) {
            takeHealAction = 1.0f;
//            battleScreen.getInfoSystem().addMsg("HP +" + (hp - prevHp), this, FlyingText.Colors.GREEN);
        } else if (value < 0) {
            takeDamageAction = 1.0f;
            currentAnimation = AnimationType.HURT;
            battleScreen.getInfoSystem().addMsg("HP -" + (prevHp - hp), this, FlyingText.Colors.RED);
        } else {
            battleScreen.getInfoSystem().addMsg("0", this, FlyingText.Colors.RED);
        }
    }

    public void addEffect(Effect effect) {
        effects.add(effect);
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void setLevelTo(int levelTo) {
        this.level = levelTo;
        this.stats.recalculate(levelTo);
        this.recalculateSecondaryStats();
    }
}
