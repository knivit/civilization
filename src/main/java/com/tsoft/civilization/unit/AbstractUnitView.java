package com.tsoft.civilization.unit;

import com.tsoft.civilization.web.view.JsonBlock;

public abstract class AbstractUnitView {

    public abstract String getLocalizedName();
    public abstract String getLocalizedDescription();
    public abstract String getJsonName();
    public abstract String getStatusImageSrc();

    public JsonBlock getJson(AbstractUnit unit) {
        JsonBlock unitBlock = new JsonBlock();
        unitBlock.addParam("col", unit.getLocation().getX());
        unitBlock.addParam("row", unit.getLocation().getY());
        unitBlock.addParam("name", unit.getView().getJsonName());
        unitBlock.addParam("civ", unit.getCivilization().getView().getJsonName());
        return unitBlock;
    }
}
