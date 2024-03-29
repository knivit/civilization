package com.tsoft.civilization.web.render.tile;

import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.tile.feature.AbstractFeature;
import com.tsoft.civilization.web.render.StatusContext;
import com.tsoft.civilization.economic.Supply;

public class TileStatusRender {

    public String render(StatusContext statusContext, AbstractTerrain tile) {
        StringBuilder buf = new StringBuilder();
        buf.append("<table>");
        buf.append("<tr>")
            .append("<th>Name</th>")
            .append("<th>Food</th>")
            .append("<th>Production</th>")
            .append("<th>Gold</th>")
            .append("</tr>");

        Supply tileSupply = tile.getBaseSupply();
        buf.append("<tr>")
            .append("<td>").append(tile.getView().getLocalizedName()).append("</td>")
            .append("<td>").append(tileSupply.getFood()).append("</td>")
            .append("<td>").append(tileSupply.getProduction()).append("</td>")
            .append("<td>").append(tileSupply.getGold()).append("</td>")
            .append("</tr>");

        for (AbstractFeature feature : tile.getFeatures()) {
            Supply featureSupply = feature.getSupply();
            buf.append("<tr>")
                .append("<td>")
                    .append(feature.isBlockingTileSupply() ? "<b>" : "")
                    .append(feature.getView().getLocalizedName())
                    .append(feature.isBlockingTileSupply() ? "</b>" : "")
                .append("</td>")
                .append("<td>").append(featureSupply.getFood()).append("</td>")
                .append("<td>").append(featureSupply.getProduction()).append("</td>")
                .append("<td>").append(featureSupply.getGold()).append("</td>")
                .append("</tr>");
        }

        buf.append("</table>");
        return buf.toString();
    }
}
