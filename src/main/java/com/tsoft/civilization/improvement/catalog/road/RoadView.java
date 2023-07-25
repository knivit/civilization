package com.tsoft.civilization.improvement.catalog.road;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.improvement.AbstractImprovementView;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;
import static com.tsoft.civilization.util.l10n.L10nLanguage.RU;

public class RoadView extends AbstractImprovementView {

    private static final L10n ROAD_NAME = new L10n()
        .put(EN, "Road")
        .put(RU, "Дорога");

    private static final L10n ROAD_DESCRIPTION = new L10n()
        .put(EN, "Road links cities that gives economical burst")
        .put(RU, "Дороги связывают города приводя к экономическому взрыву");

    @Override
    public L10n getName() {
        return ROAD_NAME;
    }

    @Override
    public String getLocalizedDescription() {
        return ROAD_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/improvements/road.png";
    }
}
