package com.tsoft.civilization.improvement.city;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.combat.CombatService;
import com.tsoft.civilization.unit.action.AttackAction;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.view.JsonBlock;
import com.tsoft.civilization.improvement.AbstractImprovementView;
import lombok.Getter;

public class CityView extends AbstractImprovementView {

    private final CombatService combatService = new CombatService();
    private final AttackAction attackAction = new AttackAction(combatService);

    @Getter
    private final L10n name;

    public CityView(L10n name) {
        this.name = name;
    }

    @Override
    public String getLocalizedName() {
        return L10nCity.CITY_NAME.getLocalized();
    }

    @Override
    public String getLocalizedDescription() {
        return L10nCity.CITY_DESCRIPTION.getLocalized();
    }

    @Override
    public String getStatusImageSrc() {
        return "images/status/city.png";
    }

    public String getLocalizedCityName() {
        return name.getLocalized();
    }

    public JsonBlock getJson(City city) {
        JsonBlock cityBlock = new JsonBlock();
        cityBlock.addParam("col", city.getLocation().getX());
        cityBlock.addParam("row", city.getLocation().getY());
        cityBlock.addParam("name", city.getView().getLocalizedCityName());
        cityBlock.addParam("civilization", city.getCivilization().getView().getJsonName());
        if (city.isCapital()) {
            cityBlock.addParam("isCapital", true);
        }

        // tiles owned by the city
        cityBlock.startArray("locations");
        city.getLocations().forEach(loc -> {
            JsonBlock locBlock = new JsonBlock();
            locBlock.addParam("col", loc.getX());
            locBlock.addParam("row", loc.getY());
            cityBlock.addElement(locBlock.getText());
        });
        cityBlock.stopArray();

        return cityBlock;
    }

    public StringBuilder getHtmlActions(City city) {
        StringBuilder cityAttackAction = attackAction.getHtml(city);
        if (cityAttackAction == null) {
            return null;
        }

        return Format.text(
            "<tr>$attackAction</tr>",

            "$attackAction", cityAttackAction
        );
    }
}
