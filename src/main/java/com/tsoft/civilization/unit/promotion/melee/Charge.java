package com.tsoft.civilization.unit.promotion.melee;

import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.promotion.Promotion;
import com.tsoft.civilization.unit.promotion.PromotionType;
import com.tsoft.civilization.util.l10n.L10n;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;

public final class Charge implements Promotion {

    private static final L10n L10N = new L10n()
        .put(EN, "+33% Combat Strength VS Wounded Units");

    @Override
    public boolean accept(AbstractUnit unit) {
        return unit.hasPromotions(PromotionType.Shock2, PromotionType.Drill2);
    }
}
