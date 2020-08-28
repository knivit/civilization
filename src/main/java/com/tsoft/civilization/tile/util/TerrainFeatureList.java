package com.tsoft.civilization.tile.util;

import com.tsoft.civilization.tile.feature.TerrainFeature;

import java.util.ArrayList;

public class TerrainFeatureList extends ArrayList<TerrainFeature<?>> {
    public <F extends TerrainFeature<?>> F getByClass(Class<F> featureClass) {
        for (TerrainFeature<?> feature : this) {
            if (featureClass.equals(feature.getClass())) {
                return (F)feature;
            }
        }

        return null;
    }
}
