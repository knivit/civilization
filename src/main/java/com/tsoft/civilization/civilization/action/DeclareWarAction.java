package com.tsoft.civilization.civilization.action;

import com.tsoft.civilization.civilization.L10nCivilization;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationsRelations;
import com.tsoft.civilization.world.World;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class DeclareWarAction {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static final ActionSuccessResult CAN_DECLARE_WAR = new ActionSuccessResult(L10nCivilization.CAN_DECLARE_WAR);

    public static final ActionFailureResult WRONG_CIVILIZATION = new ActionFailureResult(L10nCivilization.WRONG_CIVILIZATION);
    public static final ActionFailureResult ALREADY_WAR = new ActionFailureResult(L10nCivilization.ALREADY_WAR);

    public ActionAbstractResult declareWar(Civilization myCivilization, Civilization otherCivilization) {
        ActionAbstractResult result = canDeclareWar(myCivilization, otherCivilization);
        log.debug("{}", result);

        if (result.isFail()) {
            return result;
        }

        World world = myCivilization.getWorld();
        world.setCivilizationsRelations(myCivilization, otherCivilization, CivilizationsRelations.war());
        return CAN_DECLARE_WAR;
    }

    public ActionAbstractResult canDeclareWar(Civilization myCivilization, Civilization otherCivilization) {
        if (myCivilization == null || otherCivilization == null || otherCivilization.equals(myCivilization)) {
            return WRONG_CIVILIZATION;
        }

        World world = myCivilization.getWorld();
        CivilizationsRelations relations = world.getCivilizationsRelations(myCivilization, otherCivilization);

        if (relations == null) {
            return WRONG_CIVILIZATION;
        }

        if (relations.isWar()) {
            return ALREADY_WAR;
        }

        return CAN_DECLARE_WAR;
    }
}
