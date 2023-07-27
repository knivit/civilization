package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.helper.html.HtmlDocument;
import com.tsoft.civilization.helper.html.HtmlParser;
import com.tsoft.civilization.improvement.ImprovementFactory;
import com.tsoft.civilization.improvement.catalog.farm.Farm;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.MockRequest;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.state.Sessions;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.AMERICA;
import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static org.assertj.core.api.Assertions.assertThat;

public class GetImprovementInfoTest {

    private static final AbstractAjaxRequest getImprovementInfoRequest =
        AbstractAjaxRequest.getInstance(GetImprovementInfo.class.getSimpleName());

    // Get improvement's info for mine civilization - must be shown info about its supply
    @Test
    public void get_json_for_my_civilization() {
        MockWorld world = MockWorld.newSimpleWorld();

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .city("city1", new Point(2, 2))
        );

        AbstractTerrain tile = world.getTilesMap().getTile(new Point(1, 1));
        Farm farm = ImprovementFactory.newInstance(Farm.CLASS_UUID, tile, world, world.city("city1"));

        Sessions.getCurrent().setActiveCivilization(russia);
        Request request = MockRequest.newInstance("id", farm.getId());

        Response response = getImprovementInfoRequest.getJson(request);

        assertThat(response.getResponseCode())
            .isEqualTo(ResponseCode.OK);

        HtmlDocument expected = HtmlParser.parse(Format.text("""
            <table id='info_table'>
                <tr><td>Production</td><td>0 <image src="images/status/production.png"/></td></tr>
                <tr><td>Gold</td><td>0 <image src="images/status/gold.png"/></td></tr>
                <tr><td>Food</td><td>2 <image src="images/status/food.png"/></td></tr>
            </table>
            """));

        HtmlDocument actual = HtmlParser.parse(response.getContent());

        assertThat(actual.findById("info_table"))
            .isNotNull()
            .isEqualTo(expected.findById("info_table"));
    }

    // Fail to get info for foreign civilization's improvement
    @Test
    public void get_json_for_foreign_civilization() {
        MockWorld world = MockWorld.newSimpleWorld();

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .city("city1", new Point(2, 2))
        );

        Civilization america = world.createCivilization(AMERICA, new MockScenario());

        AbstractTerrain tile = world.getTilesMap().getTile(new Point(1, 1));
        Farm farm = ImprovementFactory.newInstance(Farm.CLASS_UUID, tile, world, world.city("city1"));

        Sessions.getCurrent().setActiveCivilization(america);
        Request request = MockRequest.newInstance("id", farm.getId());

        Response response = getImprovementInfoRequest.getJson(request);

        assertThat(response.getResponseCode())
            .isEqualTo(ResponseCode.BAD_REQUEST);
    }

    // Get info for improvement without civilization - there should be no supply
    @Test
    public void get_json_for_null_civilization() {
        MockWorld world = MockWorld.newSimpleWorld();

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .city("city1", Point.of(2, 2))
        );

        AbstractTerrain tile = world.getTilesMap().getTile(Point.of(1, 1));
        Farm farm = ImprovementFactory.newInstance(Farm.CLASS_UUID, tile, world, null);

        Sessions.getCurrent().setActiveCivilization(russia);
        Request request = MockRequest.newInstance("id", farm.getId());

        Response response = getImprovementInfoRequest.getJson(request);

        assertThat(response.getResponseCode())
            .isEqualTo(ResponseCode.OK);

        HtmlDocument actual = HtmlParser.parse(response.getContent());

        assertThat(actual.findById("info_table"))
            .isNull();
    }
}
