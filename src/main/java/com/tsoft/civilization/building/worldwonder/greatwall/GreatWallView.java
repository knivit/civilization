package com.tsoft.civilization.building.worldwonder.greatwall;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.building.AbstractWorldWonderView;
import lombok.Getter;

public class GreatWallView extends AbstractWorldWonderView {

    private static final L10n NAME = new L10n()
        .put("en", "The Great Wall")
        .put("ru", "The Great Wall");

    public static final L10n DESCRIPTION = new L10n()
        .put("en", "The Great Wall")
        .put("ru", "The Great Wall");

    @Getter
    public final L10n name = NAME;

    @Getter
    public final String localizedDescription = DESCRIPTION.getLocalized();

    @Getter
    public final String statusImageSrc = "images/world_wonders/greatwall.png";
}
