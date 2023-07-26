package com.tsoft.civilization.improvement.catalog.farm;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.improvement.AbstractImprovementView;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;
import static com.tsoft.civilization.util.l10n.L10nLanguage.RU;

public class FarmView extends AbstractImprovementView {

    private static final L10n FARM_NAME = new L10n()
        .put(EN, "Farm")
        .put(RU, "Ферма");

    private static final L10n FARM_DESCRIPTION = new L10n()
        .put(EN, "Farming is one of the earliest and most important of all human professions, as it allowed mankind to stop migrating and settle in one location without depleting the local resources. Farms can be constructed on most any land to improve the hex's output of food.")
        .put(RU, "Ферма одно из самых ранних и наиболее значимых из всех профессий человечества, т.к. оно позволило уменьшить миграции и поселиться в одном месте");

    @Override
    public L10n getName() {
        return FARM_NAME;
    }

    @Override
    public String getLocalizedDescription() {
        return FARM_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/improvements/farm.png";
    }
}
