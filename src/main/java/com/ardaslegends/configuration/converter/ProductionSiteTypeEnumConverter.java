package com.ardaslegends.configuration.converter;

import com.ardaslegends.domain.claimbuilds.ProductionSiteType;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

/**
 * Converter class to convert a String to a ProductionSiteType enum.
 * This class implements both Spring's Converter interface and Jackson's Converter interface.
 */
@Slf4j
public class ProductionSiteTypeEnumConverter implements Converter<String, ProductionSiteType>,
        com.fasterxml.jackson.databind.util.Converter<String, ProductionSiteType> {

    /**
     * Converts a given String to a ProductionSiteType enum.
     * The input string is modified to replace spaces with underscores and converted to uppercase
     * before being mapped to the corresponding enum value.
     *
     * @param source the String to convert
     * @return the corresponding ProductionSiteType enum
     * @throws IllegalArgumentException if the input string does not match any enum value
     */
    @Override
    public ProductionSiteType convert(@NonNull String source) {
        log.debug("Converting '{}' into ProductionSiteType", source);
        var prodType = ProductionSiteType.valueOf(source.replace(' ', '_').toUpperCase());
        log.debug("Converted '{}' into ProductionSiteType {}", source, prodType);
        return prodType;
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
     * @return the JavaType representing the output type, which is ProductionSiteType.class
     */
    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return typeFactory.constructType(ProductionSiteType.class);
    }
}
