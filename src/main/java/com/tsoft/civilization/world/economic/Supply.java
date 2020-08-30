package com.tsoft.civilization.world.economic;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Supply {
    public static final Supply EMPTY_SUPPLY = Supply.builder().build();

    private final int food;
    private final int production;
    private final int gold;
    private final int science;
    private final int culture;
    private final int happiness;
    private final int unhappiness;
    private final int population;

    private final int greatArtist;
    private final int greatMusicians;
    private final int greatWriter;
    private final int greatEngineer;
    private final int greatMerchant;
    private final int greatScientist;

    public Supply add(Supply other) {
        return Supply.builder()
            .food(food + other.food)
            .production(production + other.production)
            .gold(gold + other.gold)
            .science(science + other.science)
            .culture(culture + other.culture)
            .happiness(happiness + other.happiness)
            .unhappiness(unhappiness + other.unhappiness)
            .population(population + other.population)

            .greatArtist(greatArtist + other.greatArtist)
            .greatMusicians(greatMusicians + other.greatMusicians)
            .greatWriter(greatWriter + other.greatWriter)
            .greatEngineer(greatEngineer + other.greatEngineer)
            .greatMerchant(greatMerchant + other.greatMerchant)
            .greatScientist(greatScientist + other.greatScientist)

            .build();
    }

    @Override
    public String toString() {
        return "{" +
            "food=" + food +
            ", production=" + production +
            ", gold=" + gold +
            ", science=" + science +
            ", culture=" + culture +
            ", happiness=" + happiness +
            ", unhappiness=" + unhappiness +
            ", population=" + population +

            ", greatArtist=" + greatArtist +
            ", greatMusicians=" + greatMusicians +
            ", greatWriter=" + greatWriter +
            ", greatEngineer=" + greatEngineer +
            ", greatMerchant=" + greatMerchant +
            ", greatScientist=" + greatScientist +
        '}';
    }
}
