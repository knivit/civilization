package com.tsoft.civilization.combat;

import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.improvement.city.CityList;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.util.Point;

import java.util.ArrayList;
import java.util.List;

public class HasCombatStrengthList extends ArrayList<HasCombatStrength> {
    public List<Point> getLocations() {
        List<Point> locations = new ArrayList<>(size());
        for (HasCombatStrength combatant : this) {
            locations.add(combatant.getLocation());
        }
        return locations;
    }

    public HasCombatStrengthList addAll(UnitList<?> units) {
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
