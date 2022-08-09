package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.building.AbstractBuildingView;
import com.tsoft.civilization.building.BuildingFactory;
import com.tsoft.civilization.building.L10nBuilding;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.tile.feature.AbstractFeature;
import com.tsoft.civilization.tile.feature.AbstractFeatureView;
import com.tsoft.civilization.tile.feature.FeatureFactory;
import com.tsoft.civilization.tile.feature.L10nFeature;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.HtmlResponse;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;

import static com.tsoft.civilization.web.ajax.ServerStaticResource.*;

public class GetBuildingInfo extends AbstractAjaxRequest {

    private final GetNavigationPanel navigationPanel = new GetNavigationPanel();

    public static StringBuilder getAjax(AbstractBuilding building) {
        return Format.text("server.sendAsyncAjax('ajax/GetBuildingInfo', { id:'$building' })",
            "$building", building.getClassUuid()
        );
    }

    @Override
    public Response getJson(Request request) {
        String classUuid = request.get("id");
        AbstractBuilding building = BuildingFactory.findByClassUuid(classUuid);
        if (building == null) {
            return JsonResponse.badRequest(L10nBuilding.BUILDING_NOT_FOUND);
        }

        StringBuilder value = Format.text("""
            $navigationPanel
            $buildingInfo
            $supplyInfo
            """,

            "$navigationPanel", navigationPanel.getContent(),
            "$buildingInfo", getBuildingInfo(building),
            "$supplyInfo", getBuildingSupplyInfo(building));
        return HtmlResponse.ok(value);
    }

    private StringBuilder getBuildingInfo(AbstractBuilding building) {
        AbstractBuildingView view = building.getView();
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

    private StringBuilder getBuildingSupplyInfo(AbstractBuilding building) {
        Supply supply = building.getSupply();
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
