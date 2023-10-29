package com.tsoft.civilization.tile.terrain;

import com.tsoft.civilization.civilopedia.JsonCivilopediaText;
import lombok.Data;

import java.util.List;

@Data
public class JsonTerrain {

    private String name;
    private String type;
    private boolean impassable;
    private boolean unbuildable;
    private float defenceBonus;
    private boolean overrideStats;
    private int food;
    private int gold;
    private int production;
    private int science;
    private int culture;
    private int faith;
    private int happiness;
    private int movementCost;
    private int weight;
    private byte[] rgb;
    private String turnsInto;
    private List<String> occursOn;
    private List<String> uniques;
    private List<JsonCivilopediaText> civilopediaText;
}
