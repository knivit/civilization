package com.tsoft.civilization.unit.promotion.shared;

import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.promotion.Promotion;
import com.tsoft.civilization.util.l10n.L10n;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;

public final class Exploration implements Promotion {

    private static final L10n L10N = new L10n()
        .put(EN, "+1 Movement and +1 Sight");

    @Override
    public boolean accept(AbstractUnit unit) {
        return true;
    }
}
