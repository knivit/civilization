package com.tsoft.civilization.building.granary;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.building.AbstractBuildingView;
import lombok.Getter;

public class GranaryView extends AbstractBuildingView {

    public static final L10n NAME = new L10n()
        .put("en", "Granary")
        .put("ru", "Амбар");

    public static final L10n DESCRIPTION = new L10n()
        .put("en", "Each source of Wheat Bananas and Deer worked by this City produce +1 Food.")
        .put("ru", "Каждый источник бананов и оленей, обрабатываемый этим городом, производит +1 пищи");

    @Getter
    public final L10n name = NAME;

    @Getter
    public final String localizedDescription = DESCRIPTION.getLocalized();

    @Getter
    public final String statusImageSrc = "images/status/buildings/granary.png";
}
