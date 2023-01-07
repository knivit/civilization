package com.tsoft.civilization.unit.promotion.bonus;

import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.promotion.Promotion;
import com.tsoft.civilization.util.l10n.L10n;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;

public final class BonusVsCities250 implements Promotion {

    private static final L10n L10N = new L10n()
        .put(EN, "+250% Bonus vs Cities");

    @Override
    public boolean accept(AbstractUnit unit) {
        return true;
    }
}
