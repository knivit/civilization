package com.tsoft.civilization.web.ajax;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.L10nCivilization;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.util.Format;

/** Chain of requests initiated by a client (i.e. browser) */
public class ClientAjaxRequest {

    public static StringBuilder getCityStatus(City city) {
        return Format.text("client.getCityStatus({ col:'$cityCol', row:'$cityRow', city:'$city' })",
            "$cityCol", city.getLocation().getX(),
            "$cityRow", city.getLocation().getY(),
            "$city", city.getId()
        );
    }

    public static StringBuilder getUnitStatus(AbstractUnit unit) {
        return Format.text("client.getUnitStatus({ col:'$unitCol', row:'$unitRow', unit:'$unit' })",
            "$unitCol", unit.getLocation().getX(),
            "$unitRow", unit.getLocation().getY(),
            "$unit", unit.getId()
        );
    }

    public static StringBuilder nextTurnAction() {
        return new StringBuilder("client.nextTurnAction()");
    }

    public static StringBuilder buildCityAction(Settlers settlers) {
        return Format.text("client.buildCityAction({ settlers:'$settlers' })",
            "$settlers", settlers.getId()
        );
    }

    public static StringBuilder buyUnitAction(City city, String unitClassUuid) {
        return Format.text("client.buyUnitAction({ city:'$city', unitUuid:'$unitClassUuid' })",
            "$city", city.getId(),
            "$unitClassUuid", unitClassUuid
        );
    }

    public static StringBuilder buildUnitAction(City city, String unitClassUuid) {
        return Format.text("client.buildUnitAction({ city:'$city', unitUuid:'$unitUuid' })",
            "$city", city.getId(),
            "$unitUuid", unitClassUuid
        );
    }

    public static StringBuilder buyBuildingAction(City city, String buildingClassUuid) {
        return Format.text("client.buyBuildingAction({ city:'$city', buildingUuid:'$buildingClassUuid' })",
            "$city", city.getId(),
            "$buildingClassUuid", buildingClassUuid);
    }

    public static StringBuilder buildBuildingAction(City city, String buildingClassUuid) {
        return Format.text("client.buildBuildingAction({ city:'$city', buildingUuid:'$buildingClassUuid' })",
            "$city", city.getId(),
            "$buildingClassUuid", buildingClassUuid
        );
    }

    public static StringBuilder declareWar(Civilization other) {
        return Format.text("client.declareWarAction({ otherCivilization:'$civilization', confirmationMessage:'$confirmMessage' })",
            "$civilization", other.getId(),
            "$confirmMessage", L10nCivilization.CONFIRM_DECLARE_WAR
        );
    }
}
