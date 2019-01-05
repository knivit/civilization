package com.tsoft.civilization.world.economic;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Supply {
    private int food;
    private int production;
    private int gold;
    private int science;
    private int culture;
    private int happiness;
    private int population;

    public void add(Supply supply) {
        food += supply.food;
        production += supply.production;
        gold += supply.gold;
        science += supply.science;
        culture += supply.culture;
        happiness += supply.happiness;
        population += supply.population;
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
                ", population=" + population +
                '}';
    }
}
