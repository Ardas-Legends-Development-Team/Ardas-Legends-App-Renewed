package com.ardaslegends.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a type of unit in the application.
 * This class is marked as {@link Entity} and is mapped to the "unit_types" table in the database.
 * It uses {@link JsonIdentityInfo} for JSON serialization.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "unit_types")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "unitName")
public final class UnitType extends AbstractDomainObject {

    /**
     * The unique name of the unit.
     */
    @Id
    private String unitName;

    /**
     * The token cost of the unit.
     */
    @NotNull(message = "UnitType: tokenCost must not be null")
    private Double tokenCost;

    /**
     * Indicates whether the unit is mounted.
     */
    @NotNull
    @Builder.Default
    private Boolean isMounted = false;

    /**
     * The set of factions that can use this unit.
     */
    @ManyToMany
    @JoinTable(name = "factions_units",
            joinColumns = {@JoinColumn(name = "unit_name", foreignKey = @ForeignKey(name = "fk_factions_units_unit_name"))},
            inverseJoinColumns = {@JoinColumn(name = "faction_id", foreignKey = @ForeignKey(name = "fk_factions_units_faction_id"))})
    @Builder.Default
    private Set<Faction> usableBy = new HashSet<>(2);

    /**
     * Constructs a new UnitType with the specified attributes.
     *
     * @param unitName  the name of the unit
     * @param tokenCost the token cost of the unit
     * @param isMounted indicates whether the unit is mounted
     */
    public UnitType(String unitName, Double tokenCost, Boolean isMounted) {
        this.unitName = unitName;
        this.tokenCost = tokenCost;
        this.isMounted = isMounted;
        this.usableBy = new HashSet<>(2);
    }

    /**
     * Checks if this unit type is equal to another object.
     *
     * @param o the object to compare with
     * @return true if this unit type is equal to the specified object, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UnitType unitType = (UnitType) o;

        if (!unitName.equals(unitType.unitName)) return false;
        if (!tokenCost.equals(unitType.tokenCost)) return false;
        if (!isMounted.equals(unitType.isMounted)) return false;
        return Objects.equals(usableBy, unitType.usableBy);
    }

    /**
     * Returns the hash code of this unit type.
     *
     * @return the hash code of this unit type
     */
    @Override
    public int hashCode() {
        int result = unitName.hashCode();
        result = 31 * result + tokenCost.hashCode();
        result = 31 * result + isMounted.hashCode();
        result = 31 * result + (usableBy != null ? usableBy.hashCode() : 0);
        return result;
    }
}