package com.tsoft.civilization.unit.service.promotion.healing;

import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.service.promotion.Promotion;
import com.tsoft.civilization.util.l10n.L10n;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;

public final class HealInstantly implements Promotion {

    private static final L10n L10N = new L10n()
        .put(EN, "Heal this Unit 50 HP. Doing so will Consume this Opportunity to choose a Promotion");


    @Override
    public boolean accept(AbstractUnit unit) {
        return true;
    }
}
