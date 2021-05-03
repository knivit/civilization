package com.tsoft.civilization.building;

import com.tsoft.civilization.building.action.DefaultBuildingAction;
import com.tsoft.civilization.action.AbstractAction;

import java.util.HashMap;
import java.util.Map;

public class BuildingActions {

    private static final Map<Class<? extends AbstractBuilding>, AbstractAction> ACTIONS = new HashMap<>() {{
    }};

    private final DefaultBuildingAction defaultActions = new DefaultBuildingAction();

    public <B extends AbstractBuilding> StringBuilder getHtmlActions(B building) {
        AbstractAction action = ACTIONS.get(building.getClass());
        return (action == null) ? defaultActions.getHtmlActions(building) : action.getHtmlActions(building);
    }
}
