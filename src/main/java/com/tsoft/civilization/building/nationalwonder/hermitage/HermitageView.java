package com.tsoft.civilization.building.nationalwonder.hermitage;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.building.AbstractNationalWonderView;
import lombok.Getter;

public class HermitageView extends AbstractNationalWonderView {

    private static final L10n NAME = new L10n()
        .put("en", "Hermitage")
        .put("ru", "Эрмитаж");

    public static final L10n DESCRIPTION = new L10n()
        .put("en", "Hermitage")
        .put("ru", "Эрмитаж");

    @Getter
    public final L10n name = NAME;

    @Getter
    public final String localizedDescription = DESCRIPTION.getLocalized();

    @Getter
    public final String statusImageSrc = "images/national_wonders/hermitage.png";
}