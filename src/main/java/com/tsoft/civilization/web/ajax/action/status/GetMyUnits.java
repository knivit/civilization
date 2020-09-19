package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.L10n.L10nServer;
import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.response.ContentType;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.civilization.Civilization;

public class GetMyUnits extends AbstractAjaxRequest {
    @Override
    public Response getJSON(Request request) {
        Civilization civilization = getMyCivilization();
        if (civilization == null) {
            return Response.newErrorInstance(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        StringBuilder value = Format.text(
            "$navigationPanel\n" +
            "$civilizationInfo\n" +
            "$unitsInfo\n",

            "$navigationPanel", getNavigationPanel(),
            "$civilizationInfo", getCivilizationInfo(civilization),
            "$unitsInfo", getUnitsInfo(civilization));
        return new Response(ResponseCode.OK, value.toString(), ContentType.TEXT_HTML);
    }

    private StringBuilder getCivilizationInfo(Civilization civilization) {
        return Format.text(
            "<table id='title_table'>" +
                "<tr><td>$civilizationName</td></tr>" +
                "<tr><td><img src='$civilizationImage'/></td></tr>" +
            "</table>",

            "$civilizationName", civilization.getView().getLocalizedCivilizationName(),
            "$civilizationImage", civilization.getView().getStatusImageSrc()
        );
    }

    private StringBuilder getUnitsInfo(Civilization civilization) {
        UnitList<?> units = civilization.getUnits();
        if (units.isEmpty()) {
            return Format.text(
                "<table id='actions_table'><tr><th>$text</th></tr></table>",

                "$text", L10nUnit.NO_UNITS
            );
        }

        units.sortByName();

        StringBuilder buf = new StringBuilder();
        for (AbstractUnit unit : units) {
            buf.append(Format.text(
                "<tr>" +
                    "<td><button onclick=\"client.getUnitStatus({ col:'$unitCol', row:'$unitRow', unit:'$unit' })\">$unitName</button></td>" +
                    "<td>$passScore</td>" +
                    "<td>$meleeAttackStrength</td>" +
                    "<td>$rangedAttackStrength</td>" +
                    "<td>$strength</td>" +
                "</tr>",

                "$passScore", unit.getPassScore(),
                "$meleeAttackStrength", unit.getCombatStrength().getMeleeAttackStrength(),
                "$rangedAttackStrength", unit.getCombatStrength().getRangedAttackStrength(),
                "$strength", unit.getCombatStrength().getStrength(),
                "$unitCol", unit.getLocation().getX(),
                "$unitRow", unit.getLocation().getY(),
                "$unit", unit.getId(),
                "$unitName", unit.getView().getLocalizedName()
            ));
        }

        return Format.text(
            "<table id='actions_table'>" +
                "<tr>" +
                    "<th>$name</th>" +
                    "<th>$passScoreHeader</th>" +
                    "<th>$meleeAttackStrengthHeader</th>" +
                    "<th>$rangedAttackStrengthHeader</th>" +
                    "<th>$strengthHeader</th>" +
                "</tr>" +
                "$units" +
            "</table>" +
            "<table id='legend_table'>" +
                "<tr><td>$passScoreHeader</td><td>$passScoreLegend</td></tr>" +
                "<tr><td>$meleeAttackStrengthHeader</td><td>$meleeAttackStrengthLegend</td></tr>" +
                "<tr><td>$rangedAttackStrengthHeader</td><td>$rangedAttackStrengthLegend</td></tr>" +
                "<tr><td>$strengthHeader</td><td>$strengthLegend</td></tr>" +
            "</table>",

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
