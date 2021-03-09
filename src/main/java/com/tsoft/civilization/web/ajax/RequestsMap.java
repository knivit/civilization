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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RequestsMap {
    private static final Map<String, AbstractAjaxRequest> REQUESTS;

    static {
        Map<String, AbstractAjaxRequest> requests = new HashMap<>();
        REQUESTS = Collections.unmodifiableMap(requests);

        // Worlds
        requests.put(GetWorldsRequest.class.getSimpleName(), new GetWorldsRequest());
        requests.put(CreateWorldRequest.class.getSimpleName(), new CreateWorldRequest());
        requests.put(GetCreateWorldForm.class.getSimpleName(), new GetCreateWorldForm());
        requests.put(JoinWorldRequest.class.getSimpleName(), new JoinWorldRequest());
        requests.put(LoadWorld.class.getSimpleName(), new LoadWorld());

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

        // Actions

        // Unit's Common
        requests.put(AttackActionRequest.class.getSimpleName(), new AttackActionRequest());
        requests.put(MoveUnitActionRequest.class.getSimpleName(), new MoveUnitActionRequest());
        requests.put(CaptureUnitActionRequest.class.getSimpleName(), new CaptureUnitActionRequest());
        requests.put(DestroyUnitActionRequest.class.getSimpleName(), new DestroyUnitActionRequest());

        // Settlers
        requests.put(BuildCityActionRequest.class.getSimpleName(), new BuildCityActionRequest());

        // Workers
        requests.put(RemoveForestActionRequest.class.getSimpleName(), new RemoveForestActionRequest());
        requests.put(RemoveHillActionRequest.class.getSimpleName(), new RemoveHillActionRequest());
        requests.put(BuildFarmActionRequest.class.getSimpleName(), new BuildFarmActionRequest());

        // City
        requests.put(BuyUnitActionRequest.class.getSimpleName(), new BuyUnitActionRequest());
        requests.put(BuyBuildingActionRequest.class.getSimpleName(), new BuyBuildingActionRequest());
        requests.put(BuildUnitActionRequest.class.getSimpleName(), new BuildUnitActionRequest());
        requests.put(BuildBuildingActionRequest.class.getSimpleName(), new BuildBuildingActionRequest());
        requests.put(DestroyBuildingActionRequest.class.getSimpleName(), new DestroyBuildingActionRequest());

        // Civilization
        requests.put(DeclareWarActionRequest.class.getSimpleName(), new DeclareWarActionRequest());
        requests.put(NextTurnActionRequest.class.getSimpleName(), new NextTurnActionRequest());
    }

    public static AbstractAjaxRequest get(String name) {
        return REQUESTS.get(name);
    }
}
