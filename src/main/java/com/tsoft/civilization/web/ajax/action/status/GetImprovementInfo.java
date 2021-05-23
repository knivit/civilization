package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.improvement.AbstractImprovement;
import com.tsoft.civilization.improvement.AbstractImprovementView;
import com.tsoft.civilization.improvement.L10nImprovement;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.tile.feature.L10nFeature;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.HtmlResponse;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;

import static com.tsoft.civilization.web.ajax.ServerStaticResource.*;

public class GetImprovementInfo extends AbstractAjaxRequest {

    private final GetNavigationPanel navigationPanel = new GetNavigationPanel();

    public static StringBuilder getAjax(AbstractImprovement improvement) {
        return Format.text("server.sendAsyncAjax('ajax/GetImprovementInfo', { id:'$improvement' })",
            "$improvement", improvement.getId()
        );
    }

    @Override
    public Response getJson(Request request) {
        Civilization civilization = getMyCivilization();
        if (civilization == null) {
            return JsonResponse.badRequest(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        String id = request.get("improvement");
        AbstractImprovement improvement = civilization.getTileService().findImprovementById(id);
        if (improvement == null) {
            return JsonResponse.badRequest(L10nImprovement.IMPROVEMENT_NOT_FOUND);
        }

        StringBuilder value = Format.text("""
            $navigationPanel
            $improvementInfo
            $supplyInfo
            """,

            "$navigationPanel", navigationPanel.getContent(),
            "$improvementInfo", getImprovementInfo(improvement),
            "$supplyInfo", getImprovementSupplyInfo(improvement));
        return HtmlResponse.ok(value);
    }

    private StringBuilder getImprovementInfo(AbstractImprovement improvement) {
        AbstractImprovementView view = improvement.getView();
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

    private StringBuilder getImprovementSupplyInfo(AbstractImprovement improvement) {
        // Do not show supply info for foreign improvements
        Civilization civilization = getMyCivilization();
        City improvementCity = improvement.getCity();
        if (improvementCity == null || !improvementCity.getCivilization().equals(civilization)) {
            return null;
        }

        Supply featureSupply = improvement.getSupply();
        return Format.text("""
            <table id='info_table'>
                <tr><td>$productionLabel</td><td>$production $productionImage</td></tr>
                <tr><td>$goldLabel</td><td>$gold $goldImage</td></tr>
                <tr><td>$foodLabel</td><td>$food $foodImage</td></tr>
            </table>
            """,

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
