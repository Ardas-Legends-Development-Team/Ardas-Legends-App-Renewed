package com.ardaslegends.domain;

import com.ardaslegends.configuration.converter.RegionTypeEnumConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;

/**
 * Enum representing different types of regions.
 * This enum is used to categorize various regions in the application.
 * It is deserialized using {@link RegionTypeEnumConverter}.
 */
@JsonDeserialize(converter = RegionTypeEnumConverter.class)
public enum RegionType {

    // The number next to the region type is the cost in days to move into the region.

    SEA("Sea", 1),

    LAND("Land", 1),

    HILL("Hill", 2),

    ICE("Ice", 2),

    DESERT("Desert", 3),

    FOREST("Forest", 3),

    SWAMP("Swamp", 4),

    JUNGLE("Jungle", 5),

    MOUNTAIN("Mountain", 6);

    /**
     * The cost in days to move into the region.
     */
    private final int cost;

    /**
     * The name of the region type.
     */
    @Getter
    private final String name;

    /**
     * Constructs a new RegionType with the specified name and cost.
     *
     * @param name the name of the region type
     * @param cost the cost in days to move into the region
     */
    RegionType(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    /**
     * Returns the cost of the region in hours.
     *
     * @return the cost of the region in hours
     */
    public int getCost() {
        return cost * 24;
    }
}