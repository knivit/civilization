package com.tsoft.civilization.web.render;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RenderContext {

    private final int tileWidth;
    private final int tileHeight;
}
