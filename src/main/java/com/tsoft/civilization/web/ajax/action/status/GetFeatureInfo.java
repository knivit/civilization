package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.tile.feature.FeatureCatalog;
import com.tsoft.civilization.tile.feature.L10nFeature;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.response.HtmlResponse;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.world.economic.Supply;
import com.tsoft.civilization.tile.feature.TerrainFeature;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.tile.feature.AbstractFeatureView;

import static com.tsoft.civilization.web.ajax.ServerStaticResource.*;

public class GetFeatureInfo extends AbstractAjaxRequest {

    private final GetNavigationPanel navigationPanel = new GetNavigationPanel();

    public static StringBuilder getAjax(TerrainFeature feature) {
        return Format.text("server.sendAsyncAjax('ajax/GetFeatureInfo', { feature:'$feature' })",
            "$feature", feature.getClassUuid()
        );
    }

    @Override
    public Response getJson(Request request) {
        String featureClassUuid = request.get("feature");
        TerrainFeature feature = FeatureCatalog.findByClassUuid(featureClassUuid);
        if (feature == null) {
            return JsonResponse.badRequest(L10nFeature.FEATURE_NOT_FOUND);
        }

        StringBuilder value = Format.text("""
            $navigationPanel
            $featureInfo
            $supplyInfo
            """,

            "$navigationPanel", navigationPanel.getContent(),
            "$featureInfo", getFeatureInfo(feature),
            "$supplyInfo", getFeatureSupplyInfo(feature));
        return HtmlResponse.ok(value);
    }

    private StringBuilder getFeatureInfo(TerrainFeature feature) {
        AbstractFeatureView view = feature.getView();
        return Format.text("""
            <table id='title_table'>
                <tr><td>$name</td></tr>
                <tr><td><img src='$image'/></td></tr>
                <tr><td>$description</td></tr>
            </table>
            """,

            "$name", view.getLocalizedName(),
            "$image", view.getStatusImageSrc(),
            "$description", view.getLocalizedDescription()
        );
    }

    private StringBuilder getFeatureSupplyInfo(TerrainFeature feature) {
        Supply featureSupply = feature.getSupply();
        return Format.text("""
            <table id='info_table'>
                <tr><th colspan='2'>$features</th></tr>
                <tr><td>$productionLabel</td><td>$production $productionImage</td></tr>
                <tr><td>$goldLabel</td><td>$gold $goldImage</td></tr>
                <tr><td>$foodLabel</td><td>$food $foodImage</td></tr>
            </table>
            """,

            "$features", L10nFeature.FEATURES,

            "$productionLabel", L10nFeature.FEATURE_PRODUCTION,
            "$production", featureSupply.getProduction(),
            "$productionImage", PRODUCTION_IMAGE,
            "$goldLabel", L10nFeature.FEATURE_GOLD,
            "$gold", featureSupply.getGold(),
            "$goldImage", GOLD_IMAGE,
            "$foodLabel", L10nFeature.FEATURE_FOOD,
            "$food", featureSupply.getFood(),
            "$foodImage", FOOD_IMAGE
        );
    }
}
