package com.tsoft.civilization.building.nationalwonder.nationalepic;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.building.AbstractNationalWonderView;
import lombok.Getter;

public class NationalEpicView extends AbstractNationalWonderView {

    private static final L10n NAME = new L10n()
        .put("en", "National Epic")
        .put("ru", "National Epic");

    public static final L10n DESCRIPTION = new L10n()
        .put("en", "National Epic")
        .put("ru", "National Epic");

    @Getter
    public final L10n name = NAME;

    @Getter
    public final String localizedDescription = DESCRIPTION.getLocalized();

    @Getter
    public final String statusImageSrc = "images/national_wonders/nationalepic.png";
}
