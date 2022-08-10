package com.tsoft.civilization.civilization.building.worldwonder.chichenitza;

import com.tsoft.civilization.civilization.building.AbstractWorldWonderView;
import com.tsoft.civilization.util.l10n.L10n;
import lombok.Getter;

public class ChichenItzaView extends AbstractWorldWonderView {

    private static final L10n NAME = new L10n()
        .put("en", "Chichen Itza")
        .put("ru", "Chichen Itza");

    public static final L10n DESCRIPTION = new L10n()
        .put("en", "Chichen Itza")
        .put("ru", "Chichen Itza");

    @Getter
    public final L10n name = NAME;

    @Getter
    public final String localizedDescription = DESCRIPTION.getLocalized();

    @Getter
    public final String statusImageSrc = "images/world_wonders/chichenitza.png";
}
