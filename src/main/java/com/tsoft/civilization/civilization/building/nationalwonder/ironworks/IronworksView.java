package com.tsoft.civilization.civilization.building.nationalwonder.ironworks;

import com.tsoft.civilization.civilization.building.AbstractNationalWonderView;
import com.tsoft.civilization.util.l10n.L10n;
import lombok.Getter;

public class IronworksView extends AbstractNationalWonderView {

    private static final L10n NAME = new L10n()
        .put("en", "Ironworks")
        .put("ru", "Ironworks");

    private static final L10n DESCRIPTION = new L10n()
        .put("en", "Ironworks")
        .put("ru", "Ironworks");

    @Getter
    public final L10n name = NAME;

    @Getter
    public final String localizedDescription = DESCRIPTION.getLocalized();

    @Getter
    public final String statusImageSrc = "images/national_wonders/ironworks.png";
}
