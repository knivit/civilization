package com.tsoft.civilization.web.ajax;

import com.tsoft.civilization.web.ajax.action.city.*;
import com.tsoft.civilization.web.ajax.action.civilization.DeclareWarActionRequest;
import com.tsoft.civilization.web.ajax.action.civilization.NextTurnActionRequest;
import com.tsoft.civilization.web.ajax.action.status.*;
import com.tsoft.civilization.web.ajax.action.unit.AttackActionRequest;
import com.tsoft.civilization.web.ajax.action.unit.CaptureUnitActionRequest;
import com.tsoft.civilization.web.ajax.action.unit.DestroyUnitActionRequest;
import com.tsoft.civilization.web.ajax.action.unit.MoveUnitActionRequest;
import com.tsoft.civilization.web.ajax.action.unit.settlers.BuildCityActionRequest;
import com.tsoft.civilization.web.ajax.action.unit.workers.BuildFarmActionRequest;
import com.tsoft.civilization.web.ajax.action.unit.workers.RemoveForestActionRequest;
import com.tsoft.civilization.web.ajax.action.unit.workers.RemoveHillActionRequest;
import com.tsoft.civilization.web.ajax.action.world.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
class RequestsMap {

    private static final Map<String, AbstractAjaxRequest> REQUESTS;

    static {
        Map<String, AbstractAjaxRequest> requests = new HashMap<>();
        REQUESTS = Collections.unmodifiableMap(requests);

        // City
        requests.put(BuyTileActionRequest.class.getSimpleName(), BuyTileActionRequest.newInstance());
        requests.put(BuildBuildingActionRequest.class.getSimpleName(), BuildBuildingActionRequest.newInstance());
        requests.put(BuildUnitActionRequest.class.getSimpleName(), BuildUnitActionRequest.newInstance());
        requests.put(BuyBuildingActionRequest.class.getSimpleName(), BuyBuildingActionRequest.newInstance());
        requests.put(BuyUnitActionRequest.class.getSimpleName(), BuyUnitActionRequest.newInstance());
        requests.put(DestroyBuildingActionRequest.class.getSimpleName(), DestroyBuildingActionRequest.newInstance());

        // Civilization
        requests.put(DeclareWarActionRequest.class.getSimpleName(), DeclareWarActionRequest.newInstance());
        requests.put(NextTurnActionRequest.class.getSimpleName(), NextTurnActionRequest.newInstance());

        // Status
        requests.put(GetStartStatus.class.getSimpleName(), new GetStartStatus());
        requests.put(GetTileStatus.class.getSimpleName(), new GetTileStatus());
        requests.put(GetTileInfo.class.getSimpleName(), new GetTileInfo());
        requests.put(GetFeatureInfo.class.getSimpleName(), new GetFeatureInfo());
        requests.put(GetImprovementInfo.class.getSimpleName(), new GetImprovementInfo());
        requests.put(GetUnitStatus.class.getSimpleName(), new GetUnitStatus());
        requests.put(GetCityStatus.class.getSimpleName(), new GetCityStatus());
        requests.put(GetMyCities.class.getSimpleName(), new GetMyCities());
        requests.put(GetMyUnits.class.getSimpleName(), new GetMyUnits());
        requests.put(GetEvents.class.getSimpleName(), new GetEvents());
        requests.put(GetCivilizations.class.getSimpleName(), new GetCivilizations());
        requests.put(GetCivilizationStatus.class.getSimpleName(), GetCivilizationStatus.newInstance());
        requests.put(GetConstructionStatus.class.getSimpleName(), new GetConstructionStatus());
        requests.put(GetBuildingStatus.class.getSimpleName(), new GetBuildingStatus());
        requests.put(GetBuildingInfo.class.getSimpleName(), new GetBuildingInfo());
        requests.put(GetControlPanel.class.getSimpleName(), GetControlPanel.newInstance());

        // Unit's Common
        requests.put(AttackActionRequest.class.getSimpleName(), new AttackActionRequest());
        requests.put(CaptureUnitActionRequest.class.getSimpleName(), new CaptureUnitActionRequest());
        requests.put(DestroyUnitActionRequest.class.getSimpleName(), new DestroyUnitActionRequest());
        requests.put(MoveUnitActionRequest.class.getSimpleName(), new MoveUnitActionRequest());

        // Settlers
        requests.put(BuildCityActionRequest.class.getSimpleName(), new BuildCityActionRequest());

        // Workers
        requests.put(BuildFarmActionRequest.class.getSimpleName(), new BuildFarmActionRequest());
        requests.put(RemoveForestActionRequest.class.getSimpleName(), new RemoveForestActionRequest());
        requests.put(RemoveHillActionRequest.class.getSimpleName(), new RemoveHillActionRequest());

        // Worlds
        requests.put(SelectLanguageRequest.class.getSimpleName(), new SelectLanguageRequest());
        requests.put(GetWorldsRequest.class.getSimpleName(), new GetWorldsRequest());
        requests.put(CreateWorldRequest.class.getSimpleName(), new CreateWorldRequest());
        requests.put(GetCreateWorldFormRequest.class.getSimpleName(), new GetCreateWorldFormRequest());
        requests.put(JoinWorldRequest.class.getSimpleName(), new JoinWorldRequest());
        requests.put(LoadWorldRequest.class.getSimpleName(), new LoadWorldRequest());
    }

    /** Returns NULL for unknown requests */
    public static AbstractAjaxRequest get(String name) {
        AbstractAjaxRequest request = REQUESTS.get(name);

        if (request == null) {
            log.info("Request {} not found", name);
        }
        return request;
    }
}
