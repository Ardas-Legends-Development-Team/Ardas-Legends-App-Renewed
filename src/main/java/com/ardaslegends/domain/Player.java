package com.ardaslegends.domain;

import com.ardaslegends.domain.claimbuilds.ClaimBuild;
import com.ardaslegends.service.exceptions.logic.player.PlayerServiceException;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * Represents a player in the application.
 * This class is marked as {@link Entity} and is mapped to the "players" table in the database.
 * It implements {@link UserDetails} for Spring Security integration.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
@Entity
@Table(name = "players")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "ign")
public final class Player extends AbstractDomainObject implements UserDetails {

    /**
     * The unique identifier of the player.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The in-game name of the player. Must be unique and not null.
     */
    @Column(unique = true)
    @NotNull(message = "Player: IGN must not be null")
    private String ign;

    /**
     * The UUID of the player. Must be unique and not null.
     */
    @Column(unique = true)
    @NotNull(message = "Player: UUID must not be null")
    private String uuid;

    /**
     * The Discord ID of the player. Must be unique and not null.
     */
    @Column(name = "discord_id", unique = true)
    @NotNull(message = "Player: DiscordID must not be null")
    private String discordID;

    /**
     * The faction this player belongs to. Must not be null.
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "faction", foreignKey = @ForeignKey(name = "fk_player_faction"))
    @NotNull(message = "Player: Faction must not be null")
    private Faction faction;

    /**
     * The set of roleplay characters associated with this player.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    @Builder.Default
    private Set<RPChar> rpChars = new HashSet<>();

    /**
     * The list of claim builds built by this player.
     */
    @ManyToMany(mappedBy = "builtBy", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<ClaimBuild> builtClaimbuilds;

    /**
     * The set of roles assigned to this player.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "player_roles", joinColumns = @JoinColumn(name = "player_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    @Builder.Default
    private Set<Role> roles = new HashSet<>(1);

    /**
     * Constructs a new Player with the specified attributes.
     *
     * @param ign       the in-game name of the player
     * @param uuid      the UUID of the player
     * @param discordID the Discord ID of the player
     * @param faction   the faction this player belongs to
     * @param rpChar    the roleplay character associated with this player
     */
    public Player(String ign, String uuid, String discordID, Faction faction, RPChar rpChar) {
        Objects.requireNonNull(rpChar);

        this.ign = ign;
        this.uuid = uuid;
        this.discordID = discordID;
        this.faction = faction;
        this.rpChars = new HashSet<>(Set.of(rpChar));
        this.builtClaimbuilds = new ArrayList<>(1);
    }

    /**
     * Constructs a new Player with the specified attributes.
     *
     * @param ign       the in-game name of the player
     * @param uuid      the UUID of the player
     * @param discordID the Discord ID of the player
     * @param faction   the faction this player belongs to
     */
    public Player(String ign, String uuid, String discordID, Faction faction) {
        this.ign = ign;
        this.uuid = uuid;
        this.discordID = discordID;
        this.faction = faction;
        this.rpChars = new HashSet<>(1);
        this.builtClaimbuilds = new ArrayList<>(1);
        this.roles = new HashSet<>(1);
    }

    /**
     * Returns the active roleplay character of the player.
     *
     * @return an {@link Optional} containing the active roleplay character, or an empty {@link Optional} if none is active
     */
    public Optional<RPChar> getActiveCharacter() {
        return rpChars.stream()
                .filter(RPChar::getActive)
                .findFirst();
    }

    /**
     * Returns an unmodifiable set of roleplay characters associated with this player.
     *
     * @return an unmodifiable set of roleplay characters
     */
    public Set<RPChar> getRpChars() {
        return Collections.unmodifiableSet(rpChars);
    }

    /**
     * Adds a roleplay character to the player and sets it as active.
     *
     * @param rpChar the roleplay character to add
     * @throws PlayerServiceException if the roleplay character already exists
     */
    public void addActiveRpChar(RPChar rpChar) {
        Objects.requireNonNull(rpChar);
        if (this.rpChars == null) this.rpChars = new HashSet<>();

        this.rpChars.stream()
                .filter(RPChar::getActive)
                .forEach(this::clearRelations);

        if (!this.rpChars.add(rpChar)) {
            throw PlayerServiceException.rpcharAlreadyExists(rpChar.getName());
        }
        rpChar.setActive(true);
    }

    /**
     * Deletes the active roleplay character of the player.
     *
     * @return the deleted roleplay character
     * @throws PlayerServiceException if no active roleplay character is found
     */
    public RPChar deleteCharacter() {
        val character = getActiveCharacter().orElseThrow(PlayerServiceException::noRpChar);
        clearRelations(character);
        return character;
    }

    /**
     * Clears the relations of the specified roleplay character.
     *
     * @param rpchar the roleplay character whose relations are to be cleared
     */
    private void clearRelations(RPChar rpchar) {
        rpchar.setActive(false);

        Optional.ofNullable(rpchar.getBoundTo()).ifPresent(army -> {
            army.setBoundTo(null);
            rpchar.setBoundTo(null);

            army.getMovements().stream()
                    .filter(Movement::getIsCurrentlyActive)
                    .findFirst().ifPresent(movement -> {
                        // TODO: Decide on a way to handle active movements
                    });
        });

        // TODO: Handle active battles
    }

    /**
     * Returns an unmodifiable list of claim builds built by this player.
     *
     * @return an unmodifiable list of claim builds
     */
    public List<ClaimBuild> getBuiltClaimbuilds() {
        return Collections.unmodifiableList(builtClaimbuilds);
    }

    /**
     * Returns a string representation of this player.
     *
     * @return a string representation of this player
     */
    @Override
    public String toString() {
        return ign;
    }

    /**
     * Returns the authorities granted to the player.
     *
     * @return a collection of granted authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .toList();
    }

    /**
     * Adds a role to the player.
     *
     * @param role the role to add
     */
    public void addRole(Role role) {
        roles.add(role);
    }

    /**
     * Returns the password used to authenticate the player.
     *
     * @return the Discord ID of the player
     */
    @Override
    public String getPassword() {
        return discordID;
    }

    /**
     * Returns the username used to authenticate the player.
     *
     * @return the Discord ID of the player
     */
    @Override
    public String getUsername() {
        return discordID;
    }

    /**
     * Indicates whether the player's account has expired.
     *
     * @return true if the account is non-expired, false otherwise
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the player is locked or unlocked.
     *
     * @return true if the account is non-locked, false otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the player's credentials (password) have expired.
     *
     * @return true if the credentials are non-expired, false otherwise
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the player is enabled or disabled.
     *
     * @return true if the player is enabled, false otherwise
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}