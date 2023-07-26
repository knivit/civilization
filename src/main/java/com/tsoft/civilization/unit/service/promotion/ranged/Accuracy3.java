package com.tsoft.civilization.unit.service.promotion.ranged;

import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.service.promotion.Promotion;
import com.tsoft.civilization.unit.service.promotion.PromotionType;
import com.tsoft.civilization.util.l10n.L10n;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;

public final class Accuracy3 implements Promotion {

    private static final L10n L10N = new L10n()
        .put(EN, "+15% Ranged Combat Strength against Units in OPEN Terrain. (NO Hills, Forest or Jungle).");

    @Override
    public boolean accept(AbstractUnit unit) {
        return unit.hasPromotions(PromotionType.Accuracy2);
    }
}
