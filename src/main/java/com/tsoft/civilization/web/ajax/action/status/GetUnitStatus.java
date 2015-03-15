package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.L10n.L10nAction;
import com.tsoft.civilization.L10n.L10nServer;
import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.util.ContentType;
import com.tsoft.civilization.web.util.Request;
import com.tsoft.civilization.web.util.Response;
import com.tsoft.civilization.web.util.ResponseCode;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.world.Civilization;

public class GetUnitStatus extends AbstractAjaxRequest {
    @Override
    public Response getJSON(Request request) {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return Response.newErrorInstance(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        String unitId = request.get("unit");
        AbstractUnit unit = myCivilization.getWorld().getUnitById(unitId);
        if (unit == null) {
            return Response.newErrorInstance(L10nUnit.UNIT_NOT_FOUND);
        }

        StringBuilder value = Format.text(
            "$navigationPanel\n" +
            "$unitTitle\n" +
            "$unitInfo\n" +
            "$actions\n",

            "$navigationPanel", getNavigationPanel(),
            "$unitTitle", getUnitTitle(unit),
            "$unitInfo", getUnitInfo(unit),
            "$actions", getActions(unit));
        return new Response(ResponseCode.OK, value.toString(), ContentType.TEXT_HTML);
    }

    private StringBuilder getUnitTitle(AbstractUnit unit) {
        return Format.text(
            "<table id='title_table'>" +
                "<tr><td>$unitName</td></tr>" +
                "<tr><td><button onclick=\"server.sendAsyncAjax('ajax/GetCivilizationStatus', { civilization:'$civilization' })\">$civilizationName</button></td></tr>" +
                "<tr><td><img src='$unitImageSrc'/></td></tr>" +
                "<tr><td>$unitDescription</td></tr>" +
            "</table>",

            "$unitName", unit.getView().getLocalizedName(),
            "$civilization", unit.getCivilization().getId(),
            "$civilizationName", unit.getCivilization().getView().getLocalizedCivilizationName(),
            "$unitImageSrc", unit.getView().getStatusImageSrc(),
            "$unitDescription", unit.getView().getLocalizedDescription()
        );
    }

    private StringBuilder getUnitInfo(AbstractUnit unit) {
        Civilization myCivilization = getMyCivilization();
        if (!myCivilization.equals(unit.getCivilization())) {
            return null;
        }

        return Format.text(
            "<table id='info_table'>" +
                "<tr><th colspan='2'>$features</th>" +
                "<tr><td>$meleeAttackStrengthLabel</td><td>$meleeAttackStrength</td>" +
                "<tr><td>$canConquerCityLabel</td><td>$canConquerCity</td>" +
                "<tr><td>$attackExperienceLabel</td><td>$attackExperience</td>" +
                "<tr><td>$defenseExperienceLabel</td><td>$defenseExperience</td>" +
                "<tr><td>$rangedAttackStrengthLabel</td><td>$rangedAttackStrength</td>" +
                "<tr><td>$rangedAttackRadiusLabel</td><td>$rangedAttackRadius</td>" +
                "<tr><td>$strengthLabel</td><td>$strength</td>" +
            "</table>",

            "$features", L10nUnit.FEATURES,

            "$meleeAttackStrengthLabel", L10nUnit.MELEE_ATTACK_STRENGTH, "$meleeAttackStrength", unit.getCombatStrength().getMeleeAttackStrength(),
            "$canConquerCityLabel", L10nUnit.CAN_CONQUER_CITY, "$canConquerCity", unit.getCombatStrength().canConquerCity(),
            "$attackExperienceLabel", L10nUnit.ATTACK_EXPERIENCE, "$attackExperience", unit.getCombatStrength().getAttackExperience(),
            "$defenseExperienceLabel", L10nUnit.DEFENSE_EXPERIENCE, "$defenseExperience", unit.getCombatStrength().getDefenseExperience(),
            "$rangedAttackStrengthLabel", L10nUnit.RANGED_ATTACK_STRENGTH, "$rangedAttackStrength", unit.getCombatStrength().getRangedAttackStrength(),
            "$rangedAttackRadiusLabel", L10nUnit.RANGED_ATTACK_RADIUS, "$rangedAttackRadius", unit.getCombatStrength().getRangedAttackRadius(),
            "$strengthLabel", L10nUnit.STRENGTH, "$strength", unit.getCombatStrength().getStrength()
        );
    }

    private StringBuilder getActions(AbstractUnit unit) {
        Civilization myCivilization = getMyCivilization();
        Civilization unitCivilization = unit.getCivilization();

        // we can't manipulate foreign unit
        if (!myCivilization.equals(unitCivilization)) {
            return null;
        }

        // nothing to do with a destroyed unit
        if (unit.isDestroyed()) {
            return Format.text(
                "<table id='actions_table'>" +
                    "<tr><td>$destroyed</td></tr>" +
                "</table>",

                "$destroyed", L10nUnit.UNIT_WAS_DESTROYED
            );
        }

        StringBuilder buf = unit.getView().getHtmlActions(unit);
        if (buf == null) {
            return null;
        }

        return Format.text(
            "<table id='actions_table'>" +
                "<tr><th colspan='2'>$actions</th></tr>" +
                "$unitActions" +
            "</table",

            "$actions", L10nAction.AVAILABLE_ACTIONS,
            "$unitActions", buf
        );
    }
}
