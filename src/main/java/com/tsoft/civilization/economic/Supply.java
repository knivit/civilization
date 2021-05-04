package com.tsoft.civilization.economic;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class Supply {
    public static final Supply EMPTY = Supply.builder().build();

    private final int food;
    private final int production;
    private final int gold;
    private final int science;
    private final int culture;

    private final int greatArtist;
    private final int greatMusician;
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

            .greatArtist(greatArtist + other.greatArtist)
            .greatMusician(greatMusician + other.greatMusician)
            .greatWriter(greatWriter + other.greatWriter)
            .greatEngineer(greatEngineer + other.greatEngineer)
            .greatMerchant(greatMerchant + other.greatMerchant)
            .greatScientist(greatScientist + other.greatScientist)

            .build();
    }

    public Supply.SupplyBuilder copy() {
        return Supply.builder()
            .food(food)
            .production(production)
            .gold(gold)
            .science(science)
            .culture(culture)

            .greatArtist(greatArtist)
            .greatMusician(greatMusician )
            .greatWriter(greatWriter )
            .greatEngineer(greatEngineer)
            .greatMerchant(greatMerchant)
            .greatScientist(greatScientist);
    }

    @Override
    public String toString() {
        return "{" +
            "food=" + food +
            ", production=" + production +
            ", gold=" + gold +
            ", science=" + science +
            ", culture=" + culture +

            ", Great Artist=" + greatArtist +
            ", Great Musician=" + greatMusician +
            ", Great Writer=" + greatWriter +
            ", Great Engineer=" + greatEngineer +
            ", Great Merchant=" + greatMerchant +
            ", Great Scientist=" + greatScientist +
        '}';
    }
}
