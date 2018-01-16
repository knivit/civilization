package com.tsoft.civilization.technology;

import com.tsoft.civilization.util.Year;

public enum Technology {
    AGRICULTURE(Year.ANCIENT_ERA, new Technology[] { }, new Technology[] { }),
    POTTERY(Year.ANCIENT_ERA, new Technology[] { }, new Technology[] { }),
    ANIMAL_HUSBANDRY(Year.ANCIENT_ERA, new Technology[] { }, new Technology[] { }),
    ARCHERY(Year.ANCIENT_ERA, new Technology[] { }, new Technology[] { }),
    MINING(Year.ANCIENT_ERA, new Technology[] { }, new Technology[] { }),
    SAILING(Year.ANCIENT_ERA, new Technology[] { }, new Technology[] { }),
    CALENDAR(Year.ANCIENT_ERA, new Technology[] { }, new Technology[] { }),
    WRITING(Year.ANCIENT_ERA, new Technology[] { }, new Technology[] { }),
    TRAPPING(Year.ANCIENT_ERA, new Technology[] { }, new Technology[] { }),
    WHEEL(Year.ANCIENT_ERA, new Technology[] { }, new Technology[] { }),
    MASONRY(Year.ANCIENT_ERA, new Technology[] { }, new Technology[] { }),
    BRONZE_WORKING(Year.ANCIENT_ERA, new Technology[] { }, new Technology[] { }),

    OPTICS(Year.CLASSICAL_ERA, new Technology[] { }, new Technology[] { }),
    HORSEBACK_RIDING(Year.CLASSICAL_ERA, new Technology[] { }, new Technology[] { }),
    MATHEMATICS(Year.CLASSICAL_ERA, new Technology[] { }, new Technology[] { }),
    CONSTRUCTION(Year.CLASSICAL_ERA, new Technology[] { }, new Technology[] { }),
    PHILOSOPHY(Year.CLASSICAL_ERA, new Technology[] { }, new Technology[] { }),
    DRAMA_AND_POETRY(Year.CLASSICAL_ERA, new Technology[] { }, new Technology[] { }),
    CURRENCY(Year.CLASSICAL_ERA, new Technology[] { }, new Technology[] { }),
    ENGINEERING(Year.CLASSICAL_ERA, new Technology[] { }, new Technology[] { }),
    IRON_WORKING(Year.CLASSICAL_ERA, new Technology[] { }, new Technology[] { }),

    THEOLOGY(Year.MEDIEVAL_ERA, new Technology[] { }, new Technology[] { }),
    CIVIL_SERVICE(Year.MEDIEVAL_ERA, new Technology[] { }, new Technology[] { }),
    GUILDS(Year.MEDIEVAL_ERA, new Technology[] { }, new Technology[] { }),
    METAL_CASTING(Year.MEDIEVAL_ERA, new Technology[] { }, new Technology[] { }),
    COMPASS(Year.MEDIEVAL_ERA, new Technology[] { }, new Technology[] { }),
    EDUCATION(Year.MEDIEVAL_ERA, new Technology[] { }, new Technology[] { }),
    CHIVALRY(Year.MEDIEVAL_ERA, new Technology[] { }, new Technology[] { }),
    MACHINERY(Year.MEDIEVAL_ERA, new Technology[] { }, new Technology[] { }),
    PHYSICS(Year.MEDIEVAL_ERA, new Technology[] { }, new Technology[] { }),
    STEEL(Year.MEDIEVAL_ERA, new Technology[] { }, new Technology[] { }),

    ASTRONOMY(Year.RENAISSANCE_ERA, new Technology[] { }, new Technology[] { }),
    ACOUSTICS(Year.RENAISSANCE_ERA, new Technology[] { }, new Technology[] { }),
    BANKING(Year.RENAISSANCE_ERA, new Technology[] { }, new Technology[] { }),
    PRINTING_PRESS(Year.RENAISSANCE_ERA, new Technology[] { }, new Technology[] { }),
    GUNPOWDER(Year.RENAISSANCE_ERA, new Technology[] { }, new Technology[] { }),
    NAVIGATION(Year.RENAISSANCE_ERA, new Technology[] { }, new Technology[] { }),
    ARCHITECTURE(Year.RENAISSANCE_ERA, new Technology[] { }, new Technology[] { }),
    ECONOMICS(Year.RENAISSANCE_ERA, new Technology[] { }, new Technology[] { }),
    METALLURGY(Year.RENAISSANCE_ERA, new Technology[] { }, new Technology[] { }),
    CHEMISTRY(Year.RENAISSANCE_ERA, new Technology[] { }, new Technology[] { }),

    ARCHAEOLOGY(Year.INDUSTRIAL_ERA, new Technology[] { }, new Technology[] { }),
    SCIENTIFIC_THEORY(Year.INDUSTRIAL_ERA, new Technology[] { }, new Technology[] { }),
    INDUSTRIALIZATION(Year.INDUSTRIAL_ERA, new Technology[] { }, new Technology[] { }),
    RIFLING(Year.INDUSTRIAL_ERA, new Technology[] { }, new Technology[] { }),
    MILITARY_SCIENCE(Year.INDUSTRIAL_ERA, new Technology[] { }, new Technology[] { }),
    FERTILIZER(Year.INDUSTRIAL_ERA, new Technology[] { }, new Technology[] { }),
    BIOLOGY(Year.INDUSTRIAL_ERA, new Technology[] { }, new Technology[] { }),
    ELECTRICITY(Year.INDUSTRIAL_ERA, new Technology[] { }, new Technology[] { }),
    STEAM_POWER(Year.INDUSTRIAL_ERA, new Technology[] { }, new Technology[] { }),
    DYNAMITE(Year.INDUSTRIAL_ERA, new Technology[] { }, new Technology[] { }),

    REFRIGERATION(Year.MODERN_ERA, new Technology[] { }, new Technology[] { }),
    RADIO(Year.MODERN_ERA, new Technology[] { }, new Technology[] { }),
    REPLACEABLE_PARTS(Year.MODERN_ERA, new Technology[] { }, new Technology[] { }),
    FLIGHT(Year.MODERN_ERA, new Technology[] { }, new Technology[] { }),
    RAILROAD(Year.MODERN_ERA, new Technology[] { }, new Technology[] { }),
    PLASTICS(Year.MODERN_ERA, new Technology[] { }, new Technology[] { }),
    ELECTRONICS(Year.MODERN_ERA, new Technology[] { }, new Technology[] { }),
    BALLISTICS(Year.MODERN_ERA, new Technology[] { }, new Technology[] { }),
    COMBUSTION(Year.MODERN_ERA, new Technology[] { }, new Technology[] { }),

    PENICILLIN(Year.ATOMIC_ERA, new Technology[] { }, new Technology[] { }),
    ATOMIC_THEORY(Year.ATOMIC_ERA, new Technology[] { }, new Technology[] { }),
    RADAR(Year.ATOMIC_ERA, new Technology[] { }, new Technology[] { }),
    COMBINED_ARMS(Year.ATOMIC_ERA, new Technology[] { }, new Technology[] { }),
    ECOLOGY(Year.ATOMIC_ERA, new Technology[] { }, new Technology[] { }),
    NUCLEAR_FISSION(Year.ATOMIC_ERA, new Technology[] { }, new Technology[] { }),
    ROCKETRY(Year.ATOMIC_ERA, new Technology[] { }, new Technology[] { }),
    COMPUTERS(Year.ATOMIC_ERA, new Technology[] { }, new Technology[] { }),

    TELECOMMUNICATIONS(Year.INFORMATION_ERA, new Technology[] { }, new Technology[] { }),
    MOBILE_TACTICS(Year.INFORMATION_ERA, new Technology[] { }, new Technology[] { }),
    ADVANCED_BALLISTICS(Year.INFORMATION_ERA, new Technology[] { }, new Technology[] { }),
    SATELLITES(Year.INFORMATION_ERA, new Technology[] { }, new Technology[] { }),
    ROBOTICS(Year.INFORMATION_ERA, new Technology[] { }, new Technology[] { }),
    LASERS(Year.INFORMATION_ERA, new Technology[] { }, new Technology[] { }),
    GLOBALIZATION(Year.INFORMATION_ERA, new Technology[] { }, new Technology[] { }),
    PARTICLE_PHYSICS(Year.INFORMATION_ERA, new Technology[] { }, new Technology[] { }),
    NUCLEAR_FUSION(Year.INFORMATION_ERA, new Technology[] { }, new Technology[] { }),
    NANOTECHNOLOGY(Year.INFORMATION_ERA, new Technology[] { }, new Technology[] { }),
    STEALTH(Year.INFORMATION_ERA, new Technology[] { }, new Technology[] { }),
    FUTURE_TECH(Year.INFORMATION_ERA, new Technology[] { }, new Technology[] { });

    private int era;

    private Technology[] from;

    private Technology[] leadsTo;

    Technology(int era, Technology[] from, Technology[] leadsTo) {
        this.era = era;
        this.from = from;
        this.leadsTo = leadsTo;
    }
}
