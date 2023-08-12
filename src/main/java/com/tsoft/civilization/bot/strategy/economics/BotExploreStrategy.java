package com.tsoft.civilization.bot.strategy.economics;

import com.tsoft.civilization.civilization.Civilization;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BotExploreStrategy {

    private final Civilization civilization;

    // demands

    // - incoming demands from other strategies to this strategy (explore to find out ruins, other civilizations etc)
    // - outcome demands from this strategy to other strategies (build a scout for the production strategy etc)

    public void explore() {

    }
}
