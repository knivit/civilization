package com.tsoft.civilization.civilization.building.catalog.worldwonder.colossus;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.civilization.building.AbstractWorldWonderView;
import lombok.Getter;

public class ColossusView extends AbstractWorldWonderView {

    private static final L10n NAME = new L10n()
        .put("en", "The Colossus")
        .put("ru", "The Colossus");

    public static final L10n DESCRIPTION = new L10n()
        .put("en", "The Colossus")
        .put("ru", "The Colossus");

    @Getter
    public final L10n name = NAME;

    @Getter
    public final String localizedDescription = DESCRIPTION.getLocalized();

    @Getter
    public final String statusImageSrc = "images/world_wonders/colossus.png";
}
