package com.tsoft.civilization.tile.feature.hill;

import com.tsoft.civilization.tile.feature.AbstractFeatureView;
import com.tsoft.civilization.util.l10n.L10n;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;
import static com.tsoft.civilization.util.l10n.L10nLanguage.RU;

public class HillView extends AbstractFeatureView {

    private static final L10n HILL_NAME = new L10n()
        .put(EN, "Hills")
        .put(RU, "Холмы");

    private static final L10n HILL_DESCRIPTION = new L10n()
        .put(EN, "Hills are good for a defense")
        .put(RU, "Холмы удобны для обороны");

    @Override
    public String getLocalizedName() {
        return HILL_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return HILL_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/hill_status.png";
    }
}
