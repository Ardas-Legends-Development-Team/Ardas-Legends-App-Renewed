package com.ardaslegends.configuration.converter;

import com.ardaslegends.domain.SpecialBuilding;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

/**
 * Converter class to convert a String to a SpecialBuilding enum.
 * This class implements both Spring's Converter interface and Jackson's Converter interface.
 */
@Slf4j
public class SpecialBuildingEnumConverter implements Converter<String, SpecialBuilding>,
        com.fasterxml.jackson.databind.util.Converter<String, SpecialBuilding> {

    /**
     * Converts a given String to a SpecialBuilding enum.
     * The input string is converted to uppercase and spaces are replaced with underscores
     * before being mapped to the corresponding enum value.
     *
     * @param source the String to convert
     * @return the corresponding SpecialBuilding enum
     * @throws IllegalArgumentException if the input string does not match any enum value
     */
    @Override
    public SpecialBuilding convert(@NonNull String source) {
        log.debug("Converting '{}' into SpecialBuilding", source);
        var specialBuilding = SpecialBuilding.valueOf(source.replace(' ', '_').toUpperCase());
        log.debug("Converted '{}' into SpecialBuilding {}", source, specialBuilding);
        return specialBuilding;
    }

    /**
     * Returns the input type for the converter.
     * This method is used by Jackson to determine the input type of the converter.
     *
     * @param typeFactory the TypeFactory to use
     * @return the JavaType representing the input type, which is String.class
     */
    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return typeFactory.constructType(String.class);
    }

    /**
     * Returns the output type for the converter.
     * This method is used by Jackson to determine the output type of the converter.
     *
     * @param typeFactory the TypeFactory to use
     * @return the JavaType representing the output type, which is SpecialBuilding.class
     */
    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return typeFactory.constructType(SpecialBuilding.class);
    }
}
