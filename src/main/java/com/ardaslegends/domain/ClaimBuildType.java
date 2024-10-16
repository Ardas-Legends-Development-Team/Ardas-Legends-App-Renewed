package com.ardaslegends.domain;

import com.ardaslegends.configuration.converter.ClaimbuildTypeEnumConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;

/**
 * Enum representing different types of claim builds.
 * This enum is used to define the characteristics of various claim build types.
 *
 * <p>Each enum constant (e.g., {@code HAMLET}, {@code VILLAGE}) is defined with specific values for its attributes:
 * <ul>
 *   <li>{@code maxArmies}: Maximum number of armies allowed.</li>
 *   <li>{@code freeArmies}: Number of free armies provided.</li>
 *   <li>{@code maxTradingCompanies}: Maximum number of trading companies allowed.</li>
 *   <li>{@code freeTradingCompanies}: Number of free trading companies provided.</li>
 *   <li>{@code name}: Name of the claim build type.</li>
 * </ul>
 *
 * <p>To build a value from a given enum value, you simply refer to the enum constant and use its attributes or methods.
 * For example, to get the name of the {@code TOWN} claim build type, you would use {@code ClaimBuildType.TOWN.getName()}.
 */
@Getter
@JsonDeserialize(converter = ClaimbuildTypeEnumConverter.class)
public enum ClaimBuildType {

    HAMLET(0, 0, 0, 0, "Hamlet"),

    VILLAGE(0, 0, 0, 0, "Village"),

    TOWN(1, 1, 1, 1, "Town"),

    CAPITAL(2, 1, 2, 1, "Capital"),

    KEEP(0, 0, 0, 0, "Keep"),

    CASTLE(1, 0, 1, 0, "Castle"),

    STRONGHOLD(1, 0, 1, 0, "Stronghold");

    private final int maxArmies;
    private final int freeArmies;
    private final int maxTradingCompanies;
    private final int freeTradingCompanies;
    private final String name;

    /**
     * Constructs a ClaimBuildType with the specified characteristics.
     *
     * @param maxArmies            the maximum number of armies allowed
     * @param maxTradingCompanies  the maximum number of trading companies allowed
     * @param freeArmies           the number of free armies provided
     * @param freeTradingCompanies the number of free trading companies provided
     * @param name                 the name of the claim build type
     */
    ClaimBuildType(int maxArmies, int maxTradingCompanies, int freeArmies, int freeTradingCompanies, String name) {
        this.maxArmies = maxArmies;
        this.maxTradingCompanies = maxTradingCompanies;
        this.freeArmies = freeArmies;
        this.freeTradingCompanies = freeTradingCompanies;
        this.name = name;
    }
}