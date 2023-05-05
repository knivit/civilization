package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.combat.skill.AbstractSkill;
import com.tsoft.civilization.combat.skill.SkillLevel;
import com.tsoft.civilization.combat.skill.SkillMap;
import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.action.UnitActions;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.HtmlResponse;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.civilization.Civilization;

import java.util.Map;

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
                $defaultUnitInfo
                $meleeUnitInfo
                $rangedUnitInfo
                <tr><td>$defenseStrengthLabel</td><td>$defenseStrength</td>
            </table>
            $movementSkills
            $combatSkills
            $healingSkills
            """,

            "$features", L10nUnit.FEATURES,
            "$defaultUnitInfo", getDefaultUnitInfo(unit),
            "$meleeUnitInfo", getMeleeUnitInfo(unit),
            "$rangedUnitInfo", getRangedUnitInfo(unit),
            "$defenseStrengthLabel", L10nUnit.STRENGTH, "$defenseStrength", unit.calcCombatStrength().getDefenseStrength(),

            "$movementSkills", getUnitSkills(unit, unit.getMovementService().getMovementSkills(), L10nUnit.MOVEMENT_SKILLS_HEADER),
            "$combatSkills", getUnitSkills(unit, unit.getCombatService().getCombatSkills(), L10nUnit.COMBAT_SKILLS_HEADER),
            "$healingSkills", getUnitSkills(unit, unit.getCombatService().getHealingSkills(), L10nUnit.HEALING_SKILLS_HEADER)
        );
    }

    private StringBuilder getDefaultUnitInfo(AbstractUnit unit) {
        return Format.text("""
            <tr><td>$passScoreLabel</td><td>$passScore</td>
            """,

            "$passScoreLabel", L10nUnit.PASS_SCORE, "$passScore", unit.getPassScore()
        );
    }

    private StringBuilder getMeleeUnitInfo(AbstractUnit unit) {
        if (!unit.getUnitCategory().isMelee()) {
            return null;
        }

        return Format.text("""
            <tr><td>$meleeAttackStrengthLabel</td><td>$meleeAttackStrength</td>
            """,

            "$meleeAttackStrengthLabel", L10nUnit.MELEE_ATTACK_STRENGTH, "$meleeAttackStrength", unit.calcCombatStrength().getMeleeAttackStrength()
        );
    }

    private StringBuilder getRangedUnitInfo(AbstractUnit unit) {
        if (!unit.getUnitCategory().isRanged()) {
            return null;
        }

        return Format.text("""
            <tr><td>$rangedAttackStrengthLabel</td><td>$rangedAttackStrength</td>
            <tr><td>$rangedAttackRadiusLabel</td><td>$rangedAttackRadius</td>
            """,

            "$rangedAttackStrengthLabel", L10nUnit.RANGED_ATTACK_STRENGTH, "$rangedAttackStrength", unit.calcCombatStrength().getRangedAttackStrength(),
            "$rangedAttackRadiusLabel", L10nUnit.RANGED_ATTACK_RADIUS, "$rangedAttackRadius", unit.calcCombatStrength().getRangedAttackRadius()
        );
    }

    private StringBuilder getUnitSkills(AbstractUnit unit, SkillMap<? extends AbstractSkill> unitSkills, L10n header) {
        Civilization myCivilization = getMyCivilization();
        if (!myCivilization.equals(unit.getCivilization())) {
            return null;
        }

        if (unitSkills.isEmpty()) {
            return null;
        }

        StringBuilder skills = new StringBuilder();
        for (Map.Entry<? extends AbstractSkill, SkillLevel> skill : unitSkills) {
            skills.append(Format.text("""
                <tr><td>$skillName</td><td>$skillLevel</td></tr>
                """,

                "$skillName", skill.getKey().getLocalizedName(),
                "$skillLevel", skill.getValue().getValue()
            ));
        }

        return Format.text("""
            <table id='info_table'>
                <tr><th>$skillNameHeader</th><th>$skillLevelHeader</th></tr>
                $skills
            </table>
            """,

            "$skillNameHeader", header,
            "$skillLevelHeader", L10nUnit.SKILL_LEVEL_HEADER,
            "$skills", skills
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
