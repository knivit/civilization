package com.tsoft.civilization.tile.feature.ice;

import com.tsoft.civilization.tile.feature.AbstractFeatureView;
import com.tsoft.civilization.util.l10n.L10n;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;
import static com.tsoft.civilization.util.l10n.L10nLanguage.RU;

public class IceView extends AbstractFeatureView {

    private static final L10n ICE_NAME = new L10n()
        .put(EN, "Ice")
        .put(RU, "Лед");

    private static final L10n ICE_DESCRIPTION = new L10n()
        .put(EN, "It is cold and windy here")
        .put(RU, "Здесь холодно и ветренно");

    @Override
    public String getLocalizedName() {
        return ICE_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return ICE_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/ice_status.png";
    }
}
