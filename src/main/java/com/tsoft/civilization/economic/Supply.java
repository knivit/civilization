package com.tsoft.civilization.economic;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class Supply {

    public static final Supply EMPTY = Supply.builder().build();

    private final double food;
    private final double production;
    private final double gold;
    private final double science;
    private final double culture;
    private final double faith;
    private final double tourism;
    private final double greatPerson;

    public Supply add(Supply other) {
        if (isEmpty(other)) {
            return this;
        }

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

    public Supply multiply(int val) {
        return Supply.builder()
            .food(food * val)
            .production(production * val)
            .gold(gold * val)
            .science(science * val)
            .culture(culture * val)
            .faith(faith * val)
            .tourism(tourism * val)
            .greatPerson(greatPerson * val)
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

    public Supply applyModifier(double modifier) {
        return Supply.builder()
            .food(food * modifier)
            .production(production * modifier)
            .gold(gold * modifier)
            .science(science * modifier)
            .culture(culture* modifier)
            .faith(faith * modifier)
            .tourism(tourism * modifier)
            .greatPerson(greatPerson * modifier)
            .build();
    }

    private static boolean isEmpty(Supply supply) {
        return supply.food == 0 && supply.production == 0 && supply.gold == 0 && supply.science == 0 &&
            supply.culture == 0 && supply.faith == 0 && supply.tourism == 0 && supply.greatPerson == 0;
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
