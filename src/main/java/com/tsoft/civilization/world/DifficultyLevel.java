package com.tsoft.civilization.world;

import com.tsoft.civilization.util.l10n.L10n;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * In Vanilla and Gods & Kings
 * ---------------------------
 * Players receive gameplay bonuses that diminish as the chosen difficulty level increases. The AI plays on Chieftain
 * difficulty and receives all of the appropriate bonuses.
 *
 * Element	                                Settler	Chieftain	Warlord	Prince	King	Emperor	Immortal	Deity
 * Advanced Start Points*	                150%	130%	    110%	100%	90%	    85%	    80%	    75%
 * Base Happiness	                        15	    12	        12	    9	    9	    9	    9	    9
 * Extra Happiness Per Luxury	            1	    1	        0	    0	    0	    0	    0	    0
 * Unhappiness Per City	                    40%	    60%	        75%	    100%	100%	100%	100%	100%
 * Unhappiness Per Population	            40%	    60%	        75%	    100%	100%	100%	100%	100%
 * Production Free Units	                10	    7	        7	    5	    5	    5	    5	    5
 * Production Free Units Per City	        3	    3	        2	    2	    2	    2	    2	    2
 * Route Cost	                            34%	    50%	        75%	    100%	100%	100%	100%	100%
 * Unit Cost	                            50%	    67%	        85%	    100%	100%	100%	100%	100%
 * Building Cost	                        50%	    67%	        85%	    100%	100%	100%	100%	100%
 * Research Cost	                        90%	    95%	        100%	100%	100%	100%	100%	100%
 * Policy Cost	                            50%	    67%	        85%	    100%	100%	100%	100%	100%
 * City Production Num Options Considered	10	    4	        3	    2	    2	    2	    2	    2
 * Tech Num Options Considered	            10	    4	        3	    2	    2	    2	    2	    2
 * Policy Num Options Considered	        10	    4	        3	    2	    2	    2	    2	    2
 * Attitude Change*	                        2	    1	        0	    -1	    -1	    -1	    -1	    -1
 * No Tech Trade Modifier*	                100%	90%	        80%	    70%	    50%	    40%	    30%	    20%
 * Tech Trade Known Modifier*	            -100%	-50%	    -25%	0%	    0%	    0%	    0%	    0%
 * Barb Camp Gold	                        50	    40	        30	    25	    25	    25	    25	    25
 * Barb Spawn Mod	                        8	    5	        3	    0	    0	    0	    0	    0
 * Barbarian Bonus	                        75	    50	        40	    33	    25	    20	    10	    0
 * Turn Barbarians Can Enter Player Land	10,000	60	        20	    0	    0	    0	    0	    0
 * Barbarian Land Target Range	            2	    3	        4	    5	    5	    6	    7	    8
 * Barbarian Sea Target Range	            4	    6	        8	    10	    12	    15	    18	    20
 * * This element is either a holdover from Civilization IV or is never used in the code, so it has no effect on gameplay.
 *
 * AI Bonuses
 * The player's chosen difficulty level also affects the AI leaders. The gameplay bonuses they receive from Chieftain
 * difficulty are cumulative with the bonuses below, so if the player chooses Deity difficulty, the AI will receive 60% of 60%
 * (or 36%) of the normal Unhappiness from its cities and population.
 *
 * Element	Settler	Chieftain	Warlord	Prince	King	Emperor	Immortal	Deity
 * Free Techs	None	None	None	None	Pottery	Pottery, Animal Husbandry	Pottery, Animal Husbandry, Mining	Pottery, Animal Husbandry, Mining, The Wheel
 * Barbarian Bonus	45%	50%	55%	60%	60%	60%	60%	60%
 * Starting Settler Bonus	0	0	0	0	0	0	0	1
 * Starting Defense Units	0	0	0	0	1	1	2	2
 * Starting Worker Units	0	0	0	0	0	0	1	2
 * Starting Explore Units	0	0	0	0	0	1	1	1
 * Declare War Probability	0%	75%	85%	100%	100%	100%	100%	100%
 * Work Rate Bonus	0%	0%	0%	0%	20%	50%	75%	100%
 * Unhappiness	100%	100%	100%	100%	90%	85%	75%	60%
 * Growth Rate1	160%	130%	110%	100%	90%	85%	75%	60%
 * Train Rate2	175%	130%	110%	100%	85%	80%	65%	50%
 * World Train Rate	160%	130%	110%	100%	100%	100%	100%	100%
 * Construction Rate3	160%	130%	110%	100%	85%	80%	65%	50%
 * World Construction Rate4	160%	130%	110%	100%	100%	100%	100%	100%
 * Create Rate5	160%	130%	110%	100%	85%	80%	65%	50%
 * World Create Rate6	160%	130%	110%	100%	100%	100%	100%	100%
 * Building Cost7	100%	100%	100%	100%	85%	80%	65%	50%
 * Unit Cost8	100%	100%	100%	85%	80%	75%	65%	50%
 * Unit Supply	0%	0%	10%	20%	30%	30%	40%	50%
 * Per Era Modifier	0	0	0	0	-2	-3	-4	-5
 * Advanced Start Points*	100%	100%	100%	100%	120%	135%	150%	170%
 * 1 This element applies to the amount of Food Food that cities must store to increase their 20xPopulation5.png Population.
 * 2 This element applies to the Production Production and Faith Faith costs of units.
 * 3 This element applies to the Production Production costs of buildings.
 * 4 This element applies to the Production Production costs of wonders.
 * 5 This element applies to the Production Production costs of national projects, such as the Manhattan Project.
 * 6 This element applies to the amount of Production Production that must be contributed to complete international projects, such as the World's Fair.
 * 7 This element applies to the Gold Gold maintenance costs of buildings.
 * 8 This element applies to the Gold Gold maintenance costs of units.
 * * This element is either a holdover from Civilization IV or is never used in the code, so it has no effect on gameplay.
 *
 * In Brave New World
 * ------------------
 * Players receive gameplay bonuses that diminish as the chosen difficulty level increases.
 * The AI plays on a special difficulty level (as detailed under "AI" in the table below) and receives all of the appropriate bonuses.
 *
 * Element	                                Settler	Chief	Warlord	Prince	King	Emperor	Immortal	Deity	AI
 * Advanced Start Points*	                150%	130%	110%	100%	90%	    85%	    80%	        75%	    100%
 * Base Happiness	                        15	    12	    12	    9	    9	    9	    9	        9	    15
 * Extra Happiness Per Luxury	            1	    1	    0	    0	    0	    0	    0	        0	    0
 * Unhappiness Per City	                    40%	    60%	    75%	    100%	100%	100%	100%	    100%	90%
 * Unhappiness Per Population	            40%	    60%	    75%	    100%	100%	100%	100%	    100%	90%
 * Production Free Units	                10	    7	    7	    5	    5	    5	    5	        5	    7
 * Production Free Units Per City	        3	    3	    2	    2	    2	    2	    2	        2	    3
 * Route Cost	                            34%	    50%	    75%	    100%	100%	100%	100%	    100%	50%
 * Unit Cost	                            50%	    67%	    85%	    100%	100%	100%	100%	    100%	75%
 * Building Cost	                        50%	    67%	    85%	    100%	100%	100%	100%	    100%	75%
 * Research Cost	                        90%	    95%	    100%	100%	100%	100%	100%	    100%	85%
 * Policy Cost	                            50%	    67%	    85%	    100%	100%	100%	100%	    100%	75%
 * City Production Num Options Considered	10	    4	    3	    2	    2	    2	    2	        2	    2
 * Tech Num Options Considered	            10	    4	    3	    2	    2	    2	    2	        2	    2
 * Policy Num Options Considered	        10	    4	    3	    2	    2	    2	    2	        2	    2
 * Attitude Change*	                        2	    1	    0	    -1	    -1	    -1	    -1	        -1	    -1
 * No Tech Trade Modifier*	                100%	90%	    80%	    70%	    50%	    40%	    30%	        20%	    70%
 * Tech Trade Known Modifier*	            -100%	-50%	-25%	0%	    0%	    0%	    0%	        0%	    0%
 * Barb Camp Gold	                        50	    40	    30	    25	    25	    25	    25	        25	    25
 * Barb Spawn Mod	                        8	    5	    3	    0	    0	    0	    0	        0	    0
 * Barbarian Bonus	                        70	    60	    50	    40	    30	    20	    10	        0	    40
 * Turn Barbarians Can Enter Player Land	10,000	60	    20	    0	    0	    0	    0	        0	    0
 * Barbarian Land Target Range	            2	    3	    4	    5	    5	    6	    7	        8	    5
 * Barbarian Sea Target Range	            4	    6	    8	    10	    12	    15	    18	        20	    10
 * * This element is either a holdover from Civilization IV or is never used in the code, so it has no effect on gameplay.
 *
 * AI Bonuses
 * The player's chosen difficulty level also affects the AI leaders. The gameplay bonuses they receive from their special difficulty level are cumulative with the bonuses below, so if the player chooses Deity difficulty, the AI will receive 90% of 75% (or 67.5%) of the normal Unhappiness (Civ5).png Unhappiness from its cities and population.
 *
 * Element	Settler	Chieftain	Warlord	Prince	King	Emperor	Immortal	Deity
 * Free Techs	None	None	None	None	Pottery	Pottery, Animal Husbandry, Mining	Pottery, Animal Husbandry, Mining, Archery	Pottery, Animal Husbandry, Mining, Archery, The Wheel
 * Barbarian Bonus	75%	70%	60%	60%	60%	60%	60%	60%
 * Starting Settler Bonus	0	0	0	0	0	0	0	1
 * Starting Defense Units	0	0	0	0	1	1	2	2
 * Starting Worker Units	0	0	0	0	0	0	1	2
 * Starting Explore Units	0	0	0	0	0	1	1	1
 * Declare War Probability	0%	75%	85%	100%	100%	100%	100%	100%
 * Work Rate Bonus	0%	0%	0%	0%	20%	50%	75%	100%
 * Unhappiness	100%	100%	100%	100%	100%	90%	85%	75%
 * Growth Rate1	160%	130%	110%	100%	90%	80%	70%	55%
 * Train Rate2	175%	130%	110%	100%	85%	80%	65%	50%
 * World Train Rate	160%	130%	110%	100%	100%	100%	100%	100%
 * Construction Rate3	160%	130%	110%	100%	85%	75%	60%	50%
 * World Construction Rate4	160%	130%	110%	100%	100%	100%	100%	100%
 * Create Rate5	160%	130%	110%	100%	85%	80%	65%	50%
 * World Create Rate6	160%	130%	110%	100%	100%	100%	100%	100%
 * Building Cost7	100%	100%	100%	100%	85%	80%	65%	50%
 * Unit Cost8	100%	100%	100%	85%	80%	75%	65%	50%
 * Unit Supply	0%	0%	10%	20%	30%	30%	40%	50%
 * Per Era Modifier	0	0	0	0	-2	-3	-4	-5
 * Advanced Start Points*	100%	100%	100%	100%	120%	135%	150%	170%
 * Free XP9	0	0	0	0	10	10	30	30
 * Free XP Percent10	0%	0%	0%	0%	0%	25%	50%	100%
 * 1 This element applies to the amount of Food that cities must store to increase their Population.
 * 2 This element applies to the Production and Faith costs of units.
 * 3 This element applies to the Production costs of buildings.
 * 4 This element applies to the Production costs of wonders.
 * 5 This element applies to the Production costs of national projects, such as the Manhattan Project.
 * 6 This element applies to the amount of Production that must be contributed to complete international projects, such as the World's Fair.
 * 7 This element applies to the Gold maintenance costs of buildings.
 * 8 This element applies to the Gold maintenance costs of units.
 * 9 This element applies to the starting XP of military units.
 * 10 This element applies to the number of XP that military units earn from combat.
 * * This element is either a holdover from Civilization IV or is never used in the code, so it has no effect on gameplay.
 */
@RequiredArgsConstructor
public enum DifficultyLevel {

    SETTLER(L10nWorld.DIFFICULTY_LEVEL_SETTLER),
    CHIEFTAIN(L10nWorld.DIFFICULTY_LEVEL_CHIEFTAIN),
    WARLORD(L10nWorld.DIFFICULTY_LEVEL_WARLORD),
    PRINCE(L10nWorld.DIFFICULTY_LEVEL_PRINCE),
    KING(L10nWorld.DIFFICULTY_LEVEL_KING),
    EMPEROR(L10nWorld.DIFFICULTY_LEVEL_EMPEROR),
    IMMORTAL(L10nWorld.DIFFICULTY_LEVEL_IMMORTAL),
    DEITY(L10nWorld.DIFFICULTY_LEVEL_DEITY);

    @Getter
    private final L10n l10n;

    public static DifficultyLevel find(String name) {
        return Arrays.stream(DifficultyLevel.values())
            .filter(e -> e.name().equalsIgnoreCase(name))
            .findAny()
            .orElse(null);
    }
}
