package com.tsoft.civilization.unit.service.promotion.bonus;

import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.service.promotion.Promotion;
import com.tsoft.civilization.util.l10n.L10n;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;

public final class NearCapitalBonus implements Promotion {

    private static final L10n L10N = new L10n()
        .put(EN, "30% stronger while fighting in the capital; bonus falls off as the unit gets further away");

    @Override
    public boolean accept(AbstractUnit unit) {
        return true;
    }
}