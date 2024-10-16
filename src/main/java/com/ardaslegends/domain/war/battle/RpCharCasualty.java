package com.ardaslegends.domain.war.battle;

import com.ardaslegends.domain.Player;
import com.ardaslegends.domain.RPChar;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Represents a roleplay character casualty in a battle.
 * This class is marked as {@link Embeddable} to be used as a component of other entities.
 */
@Getter
@Embeddable
@NoArgsConstructor
public class RpCharCasualty {

    /**
     * The roleplay character that was slain.
     */
    @NotNull
    @ManyToOne
    @JoinColumn(name = "rpchar_id", foreignKey = @ForeignKey(name = "fk_battle_char_casualties_char_id"))
    private RPChar rpChar;

    /**
     * The player who slain the roleplay character.
     */
    @ManyToOne
    @JoinColumn(name = "slain_by_id", foreignKey = @ForeignKey(name = "fk_battle_char_casualties_slain_by_id"))
    private Player slainByChar;

    /**
     * The weapon used to slay the roleplay character.
     */
    private String slainByWeapon;

    /**
     * An optional cause of the casualty.
     */
    @Setter
    private String optionalCause;

    /**
     * Constructs a new RpCharCasualty.
     *
     * @param rpChar the roleplay character that was slain
     * @throws NullPointerException if the rpChar is null
     */
    public RpCharCasualty(RPChar rpChar) {
        Objects.requireNonNull(rpChar, "RpCharCasualty constructor: rpChar was null!");
        this.rpChar = rpChar;
    }

    /**
     * Constructs a new RpCharCasualty with details of the slayer and weapon.
     *
     * @param rpChar        the roleplay character that was slain
     * @param slainByChar   the player who slain the roleplay character
     * @param slainByWeapon the weapon used to slay the roleplay character
     * @throws NullPointerException if the rpChar is null
     */
    public RpCharCasualty(RPChar rpChar, Player slainByChar, String slainByWeapon) {
        Objects.requireNonNull(rpChar, "RpCharCasualty constructor: rpChar was null!");
        this.rpChar = rpChar;
        this.slainByChar = slainByChar;
        this.slainByWeapon = slainByWeapon;
    }

    /**
     * Constructs a new RpCharCasualty with an optional cause.
     *
     * @param rpChar        the roleplay character that was slain
     * @param optionalCause an optional cause of the casualty
     * @throws NullPointerException if the rpChar is null
     */
    public RpCharCasualty(RPChar rpChar, String optionalCause) {
        Objects.requireNonNull(rpChar, "RpCharCasualty constructor: rpChar was null!");
        this.rpChar = rpChar;
        this.optionalCause = optionalCause;
    }

    /**
     * Returns a string representation of this RpCharCasualty.
     *
     * @return a string representation of this RpCharCasualty
     */
    @Override
    public String toString() {
        return "RpCharCasualty{" +
                "rpChar=" + rpChar +
                ", slainByChar=" + slainByChar +
                ", slainByWeapon='" + slainByWeapon + '\'' +
                ", optionalCause='" + optionalCause + '\'' +
                '}';
    }

    /**
     * Checks if this RpCharCasualty is equal to another object.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RpCharCasualty that = (RpCharCasualty) o;

        if (!rpChar.equals(that.rpChar)) return false;
        if (!Objects.equals(slainByChar, that.slainByChar)) return false;
        if (!Objects.equals(slainByWeapon, that.slainByWeapon)) return false;
        return Objects.equals(optionalCause, that.optionalCause);
    }

    /**
     * Returns the hash code of this RpCharCasualty.
     *
     * @return the hash code of this RpCharCasualty
     */
    @Override
    public int hashCode() {
        int result = rpChar.hashCode();
        result = 31 * result + (slainByChar != null ? slainByChar.hashCode() : 0);
        result = 31 * result + (slainByWeapon != null ? slainByWeapon.hashCode() : 0);
        result = 31 * result + (optionalCause != null ? optionalCause.hashCode() : 0);
        return result;
    }
}