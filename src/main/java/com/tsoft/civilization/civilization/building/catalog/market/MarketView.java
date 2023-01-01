package com.tsoft.civilization.civilization.building.catalog.market;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.civilization.building.AbstractBuildingView;
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
