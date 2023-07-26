package com.tsoft.civilization.unit.service.promotion.melee;

import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.service.promotion.Promotion;
import com.tsoft.civilization.unit.service.promotion.PromotionType;
import com.tsoft.civilization.util.l10n.L10n;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;

public final class Blitz implements Promotion {

    private static final L10n L10N = new L10n()
        .put(EN, "1 additional attack per turn");

    @Override
    public boolean accept(AbstractUnit unit) {
        return unit.hasPromotions(PromotionType.Shock3, PromotionType.Drill3);
    }
}
