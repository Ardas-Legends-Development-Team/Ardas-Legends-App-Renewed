package com.ardaslegends.domain;

import com.ardaslegends.service.utils.ServiceUtils;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a region in the application.
 * This class is marked as {@link Entity} and is mapped to the "regions" table in the database.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
@Entity
@Table(name = "regions")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public final class Region extends AbstractDomainObject {

    /**
     * The unique identifier of the region.
     */
    @Id
    private String id;

    /**
     * The name of the region.
     */
    private String name;

    /**
     * The type of the region.
     */
    @Enumerated(EnumType.STRING)
    private RegionType regionType;

    /**
     * The list of factions which the region is claimed by.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "faction_claimed_regions",
            joinColumns = {@JoinColumn(name = "region", foreignKey = @ForeignKey(name = "fk_faction_claimed_regions_region"))},
            inverseJoinColumns = {@JoinColumn(name = "faction", foreignKey = @ForeignKey(name = "fk_faction_claimed_regions_faction"))})
    @Builder.Default
    private Set<Faction> claimedBy = new HashSet<>();

    /**
     * The list of claim builds in this region.
     */
    @JsonIgnore
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "region")
    @Builder.Default
    private Set<ClaimBuild> claimBuilds = new HashSet<>();

    /**
     * The list of neighboring regions.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "region_neighbours",
            joinColumns = {@JoinColumn(name = "region", foreignKey = @ForeignKey(name = "fk_region_neighbours_region"))},
            inverseJoinColumns = {@JoinColumn(name = "neighbour", foreignKey = @ForeignKey(name = "fk_region_neighbours_neighbour"))})
    @Builder.Default
    private Set<Region> neighboringRegions = new HashSet<>();

    /**
     * Indicates whether the ownership has changed since the last claim map update.
     */
    @Column(name = "has_ownership_changed_since_last_claimmap_update")
    private boolean hasOwnershipChanged;

    /**
     * The set of roleplay characters currently in this region.
     */
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "currentRegion")
    @Builder.Default
    private Set<RPChar> charsInRegion = new HashSet<>(1);

    /**
     * Constructs a new Region with the specified attributes.
     *
     * @param id                 the unique identifier of the region
     * @param name               the name of the region
     * @param regionType         the type of the region
     * @param claimedBy          the list of factions which the region is claimed by
     * @param claimBuilds        the list of claim builds in this region
     * @param neighboringRegions the list of neighboring regions
     */
    @JsonIgnore
    public Region(String id, String name, RegionType regionType, Set<Faction> claimedBy, Set<ClaimBuild> claimBuilds, Set<Region> neighboringRegions) {
        this.id = id;
        this.name = name;
        this.regionType = regionType;
        this.claimedBy = claimedBy;
        this.claimBuilds = claimBuilds;
        this.neighboringRegions = neighboringRegions;
        this.hasOwnershipChanged = false;
    }

    /**
     * Adds a neighboring region to this region.
     *
     * @param possibleNeighbour the region to add as a neighbor
     * @return false if the region is already a neighbor, true otherwise
     * @throws NullPointerException if the possibleNeighbour parameter is null
     */
    @JsonIgnore
    public boolean addNeighbour(@NonNull Region possibleNeighbour) {
        if (neighboringRegions.contains(possibleNeighbour))
            return false;

        neighboringRegions.add(possibleNeighbour);
        return true;
    }

    /**
     * Adds a faction to the list of factions that have claimed this region.
     *
     * @param faction the faction to add
     * @throws NullPointerException if the faction parameter is null
     */
    @JsonIgnore
    public void addFactionToClaimedBy(Faction faction) {
        log.debug("Add claiming faction [{}] to region [{}]", faction, this.id);

        Objects.requireNonNull(faction, "Faction must not be null!");
        ServiceUtils.checkBlankString(faction.getName(), "faction name");

        if (!this.claimedBy.contains(faction)) {
            log.debug("Faction [{}] is not in region [{}]'s claimedBy Set, adding it", faction, this.id);
            this.claimedBy.add(faction);
            faction.getRegions().add(this);

            this.hasOwnershipChanged = true;
            log.debug("Also setting hasOwnershipChanged to true [ownershipChanged: {}]", this.hasOwnershipChanged);
        }

        log.debug("Faction [{}] is in region [{}]'s claimedBy Set", faction, this.id);
    }

    /**
     * Removes a faction from the list of factions that have claimed this region.
     *
     * @param faction the faction to remove
     * @throws NullPointerException if the faction parameter is null
     */
    @JsonIgnore
    public void removeFactionFromClaimedBy(Faction faction) {
        log.debug("Remove claiming faction [{}] from region [{}]", faction, this.id);

        Objects.requireNonNull(faction, "Faction must not be null!");
        ServiceUtils.checkBlankString(faction.getName(), "faction name");

        if (this.getClaimedBy().contains(faction)) {
            log.debug("Faction [{}] is in region [{}]'s claimedBy Set, removing it", faction, this.id);
            this.claimedBy.remove(faction);
            faction.getRegions().remove(this);

            this.hasOwnershipChanged = true;
            log.debug("Also setting hasOwnershipChanged to true [ownershipChanged: {}]", this.hasOwnershipChanged);
        }

        log.debug("Faction [{}] is not in region [{}]'s claimedBy Set", faction, this.id);
    }

    /**
     * Returns an unmodifiable set of neighboring regions.
     *
     * @return an unmodifiable set of neighboring regions
     */
    public Set<Region> getNeighboringRegions() {
        return Collections.unmodifiableSet(neighboringRegions);
    }

    /**
     * Returns the cost of the region.
     *
     * @return the cost of the region
     */
    public int getCost() {
        return regionType.getCost();
    }

    /**
     * Checks if a faction has a claim build in this region.
     *
     * @param faction the faction to check
     * @return true if the faction has a claim build in this region, false otherwise
     * @throws NullPointerException if the faction parameter is null
     */
    @JsonIgnore
    public boolean hasClaimbuildInRegion(Faction faction) {
        Objects.requireNonNull(faction, "Faction must not be null");

        boolean hasClaimbuild = claimBuilds.stream()
                .anyMatch(claimBuild -> claimBuild.getOwnedBy().equals(faction));

        log.debug("Does faction [{}] have a claimbuild in region [{}]? Returning [{}]", faction.getName(), this.id, hasClaimbuild);
        return hasClaimbuild;
    }

    /**
     * Checks if a faction is the only faction that has claim builds in this region.
     *
     * @param faction the faction to check
     * @return true if the faction is the only faction with claim builds in this region, false otherwise
     * @throws NullPointerException if the faction parameter is null
     */
    @JsonIgnore
    public boolean isOnlyFactionInRegion(Faction faction) {
        Objects.requireNonNull(faction, "Faction must not be null");

        boolean isOnlyFaction = claimBuilds.stream()
                .anyMatch(claimBuild -> !claimBuild.getOwnedBy().equals(faction));

        log.debug("Is faction [{}] the only faction that has claimbuilds in region [{}]? Returning [{}]", faction.getName(), this.id, isOnlyFaction);
        return isOnlyFaction;
    }

    /**
     * Checks if a faction has other claim builds in this region besides the specified claim build.
     *
     * @param cb the claim build to check against
     * @return true if the faction has other claim builds in this region, false otherwise
     * @throws NullPointerException if the cb parameter is null
     */
    @JsonIgnore
    public boolean hasFactionOtherClaimbuildThan(ClaimBuild cb) {
        Objects.requireNonNull(cb, "claimbuild must not be null");

        boolean hasClaimbuild = claimBuilds.stream()
                .anyMatch(claimBuild -> claimBuild.getOwnedBy().equals(cb.getOwnedBy()) && !claimBuild.equals(cb));

        log.debug("Does faction [{}] have other claimbuild than [{}] in region [{}]? Returning [{}]", cb.getOwnedBy().getName(), cb, this.id, hasClaimbuild);
        return hasClaimbuild;
    }

    /**
     * Checks if the region is claimable by a faction.
     *
     * @param faction the faction to check
     * @return true if the region is claimable by the faction, false otherwise
     * @throws NullPointerException if the faction parameter is null
     */
    @JsonIgnore
    public boolean isClaimable(Faction faction) {
        Objects.requireNonNull(faction, "Faction must not be null");

        boolean hasClaimbuild = hasClaimbuildInRegion(faction);
        boolean regionHasNoCb = this.claimBuilds.isEmpty();

        return hasClaimbuild || regionHasNoCb;
    }

    /**
     * Returns a string representation of this region.
     *
     * @return a string representation of this region
     */
    @Override
    public String toString() {
        return "Region{" +
                "id='" + id + '\'' +
                '}';
    }

    /**
     * Checks if this Region is equal to another object.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Region region = (Region) o;
        return id.equals(region.id);
    }

    /**
     * Returns the hash code of this Region.
     *
     * @return the hash code of this Region
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}