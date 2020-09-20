package com.tsoft.civilization.improvement.city;

import java.util.Comparator;
import java.util.List;

public class CityListService {

    public CityList sortByName(CityList cities) {
        List<City> list = cities.getList();
        list.sort(Comparator.comparing(e -> e.getView().getLocalizedName()));
        return new CityList(list);
    }
}
