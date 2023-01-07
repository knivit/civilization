package com.tsoft.civilization.unit.promotion.healing;

import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.promotion.Promotion;
import com.tsoft.civilization.util.l10n.L10n;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;

public final class Survivalism1 implements Promotion {

    private static final L10n L10N = new L10n()
        .put(EN, "+5 HP healed per turn outside of friendly territory and +25% Defense");

    @Override
    public boolean accept(AbstractUnit unit) {
        return true;
    }
}
