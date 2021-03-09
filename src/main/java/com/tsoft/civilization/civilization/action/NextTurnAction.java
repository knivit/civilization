package com.tsoft.civilization.civilization.action;

import com.tsoft.civilization.L10n.L10nCivilization;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.World;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class NextTurnAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static ActionAbstractResult nextTurn(World world) {
        ActionAbstractResult result = canNextTurn(world);
        log.debug("{}", result);

        if (result.isFail()) {
            return result;
        }

        world.move();

        return NextTurnActionResults.CAN_NEXT_TURN;
    }

    private static ActionAbstractResult canNextTurn(World world) {
        if (world.nextTurnInProgress()) {
            return NextTurnActionResults.AWAITING_OTHERS_TO_MOVE;
        }

        return NextTurnActionResults.CAN_NEXT_TURN;
    }

    private static String getClientJSCode() {
        return "client.nextTurnAction()";
    }

    private static String getLocalizedName() {
        return L10nCivilization.NEXT_TURN.getLocalized();
    }

    private static String getLocalizedDescription() {
        return L10nCivilization.NEXT_MOVE_DESCRIPTION.getLocalized();
    }

    public static StringBuilder getHtml(Civilization civilization) {
        if (canNextTurn(civilization.getWorld()).isFail()) {
            return null;
        }

        return Format.text("""
            <td><button onclick="$buttonOnClick">$buttonLabel</button></td>
            """,

            "$buttonOnClick", getClientJSCode(),
            "$buttonLabel", getLocalizedName());
    }
}
