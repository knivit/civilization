package com.tsoft.civilization.civilization.building.palace;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.civilization.building.AbstractBuildingView;
import lombok.Getter;

public class PalaceView extends AbstractBuildingView {

    public static final L10n NAME = new L10n()
        .put("en", "Palace")
        .put("ru", "Дворец");

    public static final L10n DESCRIPTION = new L10n()
        .put("en", "Indicates this City is the Capital of the empire. Connecting other Cities to the Capital by Road will produce additional Gold.")
        .put("ru", "Указывает, что город является столицей империи. Соединяя другие города со столицей дорогами, увеличивается производство золота.");

    @Getter
    public final L10n name = NAME;

    @Getter
    public final String localizedDescription = DESCRIPTION.getLocalized();

    @Getter
    public final String statusImageSrc = "images/status/buildings/palace.png";
}
