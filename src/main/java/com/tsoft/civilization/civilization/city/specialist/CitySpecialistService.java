package com.tsoft.civilization.civilization.city.specialist;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.economic.Supply;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CitySpecialistService {

    // 1 citizen consumes 1 food
    private static final Supply BASE_OUTCOME_SUPPLY = Supply.builder().food(-1).build();

    private final City city;
    private final SpecialistSlotMap slots = initSpecialistSlotMap();

    private SpecialistSlotMap initSpecialistSlotMap() {
        SpecialistSlotMap slots = new SpecialistSlotMap();
        for (SpecialistType specialistType : SpecialistType.values()) {
            slots.put(specialistType, new SpecialistSlot(specialistType));
        }
        return slots;
    }

    public Supply getBaseSupply(Civilization civilization) {
        return Supply.EMPTY;
    }

    public Supply calcIncomeSupply(Civilization civilization) {
        Supply supply = Supply.EMPTY;

        for (SpecialistSlot slot : slots.values()) {
            Supply oneSpecSupply = SpecialistSupplyTable.get(slot.getSpecialistType());
            Supply usedSpecSupply = oneSpecSupply.multiply(slot.getUsed());
            supply = supply.add(usedSpecSupply);
        }

        return supply;
    }

    public Supply calcOutcomeSupply(Civilization civilization) {
        Supply supply = Supply.EMPTY;

        for (SpecialistSlot slot : slots.values()) {
            Supply usedSpecSupply = BASE_OUTCOME_SUPPLY.multiply(slot.getUsed());
            supply = supply.add(usedSpecSupply);
        }

        return supply;
    }

    public void startYear() {

    }

    public Supply stopYear() {
        Supply income = calcIncomeSupply(city.getCivilization());
        Supply outcome = calcOutcomeSupply(city.getCivilization());
        return income.add(outcome);
    }
}
