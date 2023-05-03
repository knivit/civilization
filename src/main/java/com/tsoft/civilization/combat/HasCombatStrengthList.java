package com.tsoft.civilization.combat;

import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.civilization.city.CityList;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.util.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HasCombatStrengthList extends ArrayList<HasCombatStrength> {

    public static HasCombatStrengthList of(HasCombatStrength ... list) {
        HasCombatStrengthList result = new HasCombatStrengthList();
        if (list == null) {
            return result;
        }

        result.addAll(Arrays.asList(list));
        return result;
    }

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
