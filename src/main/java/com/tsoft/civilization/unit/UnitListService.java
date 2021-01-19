package com.tsoft.civilization.unit;

import java.util.Comparator;
import java.util.List;

public class UnitListService {

    public UnitList sortByName(UnitList units) {
        List<AbstractUnit> list = units.getListCopy();
        list.sort(Comparator.comparing(e -> e.getView().getLocalizedName()));
        return new UnitList(list);
    }
}
