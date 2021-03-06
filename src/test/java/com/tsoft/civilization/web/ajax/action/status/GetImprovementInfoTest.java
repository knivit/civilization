package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.helper.html.HtmlDocument;
import com.tsoft.civilization.helper.html.HtmlParser;
import com.tsoft.civilization.improvement.AbstractImprovement;
import com.tsoft.civilization.improvement.ImprovementFactory;
import com.tsoft.civilization.improvement.ancientruins.AncientRuins;
import com.tsoft.civilization.improvement.farm.Farm;
import com.tsoft.civilization.tile.tile.AbstractTile;
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

    // Get improvement's info for mine civilization - must be shown info about it's supply
    @Test
    public void get_json_for_my_civilization() {
        MockWorld world = MockWorld.newSimpleWorld();

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .city("city1", new Point(2, 2))
        );

        world.startGame();

        AbstractTile tile = world.getTilesMap().getTile(new Point(1, 1));
        Farm farm = ImprovementFactory.newInstance(Farm.CLASS_UUID, tile, world.city("city1"));

        Sessions.getCurrent().setActiveCivilization(russia);
        Request request = MockRequest.newInstance("improvement", farm.getId());

        Response response = getImprovementInfoRequest.getJson(request);

        assertThat(response.getResponseCode())
            .isEqualTo(ResponseCode.OK);

        HtmlDocument expected = HtmlParser.parse(Format.text("""
            <table id='info_table'>
                <tr><td>Production</td><td>0 <image src="images/status/production.png"/></td></tr>
                <tr><td>Gold</td><td>-2 <image src="images/status/gold.png"/></td></tr>
                <tr><td>Food</td><td>0 <image src="images/status/food.png"/></td></tr>
            </table>
            """));

        HtmlDocument actual = HtmlParser.parse(response.getContent());

        assertThat(actual.findById("info_table"))
            .isNotNull()
            .isEqualTo(expected.findById("info_table"));
    }

    // Get info for foreign civilization's improvement - there should be no supply
    @Test
    public void get_json_for_foreign_civilization() {
        MockWorld world = MockWorld.newSimpleWorld();

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .city("city1", new Point(2, 2))
        );

        Civilization america = world.createCivilization(AMERICA, new MockScenario());

        world.startGame();

        AbstractTile tile = world.getTilesMap().getTile(new Point(1, 1));
        Farm farm = ImprovementFactory.newInstance(Farm.CLASS_UUID, tile, world.city("city1"));

        Sessions.getCurrent().setActiveCivilization(america);
        Request request = MockRequest.newInstance("improvement", farm.getId());

        Response response = getImprovementInfoRequest.getJson(request);

        assertThat(response.getResponseCode())
            .isEqualTo(ResponseCode.OK);

        HtmlDocument actual = HtmlParser.parse(response.getContent());

        assertThat(actual.findById("info_table"))
            .isNull();
    }

    // Get info for improvement without civilization - there should be no supply
    @Test
    public void get_json_for_null_civilization() {
        MockWorld world = MockWorld.newSimpleWorld();

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .city("city1", new Point(2, 2))
        );

        world.startGame();

        AbstractTile tile = world.getTilesMap().getTile(new Point(1, 1));
        AncientRuins ancientRuins = ImprovementFactory.newInstance(AncientRuins.CLASS_UUID, tile, null);

        Sessions.getCurrent().setActiveCivilization(russia);
        Request request = MockRequest.newInstance("improvement", ancientRuins.getId());

        Response response = getImprovementInfoRequest.getJson(request);

        assertThat(response.getResponseCode())
            .isEqualTo(ResponseCode.OK);

        HtmlDocument actual = HtmlParser.parse(response.getContent());

        assertThat(actual.findById("info_table"))
            .isNull();
    }
}
