package com.tsoft.civilization.civilization.city.specialist;

import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.economic.HasSupply;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.world.HasHistory;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(of = "id")
public class Specialist implements HasSupply, HasHistory {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private final String id = UUID.randomUUID().toString();

    private final City city;
    private final SpecialistType specialistType;

    public Specialist(City city, SpecialistType specialistType) {
        this.city = city;
        this.specialistType = specialistType;
    }

    public City getCity() {
        return city;
    }

    @Override
    public Supply calcIncomeSupply() {
        return SpecialistSupplyTable.get(specialistType);
    }

    @Override
    public Supply calcOutcomeSupply() {
        // 1 citizen consumes 1 food
        return Supply.builder().food(-1).build();
    }

    @Override
    public void startYear() {

    }

    @Override
    public void stopYear() {

    }
}
