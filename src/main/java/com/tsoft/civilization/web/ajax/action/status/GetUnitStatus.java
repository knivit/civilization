package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitActions;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.HtmlResponse;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.civilization.Civilization;

public class GetUnitStatus extends AbstractAjaxRequest {

    private final UnitActions unitActions = new UnitActions();
    private final GetNavigationPanel navigationPanel = new GetNavigationPanel();

    @Override
    public Response getJson(Request request) {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return JsonResponse.badRequest(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        String unitId = request.get("unit");
        AbstractUnit unit = myCivilization.getWorld().getUnitById(unitId);
        if (unit == null) {
            return JsonResponse.badRequest(L10nUnit.UNIT_NOT_FOUND);
        }

        StringBuilder value = Format.text("""
            $navigationPanel
            $unitTitle
            $unitInfo
            $actions
            """,

            "$navigationPanel", navigationPanel.getContent(),
            "$unitTitle", getUnitTitle(unit),
            "$unitInfo", getUnitInfo(unit),
            "$actions", getActions(unit));
        return HtmlResponse.ok(value);
    }

    private StringBuilder getUnitTitle(AbstractUnit unit) {
        return Format.text("""
            <table id='title_table'>
                <tr><td>$unitName</td></tr>
                <tr><td><button onclick="$getCivilizationStatus">$civilizationName</button></td></tr>
                <tr><td><img src='$unitImageSrc'/></td></tr>
                <tr><td>$unitDescription</td></tr>
            </table>
            """,

            "$unitName", unit.getView().getLocalizedName(),
            "$getCivilizationStatus", GetCivilizationStatus.getAjax(unit.getCivilization()),
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

        return Format.text("""
            <table id='info_table'>
                <tr><th colspan='2'>$features</th>
                <tr><td>$meleeAttackStrengthLabel</td><td>$meleeAttackStrength</td>
                <tr><td>$canConquerCityLabel</td><td>$canConquerCity</td>
                <tr><td>$attackExperienceLabel</td><td>$attackExperience</td>
                <tr><td>$defenseExperienceLabel</td><td>$defenseExperience</td>
                <tr><td>$rangedAttackStrengthLabel</td><td>$rangedAttackStrength</td>
                <tr><td>$rangedAttackRadiusLabel</td><td>$rangedAttackRadius</td>
                <tr><td>$strengthLabel</td><td>$strength</td>
            </table>
            """,

            "$features", L10nUnit.FEATURES,

            "$meleeAttackStrengthLabel", L10nUnit.MELEE_ATTACK_STRENGTH, "$meleeAttackStrength", unit.getCombatStrength().getMeleeAttackStrength(),
            "$canConquerCityLabel", L10nUnit.CAN_CONQUER_CITY, "$canConquerCity", unit.getCombatStrength().isCanConquerCity(),
            "$attackExperienceLabel", L10nUnit.ATTACK_EXPERIENCE, "$attackExperience", unit.getCombatStrength().getAttackExperience(),
            "$defenseExperienceLabel", L10nUnit.DEFENSE_EXPERIENCE, "$defenseExperience", unit.getCombatStrength().getDefenseExperience(),
            "$rangedAttackStrengthLabel", L10nUnit.RANGED_ATTACK_STRENGTH, "$rangedAttackStrength", unit.getCombatStrength().getRangedAttackStrength(),
            "$rangedAttackRadiusLabel", L10nUnit.RANGED_ATTACK_RADIUS, "$rangedAttackRadius", unit.getCombatStrength().getRangedAttackRadius(),
            "$strengthLabel", L10nUnit.STRENGTH, "$strength", unit.getCombatStrength().getDefenseStrength()
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
            return Format.text("""
                <table id='actions_table'>
                    <tr><td>$destroyed</td></tr>
                </table>
                """,

                "$destroyed", L10nUnit.UNIT_WAS_DESTROYED
            );
        }

        StringBuilder actions = unitActions.getHtmlActions(unit);
        if (actions == null) {
            return null;
        }

        return Format.text("""
            <table id='actions_table'>
                <tr><th colspan='2'>$actions</th></tr>
                $unitActions
            </table
            """,

            "$actions", L10nStatus.AVAILABLE_ACTIONS,
            "$unitActions", actions
        );
    }
}
