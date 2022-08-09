package com.tsoft.civilization.building.worldwonder.angkorwat;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.building.AbstractWorldWonderView;
import lombok.Getter;

public class AngkorWatView extends AbstractWorldWonderView {

    private static final L10n NAME = new L10n()
        .put("en", "Angkor Wat")
        .put("ru", "Angkor Wat");

    public static final L10n DESCRIPTION = new L10n()
        .put("en", "Angkor Wat")
        .put("ru", "Angkor Wat");

    @Getter
    public final L10n name = NAME;

    @Getter
    public final String localizedDescription = DESCRIPTION.getLocalized();

    @Getter
    public final String statusImageSrc = "images/world_wonders/angkorwat.png";
}
