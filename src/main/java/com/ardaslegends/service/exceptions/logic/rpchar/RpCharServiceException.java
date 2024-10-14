package com.ardaslegends.service.exceptions.logic.rpchar;

import com.ardaslegends.service.exceptions.logic.LogicException;

import java.util.Arrays;

public class RpCharServiceException extends LogicException {
    private final static String NO_RPCHAR_FOUND = "No RP Char found with name '%s'";
    private final static String NO_ACTIVE_RPCHAR = "Player '%s' has no active RpChar!";
    private final static String NO_RPCHARS_FOUND = "No RP Chars found with names '%s'";
    private final static String CHARACTER_ALREADY_STATIONED = "Character '%s' is already stationed at '%s'";
    private final static String CLAIMBUILD_NOT_IN_THE_SAME_OR_ALLIED_FACTION = "Character '%s' is not in the same or allied faction as claimbuild '%s'";

    protected RpCharServiceException(String message) {
        super(message);
    }

    public static RpCharServiceException noRpCharFound(String name) {
        return new RpCharServiceException(NO_RPCHAR_FOUND.formatted(name));
    }

    public static RpCharServiceException noRpCharsFound(String[] names) {
        return new RpCharServiceException(NO_RPCHARS_FOUND.formatted(Arrays.toString(names)));
    }

    public static RpCharServiceException noActiveRpChar(String ign) {
        return new RpCharServiceException(NO_ACTIVE_RPCHAR.formatted(ign));
    }

    public static RpCharServiceException characterAlreadyStationed(String characterName, String claimBuildName) {
        return new RpCharServiceException(CHARACTER_ALREADY_STATIONED.formatted(characterName, claimBuildName));
    }

    public static RpCharServiceException claimbuildNotInTheSameOrAlliedFaction(String characterName, String claimBuildName) {
        return new RpCharServiceException(CLAIMBUILD_NOT_IN_THE_SAME_OR_ALLIED_FACTION.formatted(characterName, claimBuildName));
    }
}
