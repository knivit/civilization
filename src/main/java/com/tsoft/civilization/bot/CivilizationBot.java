package com.tsoft.civilization.bot;

import com.tsoft.civilization.bot.strategy.military.BotAttackStrategy;
import com.tsoft.civilization.bot.strategy.military.BotDefendStrategy;
import com.tsoft.civilization.bot.strategy.economics.*;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.PlayerType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CivilizationBot implements Runnable {

    private BotAttackStrategy attackStrategy;
    private BotDefendStrategy defendStrategy;
    private BotImproveStrategy improveStrategy;
    private BotExploreStrategy exploreStrategy;
    private BotSettleStrategy settleStrategy;
    private BotProductionStrategy productionStrategy;
    private BotPurchaseStrategy purchaseStrategy;

    private final Civilization civilization;

    private Thread worker;

    public static CivilizationBot create(Civilization civilization) {
        CivilizationBot bot = new CivilizationBot(civilization);
        bot.init();
        return bot;
    }

    private CivilizationBot(Civilization civilization) {
        this.civilization = civilization;
    }

    private void init() {
        attackStrategy = new BotAttackStrategy(civilization);
        defendStrategy = new BotDefendStrategy(civilization);
        improveStrategy = new BotImproveStrategy(civilization);
        exploreStrategy = new BotExploreStrategy(civilization);
        settleStrategy = new BotSettleStrategy(civilization);
        productionStrategy = new BotProductionStrategy(civilization);
        purchaseStrategy = new BotPurchaseStrategy(civilization);
    }

    private void calculate() {
        economics();
        military();
        science();
        culture();
        faith();
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

    private void science() {

    }

    private void culture() {

    }

    private void faith() {

    }

    public void startYear() {
        worker = new Thread(this);
        worker.start();
    }

    @Override
    public void run() {
        calculate();

        // If this is a bot (fully managed civilization, not just a human's helper)
        // do a next turn
        if (PlayerType.BOT.equals(civilization.getPlayerType())) {
            civilization.stopYear();
        }
    }

    // Use this method to wait till the helper bot is done
    public void join() {
        if (!PlayerType.BOT.equals(civilization.getPlayerType())) {
            try {
                worker.join();
            } catch (Exception ex) {
                throw new IllegalStateException(ex);
            }
        }
    }
}
