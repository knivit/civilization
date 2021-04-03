package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.unit.UnitListService;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.ajax.ClientAjaxRequest;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.HtmlResponse;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.civilization.Civilization;

public class GetMyUnits extends AbstractAjaxRequest {

    private final GetNavigationPanel navigationPanel = new GetNavigationPanel();
    private final UnitListService unitListService = new UnitListService();

    public static StringBuilder getAjax() {
        return new StringBuilder("server.sendAsyncAjax('ajax/GetMyUnits')");
    }

    @Override
    public Response getJson(Request request) {
        Civilization civilization = getMyCivilization();
        if (civilization == null) {
            return JsonResponse.badRequest(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        StringBuilder value = Format.text("""
            $navigationPanel
            $civilizationInfo
            $unitsInfo
            """,

            "$navigationPanel", navigationPanel.getContent(),
            "$civilizationInfo", getCivilizationInfo(civilization),
            "$unitsInfo", getUnitsInfo(civilization));
        return HtmlResponse.ok(value);
    }

    private StringBuilder getCivilizationInfo(Civilization civilization) {
        return Format.text("""
            <table id='title_table'>
                <tr><td>$civilizationName</td></tr>
                <tr><td><img src='$civilizationImage'/></td></tr>
            </table>
            """,

            "$civilizationName", civilization.getView().getLocalizedCivilizationName(),
            "$civilizationImage", civilization.getView().getStatusImageSrc()
        );
    }

    private StringBuilder getUnitsInfo(Civilization civilization) {
        UnitList units = civilization.units().getUnits();
        if (units.isEmpty()) {
            return Format.text(
                "<table id='actions_table'><tr><th>$text</th></tr></table>",

                "$text", L10nUnit.NO_UNITS
            );
        }

        units = unitListService.sortByName(units);

        StringBuilder buf = new StringBuilder();
        for (AbstractUnit unit : units) {
            buf.append(Format.text("""
                <tr>
                    <td><button onclick="$getUnitStatus">$unitName</button></td>
                    <td>$passScore</td>
                    <td>$meleeAttackStrength</td>
                    <td>$rangedAttackStrength</td>
                    <td>$strength</td>
                </tr>
                """,

                "$getUnitStatus", ClientAjaxRequest.getUnitStatus(unit),
                "$unitName", unit.getView().getLocalizedName(),
                "$passScore", unit.getPassScore(),
                "$meleeAttackStrength", unit.getCombatStrength().getMeleeAttackStrength(),
                "$rangedAttackStrength", unit.getCombatStrength().getRangedAttackStrength(),
                "$strength", unit.getCombatStrength().getStrength()
            ));
        }

        return Format.text("""
            <table id='actions_table'>
                <tr>
                    <th>$name</th>
                    <th>$passScoreHeader</th>
                    <th>$meleeAttackStrengthHeader</th>
                    <th>$rangedAttackStrengthHeader</th>
                    <th>$strengthHeader</th>
                </tr>
                $units
            </table>
            <table id='legend_table'>
                <tr><td>$passScoreHeader</td><td>$passScoreLegend</td></tr>
                <tr><td>$meleeAttackStrengthHeader</td><td>$meleeAttackStrengthLegend</td></tr>
                <tr><td>$rangedAttackStrengthHeader</td><td>$rangedAttackStrengthLegend</td></tr>
                <tr><td>$strengthHeader</td><td>$strengthLegend</td></tr>
            </table>
            """,

            "$passScoreHeader", L10nUnit.PASS_SCORE_HEADER,
            "$meleeAttackStrengthHeader", L10nUnit.MELEE_ATTACK_STRENGTH_HEADER,
            "$rangedAttackStrengthHeader", L10nUnit.RANGED_ATTACK_STRENGTH_HEADER,
            "$strengthHeader", L10nUnit.STRENGTH_HEADER,

            "$name", L10nUnit.NAME,
            "$units", buf,

            "$passScoreLegend", L10nUnit.PASS_SCORE,
            "$meleeAttackStrengthLegend", L10nUnit.MELEE_ATTACK_STRENGTH,
            "$rangedAttackStrengthLegend", L10nUnit.RANGED_ATTACK_STRENGTH,
            "$strengthLegend", L10nUnit.STRENGTH
        );
    }
}
