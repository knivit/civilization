package com.tsoft.civilization.web.ajax;

import com.tsoft.civilization.L10n.L10nClient;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.ajax.action.status.GetEvents;
import com.tsoft.civilization.web.ajax.action.unit.AttackActionRequest;
import com.tsoft.civilization.web.util.Request;
import com.tsoft.civilization.web.util.Response;
import com.tsoft.civilization.web.ajax.action.city.BuildBuildingActionRequest;
import com.tsoft.civilization.web.ajax.action.city.BuildUnitActionRequest;
import com.tsoft.civilization.web.ajax.action.city.BuyBuildingActionRequest;
import com.tsoft.civilization.web.ajax.action.city.BuyUnitActionRequest;
import com.tsoft.civilization.web.ajax.action.city.DestroyBuildingActionRequest;
import com.tsoft.civilization.web.ajax.action.civilization.DeclareWarActionRequest;
import com.tsoft.civilization.web.ajax.action.civilization.NextMoveActionRequest;
import com.tsoft.civilization.web.ajax.action.status.GetBuildingStatus;
import com.tsoft.civilization.web.ajax.action.status.GetCityStatus;
import com.tsoft.civilization.web.ajax.action.status.GetCivilizationStatus;
import com.tsoft.civilization.web.ajax.action.status.GetCivilizations;
import com.tsoft.civilization.web.ajax.action.status.GetControlPanel;
import com.tsoft.civilization.web.ajax.action.status.GetFeatureInfo;
import com.tsoft.civilization.web.ajax.action.status.GetMyCities;
import com.tsoft.civilization.web.ajax.action.status.GetMyUnits;
import com.tsoft.civilization.web.ajax.action.status.GetStartStatus;
import com.tsoft.civilization.web.ajax.action.status.GetTileInfo;
import com.tsoft.civilization.web.ajax.action.status.GetTileStatus;
import com.tsoft.civilization.web.ajax.action.status.GetUnitStatus;
import com.tsoft.civilization.web.ajax.action.status.GetWorlds;
import com.tsoft.civilization.web.ajax.action.unit.CaptureUnitActionRequest;
import com.tsoft.civilization.web.ajax.action.unit.DestroyUnitActionRequest;
import com.tsoft.civilization.web.ajax.action.unit.MoveUnitActionRequest;
import com.tsoft.civilization.web.ajax.action.unit.settlers.BuildCityActionRequest;
import com.tsoft.civilization.web.ajax.action.unit.workers.RemoveForestActionRequest;
import com.tsoft.civilization.web.ajax.action.unit.workers.RemoveHillActionRequest;
import com.tsoft.civilization.web.ajax.action.world.CreateWorld;
import com.tsoft.civilization.web.ajax.action.world.GetCreateWorldForm;
import com.tsoft.civilization.web.ajax.action.world.JoinWorld;
import com.tsoft.civilization.web.ajax.action.world.LoadWorld;
import com.tsoft.civilization.web.state.ClientSession;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.world.Civilization;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractAjaxRequest {
    private static Map<String, AbstractAjaxRequest> requests = new HashMap<>();

    static {
        // Worlds

        requests.put(GetWorlds.class.getSimpleName(), new GetWorlds());
        requests.put(CreateWorld.class.getSimpleName(), new CreateWorld());
        requests.put(GetCreateWorldForm.class.getSimpleName(), new GetCreateWorldForm());
        requests.put(JoinWorld.class.getSimpleName(), new JoinWorld());
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

        // City
        requests.put(BuyUnitActionRequest.class.getSimpleName(), new BuyUnitActionRequest());
        requests.put(BuyBuildingActionRequest.class.getSimpleName(), new BuyBuildingActionRequest());
        requests.put(BuildUnitActionRequest.class.getSimpleName(), new BuildUnitActionRequest());
        requests.put(BuildBuildingActionRequest.class.getSimpleName(), new BuildBuildingActionRequest());
        requests.put(DestroyBuildingActionRequest.class.getSimpleName(), new DestroyBuildingActionRequest());

        // Civilization
        requests.put(DeclareWarActionRequest.class.getSimpleName(), new DeclareWarActionRequest());
        requests.put(NextMoveActionRequest.class.getSimpleName(), new NextMoveActionRequest());
    }

    public abstract Response getJSON(Request request);

    /** Returns null if requested AJAX doesn't found */
    public static AbstractAjaxRequest getInstance(String requestName) {
        return requests.get(requestName);
    }

    protected Civilization getMyCivilization() {
        ClientSession mySession = Sessions.getCurrent();
        return mySession.getCivilization();
    }

    protected StringBuilder getNavigationPanel() {
        return Format.text(
            "<table id='navigation_panel'><tr>" +
                "<td><button onclick=\"server.sendAsyncAjax('ajax/GetCivilizations')\">$civilizationsButton</button></td>" +
                "<td><button onclick=\"server.sendAsyncAjax('ajax/GetMyCities')\">$citiesButton</button></td>" +
                "<td><button onclick=\"server.sendAsyncAjax('ajax/GetMyUnits')\">$unitsButton</button></td>" +

                // use year:10 000 to show last events first
                "<td><button onclick=\"server.sendAsyncAjax('ajax/GetEvents', { year:'10000' })\">$showEventsButton</button></td>" +
            "</tr></table>",

            "$civilizationsButton", L10nClient.CIVILIZATIONS_BUTTON,
            "$citiesButton", L10nClient.MY_CITIES_BUTTON,
            "$unitsButton", L10nClient.MY_UNITS_BUTTON,
            "$showEventsButton", L10nClient.SHOW_EVENTS_BUTTON
        );
    }
}
