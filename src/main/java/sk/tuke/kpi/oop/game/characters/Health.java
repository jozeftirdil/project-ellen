package sk.tuke.kpi.oop.game.characters;


import java.util.HashSet;
import java.util.Set;

public class Health {

    private int actualHealth;
    private int maxHealth;
    private boolean firstTime;

    private Set<FatigueEffect> effects;

    public Health(int actualHealth, int maxHealth) {
        this.actualHealth = actualHealth;
        this.maxHealth = maxHealth;
        this.effects = new HashSet<>();
        firstTime = false;
    }

    public Health(int actualMaxHealth) {
        this.actualHealth = actualMaxHealth;
        this.maxHealth = actualMaxHealth;
        this.effects = new HashSet<>();
        firstTime = false;
    }

    public int getValue() {
        return actualHealth;
    }

    public void refill(int amount) {
        actualHealth += amount;

        if(actualHealth > maxHealth) {
            actualHealth = maxHealth;
        }
    }

    public void restore() {
        actualHealth = maxHealth;
    }

    public void drain(int amount) {
        actualHealth -= amount;

        if(actualHealth <= 0) {
            actualHealth = 0;
            applyEffects();
        }
    }

    public void exhaust() {
        actualHealth = 0;
        applyEffects();
    }

    @FunctionalInterface
    public interface FatigueEffect {
        void apply();
    }

    public void onFatigued(FatigueEffect effect) {
        effects.add(effect);
    }

    private void applyEffects() {
        if(!firstTime) {
            firstTime = true;

            for(FatigueEffect effect : effects){
                effect.apply();
            }
        }
    }
}
