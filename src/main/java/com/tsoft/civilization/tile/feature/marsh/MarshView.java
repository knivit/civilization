package com.tsoft.civilization.tile.feature.marsh;

import com.tsoft.civilization.tile.feature.AbstractFeatureView;
import com.tsoft.civilization.util.l10n.L10n;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;
import static com.tsoft.civilization.util.l10n.L10nLanguage.RU;

public class MarshView extends AbstractFeatureView {

    private static final L10n MARSH_NAME = new L10n()
        .put(EN, "Marsh")
        .put(RU, "Болото");

    private static final L10n MARSH_DESCRIPTION = new L10n()
        .put(EN, "Evil spirits populate them")
        .put(RU, "Их населяют злые духи");

    @Override
    public String getLocalizedName() {
        return MARSH_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return MARSH_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/marsh_status.png";
    }
}
