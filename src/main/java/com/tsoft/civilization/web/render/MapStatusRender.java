package com.tsoft.civilization.web.render;

import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.web.render.tile.TileStatusRender;

public class MapStatusRender {

    private final TileStatusRender tileStatusRender = new TileStatusRender();

    public void render(StatusContext statusContext, TilesMap map) {
        StringBuilder buf = new StringBuilder();
        buf.append("<table id='tiles-table'>\n");

        for (int y = 0; y < map.getHeight(); y ++) {
            buf.append("<tr>");

            for (int x = 0; x < map.getWidth(); x ++) {
                buf.append("<td>\n");
                buf.append(tileStatusRender.render(statusContext, map.getTile(x, y)));
                buf.append("</td>\n");
            }

            buf.append("</tr>\n");
        }

        buf.append("</table>\n");

        statusContext.setTiles(buf.toString());
    }
}
