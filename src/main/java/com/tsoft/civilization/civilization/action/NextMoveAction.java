package com.tsoft.civilization.civilization.action;

import com.tsoft.civilization.L10n.L10nCivilization;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.civilization.CivilizationList;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.World;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class NextMoveAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static ActionAbstractResult nextMove(World world) {
        ActionAbstractResult result = canNextMove(world);
        log.debug("{}", result);

        if (result.isFail()) {
            return result;
        }

        world.nextMove();

        return NextMoveActionResults.CAN_GO_NEXT;
    }

    private static ActionAbstractResult canNextMove(World world) {
        CivilizationList notMoved = world.getNotMovedHumanCivilizations();
        if (!notMoved.isEmpty()) {
            return NextMoveActionResults.AWAITING_OTHERS_TO_MOVE;
        }

        return NextMoveActionResults.CAN_GO_NEXT;
    }

    private static String getClientJSCode() {
        return "client.nextMoveAction()";
    }

    private static String getLocalizedName() {
        return L10nCivilization.NEXT_MOVE.getLocalized();
    }

    private static String getLocalizedDescription() {
        return L10nCivilization.NEXT_MOVE_DESCRIPTION.getLocalized();
    }

    public static StringBuilder getHtml(Civilization civilization) {
        if (canNextMove(civilization.getWorld()).isFail()) {
            return null;
        }

        return Format.text(
            "<td><button onclick=\"$buttonOnClick\">$buttonLabel</button></td>",

            "$buttonOnClick", getClientJSCode(),
            "$buttonLabel", getLocalizedName());
    }
}
