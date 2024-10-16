package com.ardaslegends.domain.war.battle;

import com.ardaslegends.domain.AbstractDomainObject;
import com.ardaslegends.domain.Army;
import com.ardaslegends.domain.Faction;
import com.ardaslegends.domain.war.War;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a battle in the context of a war.
 * This class extends {@link AbstractDomainObject} and provides specific fields and methods for battles.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "battles")
public class Battle extends AbstractDomainObject {

    /**
     * The unique identifier of the battle.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The wars associated with this battle.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "wars_battles",
            joinColumns = {@JoinColumn(name = "battle_id", foreignKey = @ForeignKey(name = "fk_wars_battles_battle_id"))},
            inverseJoinColumns = {@JoinColumn(name = "war_id", foreignKey = @ForeignKey(name = "fk_wars_battles_war_id"))})
    private Set<War> wars;

    /**
     * The name of the battle.
     */
    private String name;

    /**
     * The attacking armies in the battle.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "battle_attackingArmies",
            joinColumns = {@JoinColumn(name = "battle_id", foreignKey = @ForeignKey(name = "fk_battle_attackingArmies_battle"))},
            inverseJoinColumns = {@JoinColumn(name = "atackingArmy_id", foreignKey = @ForeignKey(name = "fk_battle_attackingArmies_attackingArmy"))})
    private Set<Army> attackingArmies = new HashSet<>(1);

    /**
     * The initial attacker in the battle.
     */
    @NotNull
    @ManyToOne
    @JoinColumn(name = "initial_attacker", foreignKey = @ForeignKey(name = "fk_battle_initial_attacker"))
    private Army initialAttacker;

    /**
     * The initial defender in the battle.
     */
    @ManyToOne
    @JoinColumn(name = "initial_defender", foreignKey = @ForeignKey(name = "fk_battle_initial_defender"))
    private Faction initialDefender;

    /**
     * The defending armies in the battle.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "battle_defendingArmies",
            joinColumns = {@JoinColumn(name = "battle_id", foreignKey = @ForeignKey(name = "fk_battle_defendingArmies_battle"))},
            inverseJoinColumns = {@JoinColumn(name = "defendingArmy_id", foreignKey = @ForeignKey(name = "fk_battle_defendingArmies_defendingArmy"))})
    private Set<Army> defendingArmies = new HashSet<>();

    /**
     * The current phase of the battle.
     */
    @Setter
    @Enumerated(EnumType.STRING)
    private BattlePhase battlePhase;

    /**
     * The date when the battle was declared.
     */
    private OffsetDateTime declaredDate;

    /**
     * The date and time from which the battle is frozen.
     */
    @Setter
    private OffsetDateTime timeFrozenFrom;

    /**
     * The date and time until which the battle is frozen.
     */
    @Setter
    private OffsetDateTime timeFrozenUntil;

    /**
     * The agreed date for the battle.
     */
    private OffsetDateTime agreedBattleDate;

    /**
     * The location of the battle.
     */
    @Embedded
    private BattleLocation battleLocation;

    /**
     * The result of the battle.
     */
    @Setter
    @Embedded
    private BattleResult battleResult;

    /**
     * Constructs a new Battle.
     *
     * @param wars             the wars associated with this battle
     * @param name             the name of the battle
     * @param attackingArmies  the attacking armies in the battle
     * @param defendingArmies  the defending armies in the battle
     * @param declaredDate     the date when the battle was declared
     * @param timeFrozenFrom   the date and time from which the battle is frozen
     * @param timeFrozenUntil  the date and time until which the battle is frozen
     * @param agreedBattleDate the agreed date for the battle
     * @param battleLocation   the location of the battle
     * @throws IllegalArgumentException if no initial attacker is found or if no initial defender is found for a field battle
     */
    public Battle(Set<War> wars, String name, Set<Army> attackingArmies, Set<Army> defendingArmies, OffsetDateTime declaredDate, OffsetDateTime timeFrozenFrom, OffsetDateTime timeFrozenUntil, OffsetDateTime agreedBattleDate, BattleLocation battleLocation) {
        this.wars = new HashSet<>(wars);
        this.name = name;
        this.attackingArmies = new HashSet<>(attackingArmies);
        this.defendingArmies = new HashSet<>(defendingArmies);
        this.declaredDate = declaredDate;
        this.battlePhase = BattlePhase.PRE_BATTLE;
        this.timeFrozenFrom = timeFrozenFrom;
        this.timeFrozenUntil = timeFrozenUntil;
        this.agreedBattleDate = agreedBattleDate;
        this.battleLocation = battleLocation;
        this.initialAttacker = attackingArmies.stream().findFirst().orElseThrow(() -> new IllegalArgumentException("CONTACT DEVS: No initial attacker in Battle %s!".formatted(name)));
        if (battleLocation.getFieldBattle())
            this.initialDefender = defendingArmies.stream().findFirst().orElseThrow(() -> new IllegalArgumentException("CONTACT DEVS: No initial defender in Battle %s!".formatted(name))).getFaction();
        else
            this.initialDefender = battleLocation.getClaimBuild().getOwnedBy();
    }

    /**
     * Returns all armies partaking in the battle.
     *
     * @return an unmodifiable set of all armies partaking in the battle
     */
    public Set<Army> getPartakingArmies() {
        HashSet<Army> allArmies = new HashSet<>(attackingArmies.size() + defendingArmies.size());
        allArmies.addAll(attackingArmies);
        allArmies.addAll(defendingArmies);
        return Collections.unmodifiableSet(allArmies);
    }

    /**
     * Returns the first defending army in the battle.
     *
     * @return the first defending army
     * @throws NullPointerException if no defending armies are found
     */
    public Army getFirstDefender() {
        return defendingArmies.stream().findFirst()
                .orElseThrow(() -> new NullPointerException("Found no defending armies in battle at location %s".formatted(battleLocation.toString())));
    }

    /**
     * Checks if the battle is over.
     *
     * @return true if the battle is over, false otherwise
     */
    public boolean isOver() {
        return BattlePhase.CONCLUDED.equals(this.battlePhase);
    }

    /**
     * Checks if the battle is a field battle.
     *
     * @return true if the battle is a field battle, false otherwise
     */
    public boolean isFieldBattle() {
        return battleLocation.getFieldBattle();
    }
}