package com.tsoft.civilization.web.render.improvement.ancientruins;

import com.tsoft.civilization.tile.resource.bonus.AncientRuins;
import com.tsoft.civilization.web.render.*;

public class AncientRuinsRender implements Render<AncientRuins> {

    private final IconRender icon = new IconRender("web/images/resource/bonus/ancient_ruins.png");

    @Override
    public void render(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, AncientRuins objToRender) {
        icon.render(context, graphics, tileInfo);
    }
}
