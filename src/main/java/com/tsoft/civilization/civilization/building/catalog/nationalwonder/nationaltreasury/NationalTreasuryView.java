package com.tsoft.civilization.civilization.building.catalog.nationalwonder.nationaltreasury;

import com.tsoft.civilization.civilization.building.AbstractNationalWonderView;
import com.tsoft.civilization.util.l10n.L10n;
import lombok.Getter;

public class NationalTreasuryView extends AbstractNationalWonderView {

    private static final L10n NAME = new L10n()
        .put("en", "National Treasury")
        .put("ru", "National Treasury");

    public static final L10n DESCRIPTION = new L10n()
        .put("en", "National Treasury")
        .put("ru", "National Treasury");

    @Getter
    public final L10n name = NAME;

    @Getter
    public final String localizedDescription = DESCRIPTION.getLocalized();

    @Getter
    public final String statusImageSrc = "images/national_wonders/nationaltreasury.png";
}
