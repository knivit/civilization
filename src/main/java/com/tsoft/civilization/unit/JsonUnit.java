package com.tsoft.civilization.unit;

import com.tsoft.civilization.civilopedia.JsonCivilopediaText;
import lombok.Data;

import java.util.List;

@Data
public class JsonUnit {
    private String name;
    private String unitType;
    private Integer movement;
    private Integer cost;
    private Integer hurryCostModifier;
    private Integer strength;
    private Integer rangedStrength;
    private String requiredTech;
    private String obsoleteTech;
    private List<String> uniques;
    private List<String> promotions;
    private String replaces;
    private String upgradesTo;
    private String uniqueTo;
    private String requiredResource;
    private String attackSound;
    private List<JsonCivilopediaText> civilopediaText;
}