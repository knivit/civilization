package com.tsoft.civilization.world.agreement;

import com.tsoft.civilization.world.Civilization;

public class AbstractAgreement {
    // who is giving this agreement
    private Civilization givingCivilization;

    // when it will be expired
    private int expireYear;

    public AbstractAgreement(Civilization givingCivilization, int expireYear) {
        this.givingCivilization = givingCivilization;
        this.expireYear = expireYear;
    }
}
