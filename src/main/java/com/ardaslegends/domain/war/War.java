package com.ardaslegends.domain.war;

import com.ardaslegends.domain.AbstractDomainObject;
import com.ardaslegends.domain.Faction;
import com.ardaslegends.domain.war.battle.Battle;
import com.ardaslegends.service.exceptions.logic.war.WarServiceException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a war between factions.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Entity
@Table(name = "wars")
public class War extends AbstractDomainObject {

    /**
     * The unique identifier of the war.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the war.
     */
    @NotNull
    private String name;

    /**
     * The factions that are aggressors in the war.
     */
    @ElementCollection
    @CollectionTable(name = "war_aggressors",
            joinColumns = @JoinColumn(name = "war_id", foreignKey = @ForeignKey(name = "fk_war_aggressors_war_id")))
    @Builder.Default
    private Set<WarParticipant> aggressors = new HashSet<>(2);

    /**
     * The factions that are defenders in the war.
     */
    @ElementCollection
    @CollectionTable(name = "war_defenders",
            joinColumns = @JoinColumn(name = "war_id", foreignKey = @ForeignKey(name = "fk_war_defenders_war_id")))
    @Builder.Default
    private Set<WarParticipant> defenders = new HashSet<>(2);

    /**
     * The start date of the war.
     */
    @NotNull
    private OffsetDateTime startDate;

    /**
     * The end date of the war.
     */
    @Setter(AccessLevel.PRIVATE)
    private OffsetDateTime endDate;

    /**
     * Indicates whether the war is active.
     */
    @Setter(AccessLevel.PRIVATE)
    @NotNull
    private Boolean isActive;

    /**
     * The battles that are part of the war.
     */
    @ManyToMany(mappedBy = "wars", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Builder.Default
    private Set<Battle> battles = new HashSet<>(4);

    /**
     * Constructs a new War.
     *
     * @param name      the name of the war
     * @param aggressor the faction that is the aggressor
     * @param defender  the faction that is the defender
     * @throws NullPointerException if the name, aggressor, or defender is null
     */
    public War(String name, Faction aggressor, Faction defender) {
        Objects.requireNonNull(name, "WarConstructor: Name must not be null");
        Objects.requireNonNull(aggressor, "WarConstructor: Aggressor must not be null");
        Objects.requireNonNull(defender, "WarConstructor: Defender must not be null");

        var warDeclarationDate = OffsetDateTime.now();

        log.trace("Creating Aggressor WarParticipantObject");
        WarParticipant aggressorWarParticipant = new WarParticipant(aggressor, true, warDeclarationDate);

        log.trace("Creating Defender WarParticipantObject");
        WarParticipant defenderWarParticipant = new WarParticipant(defender, true, warDeclarationDate);

        this.name = name;
        this.aggressors = new HashSet<>(2);
        this.defenders = new HashSet<>(2);
        this.aggressors.add(aggressorWarParticipant);
        this.defenders.add(defenderWarParticipant);

        this.startDate = warDeclarationDate;
        this.isActive = true;
    }

    /**
     * Returns the enemies of a given faction.
     *
     * @param faction the faction whose enemies are to be returned
     * @return a set of enemies of the given faction
     */
    @NotNull
    public Set<WarParticipant> getEnemies(Faction faction) {
        var containsAggressor = this.aggressors.stream()
                .map(WarParticipant::getWarParticipant)
                .anyMatch(aggressor -> aggressor.equals(faction));

        if (containsAggressor)
            return this.defenders;

        var containsDefenders = this.defenders.stream()
                .map(WarParticipant::getWarParticipant)
                .anyMatch(defender -> defender.equals(faction));

        if (containsDefenders)
            return this.aggressors;

        return new HashSet<>();
    }

    /**
     * Returns the initial attacker in the war.
     *
     * @return the initial attacker
     */
    public WarParticipant getInitialAttacker() {
        return getInitialParty(this.aggressors);
    }

    /**
     * Returns the initial defender in the war.
     *
     * @return the initial defender
     */
    public WarParticipant getInitialDefender() {
        return getInitialParty(this.defenders);
    }

    /**
     * Returns the initial party from a set of participants.
     *
     * @param participants the set of participants
     * @return the initial party
     * @throws NullPointerException if no initial party is found
     */
    private WarParticipant getInitialParty(Set<WarParticipant> participants) {
        return participants.stream()
                .filter(WarParticipant::getInitialParty)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Found no initialParty in War '%s'".formatted(this.name)));
    }

    /**
     * Adds an object to a set and throws an exception if the object is already present.
     *
     * @param set       the set to which the object is to be added
     * @param object    the object to be added
     * @param exception the exception to be thrown if the object is already present
     * @param <T>       the type of the object
     * @throws WarServiceException if the object is already present in the set
     */
    private <T> void addToSet(Set<T> set, T object, WarServiceException exception) {
        var successfullyAdded = set.add(object);

        if (!successfullyAdded) {
            log.warn("Could not add {} [{}] because it is already present in Set", object.getClass().getSimpleName(), object);
            throw exception;
        }
    }

    /**
     * Builds a WarParticipant for a given faction.
     *
     * @param faction the faction for which the WarParticipant is to be built
     * @return the built WarParticipant
     * @throws NullPointerException if the faction is null
     */
    private WarParticipant buildWarParticipant(Faction faction) {
        Objects.requireNonNull(faction, "Faction must not be null!");
        return new WarParticipant(faction, false, OffsetDateTime.now());
    }

    /**
     * Adds a faction to the aggressors.
     *
     * @param faction the faction to be added
     * @throws WarServiceException if the faction is already an aggressor
     */
    public void addToAggressors(Faction faction) {
        val participant = buildWarParticipant(faction);
        addToSet(this.aggressors, participant, WarServiceException.factionAlreadyJoinedTheWarAsAttacker(participant.getWarParticipant().getName()));
    }

    /**
     * Adds a faction to the defenders.
     *
     * @param faction the faction to be added
     * @throws WarServiceException if the faction is already a defender
     */
    public void addToDefenders(Faction faction) {
        val participant = buildWarParticipant(faction);
        addToSet(this.defenders, participant, WarServiceException.factionAlreadyJoinedTheWarAsDefender(participant.getWarParticipant().getName()));
    }

    /**
     * Adds a battle to the war.
     *
     * @param battle the battle to be added
     * @throws WarServiceException if the battle is already listed
     */
    public void addToBattles(Battle battle) {
        addToSet(this.battles, battle, WarServiceException.battleAlreadyListed(battle.getName()));
    }

    /**
     * Ends the war.
     */
    public void end() {
        log.debug("Setting war [{}] to inactive", name);
        setIsActive(false);

        val endDate = OffsetDateTime.now();
        log.debug("Setting war [{}] end date to [{}]", name, endDate);
        setEndDate(endDate);
    }

    /**
     * Returns an unmodifiable set of aggressors.
     *
     * @return an unmodifiable set of aggressors
     */
    public Set<WarParticipant> getAggressors() {
        return Collections.unmodifiableSet(this.aggressors);
    }

    /**
     * Returns an unmodifiable set of defenders.
     *
     * @return an unmodifiable set of defenders
     */
    public Set<WarParticipant> getDefenders() {
        return Collections.unmodifiableSet(this.defenders);
    }

    /**
     * Returns an unmodifiable set of battles.
     *
     * @return an unmodifiable set of battles
     */
    public Set<Battle> getBattles() {
        return Collections.unmodifiableSet(this.battles);
    }

    /**
     * Returns a string representation of this War.
     *
     * @return a string representation of this War
     */
    @Override
    public String toString() {
        return "War{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", aggressors=" + aggressors +
                ", defenders=" + defenders +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", battles=" + battles +
                '}';
    }
}