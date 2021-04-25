package com.tsoft.civilization.improvement.city.event;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.improvement.city.L10nCity;
import com.tsoft.civilization.world.Event;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public final class CityStopYearEvent implements Event {

    private final Instant serverTime = Instant.now();

    private final boolean updateWorldMap;
    private final boolean updateControlPanel;
    private final boolean updateStatusPanel;

    private final L10n message = L10nCity.CITY_STOP_YEAR_EVENT;
    private final L10n cityName;
    private final Supply populationSupply;
    private final Supply buildingsSupply;
    private final Supply constructionSupply;
    private final Supply totalSupply;

    public Object[] getArgs() {
        return new Object[] {
            "$cityName", cityName,
            "$populationSupply", populationSupply,
            "$buildingsSupply", buildingsSupply,
            "$constructionSupply", constructionSupply,
            "$supply", totalSupply
        };
    }
}
