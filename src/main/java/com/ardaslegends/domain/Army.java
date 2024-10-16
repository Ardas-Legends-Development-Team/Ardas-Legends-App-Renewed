package com.ardaslegends.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents an army in the application.
 * This class is marked as {@link Entity} and is mapped to the "armies" table in the database.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "armies")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "name")
public final class Army extends AbstractDomainObject {

    /**
     * The unique identifier of the army.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The name of the army.
     */
    @Column(name = "name")
    private String name;

    /**
     * The type of the army.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "army_type")
    @NotNull(message = "Army: Army Type must not be null or empty")
    private ArmyType armyType;

    /**
     * The faction this army belongs to.
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "faction", foreignKey = @ForeignKey(name = "fk_armies_faction"))
    @NotNull(message = "Army: Faction must not be null")
    private Faction faction;

    /**
     * The region the army is currently in.
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "current_Region", foreignKey = @ForeignKey(name = "fk_armies_current_region"))
    @NotNull(message = "Army: Region must not be null")
    private Region currentRegion;

    /**
     * The RP character the army is currently bound to.
     */
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "boundTo")
    @JoinColumn(name = "bound_to", foreignKey = @ForeignKey(name = "fk_armies_bound_to"))
    private RPChar boundTo;

    /**
     * The units in this army.
     */
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "army")
    @Builder.Default
    private List<Unit> units = new ArrayList<>();

    /**
     * The list of siege equipment this army contains.
     */
    @ElementCollection
    @CollectionTable(name = "army_sieges",
            joinColumns = @JoinColumn(name = "army_id", foreignKey = @ForeignKey(name = "fk_army_sieges_army_id")))
    @Builder.Default
    private List<String> sieges = new ArrayList<>();

    /**
     * The claim build where this army is stationed.
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "stationed_at", foreignKey = @ForeignKey(name = "fk_armies_stationed_at"))
    private ClaimBuild stationedAt;

    /**
     * The number of free unit tokens this army has left.
     */
    @NotNull(message = "Army: freeTokens must not be null")
    @Column(name = "free_tokens")
    private Double freeTokens;

    /**
     * Indicates whether the army is healing.
     */
    @Builder.Default
    @Column(name = "is_healing")
    private Boolean isHealing = false;

    /**
     * The start time of the healing process.
     */
    @Column(name = "heal_start")
    private OffsetDateTime healStart;

    /**
     * The end time of the healing process.
     */
    @Column(name = "heal_end")
    private OffsetDateTime healEnd;

    /**
     * The number of hours the army has healed.
     */
    @Column(name = "hours_healed")
    private Integer hoursHealed;

    /**
     * The number of hours left for the army to heal.
     */
    @Column(name = "hours_left_healing")
    private Integer hoursLeftHealing;

    /**
     * The last time the healing process was updated.
     */
    @Column(name = "heal_last_updated_at")
    private OffsetDateTime healLastUpdatedAt;

    /**
     * The claim build where this army was created from.
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "origin_claimbuild", foreignKey = @ForeignKey(name = "fk_armies_origin_claimbuild"))
    private ClaimBuild originalClaimbuild;

    /**
     * The creation time of the army.
     */
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    /**
     * The list of movements associated with this army.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "army", cascade = {CascadeType.REMOVE})
    @Builder.Default
    private List<Movement> movements = new ArrayList<>();

    /**
     * Indicates whether the army is paid.
     */
    @Column(name = "is_paid")
    private Boolean isPaid;

    /**
     * Constructs a new Army.
     *
     * @param name               the name of the army
     * @param armyType           the type of the army
     * @param faction            the faction this army belongs to
     * @param currentRegion      the region the army is currently in
     * @param boundTo            the RP character the army is currently bound to
     * @param units              the units in this army
     * @param sieges             the list of siege equipment this army contains
     * @param stationedAt        the claim build where this army is stationed
     * @param freeTokens         the number of free unit tokens this army has left
     * @param isHealing          indicates whether the army is healing
     * @param healStart          the start time of the healing process
     * @param healEnd            the end time of the healing process
     * @param hoursHealed        the number of hours the army has healed
     * @param hoursLeftHealing   the number of hours left for the army to heal
     * @param originalClaimbuild the claim build where this army was created from
     * @param createdAt          the creation time of the army
     * @param isPaid             indicates whether the army is paid
     */
    public Army(String name, ArmyType armyType, Faction faction, Region currentRegion, RPChar boundTo, List<Unit> units, List<String> sieges, ClaimBuild stationedAt, Double freeTokens, boolean isHealing, OffsetDateTime healStart, OffsetDateTime healEnd,
                Integer hoursHealed, Integer hoursLeftHealing, ClaimBuild originalClaimbuild, OffsetDateTime createdAt, boolean isPaid) {
        this.name = name;
        this.armyType = armyType;
        this.faction = faction;
        this.currentRegion = currentRegion;
        this.boundTo = boundTo;
        this.units = units;
        this.sieges = sieges;
        this.stationedAt = stationedAt;
        this.freeTokens = freeTokens;
        this.isHealing = isHealing;
        this.healStart = healStart;
        this.healEnd = healEnd;
        this.hoursHealed = hoursHealed;
        this.healLastUpdatedAt = healStart;
        this.hoursLeftHealing = hoursLeftHealing;
        this.originalClaimbuild = originalClaimbuild;
        this.createdAt = createdAt;
        this.isPaid = isPaid;
    }

    /**
     * Returns a string representation of this Army.
     *
     * @return a string representation of this Army
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Checks if this Army is equal to another object.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Army army = (Army) o;
        return name.equals(army.name);
    }

    /**
     * Returns the hash code of this Army.
     *
     * @return the hash code of this Army
     */
    @Override
    public int hashCode() {
        return name != null ? Objects.hash(name) : 0;
    }

    /**
     * Checks if all units in the army are alive.
     *
     * @return true if all units are alive, false otherwise
     */
    public boolean allUnitsAlive() {
        return this.units.stream().allMatch(unit -> Objects.equals(unit.getAmountAlive(), unit.getCount()));
    }

    /**
     * Checks if the army has any units left.
     *
     * @return true if the army has units left, false otherwise
     */
    public boolean hasUnitsLeft() {
        return units.stream().anyMatch(unit -> unit.getAmountAlive() > 0);
    }

    /**
     * Returns the active movement of the army, if any.
     *
     * @return an {@link Optional} containing the active movement, or an empty {@link Optional} if no active movement is found
     */
    public Optional<Movement> getActiveMovement() {
        return this.getMovements().stream().filter(Movement::getIsCurrentlyActive).findFirst();
    }

    /**
     * Returns the amount of heal hours required for the army.
     *
     * @return the amount of heal hours required
     */
    @JsonIgnore
    public int getAmountOfHealHours() {
        double tokensMissing = units.stream()
                .map(unit -> ((unit.getCount() - unit.getAmountAlive())) * unit.getCost())
                .reduce(0.0, Double::sum);
        double hoursHeal = tokensMissing * 24 / 6;
        int divisor = 24;
        if (this.stationedAt.getType().equals(ClaimBuildType.STRONGHOLD)) {
            hoursHeal /= 2;
            divisor = 12;
        }
        int intHoursHeal = (int) Math.ceil(hoursHeal);
        int hoursLeftUntil24h = divisor - (intHoursHeal % divisor);
        return intHoursHeal + hoursLeftUntil24h;
    }

    /**
     * Resets the healing statistics of the army.
     */
    @JsonIgnore
    public void resetHealingStats() {
        this.setIsHealing(false);
        this.setHealStart(null);
        this.setHealEnd(null);
        this.setHoursHealed(0);
        this.setHoursLeftHealing(0);
    }

    /**
     * Checks if the army is younger than 24 hours.
     *
     * @return true if the army is younger than 24 hours, false otherwise
     */
    public boolean isYoungerThan24h() {
        return OffsetDateTime.now().isBefore(this.createdAt.plusHours(24));
    }
}