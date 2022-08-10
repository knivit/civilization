package com.tsoft.civilization.tile.feature.oasis;

import com.tsoft.civilization.tile.feature.AbstractFeatureView;
import com.tsoft.civilization.util.l10n.L10n;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;
import static com.tsoft.civilization.util.l10n.L10nLanguage.RU;

public class OasisView extends AbstractFeatureView {

    private static final L10n OASIS_NAME = new L10n()
        .put(EN, "Oasis")
        .put(RU, "Оазис");

    private static final L10n OASIS_DESCRIPTION = new L10n()
        .put(EN, "Only source of water in desert")
        .put(RU, "Единственный источник воды в пустыне");

    @Override
    public String getLocalizedName() {
        return OASIS_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return OASIS_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/oasis_status.png";
    }
}
