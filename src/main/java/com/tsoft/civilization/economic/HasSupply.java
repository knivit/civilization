package com.tsoft.civilization.economic;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.HasId;

public interface HasSupply extends HasId {

    Supply getBaseSupply(Civilization civilization);

    // Dynamically calculated during a year;
    // doesn't change anything's state;
    // invoked at the end of a year and stored to the supply variable
    Supply calcIncomeSupply(Civilization civilization);

    Supply calcOutcomeSupply(Civilization civilization);

    default Supply calcSupply(Civilization civilization) {
        Supply income = calcIncomeSupply(civilization);
        Supply outcome = calcOutcomeSupply(civilization);
        return income.add(outcome);
    }
}
