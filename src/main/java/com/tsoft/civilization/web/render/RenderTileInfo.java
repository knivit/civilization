package com.tsoft.civilization.web.render;

import lombok.RequiredArgsConstructor;

// One tile render area
@RequiredArgsConstructor
public class RenderTileInfo {
    public final int x;
    public final int y;
    public final int col;
    public final int row;
}
