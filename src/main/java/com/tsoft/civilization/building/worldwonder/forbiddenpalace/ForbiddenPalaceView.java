package com.tsoft.civilization.building.worldwonder.forbiddenpalace;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.building.AbstractWorldWonderView;
import lombok.Getter;

public class ForbiddenPalaceView extends AbstractWorldWonderView {

    private static final L10n NAME = new L10n()
        .put("en", "The Forbidden Palace")
        .put("ru", "The Forbidden Palace");

    public static final L10n DESCRIPTION = new L10n()
        .put("en", "The Forbidden Palace")
        .put("ru", "The Forbidden Palace");

    @Getter
    public final L10n name = NAME;

    @Getter
    public final String localizedDescription = DESCRIPTION.getLocalized();

    @Getter
    public final String statusImageSrc = "images/world_wonders/forbiddenpalace.png";
}
