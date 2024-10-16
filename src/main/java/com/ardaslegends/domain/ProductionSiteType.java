package com.ardaslegends.domain;

import com.ardaslegends.configuration.converter.ProductionSiteTypeEnumConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;

/**
 * Enum representing different types of production sites.
 * This enum is used to categorize various production sites in the application.
 * It is deserialized using {@link ProductionSiteTypeEnumConverter}.
 */
@Getter
@JsonDeserialize(converter = ProductionSiteTypeEnumConverter.class)
public enum ProductionSiteType {
    FARM("Farm"),
    FISHING_LODGE("Fishing Lodge"),
    MINE("Mine"),
    QUARRY("Quarry"),
    MAN_FLESH_PIT("Manflesh Pit"),
    SLAUGHTERHOUSE("Slaughterhouse"),
    HUNTING_LODGE("Hunting Lodge"),
    ORCHARD("Orchard"),
    LUMBER_CAMP("Lumber Camp"),
    WORKSHOP("Workshop"),
    INCOME("Income"),
    PEARL_FISHER("Pearl Fisher"),
    HOUSE_OF_LORE("House of Lore"),
    DYE_HOUSE("Dye House");

    /**
     * The name of the production site type.
     */
    private final String name;

    /**
     * Constructs a new ProductionSiteType with the specified name.
     *
     * @param name the name of the production site type
     */
    ProductionSiteType(String name) {
        this.name = name;
    }

}