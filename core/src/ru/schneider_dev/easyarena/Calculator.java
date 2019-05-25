package ru.schneider_dev.easyarena;

public class Calculator {

    private static final int MIN_MELEE_ATTACK_CHANCE = 20;
    private static final int MAX_MELEE_ATTACK_CHANCE = 95;


    public static int getMeleeDamage(Unit attacker, Unit target) {
        int dmg = attacker.getStats().getStrength() - target.getStats().getDefence();
        dmg = (int) (dmg * 0.8f + (float) dmg * Math.random() * 0.6f);
        if (dmg < 0) {
            dmg = 0;
        }
        return dmg;
    }

    public static int getPosionMeleeDamage(Unit attacker, Unit target) {

        int dmg = attacker.getStats().getStrength() - target.getStats().getDefence();
        dmg = (int) (dmg * 0.8f + (float) dmg * Math.random() * 0.6f);
        if (dmg < 0) {
            dmg = 0;
        }
        return dmg;
    }

    public static int getArcherDamage(Unit attacker, Unit target) {
        int dmg = attacker.getStats().getAgility() - target.getStats().getDefence();
        dmg = (int) (dmg * 0.8f + (float) dmg * Math.random() * 0.6f);
        if (dmg < 0) {
            dmg = 0;
        }
        return dmg;
    }

    public static int getMagicDamage(Unit attacker, Unit target) {
        int dmg = attacker.getStats().getSpellPower();
        dmg = (int) (dmg * 0.8f + (float) dmg * Math.random() * 0.2f);
        if (dmg < 0) {
            dmg = 0;
        }
        return dmg;
    }

    public static boolean isTargetEvaded(Unit attacker, Unit target) {
        int attackChance = (int) (85.0f + ((attacker.getStats().getAgility() - target.getStats().getAgility()) * 0.2f + (attacker.getStats().getLevel() - target.getStats().getLevel()) * 1.0f));
        if (attackChance > MAX_MELEE_ATTACK_CHANCE) attackChance = MAX_MELEE_ATTACK_CHANCE;
        if (attackChance < MIN_MELEE_ATTACK_CHANCE) attackChance = MIN_MELEE_ATTACK_CHANCE;
        return Math.random() * 100 > attackChance;
    }
}
