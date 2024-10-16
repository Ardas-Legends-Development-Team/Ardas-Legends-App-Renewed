package com.ardaslegends.domain;

import com.ardaslegends.service.exceptions.logic.faction.FactionServiceException;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a faction in the application.
 * This class is marked as {@link Entity} and is mapped to the "factions" table in the database.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
@Entity
@Table(name = "factions")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "name")
public final class Faction extends AbstractDomainObject {

    /**
     * The unique identifier of the faction.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The unique name of the faction.
     */
    @Column(unique = true)
    private String name;

    /**
     * The initial faction details.
     */
    private InitialFaction initialFaction;

    /**
     * The player who leads this faction.
     */
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Player leader;

    /**
     * The armies belonging to this faction.
     */
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "faction")
    @JsonIdentityReference(alwaysAsId = true)
    private List<Army> armies;

    /**
     * The players belonging to this faction.
     */
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "faction")
    private List<Player> players;

    /**
     * The regions claimed by this faction.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "claimedBy")
    private Set<Region> regions;

    /**
     * The claim builds owned by this faction.
     */
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "ownedBy")
    private List<ClaimBuild> claimBuilds;

    /**
     * The units available to this faction.
     */
    @ManyToMany(mappedBy = "usableBy")
    @Builder.Default
    private Set<UnitType> availableUnits = new HashSet<>(15);

    /**
     * The allies of this faction.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "faction_allies",
            joinColumns = {@JoinColumn(name = "faction", foreignKey = @ForeignKey(name = "fk_faction_allies_faction"))},
            inverseJoinColumns = {@JoinColumn(name = "ally_faction", foreignKey = @ForeignKey(name = "fk_faction_allies_ally_faction"))})
    private List<Faction> allies;

    /**
     * The color code of the faction, used for painting the map.
     */
    private String colorcode;

    /**
     * The role ID of the faction role so that it can be pinged.
     */
    @Column(name = "role_id", unique = true)
    private Long factionRoleId;

    /**
     * The home region of the faction.
     */
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Region homeRegion;

    /**
     * The description of this faction's buff.
     */
    @Length(max = 512)
    private String factionBuffDescr;

    /**
     * The food stockpile of the faction, used for army movements.
     */
    @Column(name = "food_stockpile")
    @Builder.Default
    private Integer foodStockpile = 0;

    /**
     * The aliases of the faction.
     */
    @ElementCollection
    @CollectionTable(name = "faction_aliases", joinColumns = @JoinColumn(name = "faction_id", foreignKey = @ForeignKey(name = "fk_faction_aliases_faction_id")))
    @Builder.Default
    private Set<String> aliases = new HashSet<>();

    /**
     * Constructs a new Faction.
     *
     * @param name             the name of the faction
     * @param leader           the player who leads this faction
     * @param armies           the armies belonging to this faction
     * @param players          the players belonging to this faction
     * @param regions          the regions claimed by this faction
     * @param claimBuilds      the claim builds owned by this faction
     * @param allies           the allies of this faction
     * @param colorcode        the color code of the faction
     * @param homeRegion       the home region of the faction
     * @param factionBuffDescr the description of this faction's buff
     */
    public Faction(String name, Player leader, List<Army> armies, List<Player> players, Set<Region> regions, List<ClaimBuild> claimBuilds, List<Faction> allies, String colorcode, Region homeRegion, String factionBuffDescr) {
        this.name = name;
        this.leader = leader;
        this.armies = armies;
        this.players = players;
        this.regions = regions;
        this.claimBuilds = claimBuilds;
        this.allies = allies;
        this.colorcode = colorcode;
        this.homeRegion = homeRegion;
        this.factionBuffDescr = factionBuffDescr;
        this.foodStockpile = 0;
        this.availableUnits = new HashSet<>(15);
    }

    /**
     * Adds food to the stockpile of the faction.
     *
     * @param amount the amount of food to add
     * @throws FactionServiceException if the amount is negative
     */
    @JsonIgnore
    public void addFoodToStockpile(int amount) {
        log.debug("Adding food [amount:{}] to stockpile of faction [{}]", amount, this.name);
        if (amount < 0) {
            log.warn("Amount to add is below 0 [{}]", amount);
            throw FactionServiceException.negativeStockpileAddNotSupported();
        }
        this.foodStockpile += amount;
    }

    /**
     * Subtracts food from the stockpile of the faction.
     *
     * @param amount the amount of food to subtract
     * @throws FactionServiceException if the amount is negative or if there is not enough food in the stockpile
     */
    @JsonIgnore
    public void subtractFoodFromStockpile(int amount) {
        log.debug("Removing food [amount: {}] from stockpile of faction [{}]", amount, this.name);
        if (amount < 0) {
            log.warn("Amount to remove is above 0 [{}]", amount);
            throw FactionServiceException.negativeStockpileSubtractNotSupported();
        }

        if (this.foodStockpile - amount < 0) {
            log.warn("Subtract would set the stockpile of faction [{}] to below zero!", this.name);
            throw FactionServiceException.notEnoughFoodInStockpile(this.name, this.foodStockpile, amount);
        }
        this.foodStockpile -= amount;
    }

    /**
     * Checks if this Faction is equal to another object.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faction faction = (Faction) o;
        return name.equals(faction.name);
    }

    /**
     * Returns the hash code of this Faction.
     *
     * @return the hash code of this Faction
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Returns a string representation of this Faction.
     *
     * @return a string representation of this Faction
     */
    @Override
    public String toString() {
        return name;
    }
}