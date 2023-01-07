package com.tsoft.civilization.unit.promotion;

import com.tsoft.civilization.unit.AbstractUnit;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.Set;

@RequiredArgsConstructor
public class PromotionService {

    private final AbstractUnit unit;

    private Set<PromotionType> promotions = EnumSet.allOf(PromotionType.class);

    public boolean hasPromotions(PromotionType ... required) {
        for (PromotionType promotion : required) {
            if (!promotions.contains(promotion)) {
                return false;
            }
        }
        return true;
    }
}
