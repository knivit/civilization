package com.tsoft.civilization.web.ajax;

public class ServerStaticResource {

    public static final String POPULATION_IMAGE = "images/status/population.png";
    public static final String PRODUCTION_IMAGE = "images/status/production.png";
    public static final String GOLD_IMAGE = "images/status/gold.png";
    public static final String FOOD_IMAGE = "images/status/food.png";
    public static final String HAPPINESS_IMAGE(int happiness) { return (happiness < 0) ? "images/status/unhappiness.png" : "images/status/happiness.png"; }
}
