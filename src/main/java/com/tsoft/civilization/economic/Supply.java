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
    private final int faith;
    private final int tourism;
    private final int greatPerson;

    public Supply add(Supply other) {
        return Supply.builder()
            .food(food + other.food)
            .production(production + other.production)
            .gold(gold + other.gold)
            .science(science + other.science)
            .culture(culture + other.culture)
            .faith(faith + other.faith)
            .tourism(tourism + other.tourism)
            .greatPerson(greatPerson + other.greatPerson)
            .build();
    }

    public Supply.SupplyBuilder copy() {
        return Supply.builder()
            .food(food)
            .production(production)
            .gold(gold)
            .science(science)
            .culture(culture)
            .faith(faith)
            .tourism(tourism)
            .greatPerson(greatPerson);
    }

    @Override
    public String toString() {
        return "{" +
            "food=" + food +
            ", production=" + production +
            ", gold=" + gold +
            ", science=" + science +
            ", culture=" + culture +
            ", faith=" + faith +
            ", tourism=" + tourism +
            ", greatPerson=" + greatPerson +
        '}';
    }
}
