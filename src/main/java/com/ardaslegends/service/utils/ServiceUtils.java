package com.ardaslegends.service.utils;

import com.ardaslegends.domain.Army;
import com.ardaslegends.domain.PathElement;
import com.ardaslegends.domain.Player;
import com.ardaslegends.domain.Region;
import com.ardaslegends.service.exceptions.ServiceException;
import com.ardaslegends.service.exceptions.logic.player.PlayerServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.lang.reflect.Field;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Utility class for various service-related operations.
 * <p>
 * This class provides utility methods for checking blanks and nulls in objects, validating string syntax,
 * and performing operations related to paths and durations.
 * </p>
 */
@Slf4j
public class ServiceUtils {

    /**
     * Checks if the specified fields in the given object are blank.
     * <p>
     * This method checks if the specified fields in the given object are blank and throws an exception if any field is blank.
     * </p>
     *
     * @param <T>        The type of the object.
     * @param obj        The object to check.
     * @param fieldNames The names of the fields to check.
     * @throws IllegalArgumentException if any field is blank.
     */
    public static <T> void checkBlanks(T obj, List<String> fieldNames) {
        List<Field> fields = getFieldsFromNames(obj, fieldNames);
        for (var field : fields) {
            field.setAccessible(true);
            if (field.getType().equals(String.class)) {
                try {
                    String strField = (String) field.get(obj);
                    checkBlankString(strField, field.getName());
                } catch (IllegalAccessException e) {
                    log.warn("Illegal access for object {} and field {}", obj, field);
                }
            }
        }
    }

    /**
     * Checks if all string fields in the given object are blank.
     * <p>
     * This method checks if all string fields in the given object are blank and throws an exception if any field is blank.
     * </p>
     *
     * @param <T> The type of the object.
     * @param obj The object to check.
     * @throws IllegalArgumentException if any field is blank.
     */
    public static <T> void checkAllBlanks(T obj) {
        List<String> stringFields = Arrays.stream(obj.getClass().getDeclaredFields())
                .filter(field -> field.getType().equals(String.class))
                .map(Field::getName)
                .collect(Collectors.toList());
        checkBlanks(obj, stringFields);
    }

    /**
     * Checks if the specified fields in the given object are null.
     * <p>
     * This method checks if the specified fields in the given object are null and throws an exception if any field is null.
     * </p>
     *
     * @param <T>        The type of the object.
     * @param obj        The object to check.
     * @param fieldNames The names of the fields to check.
     * @throws NullPointerException if any field is null.
     */
    public static <T> void checkNulls(T obj, List<String> fieldNames) {
        List<Field> fields = getFieldsFromNames(obj, fieldNames);
        for (var field : fields) {
            field.setAccessible(true);
            try {
                if (field.get(obj) == null) {
                    log.warn("{} must not be null!", field.getName());
                    throw new NullPointerException("%s must not be null!".formatted(field.getName()));
                }
            } catch (IllegalAccessException e) {
                log.warn("Illegal access for object {} and field {}", obj, field);
            }
        }
    }

    /**
     * Checks if all fields in the given object are null.
     * <p>
     * This method checks if all fields in the given object are null and throws an exception if any field is null.
     * </p>
     *
     * @param <T> The type of the object.
     * @param obj The object to check.
     * @throws NullPointerException if any field is null.
     */
    public static <T> void checkAllNulls(T obj) {
        checkNulls(obj, Arrays.stream(obj.getClass().getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.toList()));
    }

    /**
     * Checks if the player has permission to perform an action on the army.
     * <p>
     * This method checks if the player is bound to the army, is the faction leader, or has lordship permissions.
     * </p>
     *
     * @param player The player to check.
     * @param army   The army to check.
     * @return {@code true} if the player has permission, {@code false} otherwise.
     */
    public static boolean boundLordLeaderPermission(Player player, Army army) {
        log.debug("Checking if bound - lord - leader permission is fulfilled for Army [{}, Faction: {}], Player [{}, Faction:{}]", army.getName(), army.getFaction(), player.getIgn(), player.getFaction());

        if (player.getActiveCharacter().orElseThrow(PlayerServiceException::noRpChar).equals(army.getBoundTo())) {
            log.debug("Player [{}] is bound to army, allowed action", player.getIgn());
            return true;
        }

        // TODO: Implement Lordship Permissions

        if (player.equals(army.getFaction().getLeader())) {
            log.debug("Player [{}] is faction leader of army, allowed action", player.getIgn());
            return true;
        }

        log.debug("Player [{}] is not allowed to perform action as per bound - lord - leader permission set!", player.getIgn());
        return false;
    }

    /**
     * Checks if the given string is blank.
     * <p>
     * This method checks if the given string is blank and throws an exception if it is blank.
     * </p>
     *
     * @param value     The string to check.
     * @param fieldName The name of the field.
     * @throws IllegalArgumentException if the string is blank.
     */
    public static void checkBlankString(String value, String fieldName) {
        if (value.isBlank()) {
            log.warn("{} must not be blank!", fieldName);
            throw new IllegalArgumentException("%s must not be blank!".formatted(fieldName));
        }
    }

    /**
     * Retrieves the fields from the given object based on the specified field names.
     * <p>
     * This method retrieves the fields from the given object based on the specified field names.
     * </p>
     *
     * @param <T>        The type of the object.
     * @param obj        The object to retrieve fields from.
     * @param fieldNames The names of the fields to retrieve.
     * @return A list of fields.
     * @throws ServiceException if a field does not exist.
     */
    private static <T> List<Field> getFieldsFromNames(T obj, List<String> fieldNames) {
        List<Field> fields = new ArrayList<>();
        for (String fieldName : fieldNames) {
            try {
                fields.add(obj.getClass().getDeclaredField(fieldName));
            } catch (NoSuchFieldException e) {
                log.warn("No such field '{}'!", fieldName);
                throw ServiceException.noSuchField(fieldName, e);
            }
        }
        return fields;
    }

    /**
     * Calculates the food cost for the given path.
     * <p>
     * This method calculates the food cost for the given path based on the total path cost.
     * </p>
     *
     * @param path The path to calculate the food cost for.
     * @return The food cost.
     */
    public static Integer getFoodCost(List<PathElement> path) {
        return (int) Math.ceil(ServiceUtils.getTotalPathCost(path) / 24.0);
    }

    /**
     * Formats the given duration as a human-readable string.
     * <p>
     * This method formats the given duration as a human-readable string.
     * </p>
     *
     * @param duration The duration to format.
     * @return The formatted duration string.
     * @throws NullPointerException if the duration is null.
     */
    public static String formatDuration(Duration duration) {
        Objects.requireNonNull(duration, "Duration must not be null in formatDuration");
        return DurationFormatUtils.formatDurationWords(duration.toMillis(), true, true);
    }

    /**
     * Validates the syntax of the given string.
     * <p>
     * This method validates the syntax of the given string based on the specified syntax characters and throws an exception if the syntax is invalid.
     * </p>
     *
     * @param string           The string to validate.
     * @param syntaxChars      The syntax characters to validate against.
     * @param exceptionToThrow The exception to throw if the syntax is invalid.
     * @throws ServiceException if the syntax is invalid.
     */
    public static void validateStringSyntax(String string, Character[] syntaxChars, ServiceException exceptionToThrow) {
        log.debug("Validating unitString [{}]", string);

        // Is also true at the start, from then every time a expectedChar is switched
        int currentExpectedCharIndex = 0; //index of the currently expected char
        char expectedChar = syntaxChars[0]; //Set the expectedChar to first in array

        boolean possibleEnd =
                expectedChar == syntaxChars[syntaxChars.length - 1];

        log.trace("Starting validation, unitString length: [{}]", string.length());

        for (int i = 0; i < string.length(); i++) {
            log.trace("Index: [{}]", i);
            log.trace("Expected next syntax char [{}]", expectedChar);
            char currentChar = string.charAt(i);
            log.trace("Current char: [{}]", currentChar);

            if (currentChar == expectedChar) {
                log.trace("Current char {} is expected char {}", currentChar, expectedChar);
                possibleEnd = currentExpectedCharIndex == (syntaxChars.length - 2);
                if (syntaxChars.length == 1)
                    possibleEnd = true;
                currentExpectedCharIndex++;
                if (currentExpectedCharIndex == syntaxChars.length)
                    currentExpectedCharIndex = 0;
                log.trace("Incremented the currentExpectedCharIndex to {}", currentExpectedCharIndex);
                expectedChar = syntaxChars[currentExpectedCharIndex];
            } else if (Arrays.asList(syntaxChars).contains(currentChar)) {
                log.warn("Char [{}] at [{}] has created an error in string [{}], next expected was [{}]", currentChar, i, string, expectedChar);
                throw exceptionToThrow;
            }

            if ((i + 1) == string.length() && !possibleEnd) {
                log.warn("String reached its end without having finished syntax!");
                throw exceptionToThrow;
            }
        }
    }

    /**
     * Calculates the total path cost for the given path.
     * <p>
     * This method calculates the total path cost for the given path by summing the actual costs of the path elements.
     * </p>
     *
     * @param path The path to calculate the total cost for.
     * @return The total path cost.
     */
    public static int getTotalPathCost(List<PathElement> path) {
        return path.stream().map(PathElement::getActualCost).reduce(0, Integer::sum);
    }

    /**
     * Builds a string representation of the given path.
     * <p>
     * This method builds a string representation of the given path by joining the region IDs of the path elements.
     * </p>
     *
     * @param path The path to build the string representation for.
     * @return The string representation of the path.
     */
    public static String buildPathString(List<PathElement> path) {
        return path.stream().map(PathElement::getRegion).map(Region::getId).collect(Collectors.joining(" -> "));
    }

    /**
     * Builds a string representation of the given path with the current region highlighted.
     * <p>
     * This method builds a string representation of the given path by joining the region IDs of the path elements and highlighting the current region.
     * </p>
     *
     * @param path    The path to build the string representation for.
     * @param current The current region.
     * @return The string representation of the path with the current region highlighted.
     */
    public static String buildPathStringWithCurrentRegion(List<PathElement> path, Region current) {
        return buildPathString(path).replace(current.getId(), current.getId() + " (current)");
    }
}