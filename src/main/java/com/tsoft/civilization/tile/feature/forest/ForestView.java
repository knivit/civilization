package com.tsoft.civilization.tile.feature.forest;

import com.tsoft.civilization.tile.feature.AbstractFeatureView;
import com.tsoft.civilization.util.l10n.L10n;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;
import static com.tsoft.civilization.util.l10n.L10nLanguage.RU;

public class ForestView extends AbstractFeatureView {

    private static final L10n FOREST_NAME = new L10n()
        .put(EN, "Forest")
        .put(RU, "Лес");

    private static final L10n FOREST_DESCRIPTION = new L10n()
        .put(EN, "There are many food there")
        .put(RU, "Здесь много еды.");

    @Override
    public String getLocalizedName() {
        return FOREST_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return FOREST_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/forest_status.png";
    }
}
