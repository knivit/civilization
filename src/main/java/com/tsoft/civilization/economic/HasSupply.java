package com.tsoft.civilization.economic;

public interface HasSupply {

    Supply getSupply();  // Supply at the beginning of a year; doesn't change during a year; changed (calculated) at the end of the year

    Supply calcSupply(); // Dynamically calculated during a year; doesn't change anybody's state; invoked at the end of a year an stored to the supply variable

    void startYear();    // Start a new economic year

    void stopYear();     // Stop the year and updates supply variable
}
