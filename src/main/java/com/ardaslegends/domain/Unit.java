package com.ardaslegends.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

/**
 * Represents a unit in the application.
 * This class is marked as {@link Entity} and is mapped to the "units" table in the database.
 * It uses {@link JsonIdentityInfo} for JSON serialization.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "units")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "unitType")
public final class Unit extends AbstractDomainObject {

    /**
     * The unique identifier of the unit.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The type of the unit.
     * This is a many-to-one relationship with {@link UnitType}.
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "unit_type", foreignKey = @ForeignKey(name = "fk_unit_unit_type"))
    private UnitType unitType;

    /**
     * The army to which the unit belongs.
     * This is a many-to-one relationship with {@link Army}.
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "army", foreignKey = @ForeignKey(name = "fk_unit_army"))
    private Army army;

    /**
     * The maximum number of units in the army.
     */
    private Integer count;

    /**
     * The current number of alive units.
     */
    private Integer amountAlive;

    /**
     * Returns the cost of the unit.
     * This method is ignored in JSON serialization.
     *
     * @return the cost of the unit
     */
    @JsonIgnore
    public Double getCost() {
        return unitType.getTokenCost();
    }

    /**
     * Checks if the unit is mounted.
     *
     * @return true if the unit is mounted, false otherwise
     */
    public boolean isMounted() {
        return unitType.getIsMounted();
    }
}