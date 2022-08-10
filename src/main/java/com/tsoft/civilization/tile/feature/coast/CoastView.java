package com.tsoft.civilization.tile.feature.coast;

import com.tsoft.civilization.tile.feature.AbstractFeatureView;
import com.tsoft.civilization.util.l10n.L10n;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;
import static com.tsoft.civilization.util.l10n.L10nLanguage.RU;

public class CoastView extends AbstractFeatureView {

    private static final L10n COAST_NAME = new L10n()
        .put(EN, "Coast")
        .put(RU, "Побережье");

    private static final L10n COAST_DESCRIPTION = new L10n()
        .put(EN, "Coastal tiles now often go several hexes out into the water, allowing for better freedom of movement for ancient units that are restricted from deep ocean")
        .put(RU, "Ячейки побережья сейчас выходят на несколько ячеек в океан, позволяя древним юнитам, которые не могут выйти в океан, чуствовать себя свободнее");

    @Override
    public String getLocalizedName() {
        return COAST_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return COAST_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/tiles/coast_status.png";
    }
}
