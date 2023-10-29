package com.tsoft.civilization.unit;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.world.AbstractView;
import com.tsoft.civilization.web.view.JsonBlock;
import lombok.Builder;
import lombok.Getter;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;

@Getter
public class AbstractUnitView implements AbstractView {

    private final String jsonName;
    private final L10n name;
    private final String localizedName;
    private final String localizedDescription;
    private final String statusImageSrc;

    public AbstractUnitView(String jsonName) {
        this.jsonName = jsonName;

        name = new L10n().put(EN, jsonName);
        localizedName = jsonName;
        localizedDescription = jsonName;
        statusImageSrc = "assets/Images.AbsoluteUnits/TileSets/AbsoluteUnits/Units" + jsonName + ".png";
    }

    public JsonBlock getJson(AbstractUnit unit) {
        JsonBlock unitBlock = new JsonBlock();
        unitBlock.addParam("col", unit.getLocation().getX());
        unitBlock.addParam("row", unit.getLocation().getY());
        unitBlock.addParam("name", unit.getView().getJsonName());
        unitBlock.addParam("civ", unit.getCivilization().getView().getJsonName());
        return unitBlock;
    }
}
