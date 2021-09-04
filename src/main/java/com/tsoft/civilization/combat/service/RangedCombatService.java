package com.tsoft.civilization.combat.service;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.combat.*;
import com.tsoft.civilization.tile.feature.FeatureList;
import com.tsoft.civilization.tile.tile.AbstractTile;
import com.tsoft.civilization.tile.feature.AbstractFeature;
import com.tsoft.civilization.unit.service.move.TilePassCostTable;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.World;

import java.util.ArrayList;
import java.util.List;

public class RangedCombatService {

    private static final BaseCombatService baseCombatService = new BaseCombatService();

    // Get all targets where the unit's missile can reach (there can be more than one targets on a tile)
    public HasCombatStrengthList getTargetsToAttack(HasCombatStrength attacker) {
        HasCombatStrengthList targets = new HasCombatStrengthList();

        // Add foreign units/cities if its civilization is in war with our
        int attackRadius = attacker.calcCombatStrength().getRangedAttackRadius();
        HasCombatStrengthList targetsAround = baseCombatService.getPossibleTargetsAround(attacker, attackRadius);
        Civilization myCivilization = attacker.getCivilization();
        World world = myCivilization.getWorld();

        for (HasCombatStrength target : targetsAround) {
            Civilization otherCivilization = target.getCivilization();
            if (world.isWar(myCivilization, otherCivilization)) {
                int missileStrength = getMissileStrength(attacker, target);
                if (missileStrength > 0) {
                    targets.add(target);
                }
            }
        }

        return targets;
    }

    public CombatResult attack(HasCombatStrength attacker, HasCombatStrength target) {
        int strikeStrength = getMissileStrength(attacker, target);
        if (strikeStrength <= 0) {
            return CombatResult.builder()
                .status(CombatStatus.FAILED_RANGED_UNDERSHOOT)
                .build();
        }

        return baseCombatService.attack(attacker, target, strikeStrength);
    }

    private int getMissileStrength(HasCombatStrength attacker, HasCombatStrength target) {
        int rangedAttackStrength = attacker.calcCombatStrength().getRangedAttackStrength();

        // add all bonuses
        int missileStrength = baseCombatService.calcStrikeStrength(attacker, rangedAttackStrength, target);

        // calc path cost
        int missilePathCost = 0;
        List<Point> path = getMissilePath(attacker.getLocation(), target.getLocation());
        for (Point loc : path) {
            AbstractTile tile = attacker.getCivilization().getTilesMap().getTile(loc);
            missilePathCost += getMissilePastCost(attacker, tile);
        }

        missileStrength -= missilePathCost;
        return missileStrength;
    }

    // Digital Differential Analyzer (DDA) algorithm for rasterization of lines
    // Point 'from' doesn't included
    private List<Point> getMissilePath(Point from, Point to) {
        List<Point> path = new ArrayList<>();

        int ix1 = from.getX();
        int iy1 = from.getY();
        int ix2 = to.getX();
        int iy2 = to.getY();
        float len = Math.max(Math.abs(ix2 - ix1), Math.abs(iy2 - iy1));
        float dX = (ix2 - ix1) / len;
        float dY = (iy2 - iy1) / len;

        boolean toAdded = false;
        float x = ix1, y = iy1;
        for (int i = 0; i < len; i ++) {
            x += dX;
            y += dY;
            Point p = new Point(Math.round(x), Math.round(y));
            toAdded |= p.equals(to);
            path.add(p);
        }

        if (!toAdded) {
            path.add(to);
        }

        return path;
    }

    // Returns passing (or flying) cost for attacker's missile
    public int getMissilePastCost(HasCombatStrength attacker, AbstractTile tile) {
        int passCost = MissileTilePastCostTable.get(attacker, tile);

        FeatureList features = tile.getFeatures();
        if (features.isEmpty()) {
            return passCost;
        }

        // start from last (i.e. on top) feature
        for (int i = features.size() - 1; i >= 0; i --) {
            AbstractFeature feature = features.get(i);

            int featurePassCost = getMissilePassCost(attacker, feature);
            if (featurePassCost == TilePassCostTable.UNPASSABLE) {
                return TilePassCostTable.UNPASSABLE;
            }

            passCost += featurePassCost;
        }

        return passCost;
    }

    public int getMissilePassCost(HasCombatStrength attacker, AbstractFeature feature) {
        return MissileFeaturePastCostTable.get(attacker, feature);
    }
}
