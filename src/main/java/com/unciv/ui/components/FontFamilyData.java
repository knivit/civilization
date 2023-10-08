package com.unciv.ui.components;

// If save in `GameSettings` need use invariantFamily.
// If show to user need use localName.
// If save localName in `GameSettings` may generate garbled characters by encoding.

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FontFamilyData {

    public static final FontFamilyData DEFAULT = new FontFamilyData("Default Font", Fonts.DEFAULT_FONT_FAMILY);

    @Getter
    private final String localName;

    @Getter
    private final String invariantName = localName;

    @Getter
    private final String filePath;
}
