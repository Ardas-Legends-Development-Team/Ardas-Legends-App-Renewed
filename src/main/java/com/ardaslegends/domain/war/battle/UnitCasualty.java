package com.ardaslegends.domain.war.battle;

import com.ardaslegends.domain.Army;
import com.ardaslegends.domain.Unit;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Represents a unit casualty in a battle.
 * This class is marked as {@link Embeddable} to be used as a component of other entities.
 */
@NoArgsConstructor
@Getter
@Embeddable
public class UnitCasualty {

    /**
     * The unit that suffered casualties.
     */
    @NotNull
    @ManyToOne
    @JoinColumn(name = "unit_id", foreignKey = @ForeignKey(name = "fk_battle_unit_casualties_unit_id"))
    private Unit unit;

    /**
     * The amount of casualties.
     */
    @NotNull
    private Long amount;

    /**
     * Constructs a new UnitCasualty.
     *
     * @param unit   the unit that suffered casualties
     * @param amount the amount of casualties
     * @throws NullPointerException if the unit or amount is null
     */
    public UnitCasualty(Unit unit, Long amount) {
        Objects.requireNonNull(unit, "UnitCasualty constructor: unit was null!");
        Objects.requireNonNull(amount, "UnitCasualty constructor: amount was null!");

        this.unit = unit;
        this.amount = amount;
    }

    /**
     * Returns the army to which the unit belongs.
     *
     * @return the army to which the unit belongs
     */
    public Army getArmy() {
        return unit.getArmy();
    }

    /**
     * Returns a string representation of this UnitCasualty.
     *
     * @return a string representation of this UnitCasualty
     */
    @Override
    public String toString() {
        return "UnitCasualty{" +
                "unit=" + unit +
                ", amount=" + amount +
                '}';
    }

    /**
     * Checks if this UnitCasualty is equal to another object.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UnitCasualty that = (UnitCasualty) o;

        if (!unit.equals(that.unit)) return false;
        return amount.equals(that.amount);
    }

    /**
     * Returns the hash code of this UnitCasualty.
     *
     * @return the hash code of this UnitCasualty
     */
    @Override
    public int hashCode() {
        int result = unit.hashCode();
        result = 31 * result + amount.hashCode();
        return result;
    }
}