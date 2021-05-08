package com.tsoft.civilization.unit.civil.workers.action;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.helper.html.HtmlDocument;
import com.tsoft.civilization.helper.html.HtmlParser;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.tile.tile.grassland.Grassland;
import com.tsoft.civilization.tile.feature.AbstractFeature;
import com.tsoft.civilization.tile.feature.hill.Hill;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.unit.civil.workers.Workers;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.util.Point;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static com.tsoft.civilization.unit.civil.workers.action.RemoveForestAction.FOREST_IS_REMOVED;
import static com.tsoft.civilization.unit.civil.workers.action.RemoveForestAction.REMOVING_FOREST;
import static com.tsoft.civilization.unit.civil.workers.action.RemoveHillAction.HILL_IS_REMOVED;
import static com.tsoft.civilization.unit.civil.workers.action.RemoveHillAction.REMOVING_HILL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RemoveForestActionTest {

    @Test
    public void cantRemoveForestedHill() {
        MockWorld world = MockWorld.of(MockTilesMap.of(3,
            " |0 1 2 ", " |0 1 2 ", " |0 1 2 ",
            "-+------", "-+------", "-+------",
            "0|. . . ", "0|. . . ", "0|. . . ",
            "1| . g .", "1| . h .", "1| . f .",
            "2|. . . ", "2|. . . ", "2|. . . ",
            "3| . . .", "3| . . .", "3| . . ."));

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .workers("workers", new Point(1, 1))
        );

        world.startGame();

        // case 1
        // no needed technology
        assertThat(RemoveForestAction.removeForest((Workers) world.unit("workers")))
            .isEqualTo(WorkersActionResults.FAIL_NO_MINING_TECHNOLOGY);

        // case 2
        // we must remove forest before hill's removal
        russia.addTechnology(Technology.MINING);
        assertThat(RemoveHillAction.removeHill((Workers) world.unit("workers")))
            .isEqualTo(WorkersActionResults.FAIL_FOREST_MUST_BE_REMOVED_FIRST);
    }

    @Test
    public void removeForestedHill() {
        MockWorld world = MockWorld.of(MockTilesMap.of(3,
            " |0 1 2 ", " |0 1 2 ", " |0 1 2 ",
            "-+------", "-+------", "-+------",
            "0|. . . ", "0|. . . ", "0|. . . ",
            "1| . g .", "1| . h .", "1| . f .",
            "2|. . . ", "2|. . . ", "2|. . . ",
            "3| . . .", "3| . . .", "3| . . ."));

        Civilization civilization = world.createCivilization(RUSSIA, new MockScenario()
            .workers("workers", new Point(1, 1))
        );

        world.startGame();
        civilization.addTechnology(Technology.MINING);

        // remove a forest
        for (int i = 0; i < 4; i ++) {
            assertThat(RemoveForestAction.removeForest((Workers) world.unit("workers")))
                .isEqualTo(REMOVING_FOREST);
        }

        // forest removed
        assertThat(RemoveForestAction.removeForest((Workers) world.unit("workers")))
            .isEqualTo(FOREST_IS_REMOVED);

        assertThat(world.getTilesMap().getTile(1, 1).getClass()).isEqualTo(Grassland.class);

        // now there is a hill
        assertThat(world.getTilesMap().getTile(1, 1).getFeatures())
            .isNotNull()
            .returns(1, ArrayList::size)
            .extracting(list -> list.get(0))
            .isNotNull()
            .returns(Hill.class, AbstractFeature::getClass);

        // remove a hill
        for (int i = 0; i < 4; i ++) {
            assertThat(RemoveHillAction.removeHill((Workers) world.unit("workers")))
                .isEqualTo(REMOVING_HILL);
        }

        // hill removed
        assertThat(RemoveHillAction.removeHill((Workers) world.unit("workers")))
            .isEqualTo(HILL_IS_REMOVED);

        assertThat(world.getTilesMap().getTile(1, 1).getClass()).isEqualTo(Grassland.class);

        // no any features
        assertThat(world.getTilesMap().getTile(1, 1).getFeatures())
            .isNotNull()
            .returns(0, ArrayList::size);
    }

    @Test
    public void get_html() {
        MockWorld world = MockWorld.of(MockTilesMap.of(3,
            " |0 1 2 ", " |0 1 2 ", " |0 1 2 ",
            "-+------", "-+------", "-+------",
            "0|. . . ", "0|. . . ", "0|. . . ",
            "1| . g .", "1| . h .", "1| . f .",
            "2|. . . ", "2|. . . ", "2|. . . ",
            "3| . . .", "3| . . .", "3| . . ."));

        Civilization civilization = world.createCivilization(RUSSIA, new MockScenario()
            .workers("workers", new Point(1, 1))
        );

        world.startGame();
        civilization.addTechnology(Technology.MINING);

        Workers workers = (Workers) world.unit("workers");
        StringBuilder buf = RemoveForestAction.getHtml(workers);

        HtmlDocument expected = HtmlParser.parse(Format.text("""
            <td>
               <button onclick="client.removeForestAction({ workers:'$workersId' })">Remove Forest</button>
            </td>
            <td>The forest will be removed from the tile</td>
            """,

            "$workersId", workers.getId()));

        HtmlDocument actual = HtmlParser.parse(buf);
        assertThat(actual).isEqualTo(expected);
    }
}
