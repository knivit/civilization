package com.tsoft.civilization.building.worldwonder.brandenburggate;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.building.AbstractWorldWonderView;
import lombok.Getter;

public class BrandenburgGateView extends AbstractWorldWonderView {

    private static final L10n NAME = new L10n()
        .put("en", "Brandenburg Gate")
        .put("ru", "Brandenburg Gate");

    public static final L10n DESCRIPTION = new L10n()
        .put("en", "Brandenburg Gate")
        .put("ru", "Brandenburg Gate");

    @Getter
    public final L10n name = NAME;

    @Getter
    public final String localizedDescription = DESCRIPTION.getLocalized();

    @Getter
    public final String statusImageSrc = "images/world_wonders/brandenburggate.png";
}
