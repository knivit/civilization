package com.tsoft.civilization.building.market;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.building.AbstractBuildingView;
import lombok.Getter;

public class MarketView extends AbstractBuildingView {

    public static final L10n NAME = new L10n()
        .put("en", "Market")
        .put("ru", "Рынок");

    public static final L10n DESCRIPTION = new L10n()
        .put("en", "The Market significantly increases a city's output of gold.")
        .put("ru", "Рынок значительно увеличивает производство золота в городе");

    @Getter
    public final L10n name = NAME;

    @Getter
    public final String localizedDescription = DESCRIPTION.getLocalized();

    @Getter
    public final String statusImageSrc = "images/status/buildings/market.png";
}
