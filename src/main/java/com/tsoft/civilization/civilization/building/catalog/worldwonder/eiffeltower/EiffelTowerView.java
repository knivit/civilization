package com.tsoft.civilization.civilization.building.catalog.worldwonder.eiffeltower;

import com.tsoft.civilization.civilization.building.AbstractWorldWonderView;
import com.tsoft.civilization.util.l10n.L10n;
import lombok.Getter;

public class EiffelTowerView extends AbstractWorldWonderView {

    private static final L10n NAME = new L10n()
        .put("en", "Eiffel Tower")
        .put("ru", "Eiffel Tower");

    public static final L10n DESCRIPTION = new L10n()
        .put("en", "Eiffel Tower")
        .put("ru", "Eiffel Tower");

    @Getter
    public final L10n name = NAME;

    @Getter
    public final String localizedDescription = DESCRIPTION.getLocalized();

    @Getter
    public final String statusImageSrc = "images/world_wonders/eiffeltower.png";
}
