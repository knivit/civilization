package com.tsoft.civilization.unit;

import java.util.Comparator;
import java.util.List;

public class UnitListService {

    public UnitList<?> sortByName(UnitList<?> units) {
        List<? extends AbstractUnit> list = units.getList();
        list.sort(Comparator.comparing(e -> e.getView().getLocalizedName()));
        return new UnitList<>(list);
    }
}
