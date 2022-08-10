package com.tsoft.civilization.civilization.building.worldwonder.bigben;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.civilization.building.AbstractWorldWonderView;
import lombok.Getter;

public class BigBenView extends AbstractWorldWonderView {

    private static final L10n NAME = new L10n()
        .put("en", "Big Ben")
        .put("ru", "Big Ben");

    public static final L10n DESCRIPTION = new L10n()
        .put("en", "Big Ben")
        .put("ru", "Big Ben");

    @Getter
    public final L10n name = NAME;

    @Getter
    public final String localizedDescription = DESCRIPTION.getLocalized();

    @Getter
    public final String statusImageSrc = "images/world_wonders/bigben.png";
}
