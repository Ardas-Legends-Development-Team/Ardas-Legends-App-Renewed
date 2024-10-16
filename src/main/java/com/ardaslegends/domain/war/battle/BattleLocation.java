package com.ardaslegends.domain.war.battle;

import com.ardaslegends.domain.Region;
import com.ardaslegends.domain.claimbuilds.ClaimBuild;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

import java.util.Objects;

/**
 * Represents the location of a battle.
 * This class is marked as {@link Embeddable} to be used as a component of other entities.
 */
@Getter
@Embeddable
public class BattleLocation {

    /**
     * The region where the battle takes place.
     */
    @ManyToOne
    @JoinColumn(name = "region_id", foreignKey = @ForeignKey(name = "fk_battle_location_region_id"))
    private Region region;

    /**
     * Indicates whether the battle is a field battle.
     */
    private Boolean fieldBattle;

    /**
     * The claim build associated with the battle.
     */
    @ManyToOne
    @JoinColumn(name = "claimbuild_id", foreignKey = @ForeignKey(name = "fk_battle_location_claimbuild_id"))
    private ClaimBuild claimBuild;

    /**
     * Constructs a new BattleLocation.
     *
     * @param region      the region where the battle takes place
     * @param fieldBattle indicates whether the battle is a field battle
     * @param claimBuild  the claim build associated with the battle
     */
    public BattleLocation(Region region, boolean fieldBattle, ClaimBuild claimBuild) {
        this.region = region;
        this.fieldBattle = fieldBattle;
        this.claimBuild = claimBuild;
    }

    /**
     * Default constructor for BattleLocation.
     */
    public BattleLocation() {
    }

    /**
     * Checks if this BattleLocation is equal to another object.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BattleLocation that = (BattleLocation) o;

        if (!region.equals(that.region)) return false;
        if (!fieldBattle.equals(that.fieldBattle)) return false;
        return Objects.equals(claimBuild, that.claimBuild);
    }

    /**
     * Returns the hash code of this BattleLocation.
     *
     * @return the hash code of this BattleLocation
     */
    @Override
    public int hashCode() {
        int result = region.hashCode();
        result = 31 * result + fieldBattle.hashCode();
        result = 31 * result + (claimBuild != null ? claimBuild.hashCode() : 0);
        return result;
    }

    /**
     * Returns a string representation of this BattleLocation.
     *
     * @return a string representation of this BattleLocation
     */
    @Override
    public String toString() {
        return "BattleLocation{" +
                "region=" + region +
                ", fieldBattle=" + fieldBattle +
                ", claimBuild=" + claimBuild +
                '}';
    }
}