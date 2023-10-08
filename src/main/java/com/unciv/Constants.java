package com.unciv;

import java.util.List;

public final class Constants {

    public static final String settler = "Settler";
    public static final String eraSpecificUnit = "Era Starting Unit";
    public static final String spreadReligion = "Spread Religion";
    public static final String removeHeresy = "Remove Foreign religions from your own cities";

    public static final String english = "English";

    public static final String impassable = "Impassable";
    public static final String ocean = "Ocean";

    /** The "Coast" _terrain_ */
    public static final String coast = "Coast";

    /** The "Coastal" terrain _filter_ */
    public static final String coastal = "Coastal";

    public static final String mountain = "Mountain";
    public static final String hill = "Hill";
    public static final String plains = "Plains";
    public static final String lakes = "Lakes";
    public static final String desert = "Desert";
    public static final String grassland = "Grassland";
    public static final String tundra = "Tundra";
    public static final String snow = "Snow";

    public static final String forest = "Forest";
    public static final String jungle = "Jungle";
    public static final String ice = "Ice";
    List<String> vegetation = List.of(forest, jungle);

    // Note the difference in case. **Not** interchangeable!
    // TODO this is very opaque behaviour to modders
    /** The "Fresh water" terrain _unique_ */
    public static final String freshWater = "Fresh water";

    /** The "Fresh Water" terrain _filter_ */
    public static final String freshWaterFilter = "Fresh Water";

    public static final String barbarianEncampment = "Barbarian encampment";
    public static final String cityCenter = "City center";

    public static final String peaceTreaty = "Peace Treaty";
    public static final String researchAgreement = "Research Agreement";
    public static final String openBorders = "Open Borders";
    public static final String defensivePact = "Defensive Pact";

    /** Used as origin in StatMap or ResourceSupplyList, or the toggle button in DiplomacyOverviewTab */
    public static final String cityStates = "City-States";

    /** Used as origin in ResourceSupplyList */
    public static final String tradable = "Tradable";

    public static final String random = "Random";
    public static final String unknownNationName = "???";
    public static final String unknownCityName = "???";

    public static final String fort = "Fort";

    public static final String futureTech = "Future Tech";

    // Easter egg name. Is to avoid conflicts when players name their own religions.
    // This religion name should never be displayed.
    public static final String noReligionName = "The religion of TheLegend27";
    public static final String spyHideout = "Spy Hideout";

    public static final String neutralVictoryType = "Neutral";

    public static final String cancelImprovementOrder = "Cancel improvement order";
    public static final String tutorialPopupNamePrefix = "Tutorial: ";

    public static final String OK = "OK";
    public static final String close = "Close";
    public static final String yes = "Yes";
    public static final String no = "No";
    public static final String loading = "Loading...";
    public static final String working = "Working...";

    public static final String barbarians = "Barbarians";
    public static final String spectator = "Spectator";

    public static final String embarked = "Embarked";
    public static final String wounded = "Wounded";


    public static final String rising = "Rising";
    public static final String lowering = "Lowering";
    public static final String remove = "Remove ";
    public static final String repair = "Repair";

    public static final String uniqueOrDelimiter = "\" OR \"";

    public static final String dropboxMultiplayerServer = "Dropbox";
    public static final String uncivXyzServer = "https://uncivserver.xyz";

    public static final String defaultTileset = "HexaRealm";

    /** Default for TileSetConfig.fallbackTileSet - Don't change unless you've also moved the crosshatch, borders, and arrows as well */
    public static final String defaultFallbackTileset = "FantasyHex";
    public static final String defaultUnitset = "AbsoluteUnits";
    public static final String defaultSkin = "Minimal";

    /**
     * Use this to determine whether a [MapUnit][com.unciv.logic.map.MapUnit]'s movement is exhausted
     * (currentMovement <= this) if and only if a fuzzy comparison is needed to account for Float rounding errors.
     * _Most_ checks do compare to 0!
     */
    public static final float minimumMovementEpsilon = 0.05f;  // 0.1f was used previously, too - here for global searches
    public static final float aiPreferInquisitorOverMissionaryPressureDifference = 3000f;

    public static int defaultFontSize = 18;
    public static int headingFontSize = 24;
}
