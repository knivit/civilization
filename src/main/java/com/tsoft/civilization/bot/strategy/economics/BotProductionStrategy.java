package com.tsoft.civilization.bot.strategy.economics;

import com.tsoft.civilization.civilization.Civilization;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BotProductionStrategy {

    private final Civilization civilization;

    public void build() {
        buildBuildings();
        buildWonders();
        buildUnits();
    }

    private void buildUnits() {

    }

    private void buildWonders() {

    }

    private void buildBuildings() {

    }
}
