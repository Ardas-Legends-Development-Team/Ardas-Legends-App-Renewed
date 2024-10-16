package com.ardaslegends.domain.war;

import com.ardaslegends.domain.Faction;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * Represents a participant in a war.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class WarParticipant {

    /**
     * The faction participating in the war.
     */
    @ManyToOne
    @JoinColumn(name = "participant_faction_id")
    @NotNull
    private Faction warParticipant;

    /**
     * Indicates whether this faction is the initial party in the war.
     */
    @NotNull
    private Boolean initialParty;

    /**
     * The date when the faction joined the war.
     */
    @NotNull
    private OffsetDateTime joiningDate;

    /**
     * Checks if this WarParticipant is equal to another object.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WarParticipant that = (WarParticipant) o;
        return Objects.equals(warParticipant, that.warParticipant);
    }

    /**
     * Returns the hash code of this WarParticipant.
     *
     * @return the hash code of this WarParticipant
     */
    @Override
    public int hashCode() {
        return Objects.hash(warParticipant);
    }

    /**
     * Returns a string representation of this WarParticipant.
     *
     * @return a string representation of this WarParticipant
     */
    @Override
    public String toString() {
        return "WarParticipant{" +
                "warParticipant=" + warParticipant +
                ", initialParty=" + initialParty +
                ", joiningDate=" + joiningDate +
                '}';
    }

    /**
     * Returns the name of the war participant faction.
     * Added so you don't have to do {@code .getWarParticipant().getName()}.
     *
     * @return the name of the faction
     */
    public String getName() {
        return warParticipant.getName();
    }
}