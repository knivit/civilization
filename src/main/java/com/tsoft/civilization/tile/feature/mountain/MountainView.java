package com.tsoft.civilization.tile.feature.mountain;

import com.tsoft.civilization.tile.feature.AbstractFeatureView;
import com.tsoft.civilization.util.l10n.L10n;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;
import static com.tsoft.civilization.util.l10n.L10nLanguage.RU;

public class MountainView extends AbstractFeatureView {

    private static final L10n MOUNTAIN_NAME = new L10n()
        .put(EN, "Mountain")
        .put(RU, "Высокая гора");

    private static final L10n MOUNTAIN_DESCRIPTION = new L10n()
        .put(EN, "Mountains are impassable for any units except an aerial")
        .put(RU, "Горы могут пересекать только воздушные юниты");

    @Override
    public String getLocalizedName() {
        return MOUNTAIN_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return MOUNTAIN_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/mountain_status.png";
    }
}
