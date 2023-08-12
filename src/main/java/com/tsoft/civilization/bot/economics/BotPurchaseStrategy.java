package com.tsoft.civilization.bot.economics;

import com.tsoft.civilization.civilization.Civilization;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BotPurchaseStrategy {

    private final Civilization civilization;

    public void process() {
        buyTile();
        buyUnit();
        buyBuilding();
    }

    private void buyUnit() {

    }

    private void buyBuilding() {

    }

    private void buyTile() {

    }
}
