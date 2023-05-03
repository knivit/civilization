package com.tsoft.civilization.combat.action;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.combat.HasCombatStrengthList;
import com.tsoft.civilization.combat.service.PillageService;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.ajax.ClientAjaxRequest;
import com.tsoft.civilization.web.view.JsonBlock;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class PillageAction {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private final PillageService pillageService;

    public ActionAbstractResult pillage(HasCombatStrength attacker) {
        return pillageService.pillage(attacker);
    }

    private String getLocalizedName() {
        return L10nUnit.PILLAGE_NAME.getLocalized();
    }

    private String getLocalizedDescription() {
        return L10nUnit.PILLAGE_DESCRIPTION.getLocalized();
    }

    public StringBuilder getHtml(HasCombatStrength attacker) {
        if (pillageService.canPillage(attacker).isFail()) {
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

        HasCombatStrengthList targets = pillageService.getTargetsToPillage(attacker);
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

        return ClientAjaxRequest.unitPillageAction(attacker, locations);
    }
}
