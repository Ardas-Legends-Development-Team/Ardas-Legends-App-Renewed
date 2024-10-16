package com.ardaslegends.domain.war.battle;

import com.ardaslegends.domain.Faction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents the result of a battle.
 * This class is marked as {@link Embeddable} to be used as a component of other entities.
 */
@Getter
@NoArgsConstructor
@Embeddable
public class BattleResult {

    /**
     * The faction that won the battle.
     */
    @ManyToOne
    @JoinColumn(name = "winner_id", foreignKey = @ForeignKey(name = "fk_battle_result_winner_id"))
    private Faction winner;

    /**
     * The unit casualties in the battle.
     */
    @ElementCollection
    @CollectionTable(name = "battle_unit_casualties",
            joinColumns = @JoinColumn(name = "battle_id", foreignKey = @ForeignKey(name = "fk_battle_unit_casualties_battle_id")))
    private Set<UnitCasualty> unitCasualties = new HashSet<>(2);

    /**
     * The roleplay character casualties in the battle.
     */
    @ElementCollection
    @CollectionTable(name = "battle_char_casualties",
            joinColumns = @JoinColumn(name = "battle_id", foreignKey = @ForeignKey(name = "fk_battle_char_casualties_battle_id")))
    private Set<RpCharCasualty> rpCharCasualties = new HashSet<>(2);

    /**
     * Constructs a new BattleResult.
     *
     * @param winner the faction that won the battle
     * @throws NullPointerException if the winner is null
     */
    public BattleResult(Faction winner) {
        Objects.requireNonNull(winner, "BattleResult constructor: winner was null!");
        this.winner = winner;
    }

    /**
     * Constructs a new BattleResult with unit and roleplay character casualties.
     *
     * @param winner           the faction that won the battle
     * @param unitCasualties   the unit casualties in the battle
     * @param rpCharCasualties the roleplay character casualties in the battle
     * @throws NullPointerException if the winner is null
     */
    public BattleResult(Faction winner, Set<UnitCasualty> unitCasualties, Set<RpCharCasualty> rpCharCasualties) {
        Objects.requireNonNull(winner, "BattleResult constructor: winner was null!");
        this.winner = winner;
        this.unitCasualties = unitCasualties;
        this.rpCharCasualties = rpCharCasualties;
    }

    /**
     * Returns an unmodifiable set of unit casualties.
     *
     * @return an unmodifiable set of unit casualties
     */
    public Set<UnitCasualty> getUnitCasualties() {
        return Collections.unmodifiableSet(unitCasualties);
    }

    /**
     * Returns an unmodifiable set of roleplay character casualties.
     *
     * @return an unmodifiable set of roleplay character casualties
     */
    public Set<RpCharCasualty> getRpCharCasualties() {
        return Collections.unmodifiableSet(rpCharCasualties);
    }

    /**
     * Returns a string representation of this BattleResult.
     *
     * @return a string representation of this BattleResult
     */
    @Override
    public String toString() {
        return "BattleResult{" +
                "winner=" + winner +
                ", unitCasualties=" + unitCasualties +
                ", rpCharCasualties=" + rpCharCasualties +
                '}';
    }

    /**
     * Checks if this BattleResult is equal to another object.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BattleResult that = (BattleResult) o;

        if (!winner.equals(that.winner)) return false;
        if (!Objects.equals(unitCasualties, that.unitCasualties))
            return false;
        return Objects.equals(rpCharCasualties, that.rpCharCasualties);
    }

    /**
     * Returns the hash code of this BattleResult.
     *
     * @return the hash code of this BattleResult
     */
    @Override
    public int hashCode() {
        int result = winner.hashCode();
        result = 31 * result + (unitCasualties != null ? unitCasualties.hashCode() : 0);
        result = 31 * result + (rpCharCasualties != null ? rpCharCasualties.hashCode() : 0);
        return result;
    }

    /**
     * Returns if a BattleResult is present or not. This is needed because
     * Hibernate always creates a BattleResult instance and initializes all the attributes
     * with null.
     *
     * @return true if the BattleResult is present, false otherwise
     */
    public boolean isPresent() {
        return winner != null;
    }
}