package com.tsoft.civilization.civilization.building.market;

import com.tsoft.civilization.civilization.building.AbstractBuilding;
import com.tsoft.civilization.civilization.building.BuildingType;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.Year;
import com.tsoft.civilization.economic.Supply;
import lombok.Getter;

import java.util.UUID;

/**
 * Market
 *
 * Cost
 * ----
 * Production	Maintenance
 * 120 Production	0 Gold
 * Required Technology: Currency
 * Specialists Type: Merchant
 *
 * Effects
 *   +25% Gold
 *   +1 Gold
 *   +1 Gold per incoming trade route (+1 for the owner of the trade route)
 *
 * Game Info
 * Basic Gold-boosting building of the Classical Era.
 *
 * Common traits:
 *   +25% Gold
 *   +2 Gold
 *    1 Merchant specialist slot
 *   +1 Gold Gold per incoming Trade Route (also +1 Gold Gold for the owner of the route)
 *   +1 Science Science with Mercantilism Social policy
 *
 * Strategy
 * The Market is the first money-boosting building, significantly increasing a city's output of gold.
 * It usually becomes available at a moment in the civilization's development when it's short on gold
 * (you've accumulated buildings and units, you're starting to build your road system, but aren't trading
 * with other civilizations much yet) so it is important to research Currency and start building it in your
 * cities to overcome the shortage.
 *
 * Build it first in cities with good Gold Gold output - this way the 25% boost will have greater effect earlier on.
 * It also allows the creation of one Merchant specialist, increasing the speed at which Great Merchants appear.
 * You need a Market in every city to unlock the National Treasury (a Wonder which is replaced by the East India Company in Brave New World).
 * In Brave New World the Market's general purpose changes a bit, becoming a booster for international trade.
 * The Market becomes more effective as the number of Trade Routes passing through a city increases,
 * so when prioritizing where to build it first, check and see which of your cities have the most Trade Routes with other civilizations.
 *
 * Civilopedia entry
 * A market is a location where farmers and tradesmen and merchants bring their wares to sell. While the earliest
 * and most primitive markets may have operated under a barter system, a truly successful market requires a working
 * trusted currency to allow for the free exchange of goods and services. For obvious reasons, markets are located
 * where the customers are, in villages, towns and cities. Smithfield has been a meat-trading market in London
 * for over a thousand years, which helps to explain some of the street names like, "Cow Cross Street."
 */
public class Market extends AbstractBuilding {

    public static final String CLASS_UUID = UUID.randomUUID().toString();
    private static final MarketView VIEW = new MarketView();

    @Getter
    private int baseProductionCost = 120;

    @Getter
    private final int cityDefenseStrength = 0;

    @Getter
    private final int localHappiness = 0;

    @Getter
    private final int globalHappiness = 0;

    @Getter
    private Supply supply = Supply.EMPTY;

    @Override
    public BuildingType getBuildingType() {
        return BuildingType.BUILDING;
    }

    public Market(City city) {
        super(city);
    }

    @Override
    public boolean checkEraAndTechnology(Civilization civilization) {
        return civilization.getYear().getEra() == Year.ANCIENT_ERA;
    }

    /**
     * The Market significantly increases a city's output of gold.
     */
    @Override
    public Supply calcIncomeSupply(Civilization civilization) {
        Supply tileScore = getCity().calcTilesSupply();
        int gold = tileScore.getGold();
        if (gold > 0) {
            gold = (int) Math.round(gold * 0.25);
            if (gold == 0) gold = 1;
        }

        return Supply.builder().gold(2 + gold).build();
    }

    @Override
    public Supply calcOutcomeSupply(Civilization civilization) {
        return Supply.EMPTY;
    }

    @Override
    public void startYear() {

    }

    @Override
    public void stopYear() {

    }

    @Override
    public boolean requiresEraAndTechnology(Civilization civilization) {
        return civilization.getYear().isAfter(Year.CLASSICAL_ERA) &&
            civilization.isResearched(Technology.CURRENCY);
    }

    @Override
    public int getGoldCost(Civilization civilization) {
        return 580;
    }

    @Override
    public MarketView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
