package com.tsoft.civilization.economic;

import com.tsoft.civilization.civilization.Civilization;

public interface HasSupply {

    Supply getBaseSupply(Civilization civilization);

    // Dynamically calculated during a year; doesn't change anybody's state; invoked at the end of a year and stored to the supply variable
    Supply calcIncomeSupply(Civilization civilization);

    Supply calcOutcomeSupply(Civilization civilization);
}
