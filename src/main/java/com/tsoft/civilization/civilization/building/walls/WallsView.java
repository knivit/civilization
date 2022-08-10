package com.tsoft.civilization.civilization.building.walls;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.civilization.building.AbstractBuildingView;
import lombok.Getter;

public class WallsView extends AbstractBuildingView {

    public static final L10n NAME = new L10n()
        .put("en", "Walls")
        .put("ru", "Стены");

    public static final L10n DESCRIPTION = new L10n()
        .put("en", "Walls increase a city's Defense Strength making the city more difficult to capture. Walls are quite useful for cities located along a civilization's frontier.")
        .put("ru", "Стены увеличивают оборону города, усложняя его захват. Стены полезны для городов, расположенных вблизи границ цивилизации.");

    @Getter
    public final L10n name = NAME;

    @Getter
    public final String localizedDescription = DESCRIPTION.getLocalized();

    @Getter
    public final String statusImageSrc = "images/status/buildings/walls.png";
}
