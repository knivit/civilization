package com.tsoft.civilization.civilization.building.monument;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.civilization.building.AbstractBuildingView;
import lombok.Getter;

public class MonumentView extends AbstractBuildingView {

    public static final L10n NAME = new L10n()
        .put("en", "Monument")
        .put("ru", "Памятник");

    public static final L10n DESCRIPTION = new L10n()
        .put("en", "The Monument increases the Culture of a city speeding the growth of the city's territory and the civilization's acquisition of Social Policies.")
        .put("ru", "Памятник увеличивает культуру в городе, что ускоряет рост территории города и приобретение цивилизацией социальных политик.");

    @Getter
    public final L10n name = NAME;

    @Getter
    public final String localizedDescription = DESCRIPTION.getLocalized();

    @Getter
    public final String statusImageSrc = "images/status/buildings/monument.png";
}
