package com.tsoft.civilization.unit.action;

import com.tsoft.civilization.action.AbstractAction;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.catalog.greatartist.GreatArtistActions;
import com.tsoft.civilization.unit.catalog.greatengineer.GreatEngineerActions;
import com.tsoft.civilization.unit.catalog.greatgeneral.GreatGeneralActions;
import com.tsoft.civilization.unit.catalog.greatmerchant.GreatMerchantActions;
import com.tsoft.civilization.unit.catalog.greatscientist.GreatScientistActions;
import com.tsoft.civilization.unit.catalog.settlers.SettlersActions;
import com.tsoft.civilization.unit.catalog.workers.WorkersActions;

import java.util.HashMap;
import java.util.Map;

public class UnitActions {

    private static final Map<String, AbstractAction> ACTIONS = new HashMap<>() {{
        put("GreatArtist", new GreatArtistActions());
        put("GreatEngineer", new GreatEngineerActions());
        put("GreatGeneral", new GreatGeneralActions());
        put("GreatMerchant", new GreatMerchantActions());
        put("GreatScientist", new GreatScientistActions());
        put("Settlers", new SettlersActions());
        put("Workers", new WorkersActions());
    }};

    private final DefaultUnitActions defaultActions = new DefaultUnitActions();

    public <U extends AbstractUnit> StringBuilder getHtmlActions(U unit) {
        AbstractAction<U> action = ACTIONS.get(unit.getClass());
        return (action == null) ? defaultActions.getHtmlActions(unit) : action.getHtmlActions(unit);
    }
}
