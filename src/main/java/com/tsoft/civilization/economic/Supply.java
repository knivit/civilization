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
            .food((int)Math.round(food * modifier))
            .production((int)Math.round(production * modifier))
            .gold((int)Math.round(gold * modifier))
            .science((int)Math.round(science * modifier))
            .culture((int)Math.round(culture* modifier))
            .faith((int)Math.round(faith * modifier))
            .tourism((int)Math.round(tourism * modifier))
            .greatPerson((int)Math.round(greatPerson * modifier))
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
