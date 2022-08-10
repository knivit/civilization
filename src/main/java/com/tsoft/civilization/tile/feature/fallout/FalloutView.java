package com.tsoft.civilization.tile.feature.fallout;

import com.tsoft.civilization.tile.feature.AbstractFeatureView;
import com.tsoft.civilization.util.l10n.L10n;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;
import static com.tsoft.civilization.util.l10n.L10nLanguage.RU;

public class FalloutView extends AbstractFeatureView {

    private static final L10n FALLOUT_NAME = new L10n()
        .put(EN, "Fallout")
        .put(RU, "Ядерная пустошь");

    private static final L10n FALLOUT_DESCRIPTION = new L10n()
        .put(EN, "Nothing there. Only bad  conditions for any life form.")
        .put(RU, "Ничего не осталось, кроме радиоактивного загрязнения");

    @Override
    public String getLocalizedName() {
        return FALLOUT_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return FALLOUT_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/fallout_status.png";
    }
}
