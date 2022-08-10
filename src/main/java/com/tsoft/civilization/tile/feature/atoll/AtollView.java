package com.tsoft.civilization.tile.feature.atoll;

import com.tsoft.civilization.tile.feature.AbstractFeatureView;
import com.tsoft.civilization.util.l10n.L10n;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;
import static com.tsoft.civilization.util.l10n.L10nLanguage.RU;

public class AtollView extends AbstractFeatureView {

    private static final L10n ATOLL_NAME = new L10n()
        .put(EN, "Atoll")
        .put(RU, "Атолл");

    private static final L10n ATOLL_DESCRIPTION = new L10n()
        .put(EN, "Small pieces of land in ocean")
        .put(RU, "Небольшие кусочки суши в океане");

    @Override
    public String getLocalizedName() {
        return ATOLL_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return ATOLL_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/atoll_status.png";
    }
}
