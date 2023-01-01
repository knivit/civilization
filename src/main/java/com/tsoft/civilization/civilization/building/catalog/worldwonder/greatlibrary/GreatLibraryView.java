package com.tsoft.civilization.civilization.building.catalog.worldwonder.greatlibrary;

import com.tsoft.civilization.civilization.building.AbstractWorldWonderView;
import com.tsoft.civilization.util.l10n.L10n;
import lombok.Getter;

public class GreatLibraryView extends AbstractWorldWonderView {

    private static final L10n NAME = new L10n()
        .put("en", "The Great Library")
        .put("ru", "The Great Library");

    public static final L10n DESCRIPTION = new L10n()
        .put("en", "The Great Library")
        .put("ru", "The Great Library");

    @Getter
    public final L10n name = NAME;

    @Getter
    public final String localizedDescription = DESCRIPTION.getLocalized();

    @Getter
    public final String statusImageSrc = "images/world_wonders/greatlibrary.png";
}
