package com.tsoft.civilization.unit;

public enum UnitType {
    ARCHERS(0),
    GREAT_ARTIST(1),
    GREAT_ENGINEER(2),
    GREAT_GENERAL(3),
    GREAT_MERCHANT(4),
    GREAT_SCIENTIST(5),
    SETTLERS(6),
    WARRIORS(7),
    WORKERS(8);

    private int id;

    UnitType(int id) {
        this.id = id;
    }
}
