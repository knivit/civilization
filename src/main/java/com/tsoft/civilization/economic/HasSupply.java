package com.tsoft.civilization.economic;

public interface HasSupply {

    Supply calcIncomeSupply(); // Dynamically calculated during a year; doesn't change anybody's state; invoked at the end of a year an stored to the supply variable

    Supply calcOutcomeSupply();
}
