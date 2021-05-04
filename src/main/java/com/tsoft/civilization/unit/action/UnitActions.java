package com.tsoft.civilization.unit.action;

import com.tsoft.civilization.action.AbstractAction;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.action.*;
import com.tsoft.civilization.unit.civil.greatartist.GreatArtist;
import com.tsoft.civilization.unit.civil.greatartist.GreatArtistActions;
import com.tsoft.civilization.unit.civil.greatengineer.GreatEngineer;
import com.tsoft.civilization.unit.civil.greatengineer.GreatEngineerActions;
import com.tsoft.civilization.unit.civil.greatgeneral.GreatGeneral;
import com.tsoft.civilization.unit.civil.greatgeneral.GreatGeneralActions;
import com.tsoft.civilization.unit.civil.greatmerchant.GreatMerchant;
import com.tsoft.civilization.unit.civil.greatmerchant.GreatMerchantActions;
import com.tsoft.civilization.unit.civil.greatscientist.GreatScientist;
import com.tsoft.civilization.unit.civil.greatscientist.GreatScientistActions;
import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.unit.civil.settlers.SettlersActions;
import com.tsoft.civilization.unit.civil.workers.Workers;
import com.tsoft.civilization.unit.civil.workers.WorkersActions;

import java.util.HashMap;
import java.util.Map;

public class UnitActions {

    private static final Map<Class<? extends AbstractUnit>, AbstractAction> ACTIONS = new HashMap<>() {{
        put(GreatArtist.class, new GreatArtistActions());
        put(GreatEngineer.class, new GreatEngineerActions());
        put(GreatGeneral.class, new GreatGeneralActions());
        put(GreatMerchant.class, new GreatMerchantActions());
        put(GreatScientist.class, new GreatScientistActions());
        put(Settlers.class, new SettlersActions());
        put(Workers.class, new WorkersActions());
    }};

    private final DefaultUnitActions defaultActions = new DefaultUnitActions();

    public <U extends AbstractUnit> StringBuilder getHtmlActions(U unit) {
        AbstractAction<U> action = ACTIONS.get(unit.getClass());
        return (action == null) ? defaultActions.getHtmlActions(unit) : action.getHtmlActions(unit);
    }
}
