package com.tsoft.civilization.civilization.building.catalog.monument;

import com.tsoft.civilization.civilization.building.*;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.Year;

/**
 * Monument
 *
 * Building of the Ancient Era
 * Cost
 * ----
 * Production	Maintenance
 * 40 Production	1 Gold
 *
 * Effects
 * +2 Culture
 *
 * Basic culture building of the Ancient Era. Available from the start of the game.
 *
 * Common traits:
 * +2 Culture
 * +2 Happiness with Socialist Realism Order tenet
 *
 * The Monument increases the Culture of a city, speeding the growth of the city's territory and
 * the civilization's acquisition of Social Policies. It's the first building available for every city,
 * without having to research any technology.
 *
 * Note that under normal circumstances (that is, without any social policies or civilization modifiers) a settled city
 * starts without any Culture, which means its borders won't grow AT ALL. If border growth is needed for a city,
 * the easiest way to get the culture requires is to build the Monument – hence the provision of one from Legalism,
 * which saves a lot of time and production.
 *
 * There are alternatives to building culture buildings like the Monument; the opening bonus of the two early
 * social policy trees is similar in effect to those of a Monument. Cities may also gain some from Wonders,
 * including the Palace as a national wonder in the Capital. However, the Monument is vital for continuing
 * the chain of cultural buildings: it is required for the Amphitheater in the expansions (or the Temple in vanilla Civilization V).
 *
 * With the help of alternative sources of culture and if enough early social policies have been obtained,
 * it may be possible to sell the Monument midway through the game in order to temporarily save Gold.
 * If one chooses to sell one's Monuments, they can be rebuilt later when players will have more funds to invest
 * (such as after discovering Currency) and will soon have access to game-changing Social Policies
 * (such as those of the Rationalism tree).
 *
 * Civilopedia entry
 *
 * A monument is a structure built to commemorate an important person, event, deity, or concept.
 * The more important monuments are usually constructed near the center of the city, by the ruler's palace,
 * or near the city's main gates. Monuments come in all shapes and sizes, from the Great Sphinx of Giza, Egypt,
 * to the Statue of Liberty in New York City, USA, to Nelson's Column in London, England.
 * The best of them imbue the city's inhabitants with a great feeling of civic and national pride.
 */
public class Monument extends AbstractBuilding {

    public static final String CLASS_UUID = BuildingType.MONUMENT.name();

    private static final BuildingBaseState BASE_STATE = new MonumentBaseState().getBaseState();

    private static final AbstractBuildingView VIEW = new MonumentView();

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
    public boolean requiredEraAndTechnology(Civilization civilization) {
        return civilization.getYear().getEra() == Year.ANCIENT_ERA;
    }
}
