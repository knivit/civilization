package com.tsoft.civilization.web.ajax;

import com.tsoft.civilization.util.Format;

import static com.tsoft.civilization.civilization.L10nCivilization.*;

public class ServerStaticResource {

    public static final String POPULATION_IMAGE_SRC = "images/status/population.png";

    public static final StringBuilder POPULATION_IMAGE = Format.text("""
        <image src="$populationImage"/>
        """,
        "$populationImage", POPULATION_IMAGE_SRC
    );

    public static final StringBuilder POPULATION_IMAGE_WITH_TOOLTIP = Format.text("""
        <div class="tooltip"><image src="$populationImage"/><span class="tooltiptext">$text</span></div>
        """,
        "$populationImage", POPULATION_IMAGE_SRC,
        "$text", POPULATION
    );

    public static final String PRODUCTION_IMAGE_SRC = "images/status/production.png";

    public static final StringBuilder PRODUCTION_IMAGE = Format.text("""
        <image src="$productionImage"/>
        """,
        "$productionImage", PRODUCTION_IMAGE_SRC
    );

    public static final StringBuilder PRODUCTION_IMAGE_WITH_TOOLTIP = Format.text("""
        <div class="tooltip"><image src="$productionImage"/><span class="tooltiptext">$text</span></div>
        """,
        "$productionImage", PRODUCTION_IMAGE_SRC,
        "$text", PRODUCTION
    );

    public static final String GOLD_IMAGE_SRC = "images/status/gold.png";

    public static final StringBuilder GOLD_IMAGE = Format.text("""
        <image src="$goldImage"/>
        """,
        "$goldImage", GOLD_IMAGE_SRC
    );

    public static final StringBuilder GOLD_IMAGE_WITH_TOOLTIP = Format.text("""
        <div class="tooltip"><image src="$goldImage"/><span class="tooltiptext">$text</span></div>
        """,
        "$goldImage", GOLD_IMAGE_SRC,
        "$text", GOLD
    );

    public static final String FOOD_IMAGE_SRC = "images/status/food.png";

    public static final StringBuilder FOOD_IMAGE = Format.text("""
        <image src="$foodImage"/>
        """,
        "$foodImage", FOOD_IMAGE_SRC
    );

    public static final StringBuilder FOOD_IMAGE_WITH_TOOLTIP = Format.text("""
        <div class="tooltip"><image src="$foodImage"/><span class="tooltiptext">$text</span></div>
        """,
        "$foodImage", FOOD_IMAGE_SRC,
        "$text", FOOD
    );

    public static final String HAPPINESS_IMAGE_SRC(int happiness) {
        return (happiness < 0) ? "images/status/unhappiness.png" : "images/status/happiness.png";
    }

    public static final StringBuilder HAPPINESS_IMAGE(int happiness) {
        return Format.text("""
            <image src="$happinessImage"/>
            """,

            "$happinessImage", HAPPINESS_IMAGE_SRC(happiness)
        );
    }

    public static final StringBuilder HAPPINESS_IMAGE_WITH_TOOLTIP(int happiness) {
        return Format.text("""
            <div class="tooltip"><image src="$happinessImage"/><span class="tooltiptext">$text</span></div>
            """,

            "$happinessImage", HAPPINESS_IMAGE_SRC(happiness),
            "$text", HAPPINESS
        );
    }
}
