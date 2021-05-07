package com.tsoft.civilization.world;

import com.tsoft.civilization.L10n.L10n;

public interface AbstractView {

    L10n getName();

    default String getLocalizedName() {
        return getName().getLocalized();
    }

    String getLocalizedDescription();
    String getStatusImageSrc();
}
