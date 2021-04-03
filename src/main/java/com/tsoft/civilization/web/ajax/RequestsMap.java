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
public class RequestsMap {
    private static final Map<String, AbstractAjaxRequest> REQUESTS;

    static {
        Map<String, AbstractAjaxRequest> requests = new HashMap<>();
        REQUESTS = Collections.unmodifiableMap(requests);

        // City
        requests.put(BuildBuildingActionRequest.class.getSimpleName(), new BuildBuildingActionRequest());
        requests.put(BuildUnitActionRequest.class.getSimpleName(), new BuildUnitActionRequest());
        requests.put(BuyBuildingActionRequest.class.getSimpleName(), new BuyBuildingActionRequest());
        requests.put(BuyUnitActionRequest.class.getSimpleName(), new BuyUnitActionRequest());
        requests.put(DestroyBuildingActionRequest.class.getSimpleName(), new DestroyBuildingActionRequest());

        // Civilization
        requests.put(DeclareWarActionRequest.class.getSimpleName(), new DeclareWarActionRequest());
        requests.put(NextTurnActionRequest.class.getSimpleName(), new NextTurnActionRequest());

        // Status
        requests.put(GetStartStatus.class.getSimpleName(), new GetStartStatus());
        requests.put(GetTileStatus.class.getSimpleName(), new GetTileStatus());
        requests.put(GetTileInfo.class.getSimpleName(), new GetTileInfo());
        requests.put(GetFeatureInfo.class.getSimpleName(), new GetFeatureInfo());
        requests.put(GetUnitStatus.class.getSimpleName(), new GetUnitStatus());
        requests.put(GetCityStatus.class.getSimpleName(), new GetCityStatus());
        requests.put(GetMyCities.class.getSimpleName(), new GetMyCities());
        requests.put(GetMyUnits.class.getSimpleName(), new GetMyUnits());
        requests.put(GetEvents.class.getSimpleName(), new GetEvents());
        requests.put(GetCivilizations.class.getSimpleName(), new GetCivilizations());
        requests.put(GetCivilizationStatus.class.getSimpleName(), new GetCivilizationStatus());
        requests.put(GetBuildingStatus.class.getSimpleName(), new GetBuildingStatus());
        requests.put(GetControlPanel.class.getSimpleName(), new GetControlPanel());

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
