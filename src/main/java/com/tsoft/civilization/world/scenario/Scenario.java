package com.tsoft.civilization.world.scenario;

import com.tsoft.civilization.civilization.Civilization;

import java.util.function.Function;

public interface Scenario extends Function<Civilization, ScenarioApplyResult> {
}
