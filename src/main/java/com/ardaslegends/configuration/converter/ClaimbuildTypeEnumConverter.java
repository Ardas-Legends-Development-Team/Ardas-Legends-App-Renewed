package com.ardaslegends.configuration.converter;

import com.ardaslegends.domain.claimbuilds.ClaimBuildType;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

/**
 * Converter class to convert a String to a ClaimBuildType enum.
 * This class implements both Spring's Converter interface and Jackson's Converter interface.
 */
@Slf4j
public class ClaimbuildTypeEnumConverter implements Converter<String, ClaimBuildType>,
        com.fasterxml.jackson.databind.util.Converter<String, ClaimBuildType> {

    /**
     * Converts a given String to a ClaimBuildType enum.
     * The input string is converted to uppercase before being mapped to the corresponding enum value.
     *
     * @param source the String to convert
     * @return the corresponding ClaimBuildType enum
     * @throws IllegalArgumentException if the input string does not match any enum value
     */
    @Override
    public ClaimBuildType convert(@NonNull String source) {
        log.debug("Converting '{}' into ClaimBuildType...", source);
        var cbType = ClaimBuildType.valueOf(source.toUpperCase());
        log.debug("Converted '{}' into ClaimBuiltType {}", source, cbType);
        return cbType;
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
     * @return the JavaType representing the output type, which is ClaimBuildType.class
     */
    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return typeFactory.constructType(ClaimBuildType.class);
    }
}
