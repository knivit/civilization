package com.tsoft.civilization.tile.feature.floodplain;

import com.tsoft.civilization.tile.feature.AbstractFeatureView;
import com.tsoft.civilization.util.l10n.L10n;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;
import static com.tsoft.civilization.util.l10n.L10nLanguage.RU;

public class FloodPlainView extends AbstractFeatureView {

    private static final L10n FLOODPLAIN_NAME = new L10n()
        .put(EN, "Flooded plain")
        .put(RU, "Затопленная равнина");

    private static final L10n FLOODPLAIN_DESCRIPTION = new L10n()
        .put(EN, "May be to think how to do it more useful ?")
        .put(RU, "Может, подумать как сделать ее более полезной ?");

    @Override
    public String getLocalizedName() {
        return FLOODPLAIN_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return FLOODPLAIN_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/floodplain_status.png";
    }
}
