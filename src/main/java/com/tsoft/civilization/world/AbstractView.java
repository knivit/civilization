package com.tsoft.civilization.world;

import com.tsoft.civilization.L10n.L10n;

public interface AbstractView {

    L10n getName();

    String getLocalizedName();
    String getLocalizedDescription();
    String getStatusImageSrc();
}
