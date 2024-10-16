package com.ardaslegends.configuration.converter;

import com.ardaslegends.domain.ResourceType;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

/**
 * Converter class to convert a String to a ResourceType enum.
 * This class implements Spring's Converter interface.
 */
@Slf4j
public class ResourceTypeEnumConverter implements Converter<String, ResourceType> {

    /**
     * Converts a given String to a ResourceType enum.
     * The input string is converted to uppercase before being mapped to the corresponding enum value.
     *
     * @param source the String to convert
     * @return the corresponding ResourceType enum
     * @throws IllegalArgumentException if the input string does not match any enum value
     */
    @Override
    public ResourceType convert(@NonNull String source) {
        log.debug("Converting '{}' into ResourceType...", source);
        var resourceType = ResourceType.valueOf(source.toUpperCase());
        log.debug("Converted '{}' into ResourceType {}", source, resourceType);
        return resourceType;
    }
}
