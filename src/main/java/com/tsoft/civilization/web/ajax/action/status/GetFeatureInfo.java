package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.L10n.*;
import com.tsoft.civilization.tile.feature.FeatureCatalog;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.world.economic.Supply;
import com.tsoft.civilization.tile.feature.TerrainFeature;
import com.tsoft.civilization.web.response.ContentType;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.tile.feature.AbstractFeatureView;

public class GetFeatureInfo extends AbstractAjaxRequest {
    @Override
    public Response getJSON(Request request) {
        String featureClassUuid = request.get("feature");
        TerrainFeature<?> feature = FeatureCatalog.findByClassUuid(featureClassUuid);
        if (feature == null) {
            return Response.newErrorInstance(L10nFeature.FEATURE_NOT_FOUND);
        }

        StringBuilder value = Format.text(
            "$navigationPanel\n" +
            "$featureInfo\n" +
            "$supplyInfo\n",

            "$navigationPanel", getNavigationPanel(),
            "$featureInfo", getFeatureInfo(feature),
            "$supplyInfo", getFeatureSupplyInfo(feature));
        return new Response(ResponseCode.OK, value.toString(), ContentType.TEXT_HTML);
    }

    private StringBuilder getFeatureInfo(TerrainFeature<?> feature) {
        AbstractFeatureView view = feature.getView();
        return Format.text(
            "<table id='title_table'>" +
                "<tr><td>$name</td></tr>" +
                "<tr><td><img src='$image'/></td></tr>" +
                "<tr><td>$description</td></tr>" +
            "</table>",

            "$name", view.getLocalizedName(),
            "$image", view.getStatusImageSrc(),
            "$description", view.getLocalizedDescription()
        );
    }

    private StringBuilder getFeatureSupplyInfo(TerrainFeature<?> feature) {
        Supply featureSupply = feature.getSupply();
        return Format.text(
            "<table id='info_table'>" +
                "<tr><th colspan='2'>$features</th></tr>" +
                "<tr><td>$productionLabel</td><td>$production</td></tr>" +
                "<tr><td>$goldLabel</td><td>$gold</td></tr>" +
                "<tr><td>$foodLabel</td><td>$food</td></tr>" +
            "</table>",

            "$features", L10nFeature.FEATURES,

            "$productionLabel", L10nFeature.FEATURE_PRODUCTION, "$production", featureSupply.getProduction(),
            "$goldLabel", L10nFeature.FEATURE_GOLD, "$gold", featureSupply.getGold(),
            "$foodLabel", L10nFeature.FEATURE_FOOD, "$food", featureSupply.getFood()
        );
    }
}
