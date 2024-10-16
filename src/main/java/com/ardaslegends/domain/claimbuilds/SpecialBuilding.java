package com.ardaslegends.domain.claimbuilds;

import com.ardaslegends.configuration.converter.SpecialBuildingEnumConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;

/**
 * Enum representing different types of special buildings.
 * This enum is used to categorize various special buildings in the application.
 * It is deserialized using {@link SpecialBuildingEnumConverter}.
 */
@Getter
@JsonDeserialize(converter = SpecialBuildingEnumConverter.class)
public enum SpecialBuilding {
    WATCHTOWER("Watchtower"),
    HOUSE_OF_HEALING("House of Healing"),
    EMBASSY("Embassy"),
    HARBOUR("Harbour"),
    STABLES("Stables"),
    BANK("Bank"),
    INN("Inn"),
    MARKET("Market"),
    SHOP("Shop"),
    WALL("Wall");

    private final String name;

    SpecialBuilding(String name) {
        this.name = name;
    }

}
