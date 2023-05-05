package com.tsoft.civilization.technology;

import com.tsoft.civilization.world.Year;
import lombok.Getter;

public enum Technology {

    AGRICULTURE(Year.ANCIENT_ERA, null, 0),

    POTTERY(Year.ANCIENT_ERA, new Technology[] { AGRICULTURE }, 13),
    ANIMAL_HUSBANDRY(Year.ANCIENT_ERA, new Technology[] { AGRICULTURE }, 14),
    ARCHERY(Year.ANCIENT_ERA, new Technology[] { AGRICULTURE }, 15),
    MINING(Year.ANCIENT_ERA, new Technology[] { AGRICULTURE }, 15),

    SAILING(Year.ANCIENT_ERA, new Technology[] { POTTERY }, 24),
    CALENDAR(Year.ANCIENT_ERA, new Technology[] { POTTERY }, 24),
    WRITING(Year.ANCIENT_ERA, new Technology[] { POTTERY }, 24),
    TRAPPING(Year.ANCIENT_ERA, new Technology[] { ANIMAL_HUSBANDRY }, 24),
    WHEEL(Year.ANCIENT_ERA, new Technology[] { ANIMAL_HUSBANDRY, ARCHERY }, 24),
    MASONRY(Year.ANCIENT_ERA, new Technology[] { MINING }, 24),
    BRONZE_WORKING(Year.ANCIENT_ERA, new Technology[] { MINING }, 24),

    OPTICS(Year.CLASSICAL_ERA, new Technology[] { SAILING }, 37),
    HORSEBACK_RIDING(Year.CLASSICAL_ERA, new Technology[] { TRAPPING, WHEEL }, 46),
    MATHEMATICS(Year.CLASSICAL_ERA, new Technology[] { WHEEL }, 46),
    CONSTRUCTION(Year.CLASSICAL_ERA, new Technology[] { WHEEL, MASONRY }, 46),

    PHILOSOPHY(Year.CLASSICAL_ERA, new Technology[] { CALENDAR, WRITING }, 76),
    DRAMA_AND_POETRY(Year.CLASSICAL_ERA, new Technology[] { WRITING }, 76),
    CURRENCY(Year.CLASSICAL_ERA, new Technology[] { MATHEMATICS }, 76),
    ENGINEERING(Year.CLASSICAL_ERA, new Technology[] { MATHEMATICS, CONSTRUCTION }, 76),
    IRON_WORKING(Year.CLASSICAL_ERA, new Technology[] { BRONZE_WORKING }, 76),

    THEOLOGY(Year.MEDIEVAL_ERA, new Technology[] { PHILOSOPHY, DRAMA_AND_POETRY }, 119),
    CIVIL_SERVICE(Year.MEDIEVAL_ERA, new Technology[] { DRAMA_AND_POETRY, HORSEBACK_RIDING, CURRENCY }, 119),
    GUILDS(Year.MEDIEVAL_ERA, new Technology[] { CURRENCY }, 119),
    METAL_CASTING(Year.MEDIEVAL_ERA, new Technology[] { ENGINEERING, IRON_WORKING }, 119),

    COMPASS(Year.MEDIEVAL_ERA, new Technology[] { OPTICS, THEOLOGY }, 163),
    EDUCATION(Year.MEDIEVAL_ERA, new Technology[] { THEOLOGY, CIVIL_SERVICE }, 210),
    CHIVALRY(Year.MEDIEVAL_ERA, new Technology[] { CIVIL_SERVICE, GUILDS }, 210),
    MACHINERY(Year.MEDIEVAL_ERA, new Technology[] { GUILDS, ENGINEERING }, 210),
    PHYSICS(Year.MEDIEVAL_ERA, new Technology[] { METAL_CASTING }, 210),
    STEEL(Year.MEDIEVAL_ERA, new Technology[] { METAL_CASTING }, 210),

    ASTRONOMY(Year.RENAISSANCE_ERA, new Technology[] { COMPASS, EDUCATION }, 338),
    ACOUSTICS(Year.RENAISSANCE_ERA, new Technology[] { EDUCATION }, 338),
    BANKING(Year.RENAISSANCE_ERA, new Technology[] { EDUCATION, CHIVALRY }, 338),
    PRINTING_PRESS(Year.RENAISSANCE_ERA, new Technology[] { CHIVALRY, MACHINERY, PHYSICS }, 338),
    GUNPOWDER(Year.RENAISSANCE_ERA, new Technology[] { PHYSICS, STEEL }, 338),

    NAVIGATION(Year.RENAISSANCE_ERA, new Technology[] { ASTRONOMY }, 498),
    ARCHITECTURE(Year.RENAISSANCE_ERA, new Technology[] { ACOUSTICS, BANKING }, 498),
    ECONOMICS(Year.RENAISSANCE_ERA, new Technology[] { BANKING, PRINTING_PRESS }, 498),
    METALLURGY(Year.RENAISSANCE_ERA, new Technology[] { PRINTING_PRESS, GUNPOWDER }, 498),
    CHEMISTRY(Year.RENAISSANCE_ERA, new Technology[] { GUNPOWDER }, 498),

    ARCHAEOLOGY(Year.INDUSTRIAL_ERA, new Technology[] { NAVIGATION, ARCHITECTURE }, 693),
    SCIENTIFIC_THEORY(Year.INDUSTRIAL_ERA, new Technology[] { ARCHITECTURE, ECONOMICS }, 693),
    INDUSTRIALIZATION(Year.INDUSTRIAL_ERA, new Technology[] { ECONOMICS }, 693),
    RIFLING(Year.INDUSTRIAL_ERA, new Technology[] { ECONOMICS, METALLURGY }, 693),
    MILITARY_SCIENCE(Year.INDUSTRIAL_ERA, new Technology[] { METALLURGY, CHEMISTRY }, 693),
    FERTILIZER(Year.INDUSTRIAL_ERA, new Technology[] { CHEMISTRY }, 693),

    BIOLOGY(Year.INDUSTRIAL_ERA, new Technology[] { ARCHAEOLOGY, SCIENTIFIC_THEORY }, 1018),
    ELECTRICITY(Year.INDUSTRIAL_ERA, new Technology[] { SCIENTIFIC_THEORY }, 1018),
    STEAM_POWER(Year.INDUSTRIAL_ERA, new Technology[] { SCIENTIFIC_THEORY, INDUSTRIALIZATION, RIFLING }, 1018),
    DYNAMITE(Year.INDUSTRIAL_ERA, new Technology[] { MILITARY_SCIENCE, FERTILIZER }, 1018),

    REFRIGERATION(Year.MODERN_ERA, new Technology[] { BIOLOGY, ELECTRICITY }, 1343),
    RADIO(Year.MODERN_ERA, new Technology[] { ELECTRICITY }, 1343),
    REPLACEABLE_PARTS(Year.MODERN_ERA, new Technology[] { ELECTRICITY, STEAM_POWER }, 1343),
    FLIGHT(Year.MODERN_ERA, new Technology[] { STEAM_POWER }, 1343),
    RAILROAD(Year.MODERN_ERA, new Technology[] { STEAM_POWER, DYNAMITE }, 1343),
    PLASTICS(Year.MODERN_ERA, new Technology[] { RADIO }, 1776),
    ELECTRONICS(Year.MODERN_ERA, new Technology[] { REPLACEABLE_PARTS, FLIGHT }, 1776),
    BALLISTICS(Year.MODERN_ERA, new Technology[] { FLIGHT, RAILROAD }, 1776),
    COMBUSTION(Year.MODERN_ERA, new Technology[] { RAILROAD }, 1776),

    PENICILLIN(Year.ATOMIC_ERA, new Technology[] { REFRIGERATION, PLASTICS }, 2209),
    ATOMIC_THEORY(Year.ATOMIC_ERA, new Technology[] { PLASTICS, ELECTRONICS }, 2209),
    RADAR(Year.ATOMIC_ERA, new Technology[] { ELECTRONICS, BALLISTICS }, 2209),
    COMBINED_ARMS(Year.ATOMIC_ERA, new Technology[] { BALLISTICS, COMBUSTION }, 2209),

    ECOLOGY(Year.ATOMIC_ERA, new Technology[] { PENICILLIN, ATOMIC_THEORY }, 2772),
    NUCLEAR_FISSION(Year.ATOMIC_ERA, new Technology[] { ATOMIC_THEORY, RADAR }, 2772),
    ROCKETRY(Year.ATOMIC_ERA, new Technology[] { RADAR }, 2772),
    COMPUTERS(Year.ATOMIC_ERA, new Technology[] { RADAR, COMBINED_ARMS }, 2772),

    TELECOMMUNICATIONS(Year.INFORMATION_ERA, new Technology[] { ECOLOGY }, 3336),
    MOBILE_TACTICS(Year.INFORMATION_ERA, new Technology[] { ECOLOGY, NUCLEAR_FISSION }, 3336),
    ADVANCED_BALLISTICS(Year.INFORMATION_ERA, new Technology[] { NUCLEAR_FISSION, ROCKETRY }, 3336),
    SATELLITES(Year.INFORMATION_ERA, new Technology[] { ROCKETRY }, 3336),
    ROBOTICS(Year.INFORMATION_ERA, new Technology[] { ROCKETRY, COMPUTERS }, 3336),
    LASERS(Year.INFORMATION_ERA, new Technology[] { COMPUTERS }, 3336),

    THE_INTERNET(Year.INFORMATION_ERA, new Technology[] { TELECOMMUNICATIONS }, 3812),
    GLOBALIZATION(Year.INFORMATION_ERA, new Technology[] { TELECOMMUNICATIONS }, 3812),
    PARTICLE_PHYSICS(Year.INFORMATION_ERA, new Technology[] { TELECOMMUNICATIONS, MOBILE_TACTICS, ADVANCED_BALLISTICS }, 3812),
    NUCLEAR_FUSION(Year.INFORMATION_ERA, new Technology[] { ADVANCED_BALLISTICS, SATELLITES, ROBOTICS }, 3812),
    NANOTECHNOLOGY(Year.INFORMATION_ERA, new Technology[] { ROBOTICS }, 3812),
    STEALTH(Year.INFORMATION_ERA, new Technology[] { ROBOTICS, LASERS }, 3812),

    FUTURE_TECH(Year.INFORMATION_ERA, new Technology[] { THE_INTERNET, GLOBALIZATION, PARTICLE_PHYSICS, NUCLEAR_FUSION, NANOTECHNOLOGY, STEALTH }, 3812);

    @Getter
    private final int era;

    @Getter
    private final Technology[] from;

    @Getter
    private final int turns;

    Technology(int era, Technology[] from, int turns) {
        this.era = era;
        this.from = from;
        this.turns = turns;
    }
}
