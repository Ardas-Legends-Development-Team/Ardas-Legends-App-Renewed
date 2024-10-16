package com.ardaslegends.domain;

import lombok.Getter;

/**
 * Enum representing different types of armies.
 */
@Getter
public enum ArmyType {
    /**
     * Represents a standard army.
     */
    ARMY("Army"),

    /**
     * Represents a trading company.
     */
    TRADING_COMPANY("Trading Company"),

    /**
     * Represents an armed trading company.
     */
    ARMED_TRADERS("Armed Trading Company");

    /**
     * The name of the army type.
     */
    private final String name;

    /**
     * Constructs an ArmyType with the specified name.
     *
     * @param name the name of the army type
     */
    ArmyType(String name) {
        this.name = name;
    }

}