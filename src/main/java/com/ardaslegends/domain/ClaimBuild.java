package com.ardaslegends.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a claim build in the application.
 * This class is marked as {@link Entity} and is mapped to the "claimbuilds" table in the database.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
@Entity
@Table(name = "claimbuilds")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "name")
public final class ClaimBuild extends AbstractEntity {

    /**
     * The unique name of the claim build.
     */
    @Column(unique = true)
    private String name;

    /**
     * The region the claim build is in.
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "region", foreignKey = @ForeignKey(name = "fk_claimbuild_region"))
    @NotNull(message = "Claimbuild: Region must not be null")
    private Region region;

    /**
     * The type of the claim build.
     */
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Claimbuild: ClaimbuildType must not be null")
    private ClaimBuildType type;

    /**
     * The faction which owns this claim build.
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "owned_by", foreignKey = @ForeignKey(name = "fk_claimbuild_owned_by"))
    @NotNull(message = "Claimbuild: ownedBy must not be null")
    private Faction ownedBy;

    /**
     * The coordinates of the claim build.
     */
    @Embedded
    @NotNull(message = "Claimbuild: Coordinate must not be null")
    private Coordinate coordinates;

    /**
     * The armies stationed in this claim build.
     */
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "stationedAt")
    @JsonIdentityReference(alwaysAsId = true)
    @Builder.Default
    private List<Army> stationedArmies = new ArrayList<>();

    /**
     * The armies created from this claim build.
     */
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "originalClaimbuild")
    @JsonIdentityReference(alwaysAsId = true)
    @Builder.Default
    private List<Army> createdArmies = new ArrayList<>();

    /**
     * The production sites in this claim build.
     */
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "claimbuild")
    @Builder.Default
    private List<ProductionClaimbuild> productionSites = new ArrayList<>();

    /**
     * The special buildings in this claim build.
     */
    @ElementCollection(targetClass = SpecialBuilding.class)
    @CollectionTable(name = "claimbuild_special_buildings",
            joinColumns = @JoinColumn(name = "claimbuild_id", foreignKey = @ForeignKey(name = "fk_claimbuild_special_buildings_claimbuild_id")))
    @Column(name = "special_buildings")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private List<SpecialBuilding> specialBuildings = new ArrayList<>();

    /**
     * The traders in this claim build.
     */
    private String traders;

    /**
     * The siege equipment the claim build provides.
     */
    private String siege;

    /**
     * The number of houses in this claim build.
     */
    private String numberOfHouses;

    /**
     * The players who built the claim build.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "claimbuild_builders",
            joinColumns = {@JoinColumn(name = "claimbuild_id", foreignKey = @ForeignKey(name = "fk_claimbuild_builders_claimbuild_id"))},
            inverseJoinColumns = {@JoinColumn(name = "player_id", foreignKey = @ForeignKey(name = "fk_claimbuild_builders_player_id"))})
    @Builder.Default
    private Set<Player> builtBy = new HashSet<>();

    /**
     * The number of free armies remaining.
     */
    private int freeArmiesRemaining;

    /**
     * The number of free trading companies remaining.
     */
    private int freeTradingCompaniesRemaining;

    /**
     * The characters stationed in this claim build.
     */
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "stationedAt")
    @JsonIdentityReference(alwaysAsId = true)
    @Builder.Default
    private Set<RPChar> stationedChars = new HashSet<>();

    /**
     * Constructs a new ClaimBuild.
     *
     * @param name             the name of the claim build
     * @param region           the region the claim build is in
     * @param type             the type of the claim build
     * @param ownedBy          the faction which owns this claim build
     * @param coordinates      the coordinates of the claim build
     * @param specialBuildings the special buildings in this claim build
     * @param traders          the traders in this claim build
     * @param siege            the siege equipment the claim build provides
     * @param numberOfHouses   the number of houses in this claim build
     * @param builtBy          the players who built the claim build
     */
    public ClaimBuild(String name, Region region, ClaimBuildType type, Faction ownedBy, Coordinate coordinates, List<SpecialBuilding> specialBuildings, String traders, String siege, String numberOfHouses, Set<Player> builtBy) {
        this.name = name;
        this.region = region;
        this.type = type;
        this.ownedBy = ownedBy;
        this.coordinates = coordinates;
        this.productionSites = new ArrayList<>();
        this.specialBuildings = new ArrayList<>(specialBuildings);
        this.traders = traders;
        this.siege = siege;
        this.numberOfHouses = numberOfHouses;
        this.builtBy = new HashSet<>(builtBy);
        this.createdArmies = new ArrayList<>();
        this.stationedArmies = new ArrayList<>();
        this.freeArmiesRemaining = type.getFreeArmies();
        this.freeTradingCompaniesRemaining = type.getFreeTradingCompanies();
    }

    /**
     * Constructs a new ClaimBuild.
     *
     * @param name             the name of the claim build
     * @param region           the region the claim build is in
     * @param type             the type of the claim build
     * @param ownedBy          the faction which owns this claim build
     * @param coordinates      the coordinates of the claim build
     * @param productionSites  the production sites in this claim build
     * @param specialBuildings the special buildings in this claim build
     * @param traders          the traders in this claim build
     * @param siege            the siege equipment the claim build provides
     * @param numberOfHouses   the number of houses in this claim build
     * @param builtBy          the players who built the claim build
     */
    public ClaimBuild(String name, Region region, ClaimBuildType type, Faction ownedBy, Coordinate coordinates, List<ProductionClaimbuild> productionSites, List<SpecialBuilding> specialBuildings, String traders, String siege, String numberOfHouses, Set<Player> builtBy) {
        this(name, region, type, ownedBy, coordinates, specialBuildings, traders, siege, numberOfHouses, builtBy);
        this.productionSites = productionSites;
    }

    /**
     * Returns the count of armies created from this claim build.
     *
     * @return the count of armies created from this claim build
     */
    @JsonIgnore
    public int getCountOfArmies() {
        int count = (int) createdArmies.stream()
                .filter(army -> ArmyType.ARMY.equals(army.getArmyType()))
                .count();
        log.debug("Claimbuild [{}] has created [{}] armies", this.name, count);
        return count;
    }

    /**
     * Returns the count of trading companies created from this claim build.
     *
     * @return the count of trading companies created from this claim build
     */
    @JsonIgnore
    public int getCountOfTradingCompanies() {
        int count = (int) createdArmies.stream()
                .filter(army -> ArmyType.TRADING_COMPANY.equals(army.getArmyType()))
                .count();
        log.debug("Claimbuild [{}] has created [{}] trading companies", this.name, count);
        return count;
    }

    /**
     * Checks if the claim build is at the maximum number of armies.
     *
     * @return true if the claim build is at the maximum number of armies, false otherwise
     */
    public boolean atMaxArmies() {
        int countOfArmies = getCountOfArmies();
        int maxArmies = getType().getMaxArmies();
        if (countOfArmies >= maxArmies) {
            log.debug("Claimbuild [{}] is at max armies, max armies [{}] - armies created [{}]", this.name, maxArmies, countOfArmies);
            return true;
        }
        log.debug("Claimbuild [{}] can create more armies. max armies [{}] - armies created [{}]", this.name, maxArmies, countOfArmies);
        return false;
    }

    /**
     * Checks if the claim build is at the maximum number of trading companies.
     *
     * @return true if the claim build is at the maximum number of trading companies, false otherwise
     */
    @JsonIgnore
    public boolean atMaxTradingCompanies() {
        int countOfTradingCompanies = getCountOfTradingCompanies();
        int maxTradingCompanies = getType().getMaxTradingCompanies();
        if (countOfTradingCompanies >= maxTradingCompanies) {
            log.debug("Claimbuild [{}] is at max trading companies, max companies[{}] - companies created [{}]", this.name, maxTradingCompanies, countOfTradingCompanies);
            return true;
        }
        log.debug("Claimbuild [{}] can create more trading companies. max companies [{}] - companies created [{}]", this.name, maxTradingCompanies, countOfTradingCompanies);
        return false;
    }

    /**
     * Returns a string representation of this ClaimBuild.
     *
     * @return a string representation of this ClaimBuild
     */
    @Override
    public String toString() {
        return name;
    }
}