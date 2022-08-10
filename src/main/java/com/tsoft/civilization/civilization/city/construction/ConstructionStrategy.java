package com.tsoft.civilization.civilization.city.construction;

import java.util.Random;
import java.util.function.BiConsumer;

public enum ConstructionStrategy {

    MANUAL(ConstructionStrategy::manual),
    RANDOM(ConstructionStrategy::random),
    AUTO(ConstructionStrategy::auto);

    private static final Random random = new Random();

    private final BiConsumer<ConstructionList, Construction> strategy;

    ConstructionStrategy(BiConsumer<ConstructionList, Construction> strategy) {
        this.strategy = strategy;
    }

    public void add(ConstructionList list, Construction construction) {
        strategy.accept(list, construction);
    }

    // In the "manual" mode do add the construction at the end of the list
    private static void manual(ConstructionList list, Construction construction) {
        list.add(construction);
    }

    private static void random(ConstructionList list, Construction construction) {
        if (list.isEmpty()) {
            list.add(construction);
        } else {
            int index = random.nextInt(list.size());
            list.set(index, construction);
        }
    }

    private static void auto(ConstructionList list, Construction construction) {
        // TODO
        // If there is a war an the construction is a military unit then place it
        //   1) above the civil units and buildings
        //   2) if the city is under attack
        //      2.1) find out construction's "defense a city" strength
        //      2.2) place the construction accordingly
        random(list, construction);
    }
}
