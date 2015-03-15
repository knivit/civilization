package com.tsoft.civilization.L10n.unit;

import com.tsoft.civilization.L10n.L10nMap;

public class L10nUnit {
    public static L10nMap NO_UNITS = new L10nMap()
            .set("en", "No Units")
            .set("ru", "\u042e\u043d\u0438\u0442\u043e\u0432 \u043d\u0435\u0442");

    public static L10nMap UNITS_ON_TILE = new L10nMap()
            .set("en", "Units on the Tile")
            .set("ru", "\u042e\u043d\u0438\u0442\u044b \u043d\u0430 \u044d\u0442\u043e\u0439 \u044f\u0447\u0435\u0439\u043a\u0435");

    public static L10nMap NAME = new L10nMap()
            .set("en", "Name")
            .set("ru", "\u0418\u043c\u044f");

    public static L10nMap UNIT_NOT_FOUND = new L10nMap()
            .set("en", "Unit not found")
            .set("ru", "\u042e\u043d\u0438\u0442 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d");

    public static L10nMap COMBAT_STRENGTH = new L10nMap()
            .set("en", "Combat Strength")
            .set("ru", "\u0421\u0438\u043b\u0430 \u0432 \u043d\u0430\u043f\u0430\u0434\u0435\u043d\u0438\u0438");

    public static L10nMap MOVE_NAME = new L10nMap()
            .set("en", "Move")
            .set("ru", "\u0418\u0434\u0442\u0438");

    public static L10nMap MOVE_DESCRIPTION = new L10nMap()
            .set("en", "Move unit to a tile")
            .set("ru", "\u041f\u0435\u0440\u0435\u043c\u0435\u0441\u0442\u0438\u0442\u044c \u044e\u043d\u0438\u0442 \u0432 \u0434\u0440\u0443\u0433\u0443\u044e \u044f\u0447\u0435\u0439\u043a\u0443");

    public static L10nMap ATTACK_NAME = new L10nMap()
            .set("en", "Attack")
            .set("ru", "\u0410\u0442\u0430\u043a\u0430");

    public static L10nMap ATTACK_DESCRIPTION = new L10nMap()
            .set("en", "Attack foreign unit or a city")
            .set("ru", "\u0410\u0442\u0430\u043a\u043e\u0432\u0430\u0442\u044c \u0438\u043d\u043e\u0441\u0442\u0440\u0430\u043d\u043d\u044b\u0439 \u044e\u043d\u0438\u0442 \u0438\u043b\u0438 \u0433\u043e\u0440\u043e\u0434");

    public static L10nMap CAPTURE_NAME = new L10nMap()
            .set("en", "Capture")
            .set("ru", "Захватить");

    public static L10nMap CAPTURE_DESCRIPTION = new L10nMap()
            .set("en", "Capture foreign civil unit")
            .set("ru", "\u0417\u0430\u0445\u0432\u0430\u0442\u0438\u0442\u044c \u0438\u043d\u043e\u0441\u0442\u0440\u0430\u043d\u043d\u044b\u0439 \u0433\u0440\u0430\u0436\u0434\u0430\u043d\u0441\u043a\u0438\u0439 \u044e\u043d\u0438\u0442");

    public static L10nMap DESTROY_UNIT_NAME = new L10nMap()
            .set("en", "Destroy Unit")
            .set("ru", "\u0410\u0442\u0430\u043a\u0430");

    public static L10nMap DESTROY_UNIT_DESCRIPTION = new L10nMap()
            .set("en", "Destroy unit and save your money on unit's expenses")
            .set("ru", "\u0423\u043d\u0438\u0447\u0442\u043e\u0436\u044c \u044e\u043d\u0438\u0442 \u0438 \u0441\u043e\u0445\u0440\u0430\u043d\u0438 \u0434\u0435\u043d\u044c\u0433\u0438 \u043e\u0442 \u0440\u0430\u0441\u0445\u043e\u0434\u043e\u0432 \u043d\u0430 \u0441\u043e\u0434\u0435\u0440\u0436\u0430\u043d\u0438\u0435 \u044e\u043d\u0438\u0442\u0430");

    /** Events */

    public static L10nMap UNIT_WAS_DESTROYED_EVENT = new L10nMap()
            .set("en", "Unit was destroyed")
            .set("ru", "\u042e\u043d\u0438\u0442 \u0431\u044b\u043b \u0443\u043d\u0438\u0447\u0442\u043e\u0436\u0435\u043d");

    public static L10nMap UNIT_HAS_WON_ATTACK_EVENT = new L10nMap()
            .set("en", "Unit has won the attack")
            .set("ru", "\u042e\u043d\u0438\u0442 \u0432\u044b\u0438\u0433\u0440\u0430\u043b \u0430\u0442\u0430\u043a\u0443");

    public static L10nMap UNIT_MOVED_EVENT = new L10nMap()
            .set("en", "Unit has moved")
            .set("ru", "\u042e\u043d\u0438\u0442 \u043f\u0435\u0440\u0435\u043c\u0435\u0441\u0442\u0438\u043b\u0441\u044f");

    public static L10nMap ATTACK_DONE_EVENT = new L10nMap()
            .set("en", "Attack done")
            .set("ru", "\u0410\u0442\u0430\u043a\u0430 \u0432\u044b\u043f\u043e\u043b\u043d\u0435\u043d\u0430");

    /** Actions */

    public static L10nMap UNIT_WAS_DESTROYED = new L10nMap()
            .set("en", "Unit was destroyed")
            .set("ru", "\u042e\u043d\u0438\u0442 \u0431\u044b\u043b \u0443\u043d\u0438\u0447\u0442\u043e\u0436\u0435\u043d");

    public static L10nMap INVALID_LOCATION = new L10nMap()
            .set("en", "Invalid location")
            .set("ru", "\u041d\u0435\u0432\u0435\u0440\u043d\u043e\u0435 \u043c\u0435\u0441\u0442\u043e\u043f\u043e\u043b\u043e\u0436\u0435\u043d\u0438\u0435");

    public static L10nMap NO_LOCATIONS_TO_MOVE = new L10nMap()
            .set("en", "There are no locations to move in")
            .set("ru", "\u041d\u0435\u0442 \u0432\u043e\u0437\u043c\u043e\u0436\u043d\u044b\u0445 \u044f\u0447\u0435\u0435\u043a \u0434\u043b\u044f \u043f\u0435\u0440\u0435\u043c\u0435\u0449\u0435\u043d\u0438\u044f");

    public static L10nMap NO_TARGETS_TO_ATTACK = new L10nMap()
            .set("en", "There are no targets to attack")
            .set("ru", "\u041d\u0435\u0442 \u0446\u0435\u043b\u0435\u0439 \u0434\u043b\u044f \u0430\u0442\u0430\u043a\u0438");

    public static L10nMap MELEE_NOT_ENOUGH_PASS_SCORE = new L10nMap()
            .set("en", "Can't move on target's tile")
            .set("ru", "\u041d\u0435\u0432\u043e\u0437\u043c\u043e\u0436\u043d\u043e перейти на ячейку цели");

    public static L10nMap UNIT_MOVED = new L10nMap()
            .set("en", "Unit moved")
            .set("ru", "\u042e\u043d\u0438\u0442 \u043f\u0435\u0440\u0435\u043c\u0435\u0449\u0435\u043d");

    public static L10nMap CAN_MOVE = new L10nMap()
            .set("en", "Unit can move there")
            .set("ru", "\u042e\u043d\u0438\u0442 \u043c\u043e\u0436\u0435\u0442 \u0431\u044b\u0442\u044c \u043f\u0435\u0440\u0435\u043c\u0435\u0449\u0435\u043d");

    public static L10nMap CAN_ATTACK = new L10nMap()
            .set("en", "Unit can attack")
            .set("ru", "\u042e\u043d\u0438\u0442 \u043c\u043e\u0436\u0435\u0442 \u0432\u044b\u043f\u043e\u043b\u043d\u0438\u0442\u044c \u0430\u0442\u0430\u043a\u0443");

    public static L10nMap UNDERSHOOT = new L10nMap()
            .set("en", "Distance too long or missile can't overcome it")
            .set("ru", "\u0420\u0430\u0441\u0441\u0442\u043e\u044f\u043d\u0438\u0435 \u0441\u043b\u0438\u0448\u043a\u043e\u043c \u0431\u043e\u043b\u044c\u0448\u043e\u0435 \u0438\u043b\u0438 \u0441\u043d\u0430\u0440\u044f\u0434 \u043d\u0435 \u043c\u043e\u0436\u0435\u0442 \u043f\u0440\u0435\u043e\u0434\u043e\u043b\u0435\u0442\u044c \u0435\u0433\u043e");

    public static L10nMap TARGET_DESTROYED = new L10nMap()
            .set("en", "Target is destroyed")
            .set("ru", "\u0426\u0435\u043b\u044c \u0443\u043d\u0438\u0447\u0442\u043e\u0436\u0435\u043d\u0430");

    public static L10nMap ATTACKED = new L10nMap()
            .set("en", "Attack is done")
            .set("ru", "\u0410\u0442\u0430\u043a\u0430 \u0437\u0430\u0432\u0435\u0440\u0448\u0435\u043d\u0430");

    public static L10nMap FOREIGN_UNIT_CAPTURED = new L10nMap()
            .set("en", "Foreign unit is captured")
            .set("ru", "\u0418\u043d\u043e\u0441\u0442\u0440\u0430\u043d\u043d\u044b\u0439 \u044e\u043d\u0438\u0442 \u0437\u0430\u0445\u0432\u0430\u0447\u0435\u043d");

    public static L10nMap ATTACKER_IS_DESTROYED_DURING_ATTACK = new L10nMap()
            .set("en", "The attacker was destroyed during the attack")
            .set("ru", "\u0410\u0442\u0430\u043a\u0443\u044e\u0449\u0438\u0439 \u0431\u044b\u043b \u0443\u043d\u0438\u0447\u0442\u043e\u0436\u0435\u043d \u0432 \u0445\u043e\u0434\u0435 \u0430\u0442\u0430\u043a\u0438");

    public static L10nMap NO_LOCATIONS_TO_CAPTURE = new L10nMap()
            .set("en", "There is nothing to capture")
            .set("ru", "\u0414\u043b\u044f \u0437\u0430\u0445\u0432\u0430\u0442\u0435 \u043d\u0438\u0447\u0435\u0433\u043e \u043d\u0435\u0442");

    public static L10nMap CAN_CAPTURE = new L10nMap()
            .set("en", "There are units to capture")
            .set("ru", "\u0415\u0441\u0442\u044c \u044e\u043d\u0438\u0442\u044b \u0434\u043b\u044f \u0437\u0430\u0445\u0432\u0430\u0442\u0430");

    public static L10nMap NOTHING_TO_CAPTURE = new L10nMap()
            .set("en", "There is nothing to capture at that location")
            .set("ru", "\u041d\u0435\u0447\u0435\u0433\u043e \u0437\u0430\u0445\u0432\u0430\u0442\u044b\u0432\u0430\u0442\u044c \u0432 \u0434\u0430\u043d\u043d\u043e\u043c \u043c\u0435\u0441\u0442\u0435");

    public static L10nMap UNIT_DESTROYED = new L10nMap()
            .set("en", "The unit is destroyed successfully")
            .set("ru", "\u042e\u043d\u0438\u0442 \u0443\u0441\u043f\u0435\u0448\u043d\u043e \u0443\u043d\u0438\u0447\u0442\u043e\u0436\u0435\u043d");

    public static L10nMap CAN_DESTROY_UNIT = new L10nMap()
            .set("en", "The unit can be destroyed")
            .set("ru", "\u042e\u043d\u0438\u0442 \u043c\u043e\u0436\u0435\u0442 \u0431\u044b\u0442\u044c \u0443\u0434\u0430\u043b\u0435\u043d");

    public static L10nMap NOT_MILITARY_UNIT = new L10nMap()
            .set("en", "Not a military unit")
            .set("ru", "\u041d\u0435 \u0432\u043e\u0435\u043d\u043d\u044b\u0439 \u044e\u043d\u0438\u0442");

    public static L10nMap NO_PASS_SCORE = new L10nMap()
            .set("en", "Not enough the passing score")
            .set("ru", "\u041d\u0435\u0434\u043e\u0441\u0442\u0430\u0442\u043e\u0447\u043d\u043e \u0445\u043e\u0434\u043e\u0432 \u043f\u0435\u0440\u0435\u043c\u0435\u0449\u0435\u043d\u0438\u044f");

    public static L10nMap UNIT_WAS_BOUGHT = new L10nMap()
            .set("en", "An Unit was bought")
            .set("ru", "\u042e\u043d\u0438\u0442 \u043a\u0443\u043f\u043b\u0435\u043d");

    /** Web */

    public static L10nMap FEATURES = new L10nMap()
            .set("en", "Features")
            .set("ru", "\u0425\u0430\u0440\u0430\u043a\u0442\u0435\u0440\u0438\u0441\u0442\u0438\u043a\u0438");

    public static L10nMap BUILD = new L10nMap()
            .set("en", "Build")
            .set("ru", "\u041f\u043e\u0441\u0442\u0440\u043e\u0438\u0442\u044c");

    public static L10nMap BUILD_DESCRIPTION = new L10nMap()
            .set("en", "Construct a unit")
            .set("ru", "\u041f\u043e\u0441\u0442\u0440\u043e\u0438\u0442\u044c \u044e\u043d\u0438\u0442");

    public static L10nMap BUY = new L10nMap()
            .set("en", "Buy")
            .set("ru", "\u041a\u0443\u043f\u0438\u0442\u044c");

    public static L10nMap BUY_DESCRIPTION = new L10nMap()
            .set("en", "Buy a unit")
            .set("ru", "\u041a\u0443\u043f\u0438\u0442\u044c \u044e\u043d\u0438\u0442");

    public static L10nMap STRENGTH = new L10nMap()
            .set("en", "Defense Strength")
            .set("ru", "\u0421\u0438\u043b\u0430 \u0432 \u0437\u0430\u0449\u0438\u0442\u0435");

    public static L10nMap STRENGTH_HEADER = new L10nMap()
            .set("en", "DS")
            .set("ru", "\u0421\u0417");

    public static L10nMap MELEE_ATTACK_STRENGTH = new L10nMap()
            .set("en", "Melee Attack Strength")
            .set("ru", "\u0421\u0438\u043b\u0430 \u0430\u0442\u0430\u043a\u0438 \u0432 \u0431\u043b\u0438\u0436\u043d\u0435\u043c \u0431\u043e\u044e");

    public static L10nMap MELEE_ATTACK_STRENGTH_HEADER = new L10nMap()
            .set("en", "MAS")
            .set("ru", "\u0421\u0410\u0411");

    public static L10nMap CAN_CONQUER_CITY = new L10nMap()
            .set("en", "Can conquer a city ?")
            .set("ru", "\u041c\u043e\u0436\u0435\u0442 \u0437\u0430\u0445\u0432\u0430\u0442\u044b\u0432\u0430\u0442\u044c \u0433\u043e\u0440\u043e\u0434\u0430 ?");

    public static L10nMap ATTACK_EXPERIENCE = new L10nMap()
            .set("en", "Attack Experience")
            .set("ru", "\u041e\u043f\u044b\u0442 \u0430\u0442\u0430\u043a");

    public static L10nMap DEFENSE_EXPERIENCE = new L10nMap()
            .set("en", "Defense Experience")
            .set("ru", "\u041e\u043f\u044b\u0442 \u0437\u0430\u0449\u0438\u0442");

    public static L10nMap RANGED_ATTACK_STRENGTH = new L10nMap()
            .set("en", "Ranged Attack Strength")
            .set("ru", "\u0421\u0438\u043b\u0430 \u0430\u0442\u0430\u043a\u0438 \u0432 \u0434\u0430\u043b\u044c\u043d\u0435\u043c \u0431\u043e\u044e");

    public static L10nMap RANGED_ATTACK_STRENGTH_HEADER = new L10nMap()
            .set("en", "RAS")
            .set("ru", "\u0421\u0410\u0414");

    public static L10nMap RANGED_ATTACK_RADIUS = new L10nMap()
            .set("en", "Ranged Attack Radius")
            .set("ru", "\u0420\u0430\u0434\u0438\u0443\u0441 \u0430\u0442\u0430\u043a\u0438 \u0432 \u0434\u0430\u043b\u044c\u043d\u0435\u043c \u0431\u043e\u044e");

    public static L10nMap PASS_SCORE = new L10nMap()
            .set("en", "Pass Score")
            .set("ru", "\u041e\u0441\u0442\u0430\u0432\u0448\u0438\u0435\u0441\u044f \u0445\u043e\u0434\u044b");

    public static L10nMap PASS_SCORE_HEADER = new L10nMap()
            .set("en", "P")
            .set("ru", "\u0425");

    /** Units List */

    public static L10nMap ARCHERS_NAME = new L10nMap()
            .set("en", "Archers")
            .set("ru", "\u041b\u0443\u0447\u043d\u0438\u043a\u0438");

    public static L10nMap ARCHERS_DESCRIPTION = new L10nMap()
            .set("en", "May not melee attack. The bombardment range for archers is 2 hexes.")
            .set("ru", "\u041d\u0435 \u043c\u043e\u0436\u0435\u0442 \u0430\u0442\u0430\u043a\u043e\u0432\u0430\u0442\u044c \u0432 \u0431\u043b\u0438\u0436\u043d\u0435\u043c \u0431\u043e\u044e. \u0420\u0430\u0434\u0438\u0443\u0441 \u043e\u0431\u0441\u0442\u0440\u0435\u043b\u0430 - 2 \u044f\u0447\u0435\u0439\u043a\u0438.");

    public static L10nMap GREAT_ARTIST_NAME = new L10nMap()
            .set("en", "Great Artist")
            .set("ru", "\u0412\u0435\u043b\u0438\u043a\u0438\u0439 \u0445\u0443\u0434\u043e\u0436\u043d\u0438\u043a");

    public static L10nMap GREAT_ARTIST_DESCRIPTION = new L10nMap()
            .set("en", "Great Artist can paint a city which gives additional happiness")
            .set("ru", "\u0412\u0435\u043b\u0438\u043a\u0438\u0439 \u0445\u0443\u0434\u043e\u0436\u043d\u0438\u043a \u043c\u043e\u0436\u0435\u0442 \u0443\u043a\u0440\u0430\u0441\u0438\u0442\u044c \u0433\u043e\u0440\u043e\u0434 \u0438 \u043e\u043d \u0441\u0442\u0430\u043d\u0435\u0442 \u043f\u0440\u0438\u043d\u043e\u0441\u0438\u0442\u044c \u0431\u043e\u043b\u044c\u0448\u0435 \u0440\u0430\u0434\u043e\u0441\u0442\u0438");

    public static L10nMap GREAT_ENGINEER_NAME = new L10nMap()
            .set("en", "Great Engineer")
            .set("ru", "\u0412\u0435\u043b\u0438\u043a\u0438\u0439 \u0438\u043d\u0436\u0435\u043d\u0435\u0440");

    public static L10nMap GREAT_ENGINEER_DESCRIPTION = new L10nMap()
            .set("en", "Great Engineer can build an improvement which gives labour points")
            .set("ru", "\u0412\u0435\u043b\u0438\u043a\u0438\u0439 \u0438\u043d\u0436\u0435\u043d\u0435\u0440 \u0441\u043f\u043e\u0441\u043e\u0431\u0435\u043d \u043f\u043e\u0441\u0442\u0440\u043e\u0438\u0442\u044c \u0443\u0441\u043e\u0432\u0435\u0440\u0448\u0435\u043d\u0441\u0442\u0432\u043e\u0432\u0430\u043d\u0438\u0435, \u043a\u043e\u0442\u043e\u0440\u043e\u0435 \u0431\u0443\u0434\u0435\u0442 \u043f\u0440\u0438\u043d\u043e\u0441\u0438\u0442\u044c \u0434\u043e\u043f\u043e\u043b\u043d\u0438\u0442\u0435\u043b\u044c\u043d\u044b\u0439 \u0442\u0440\u0443\u0434");

    public static L10nMap GREAT_GENERAL_NAME = new L10nMap()
            .set("en", "Great General")
            .set("ru", "\u0412\u0435\u043b\u0438\u043a\u0438\u0439 \u0433\u0435\u043d\u0435\u0440\u0430\u043b");

    public static L10nMap GREAT_GENERAL_DESCRIPTION = new L10nMap()
            .set("en", "Great General can build a defense improvement on a tile or capture foreign tiles")
            .set("ru", "\u0412\u0435\u043b\u0438\u043a\u0438\u0439 \u0433\u0435\u043d\u0435\u0440\u0430\u043b \u043c\u043e\u0436\u0435\u0442 \u043f\u043e\u0441\u0442\u0440\u043e\u0438\u0442\u044c \u043e\u0431\u043e\u0440\u043e\u043d\u0438\u0442\u0435\u043b\u044c\u043d\u043e\u0435 \u0443\u043b\u0443\u0447\u0448\u0435\u043d\u0438\u0435 \u043d\u0430 \u043a\u043b\u0435\u0442\u043a\u0435 \u0438\u043b\u0438 \u0437\u0430\u0445\u0432\u0430\u0442\u0438\u0442\u044c \u0432\u0440\u0430\u0436\u0435\u0441\u043a\u0438\u0435 \u043a\u043b\u0435\u0442\u043a\u0438");

    public static L10nMap GREAT_MERCHANT_NAME = new L10nMap()
            .set("en", "Great Merchant")
            .set("ru", "\u0412\u0435\u043b\u0438\u043a\u0438\u0439 \u0442\u043e\u0440\u0433\u043e\u0432\u0435\u0446");

    public static L10nMap GREAT_MERCHANT_DESCRIPTION = new L10nMap()
            .set("en", "Great Merchant can improve relations with state-city")
            .set("ru", "\u0412\u0435\u043b\u0438\u043a\u0438\u0439 \u0442\u043e\u0440\u0433\u043e\u0432\u0435\u0446 \u043f\u043e\u043c\u043e\u0436\u0435\u0442 \u043d\u0430\u043b\u0430\u0434\u0438\u0442\u044c \u043e\u0442\u043d\u043e\u0448\u0435\u043d\u0438\u044f \u0441 \u0433\u043e\u0440\u043e\u0434\u043e\u043c-\u0433\u043e\u0441\u0443\u0434\u0430\u0440\u0441\u0442\u0432\u043e\u043c");

    public static L10nMap GREAT_SCIENTIST_NAME = new L10nMap()
            .set("en", "Great Scientist")
            .set("ru", "\u0412\u0435\u043b\u0438\u043a\u0438\u0439 \u0443\u0447\u0435\u043d\u044b\u0439");

    public static L10nMap GREAT_SCIENTIST_DESCRIPTION = new L10nMap()
            .set("en", "Great Scientist can open a new technology")
            .set("ru", "\u0412\u0435\u043b\u0438\u043a\u0438\u0439 \u0443\u0447\u0435\u043d\u044b\u0439 \u043c\u043e\u0436\u0435\u0442 \u043e\u0442\u043a\u0440\u044b\u0442\u044c \u043d\u043e\u0432\u0443\u044e \u0442\u0435\u0445\u043d\u043e\u043b\u043e\u0433\u0438\u044e");

    public static L10nMap SETTLERS_NAME = new L10nMap()
            .set("en", "Settlers")
            .set("ru", "\u041f\u043e\u0441\u0435\u043b\u0435\u043d\u0446\u044b");

    public static L10nMap SETTLERS_DESCRIPTION = new L10nMap()
            .set("en", "Founds new cities to expand your empire. Growth of the City is stopped while this unit is being built.")
            .set("ru", "\u0420\u0430\u0441\u0448\u0438\u0440\u044f\u0435\u0442 \u0432\u0430\u0448\u0443 \u0438\u043c\u043f\u0435\u0440\u0438\u044e, \u043e\u0441\u043d\u043e\u0432\u044b\u0432\u0430\u044f \u043d\u043e\u0432\u044b\u0435 \u0433\u043e\u0440\u043e\u0434\u0430. \u0420\u043e\u0441\u0442 \u0433\u043e\u0440\u043e\u0434\u0430 \u043e\u0441\u0442\u0430\u043d\u043e\u0432\u043b\u0435\u043d, \u043f\u043e\u043a\u0430 \u044d\u0442\u043e\u0442 \u044e\u043d\u0438\u0442 \u0441\u0442\u0440\u043e\u0438\u0442\u0441\u044f.");

    public static L10nMap WARRIORS_NAME = new L10nMap()
            .set("en", "Warriors")
            .set("ru", "\u0412\u043e\u0438\u043d\u044b");

    public static L10nMap WARRIORS_DESCRIPTION = new L10nMap()
            .set("en", "The Warrior has a fairly decent combat strength, and can be useful in the Ancient and even Classical eras.")
            .set("ru", "\u0412\u043e\u0438\u043d\u044b \u0438\u043c\u0435\u0442 \u0441\u0440\u0430\u0432\u043d\u0438\u0442\u0435\u043b\u044c\u043d\u043e \u043d\u0435\u043f\u043b\u043e\u0445\u0443\u044e \u0441\u0438\u043b\u0443 \u0438 \u043c\u043e\u0433\u0443\u0442 \u0431\u044b\u0442\u044c \u043f\u043e\u043b\u0435\u0437\u043d\u044b \u0432 \u0414\u0440\u0435\u0432\u043d\u0435\u0439 \u0438 \u0434\u0430\u0436\u0435 \u041a\u043b\u0430\u0441\u0441\u0438\u0447\u0435\u0441\u043a\u043e\u0439 \u044d\u0440\u0430\u0445.");

    public static L10nMap WORKERS_NAME = new L10nMap()
            .set("en", "Workers")
            .set("ru", "\u0420\u0430\u0431\u043e\u0447\u0438\u0435");

    public static L10nMap WORKERS_DESCRIPTION = new L10nMap()
            .set("en", "Workers can build improvements on tiles")
            .set("ru", "\u0420\u0430\u0431\u043e\u0447\u0438\u0435 \u043c\u043e\u0433\u0443\u0442 \u0441\u0442\u0440\u043e\u0438\u0442\u044c \u0443\u043b\u0443\u0447\u0448\u0435\u043d\u0438\u044f \u043d\u0430 \u043a\u043b\u0435\u0442\u043a\u0430\u0445");
}
