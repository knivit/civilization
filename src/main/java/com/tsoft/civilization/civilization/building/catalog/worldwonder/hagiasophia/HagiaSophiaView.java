package com.tsoft.civilization.civilization.building.catalog.worldwonder.hagiasophia;

import com.tsoft.civilization.civilization.building.AbstractWorldWonderView;
import com.tsoft.civilization.util.l10n.L10n;
import lombok.Getter;

public class HagiaSophiaView extends AbstractWorldWonderView {

    private static final L10n NAME = new L10n()
        .put("en", "Hagia Sophia")
        .put("ru", "Hagia Sophia");

    public static final L10n DESCRIPTION = new L10n()
        .put("en", "Hagia Sophia")
        .put("ru", "Hagia Sophia");

    @Getter
    public final L10n name = NAME;

    @Getter
    public final String localizedDescription = DESCRIPTION.getLocalized();

    @Getter
    public final String statusImageSrc = "images/world_wonders/hagiasophia.png";
}
