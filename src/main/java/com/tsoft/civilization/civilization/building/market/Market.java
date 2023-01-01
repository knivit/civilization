package com.tsoft.civilization.civilization.building.market;

import com.tsoft.civilization.civilization.building.*;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.Year;

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
 *   +1 Gold per incoming Trade Route (also +1 Gold Gold for the owner of the route)
 *   +1 Science with Mercantilism Social policy
 *
 * Strategy
 * The Market is the first money-boosting building, significantly increasing a city's output of gold.
 * It usually becomes available at a moment in the civilization's development when it's short on gold
 * (you've accumulated buildings and units, you're starting to build your road system, but aren't trading
 * with other civilizations much yet) so it is important to research Currency and start building it in your
 * cities to overcome the shortage.
 *
 * Build it first in cities with good Gold output - this way the 25% boost will have greater effect earlier on.
 * It also allows the creation of one Merchant specialist, increasing the speed at which Great Merchants appear.
 * You need a Market in every city to unlock the National Treasury (a Wonder which is replaced by the East India Company in Brave New World).
 * In Brave New World the Market's general purpose changes a bit, becoming a booster for international trade.
 * The Market becomes more effective as the number of Trade Routes passing through a city increases,
 * so when prioritizing where to build it first, check and see which of your cities have the most Trade Routes with other civilizations.
 *
 * Civilopedia entry
 *
 * A market is a location where farmers and tradesmen and merchants bring their wares to sell. While the earliest
 * and most primitive markets may have operated under a barter system, a truly successful market requires a working
 * trusted currency to allow for the free exchange of goods and services. For obvious reasons, markets are located
 * where the customers are, in villages, towns and cities. Smithfield has been a meat-trading market in London
 * for over a thousand years, which helps to explain some of the street names like, "Cow Cross Street."
 */
public class Market extends AbstractBuilding {

    public static final String CLASS_UUID = BuildingType.MARKET.name();

    private static final BuildingBaseState BASE_STATE = BuildingCatalog.getBaseState(BuildingType.MARKET);

    private static final AbstractBuildingView VIEW = new MarketView();

    public Market(City city) {
        super(city);
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }

    @Override
    public BuildingBaseState getBaseState() {
        return BASE_STATE;
    }

    @Override
    public AbstractBuildingView getView() {
        return VIEW;
    }

    @Override
    public boolean checkEraAndTechnology(Civilization civilization) {
        return civilization.getYear().getEra() == Year.ANCIENT_ERA;
    }

    @Override
    public boolean requiresEraAndTechnology(Civilization civilization) {
        return civilization.getYear().isAfter(Year.CLASSICAL_ERA) &&
            civilization.isResearched(Technology.CURRENCY);
    }
}
