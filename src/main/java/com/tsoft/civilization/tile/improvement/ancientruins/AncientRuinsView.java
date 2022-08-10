package com.tsoft.civilization.tile.improvement.ancientruins;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.tile.improvement.AbstractImprovementView;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;
import static com.tsoft.civilization.util.l10n.L10nLanguage.RU;

public class AncientRuinsView extends AbstractImprovementView {

    private static final L10n ANCIENT_RUINS = new L10n()
        .put(EN, "Ancient Ruins")
        .put(RU, "Древние руины");

    private static final L10n ANCIENT_RUINS_DESCRIPTION = new L10n()
        .put(EN, "Farming is one of the earliest and most important of all human professions, as it allowed mankind to stop migrating and settle in one location without depleting the local resources. Farms can be constructed on most any land to improve the hex's output of food.")
        .put(RU, "Ферма одно из самых ранних и наиболее значимых из всех профессий человечества, т.к. оно позволило уменьшить миграции и поселиться в одном месте");

    @Override
    public L10n getName() {
        return ANCIENT_RUINS;
    }

    @Override
    public String getLocalizedDescription() {
        return ANCIENT_RUINS_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/improvements/ancient-ruins-big.png";
    }
}
