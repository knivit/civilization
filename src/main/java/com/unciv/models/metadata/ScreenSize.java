package com.unciv.models.metadata;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ScreenSize {
    Tiny(750f, 500f),
    Small(900f, 600f),
    Medium(1050f, 700f),
    Large(1200f, 800f),
    Huge(1500f, 1000f);

    private final float virtualWidth;
    private final float virtualHeight;
}
