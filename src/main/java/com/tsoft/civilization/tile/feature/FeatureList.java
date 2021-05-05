package com.tsoft.civilization.tile.feature;

import java.util.ArrayList;

public class FeatureList extends ArrayList<AbstractFeature> {

    public <F extends AbstractFeature> F getByClass(Class<F> featureClass) {
        for (AbstractFeature feature : this) {
            if (featureClass.equals(feature.getClass())) {
                return (F)feature;
            }
        }

        return null;
    }
}
