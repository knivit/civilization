package com.tsoft.civilization.improvement.city;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.combat.service.AttackService;
import com.tsoft.civilization.combat.action.AttackAction;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.view.JsonBlock;
import com.tsoft.civilization.improvement.AbstractImprovementView;
import lombok.Getter;

import static com.tsoft.civilization.L10n.L10nLanguage.EN;
import static com.tsoft.civilization.L10n.L10nLanguage.RU;

public class CityView extends AbstractImprovementView {

    private final AttackService attackService = new AttackService();
    private final AttackAction attackAction = new AttackAction(attackService);

    public static final L10n DESCRIPTION = new L10n()
        .put(EN, "Cities are source of power of your civilization")
        .put(RU, "Города - источник военной мощи вашей цивилизации");

    @Getter
    private final L10n name;

    @Getter
    public final String localizedDescription = DESCRIPTION.getLocalized();

    @Getter
    public final String statusImageSrc = "images/status/city.png";

    public CityView(L10n name) {
        this.name = name;
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
        city.getTileService().getLocations().forEach(loc -> {
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
