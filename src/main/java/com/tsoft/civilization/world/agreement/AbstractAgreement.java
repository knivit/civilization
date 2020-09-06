package com.tsoft.civilization.world.agreement;

import com.tsoft.civilization.world.Year;
import com.tsoft.civilization.civilization.Civilization;

public class AbstractAgreement {
    // who is giving this agreement
    private Civilization givingCivilization;

    // when it will be expired
    private Year expireYear;

    public AbstractAgreement(Civilization givingCivilization, Year expireYear) {
        this.givingCivilization = givingCivilization;
        this.expireYear = expireYear;
    }
}
