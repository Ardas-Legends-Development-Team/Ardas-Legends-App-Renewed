package com.ardaslegends.domain;

import com.ardaslegends.domain.applications.RoleplayApplication;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Represents a roleplay character in the application.
 * This class is marked as {@link Entity} and is mapped to the "rpchars" table in the database.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "rpchars")
public class RPChar extends AbstractEntity {

    /**
     * The player who owns this character.
     */
    @ManyToOne
    @JoinColumn(name = "owner_id", foreignKey = @ForeignKey(name = "fk_rpchars_owner"))
    private Player owner;

    /**
     * The unique name of the character.
     */
    @Column(unique = true)
    private String name;

    /**
     * The title of the character.
     */
    @Length(max = 25, message = "Title too long!")
    private String title;

    /**
     * The gear of the character.
     */
    private String gear;

    /**
     * Indicates whether the character is involved in PvP.
     */
    private Boolean pvp;

    /**
     * The region the character is currently in.
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "current_region", foreignKey = @ForeignKey(name = "fk_rpchar_current_region"))
    @NotNull(message = "RpChar: currentRegion must not be null")
    private Region currentRegion;

    /**
     * The army that is bound to this character.
     */
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "bound_to", foreignKey = @ForeignKey(name = "fk_rpchar_bound_to"))
    private Army boundTo;

    /**
     * The claim build where the character is stationed.
     */
    @ManyToOne
    @JoinColumn(name = "stationed_at", foreignKey = @ForeignKey(name = "fk_rpchar_stationed_at"))
    private ClaimBuild stationedAt;

    /**
     * The set of movements associated with this character.
     */
    @OneToMany(mappedBy = "rpChar")
    private Set<Movement> movements;

    /**
     * Indicates whether the character is injured.
     */
    private Boolean injured;

    /**
     * Indicates whether the character is healing.
     */
    private Boolean isHealing;

    /**
     * The time when the character started healing.
     */
    private OffsetDateTime startedHeal;

    /**
     * The time when the character's healing ends.
     */
    private OffsetDateTime healEnds;

    /**
     * The last time the character's healing was updated.
     */
    private OffsetDateTime healLastUpdatedAt;

    /**
     * The link to the character's lore.
     */
    private String linkToLore;

    /**
     * Indicates whether the character is active.
     */
    private Boolean active;

    /**
     * Constructs a new RPChar with the specified roleplay application.
     *
     * @param application the roleplay application
     */
    public RPChar(RoleplayApplication application) {
        this.owner = application.getApplicant();
        name = application.getCharacterName();
        title = application.getCharacterTitle();
        gear = application.getGear();
        pvp = application.getPvp();
        currentRegion = application.getFaction().getHomeRegion();
        movements = new HashSet<>();
        boundTo = null;
        injured = false;
        isHealing = false;
        startedHeal = null;
        healEnds = null;
        active = true;
        linkToLore = application.getLinkToLore();
    }

    /**
     * Constructs a new RPChar with the specified attributes.
     *
     * @param owner      the player who owns this character
     * @param name       the name of the character
     * @param title      the title of the character
     * @param gear       the gear of the character
     * @param pvp        indicates whether the character is involved in PvP
     * @param linkToLore the link to the character's lore
     */
    public RPChar(Player owner, String name, String title, String gear, Boolean pvp, String linkToLore) {
        this.owner = owner;
        this.name = name;
        this.title = title;
        this.gear = gear;
        this.pvp = pvp;
        this.currentRegion = owner.getFaction().getHomeRegion();
        this.movements = new HashSet<>();
        boundTo = null;
        injured = false;
        isHealing = false;
        startedHeal = null;
        healEnds = null;
        active = true;
        this.linkToLore = linkToLore;
    }

    /**
     * Injures the character and unbinds it from any army.
     */
    public void injure() {
        setInjured(true);
        Optional.ofNullable(boundTo).ifPresent(army -> {
            army.setBoundTo(null);
            setBoundTo(null);
        });
    }

    /**
     * Starts the healing process for the character.
     */
    public void startHealing() {
        OffsetDateTime now = OffsetDateTime.now();
        setIsHealing(true);
        setStartedHeal(now);
        setHealEnds(now.plusDays(2));
    }

    /**
     * Returns an unmodifiable set of movements associated with this character.
     *
     * @return an unmodifiable set of movements
     */
    public Set<Movement> getMovements() {
        return Collections.unmodifiableSet(movements);
    }

    /**
     * Returns a string representation of this character.
     *
     * @return a string representation of this character
     */
    @Override
    public String toString() {
        return name;
    }
}