package com.tsoft.civilization.web.ajax;

import com.tsoft.civilization.civilization.building.AbstractBuilding;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.L10nCivilization;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.unit.civil.workers.Workers;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.view.JsonBlock;

/** Chain of requests initiated by a client (i.e. browser) */
public class ClientAjaxRequest {

    public static StringBuilder getEvents(int year) {
        return Format.text("client.getEvents({ year:'$year' })",
            "$year", year
        );
    }

    public static StringBuilder getCityStatus(City city) {
        return Format.text("client.getCityStatus({ col:'$cityCol', row:'$cityRow', city:'$city' })",
            "$cityCol", city.getLocation().getX(),
            "$cityRow", city.getLocation().getY(),
            "$city", city.getId()
        );
    }

    public static StringBuilder cityAttackAction(HasCombatStrength attacker, JsonBlock locations) {
        return Format.text("client.cityAttackAction({ attacker:'$attacker', ucol:'$ucol', urow:'$urow', $locations })",
            "$attacker", attacker.getId(),
            "$ucol", attacker.getLocation().getX(),
            "$urow", attacker.getLocation().getY(),
            "$locations", locations.getValue()
        );
    }

    public static StringBuilder buyTileAction(City city, JsonBlock locations) {
        return Format.text("client.buyTileAction({ city:'$city', col:'$col', row:'$row', $locations })",
            "$city", city.getId(),
            "$col", city.getLocation().getX(),
            "$row", city.getLocation().getY(),
            "$locations", locations.getValue()
        );
    }

    public static StringBuilder getUnitStatus(AbstractUnit unit) {
        return Format.text("client.getUnitStatus({ col:'$unitCol', row:'$unitRow', unit:'$unit' })",
            "$unitCol", unit.getLocation().getX(),
            "$unitRow", unit.getLocation().getY(),
            "$unit", unit.getId()
        );
    }

    public static StringBuilder moveUnitAction(AbstractUnit unit, JsonBlock locations) {
        return Format.text("client.moveUnitAction({ unit:'$unit', col:'$col', row:'$row', $locations })",
            "$unit", unit.getId(),
            "$col", unit.getLocation().getX(),
            "$row", unit.getLocation().getY(),
            "$locations", locations.getValue()
        );
    }

    public static StringBuilder unitAttackAction(HasCombatStrength attacker, JsonBlock locations) {
        return Format.text("client.unitAttackAction({ attacker:'$attacker', ucol:'$ucol', urow:'$urow', $locations })",
            "$attacker", attacker.getId(),
            "$ucol", attacker.getLocation().getX(),
            "$urow", attacker.getLocation().getY(),
            "$locations", locations.getValue()
        );
    }

    public static StringBuilder captureUnitAction(AbstractUnit attacker, JsonBlock locations) {
        return Format.text("client.captureUnitAction({ attacker:'$attacker', ucol:'$ucol', urow:'$urow', $locations })",
            "$attacker", attacker.getId(),
            "$ucol", attacker.getLocation().getX(),
            "$urow", attacker.getLocation().getY(),
            "$locations", locations.getValue()
        );
    }

    public static StringBuilder destroyUnitAction(AbstractUnit unit) {
        return Format.text("client.destroyUnitAction({ unit:'$unit' })",
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

    public static StringBuilder buildFarmAction(Workers workers) {
        return Format.text("client.buildFarmAction({ workers:'$workers' })",
            "$workers", workers.getId()
        );
    }

    public static StringBuilder destroyBuildingAction(AbstractBuilding building) {
        return Format.text("client.destroyBuildingAction({ building:'$building' })",
            "$building", building.getId()
        );
    }

    public static StringBuilder removeForestAction(Workers workers) {
        return Format.text("client.removeForestAction({ workers:'$workers' })",
            "$workers", workers.getId()
        );
    }

    public static StringBuilder removeHillAction(Workers workers) {
        return Format.text("client.removeHillAction({ workers:'$workers' })",
            "$workers", workers.getId()
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
