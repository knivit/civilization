package com.tsoft.civilization.web.render.city;

import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.web.render.GraphicsContext;
import com.tsoft.civilization.web.render.RenderContext;
import com.tsoft.civilization.web.render.RenderTileInfo;
import com.tsoft.civilization.web.render.civilization.CivilizationRender;

import java.awt.*;

import static com.tsoft.civilization.web.render.Render.r;

public class CityRender {

    private final CivilizationRender civilizationRender = new CivilizationRender();
    private final CapitalStarRender capitalStarRender = new CapitalStarRender();

    public void render(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, City city) {
        int x = tileInfo.x;
        int y = tileInfo.y;
        float[] ox = context.getHexBordersX();
        float[] oy = context.getHexBordersY();

        Color color = civilizationRender.getColor(city.getCivilization());
        graphics.getG().setColor(color);
        graphics.getG2().setStroke(new BasicStroke(2));

        String text = city.getName().getEnglish();
        int width = graphics.getG2().getFontMetrics().stringWidth(text);
        graphics.getG2().drawString(text, r(x + (context.getTileWidth() - width) / 2), r(y + 1.5f * oy[0]));

        if (city.isCapital()) {
            capitalStarRender.render(context, graphics, tileInfo, color);
        }
    }

}
