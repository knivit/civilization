package com.tsoft.civilization.world;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.util.Point;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;

/**
 * Map size
 * --------
 * As mentioned above, Civilization V separates maps into six distinct sizes. The size of a map determines the default numbers
 * of players and city-states and the number of natural wonders in the game, as shown in the table below.
 * It also affects penalties related to warmongering. These are the default sizes for most maps, but individual maps can use
 * different sizes. Of the official maps, Tilted Axis and Terra have the largest dimensions, while Four Corners, North vs South,
 * and West vs East have the smallest.
 *
 *                                  Duel    Tiny    Small Standard Large    Huge
 * Dimensions	                    40×24	56×36	66×42	80×52	104×64	128×80
 * Players (Default)	                2	    4	    6	    8	    10	    12
 * City-States (Default)	            4	    8	    12	    16	    20	    24
 * Natural Wonders	                    2	    3	    3	    4	    6	    7
 * Maximum active Religion Religions	2	    3	    4	    5	    6	    7
 * Unhappiness per city	                +3	    +3	    +3	    +3	    +2.4	+1.8
 * Unhappiness per annexed city	        +5	    +5	    +5	    +5	    +4	    +3
 * Tech cost modifier*	                100%	100%	100%	110%	120%	130%
 * Tech cost per city**	                +5%	    +5%	    +5%	    +5%	    +3.75%	+2.5%
 * Policy cost per city**	            +10%	+10%	+10%	+10%	+7.5%	+5%
 * * Multiplicative
 * ** Additive
 */
public enum MapSize {

    DUEL(L10nWorld.MAP_SIZE_DUEL),
    TINY(L10nWorld.MAP_SIZE_TINY),
    SMALL(L10nWorld.MAP_SIZE_SMALL),
    STANDARD(L10nWorld.MAP_SIZE_STANDARD),
    LARGE(L10nWorld.MAP_SIZE_LARGE),
    HUGE(L10nWorld.MAP_SIZE_HUGE),
    CUSTOM(L10nWorld.MAP_SIZE_CUSTOM);

    @Getter
    private final L10n l10n;

    MapSize(L10n l10n) {
        this.l10n = l10n;
    }

    public static final Map<MapSize, Point> MAP_SIZE = Map.of(
        DUEL, new Point(40, 24),
        TINY, new Point(56, 36),
        SMALL, new Point(66, 42),
        STANDARD, new Point(80, 52),
        LARGE, new Point(104, 64),
        HUGE, new Point(128, 80)
    );

    public static final Map<MapSize, Integer> MAX_NUMBER_OF_CIVILIZATIONS = Map.of(
        DUEL, 2,
        TINY, 4,
        SMALL, 6,
        STANDARD, 8,
        LARGE, 10,
        HUGE, 12
    );

    public int getWidth() {
        return MAP_SIZE.get(this).getX();
    }

    public int getHeight() {
        return MAP_SIZE.get(this).getY();
    }

    public int getMaxNumberOfCivilizations() {
        return MAX_NUMBER_OF_CIVILIZATIONS.get(this);
    }

    public static MapSize find(String name) {
        return Arrays.stream(MapSize.values())
            .filter(e -> e.name().equalsIgnoreCase(name))
            .findAny()
            .orElse(null);
    }
}
