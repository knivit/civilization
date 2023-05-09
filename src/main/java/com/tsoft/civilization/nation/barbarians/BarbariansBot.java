package com.tsoft.civilization.nation.barbarians;

import com.tsoft.civilization.bot.military.BotAttackStrategy;
import com.tsoft.civilization.bot.economics.BotPurchaseStrategy;
import com.tsoft.civilization.bot.military.BotDefendStrategy;
import com.tsoft.civilization.bot.economics.BotExploreStrategy;
import com.tsoft.civilization.bot.economics.BotImproveStrategy;
import com.tsoft.civilization.bot.economics.BotSettleStrategy;
import com.tsoft.civilization.bot.economics.BotProductionStrategy;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.bot.CivilizationBot;
import com.tsoft.civilization.world.World;

public class BarbariansBot extends CivilizationBot {

    private final BotAttackStrategy attackStrategy = new BotAttackStrategy();
    private final BotDefendStrategy defendStrategy = new BotDefendStrategy();
    private final BotImproveStrategy improveStrategy = new BotImproveStrategy();
    private final BotExploreStrategy exploreStrategy = new BotExploreStrategy();
    private final BotSettleStrategy settleStrategy = new BotSettleStrategy();
    private final BotProductionStrategy productionStrategy = new BotProductionStrategy();
    private final BotPurchaseStrategy purchaseStrategy = new BotPurchaseStrategy();

    public BarbariansBot(World world, Civilization civilization) {
        super(world, civilization);
    }

    @Override
    protected void calculate() {
        economics();
        military();
    }

    private void economics() {
        exploreStrategy.explore();
        improveStrategy.improve();
        settleStrategy.settle();
        productionStrategy.build();
        purchaseStrategy.process();
    }

    private void military() {
        defendStrategy.defend();
        attackStrategy.attack();
    }
}
