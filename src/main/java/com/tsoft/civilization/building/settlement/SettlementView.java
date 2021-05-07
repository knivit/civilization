package com.tsoft.civilization.building.settlement;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.building.AbstractBuildingView;
import lombok.Getter;

public class SettlementView extends AbstractBuildingView {

    public static final L10n NAME = new L10n()
        .put("en", "Settlement")
        .put("ru", "Поселение");

    public static final L10n DESCRIPTION = new L10n()
        .put("en", "The first building in the city.")
        .put("ru", "Первое здание в городе.");

    @Getter
    public final L10n name = NAME;

    @Getter
    public final String localizedDescription = DESCRIPTION.getLocalized();

    @Getter
    public final String statusImageSrc = "images/status/buildings/settlement.png";
}
