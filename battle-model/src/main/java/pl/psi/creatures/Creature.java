package pl.psi.creatures;//  ******************************************************************

//
//  Copyright 2022 PSI Software AG. All rights reserved.
//  PSI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms
//
//  ******************************************************************

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;

import lombok.Setter;
import pl.psi.TurnQueue;

import com.google.common.collect.Range;

import lombok.Getter;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
@Getter
public class Creature implements PropertyChangeListener {
    private CreatureStatisticIf stats;
    @Setter
    private int amount;
    private int currentHp;
    private int counterAttackCounter = 1;
    private DamageCalculatorIf calculator;
    private int movementRange;
    @Setter
    private int heroAttackModifier = 0;
    @Setter
    private int heroArmorModifier = 0;
    @Setter
    private int luck = 0;
    @Setter
    private int morale = 0;
    private boolean ExtraTurn = false;

    Creature() {
    }

    private Creature(final CreatureStatisticIf aStats, final DamageCalculatorIf aCalculator,
                     final int aAmount) {
        stats = aStats;
        amount = aAmount;
        currentHp = stats.getMaxHp();
        calculator = aCalculator;
        movementRange = stats.getMoveRange();
    }

    public boolean hasExtraTurn(){
        return ExtraTurn;
    }

    public void setExtraTurn(boolean boo){
        ExtraTurn = boo;
    }

    public float getLuckPercentage(){
        float chance = 0f;
        if (getLuck() > 0){
            chance = (float) getLuck() * 100 / 24;
            if (getLuck() > 3){
                return (float) 300/24;
            }
        }
        return chance;
    }

    public float getMoralePercentage(){
        float chance = 0f;
        if (getMorale() > 0){
            chance = (float) getMorale() * 100 / 24;
            if (getMorale() > 3){
                return (float) 300/24;
            }
        } else if (getMorale() < 0){
            chance = Math.abs((float) getMorale()) * 100 / 12;
            if (getMorale() < -3){
                return (float) 300/12;
            }
        }
        return chance;
    }

    public void decideIfExtraAttack(){
        Random random = new Random();
        float randomFloat = random.nextFloat() * 100;
        System.out.println(randomFloat);
        System.out.println(getMoralePercentage());

        if (randomFloat > getMoralePercentage()){
            setExtraTurn(false);
        } else {
            setExtraTurn(true);
        }
    }

    public void attack(final Creature aDefender) {
        if (isAlive()) {
            int damage = getCalculator().calculateDamage(this, aDefender);

            Random random = new Random();
            float randomFloat = random.nextFloat() * 100;

            if (getLuckPercentage() > randomFloat){
                damage *= 2;
            }
            
            applyDamage(aDefender, damage);
            if (canCounterAttack(aDefender)) {
                counterAttack(aDefender);
            }
            decideIfExtraAttack();
        }
    }

    public boolean isAlive() {
        return getAmount() > 0;
    }

    private void applyDamage(final Creature aDefender, final int aDamage) {
        int hpToSubstract = aDamage % aDefender.getMaxHp();
        int amountToSubstract = Math.round(aDamage / aDefender.getMaxHp());

        int hp = aDefender.getCurrentHp() - hpToSubstract;
        if (hp <= 0) {
            aDefender.setCurrentHp(aDefender.getMaxHp() - hp);
            aDefender.setAmount(aDefender.getAmount() - 1);
        }
        else{
            aDefender.setCurrentHp(hp);
        }
        aDefender.setAmount(aDefender.getAmount() - amountToSubstract);
    }

    public int getMaxHp() {
        return stats.getMaxHp();
    }

    protected void setCurrentHp(final int aCurrentHp) {
        currentHp = aCurrentHp;
    }

    private boolean canCounterAttack(final Creature aDefender) {
        return aDefender.getCounterAttackCounter() > 0 && aDefender.getCurrentHp() > 0;
    }

    private void counterAttack(final Creature aAttacker) {
        final int damage = aAttacker.getCalculator()
                .calculateDamage(aAttacker, this);
        applyDamage(this, damage);
        aAttacker.counterAttackCounter--;
    }

    Range<Integer> getDamage() {
        return stats.getDamage();
    }

    int getAttack() {
        return stats.getAttack() + heroAttackModifier;
    }

    public int getModifiedAttack() {
        return stats.getAttack() + heroAttackModifier;
    }

    int getArmor() {
        return stats.getArmor() + heroArmorModifier;
    }

    public int getModifiedArmor() {
        return stats.getArmor() + heroArmorModifier;
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (TurnQueue.END_OF_TURN.equals(evt.getPropertyName())) {
            counterAttackCounter = 1;
        }
    }

    protected void restoreCurrentHpToMax() {
        currentHp = stats.getMaxHp();
    }

    public String getName() {
        return stats.getName();
    }

    public int getMoveRange() {
        if (hasExtraTurn()){
            return 0;
        } else {
            return movementRange;
        }
    }

    public int getLuck(){
        return luck;
    }

    public int getMorale(){
        return morale;
    }

    public static class Builder {
        private int amount = 1;
        private DamageCalculatorIf calculator = new DefaultDamageCalculator(new Random());
        private CreatureStatisticIf statistic;

        public Builder statistic(final CreatureStatisticIf aStatistic) {
            statistic = aStatistic;
            return this;
        }

        public Builder amount(final int aAmount) {
            amount = aAmount;
            return this;
        }

        Builder calculator(final DamageCalculatorIf aCalc) {
            calculator = aCalc;
            return this;
        }

        public Creature build() {
            return new Creature(statistic, calculator, amount);
        }
    }

    @Override
    public String toString() {
        return getName() + System.lineSeparator() + getAmount();
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }

        if (!(obj instanceof Creature)){
            return false;
        }

        Creature comparedObj = (Creature) obj;

        return (comparedObj.getAmount() == this.getAmount()
                && comparedObj.getStats() == this.getStats()
                && comparedObj.getName() == this.getName()
                && comparedObj.getMorale() == this.getMorale()
                && comparedObj.getLuck() == this.getLuck());
    }
}
