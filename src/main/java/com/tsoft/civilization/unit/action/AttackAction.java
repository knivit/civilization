package com.tsoft.civilization.unit.action;

import com.tsoft.civilization.combat.*;
import com.tsoft.civilization.web.ajax.ClientAjaxRequest;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.view.JsonBlock;

import java.util.*;

public class AttackAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private final CombatService combatService;

    public AttackAction(CombatService combatService) {
        this.combatService = combatService;
    }

    public ActionAbstractResult attack(HasCombatStrength attacker, Point location) {
        return combatService.attack(attacker, location);
    }

    private String getLocalizedName() {
        return L10nUnit.ATTACK_NAME.getLocalized();
    }

    private String getLocalizedDescription() {
        return L10nUnit.ATTACK_DESCRIPTION.getLocalized();
    }

    public StringBuilder getHtml(HasCombatStrength attacker) {
        if (combatService.canAttack(attacker).isFail()) {
            return null;
        }

        return Format.text("""
            <td><button onclick="$buttonOnClick">$buttonLabel</button></td><td>$actionDescription</td>
            """,

            "$buttonOnClick", getClientJSCode(attacker),
            "$buttonLabel", getLocalizedName(),
            "$actionDescription", getLocalizedDescription()
        );
    }

    private StringBuilder getClientJSCode(HasCombatStrength attacker) {
        JsonBlock locations = new JsonBlock('\'');
        locations.startArray("locations");
        HasCombatStrengthList targets = combatService.getTargetsToAttack(attacker);
        for (HasCombatStrength target : targets) {
            JsonBlock locBlock = new JsonBlock('\'');
            locBlock.addParam("col", target.getLocation().getX());
            locBlock.addParam("row", target.getLocation().getY());
            locations.addElement(locBlock.getText());
        }
        locations.stopArray();

        if (attacker instanceof City) {
            return ClientAjaxRequest.cityAttackAction(attacker, locations);
        }

        return ClientAjaxRequest.unitAttackAction(attacker, locations);
    }
}
