package com.tsoft.civilization.combat.action;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.combat.HasCombatStrengthList;
import com.tsoft.civilization.combat.service.PillageService;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.ajax.ClientAjaxRequest;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class PillageAction {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private final PillageService pillageService;

    public ActionAbstractResult pillage(HasCombatStrength attacker, HasCombatStrength target) {
        return pillageService.pillage(attacker, target);
    }

    public StringBuilder getHtml(HasCombatStrength attacker) {
        if (pillageService.canPillage(attacker).isFail()) {
            return null;
        }

        HasCombatStrengthList targets = pillageService.getTargetsToPillage(attacker);
        if (targets == null || targets.isEmpty()) {
            return null;
        }

        StringBuilder buf = new StringBuilder();
        for (HasCombatStrength target : targets) {
            StringBuilder pillageTargetAction = Format.text("""
                <tr>
                    <td><button onclick="$buttonOnClick">$buttonLabel</button></td><td>$targetName</td>
                </tr>
                """,

                "$buttonOnClick", ClientAjaxRequest.pillageAction(attacker, target),
                "$buttonLabel", getLocalizedName(),
                "$targetName", target.getView().getLocalizedName());

            buf.append(pillageTargetAction);
        }

        return Format.text("""
            <table id='actions_table'>
            <tr><th colspan='2'>$header</th></tr>
            $targets
            </table>
            """,

            "$header", getLocalizedDescription(),
            "$targets", buf
        );
    }

    private String getLocalizedName() {
        return L10nUnit.PILLAGE_NAME.getLocalized();
    }

    private String getLocalizedDescription() {
        return L10nUnit.PILLAGE_DESCRIPTION.getLocalized();
    }
}
