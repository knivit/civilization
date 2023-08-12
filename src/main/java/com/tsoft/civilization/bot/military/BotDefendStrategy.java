package com.tsoft.civilization.bot.military;

import com.tsoft.civilization.civilization.Civilization;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BotDefendStrategy {

    private final Civilization civilization;

    public void defend() {
        defendCities();
        defendMilitaryUnits();
        defendCivilUnits();
    }

    private void defendCivilUnits() {
    }

    private void defendMilitaryUnits() {

    }

    private void defendCities() {

    }
}
