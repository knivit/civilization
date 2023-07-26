package com.tsoft.civilization.combat;

import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.civilization.city.CityList;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.util.AList;

public class HasCombatStrengthList extends AList<HasCombatStrength> {

    public HasCombatStrengthList addAll(UnitList units) {
        if (units != null && !units.isEmpty()) {
            for (AbstractUnit unit : units) {
                add(unit);
            }
        }
        return this;
    }

    public HasCombatStrengthList addAll(CityList cities) {
        if (cities != null && !cities.isEmpty()) {
            for (City city : cities) {
                add(city);
            }
        }
        return this;
    }
}
