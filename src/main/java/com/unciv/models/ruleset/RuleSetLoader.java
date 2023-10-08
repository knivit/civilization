package com.unciv.models.ruleset;

import com.badlogic.gdx.files.FileHandle;
import com.unciv.models.ruleset.nation.CityStateType;
import com.unciv.models.ruleset.nation.Difficulty;
import com.unciv.models.ruleset.nation.Nation;
import com.unciv.models.ruleset.tech.Era;
import com.unciv.models.ruleset.tech.TechColumn;
import com.unciv.models.ruleset.unit.BaseUnit;
import com.unciv.models.ruleset.unit.Promotion;
import com.unciv.models.ruleset.unit.UnitType;

public class RuleSetLoader {

    public void load(FileHandle folderHandle) {
        // Note: Most files are loaded using createHashmap, which sets originRuleset automatically.
        // For other files containing IRulesetObject's we'll have to remember to do so manually - e.g. Tech.
        FileHandle modOptionsFile = folderHandle.child("ModOptions.json");
        if (modOptionsFile.exists()) {
            try {
                modOptions = json().fromJsonFile(ModOptions::class.java, modOptionsFile)
                modOptions.updateDeprecations()
            } catch (ex: Exception) {
                Log.error("Failed to get modOptions from json file", ex)
            }
        }

        val techFile = folderHandle.child("Techs.json");
        if (techFile.exists()) {
            val techColumns = json().fromJsonFile(Array<TechColumn>::class.java, techFile);
            for (techColumn in techColumns) {
                this.techColumns.add(techColumn);
                for (tech in techColumn.techs) {
                    if (tech.cost == 0) tech.cost = techColumn.techCost;
                    tech.column = techColumn;
                    tech.originRuleset = name;
                    technologies[tech.name] = tech;
                }
            }
        }

        val buildingsFile = folderHandle.child("Buildings.json");
        if (buildingsFile.exists()) buildings += createHashmap(json().fromJsonFile(Array<Building>::class.java, buildingsFile))

        val terrainsFile = folderHandle.child("Terrains.json");
        if (terrainsFile.exists()) {
            terrains += createHashmap(json().fromJsonFile(Array<Terrain>::class.java, terrainsFile));
            for (terrain in terrains.values) {
                terrain.originRuleset = name;
                terrain.setTransients();
            }
        }

        val resourcesFile = folderHandle.child("TileResources.json");
        if (resourcesFile.exists()) tileResources += createHashmap(json().fromJsonFile(Array<TileResource>::class.java, resourcesFile))

        val improvementsFile = folderHandle.child("TileImprovements.json");
        if (improvementsFile.exists()) tileImprovements += createHashmap(json().fromJsonFile(Array<TileImprovement>::class.java, improvementsFile))

        val erasFile = folderHandle.child("Eras.json");
        if (erasFile.exists()) eras += createHashmap(json().fromJsonFile(Array<Era>::class.java, erasFile))
        // While `eras.values.toList()` might seem more logical, eras.values is a MutableCollection and
        // therefore does not guarantee keeping the order of elements like a LinkedHashMap does.
        // Using map{} sidesteps this problem
        eras.map { it.value }.withIndex().forEach { it.value.eraNumber = it.index }

        val speedsFile = folderHandle.child("Speeds.json");
        if (speedsFile.exists()) {
            speeds += createHashmap(json().fromJsonFile(Array<Speed>::class.java, speedsFile));
        }

        val unitTypesFile = folderHandle.child("UnitTypes.json");
        if (unitTypesFile.exists()) unitTypes += createHashmap(json().fromJsonFile(Array<UnitType>::class.java, unitTypesFile))

        val unitsFile = folderHandle.child("Units.json");
        if (unitsFile.exists()) units += createHashmap(json().fromJsonFile(Array<BaseUnit>::class.java, unitsFile))

        val promotionsFile = folderHandle.child("UnitPromotions.json");
        if (promotionsFile.exists()) unitPromotions += createHashmap(json().fromJsonFile(Array<Promotion>::class.java, promotionsFile))

        val questsFile = folderHandle.child("Quests.json");
        if (questsFile.exists()) quests += createHashmap(json().fromJsonFile(Array<Quest>::class.java, questsFile))

        val specialistsFile = folderHandle.child("Specialists.json");
        if (specialistsFile.exists()) specialists += createHashmap(json().fromJsonFile(Array<Specialist>::class.java, specialistsFile))

        val policiesFile = folderHandle.child("Policies.json");
        if (policiesFile.exists()) {
            policyBranches += createHashmap(
                    json().fromJsonFile(Array<PolicyBranch>::class.java, policiesFile);
            )

            for (branch in policyBranches.values) {
                // Setup this branch
                branch.requires = new ArrayList();
                branch.branch = branch;
                for (victoryType in victories.values) {
                    if (victoryType.name !in branch.priorities.keys) {
                        branch.priorities[victoryType.name] = 0;
                    }
                }
                policies[branch.name] = branch;

                // Append child policies of this branch
                for (policy in branch.policies) {
                    policy.branch = branch;
                    policy.originRuleset = name;
                    if (policy.requires == null) {
                        policy.requires = arrayListOf(branch.name);
                    }

                    if (policy != branch.policies.last()) {
                        // If mods override a previous policy's location, we don't want that policy to stick around,
                        // because it leads to softlocks on the policy picker screen
                        val conflictingLocationPolicy = policies.values.firstOrNull {
                            it.branch.name == policy.branch.name
                                    && it.column == policy.column
                                    && it.row == policy.row
                        }
                        if (conflictingLocationPolicy != null)
                            policies.remove(conflictingLocationPolicy.name);
                    }
                    policies[policy.name] = policy;

                }

                // Add a finisher
                branch.policies.last().name =
                        branch.name + Policy.branchCompleteSuffix;
            }
        }

        val beliefsFile = folderHandle.child("Beliefs.json");
        if (beliefsFile.exists())
            beliefs += createHashmap(json().fromJsonFile(Array<Belief>::class.java, beliefsFile));

        val religionsFile = folderHandle.child("Religions.json");
        if (religionsFile.exists())
            religions += json().fromJsonFile(Array<String>::class.java, religionsFile).toList();

        val ruinRewardsFile = folderHandle.child("Ruins.json");
        if (ruinRewardsFile.exists())
            ruinRewards += createHashmap(json().fromJsonFile(Array<RuinReward>::class.java, ruinRewardsFile))

        val nationsFile = folderHandle.child("Nations.json");
        if (nationsFile.exists()) {
            nations += createHashmap(json().fromJsonFile(Array<Nation>::class.java, nationsFile));
            for (nation in nations.values) nation.setTransients();
        }

        val difficultiesFile = folderHandle.child("Difficulties.json");
        if (difficultiesFile.exists())
            difficulties += createHashmap(json().fromJsonFile(Array<Difficulty>::class.java, difficultiesFile))

        val globalUniquesFile = folderHandle.child("GlobalUniques.json");
        if (globalUniquesFile.exists()) {
            globalUniques = json().fromJsonFile(GlobalUniques::class.java, globalUniquesFile);
            globalUniques.originRuleset = name;
        }

        val victoryTypesFile = folderHandle.child("VictoryTypes.json");
        if (victoryTypesFile.exists()) {
            victories += createHashmap(json().fromJsonFile(Array<Victory>::class.java, victoryTypesFile));
        }

        val cityStateTypesFile = folderHandle.child("CityStateTypes.json");
        if (cityStateTypesFile.exists()) {
            cityStateTypes += createHashmap(json().fromJsonFile(Array<CityStateType>::class.java, cityStateTypesFile))
        }



        // Add objects that might not be present in base ruleset mods, but are required
        if (modOptions.isBaseRuleset) {
            // This one should be temporary
            if (unitTypes.isEmpty()) {
                unitTypes.putAll(RulesetCache.getVanillaRuleset().unitTypes);
            }

            // These should be permanent
            if (ruinRewards.isEmpty())
                ruinRewards.putAll(RulesetCache.getVanillaRuleset().ruinRewards);

            if (globalUniques.uniques.isEmpty()) {
                globalUniques = RulesetCache.getVanillaRuleset().globalUniques;
            }
            // If we have no victories, add all the default victories
            if (victories.isEmpty()) victories.putAll(RulesetCache.getVanillaRuleset().victories);

            if (speeds.isEmpty()) speeds.putAll(RulesetCache.getVanillaRuleset().speeds);

            if (cityStateTypes.isEmpty())
                for (cityStateType in RulesetCache.getVanillaRuleset().cityStateTypes.values);
                    cityStateTypes[cityStateType.name] = CityStateType().apply {
                name = cityStateType.name
                color = cityStateType.color
                friendBonusUniques = ArrayList(cityStateType.friendBonusUniques.filter {
                    RulesetValidator(this@RuleSet).checkUnique(
                            Unique(it),
                            false,
                            cityStateType,
                            UniqueType.UniqueComplianceErrorSeverity.RulesetSpecific
                    ).isEmpty()
                })
                allyBonusUniques = ArrayList(cityStateType.allyBonusUniques.filter {
                    RulesetValidator(this@RuleSet).checkUnique(
                            Unique(it),
                            false,
                            cityStateType,
                            UniqueType.UniqueComplianceErrorSeverity.RulesetSpecific
                    ).isEmpty()
                })
            }
        }
    }

}
