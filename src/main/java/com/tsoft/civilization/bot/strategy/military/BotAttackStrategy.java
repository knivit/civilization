package com.tsoft.civilization.bot.strategy.military;

import com.tsoft.civilization.civilization.Civilization;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BotAttackStrategy {

    private final Civilization civilization;

    public void attack() {
        attackCities();
        attackMilitaryUnits();
        attackCivilUnits();
    }

    private void attackCivilUnits() {

    }

    private void attackMilitaryUnits() {

    }

    private void attackCities() {

    }
}
