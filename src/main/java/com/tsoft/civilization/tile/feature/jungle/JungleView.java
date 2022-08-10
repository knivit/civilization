package com.tsoft.civilization.tile.feature.jungle;

import com.tsoft.civilization.tile.feature.AbstractFeatureView;
import com.tsoft.civilization.util.l10n.L10n;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;
import static com.tsoft.civilization.util.l10n.L10nLanguage.RU;

public class JungleView extends AbstractFeatureView {

    private static final L10n JUNGLE_NAME = new L10n()
        .put(EN, "Jungle")
        .put(RU, "Джунгли");

    private static final L10n JUNGLE_DESCRIPTION = new L10n()
        .put(EN, "Difficult to pass")
        .put(RU, "Сложны для пересечения");

    @Override
    public String getLocalizedName() {
        return JUNGLE_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return JUNGLE_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/jungle_status.png";
    }
}
