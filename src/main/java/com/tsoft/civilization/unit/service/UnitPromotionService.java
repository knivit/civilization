package com.tsoft.civilization.unit.service;

import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.service.promotion.PromotionType;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.Set;

@RequiredArgsConstructor
public class UnitPromotionService {

    private final AbstractUnit unit;

    private final Set<PromotionType> promotions = EnumSet.allOf(PromotionType.class);

    public boolean hasPromotions(PromotionType ... required) {
        for (PromotionType promotion : required) {
            if (!promotions.contains(promotion)) {
                return false;
            }
        }
        return true;
    }
}
