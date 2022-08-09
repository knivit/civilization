package com.tsoft.civilization.world;

import com.tsoft.civilization.util.l10n.L10n;

public interface AbstractView {

    L10n getName();

    default String getLocalizedName() {
        return getName().getLocalized();
    }

    String getLocalizedDescription();
    String getStatusImageSrc();
}
