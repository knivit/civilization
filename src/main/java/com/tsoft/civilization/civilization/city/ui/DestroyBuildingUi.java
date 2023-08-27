package com.tsoft.civilization.civilization.city.ui;

import com.tsoft.civilization.civilization.building.L10nBuilding;
import com.tsoft.civilization.civilization.building.AbstractBuilding;
import com.tsoft.civilization.civilization.city.action.DestroyBuildingAction;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.ajax.ClientAjaxRequest;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class DestroyBuildingUi {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private final DestroyBuildingAction destroyBuildingAction;

    public static DestroyBuildingUi newInstance() {
        return new DestroyBuildingUi(new DestroyBuildingAction());
    }

    private static String getLocalizedName() {
        return L10nBuilding.DESTROY.getLocalized();
    }

    private static String getLocalizedDescription() {
        return L10nBuilding.DESTROY_DESCRIPTION.getLocalized();
    }

    public StringBuilder getHtml(AbstractBuilding building) {
        if (destroyBuildingAction.canDestroyBuilding(building).isFail()) {
            return null;
        }

        return Format.text("""
            <td><button onclick="$buttonOnClick">$buttonLabel</button></td><td>$actionDescription</td>
            """,

            "$buttonOnClick", ClientAjaxRequest.destroyBuildingAction(building),
            "$buttonLabel", getLocalizedName(),
            "$actionDescription", getLocalizedDescription()
        );
    }
}