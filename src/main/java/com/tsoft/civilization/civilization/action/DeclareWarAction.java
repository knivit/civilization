package com.tsoft.civilization.civilization.action;

import com.tsoft.civilization.L10n.L10nCivilization;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationsRelations;
import com.tsoft.civilization.world.World;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class DeclareWarAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static ActionAbstractResult declareWar(Civilization myCivilization, Civilization otherCivilization) {
        ActionAbstractResult result = canDeclareWar(myCivilization, otherCivilization);
        log.debug("{}", result);

        if (result.isFail()) {
            return result;
        }

        World world = myCivilization.getWorld();
        world.setCivilizationsRelations(myCivilization, otherCivilization, CivilizationsRelations.WAR);
        return DeclareWarActionResults.CAN_DECLARE_WAR;
    }

    private static ActionAbstractResult canDeclareWar(Civilization myCivilization, Civilization otherCivilization) {
        if (otherCivilization == null || otherCivilization.equals(myCivilization)) {
            return DeclareWarActionResults.WRONG_CIVILIZATION;
        }

        World world = myCivilization.getWorld();
        CivilizationsRelations relations = world.getCivilizationsRelations(myCivilization, otherCivilization);
        if (relations.isWar()) {
            return DeclareWarActionResults.ALREADY_WAR;
        }

        return DeclareWarActionResults.CAN_DECLARE_WAR;
    }

    private static String getClientJSCode(Civilization civilization) {
        return String.format("client.declareWarAction({ otherCivilization:'%1$s', confirmationMessage:'%2$s' })",
            civilization.getId(), L10nCivilization.CONFIRM_DECLARE_WAR);
    }

    private static String getLocalizedName() {
        return L10nCivilization.DECLARE_WAR_NAME.getLocalized();
    }

    private static String getLocalizedDescription() {
        return L10nCivilization.DECLARE_WAR_DESCRIPTION.getLocalized();
    }

    public static StringBuilder getHtml(Civilization myCivilization, Civilization otherCivilization) {
        if (canDeclareWar(myCivilization, otherCivilization).isFail()) {
            return null;
        }

        return Format.text(
            "<td><button onclick=\"$buttonOnClick\">$buttonLabel</button></td><td>$actionDescription</td>",

            "$buttonOnClick", getClientJSCode(otherCivilization),
            "$buttonLabel", getLocalizedName(),
            "$actionDescription", getLocalizedDescription());
    }
}
