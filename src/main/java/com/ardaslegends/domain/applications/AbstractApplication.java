package com.ardaslegends.domain.applications;

import com.ardaslegends.domain.AbstractEntity;
import com.ardaslegends.domain.Player;
import com.ardaslegends.domain.Role;
import com.ardaslegends.service.exceptions.logic.applications.ApplicationException;
import com.ardaslegends.service.exceptions.logic.applications.RoleplayApplicationServiceException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.net.URL;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Abstract base class for application entities.
 * This class provides common properties and methods for handling application states,
 * votes, and Discord message links.
 *
 * @param <T> the type of the application result
 */
@Getter
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class AbstractApplication<T> extends AbstractEntity {

    /**
     * The player who applied.
     */
    @ManyToOne
    @NotNull
    @JoinColumn(name = "player_id")
    protected Player applicant;

    /**
     * The state of the application.
     */
    @Getter
    @NotNull
    @Enumerated(EnumType.STRING)
    protected ApplicationState state;

    /**
     * The date and time when the application was submitted.
     */
    @NotNull
    @PastOrPresent
    private OffsetDateTime appliedAt;

    /**
     * The number of votes the application has received.
     */
    @Getter
    @NotNull
    private Short voteCount;

    /**
     * The date and time when the last vote was cast.
     */
    @NotNull
    private OffsetDateTime lastVoteAt;

    /**
     * The set of players who accepted the application.
     */
    @OneToMany
    private Set<Player> acceptedBy = new HashSet<>();

    /**
     * The set of players who declined the application.
     */
    @OneToMany
    private Set<Player> declinedBy = new HashSet<>();

    /**
     * The date and time when the application was resolved.
     */
    @PastOrPresent
    private OffsetDateTime resolvedAt;

    /**
     * The link to the Discord application message.
     */
    @Setter(value = AccessLevel.PROTECTED)
    private URL discordApplicationMessageLink;

    /**
     * The ID of the Discord application message.
     */
    @Setter(value = AccessLevel.PROTECTED)
    private Long discordApplicationMessageId;

    /**
     * The link to the Discord accepted message.
     */
    @Setter(value = AccessLevel.PROTECTED)
    private URL discordAcceptedMessageLink;

    /**
     * The ID of the Discord accepted message.
     */
    @Setter(value = AccessLevel.PROTECTED)
    private Long discordAcceptedMessageId;

    /**
     * Constructs a new AbstractApplication with the specified applicant.
     *
     * @param applicant the player who applied
     */
    protected AbstractApplication(Player applicant) {
        this.applicant = applicant;
        voteCount = 0;
        appliedAt = OffsetDateTime.now();
        lastVoteAt = OffsetDateTime.now();
        state = ApplicationState.OPEN;
        acceptedBy = HashSet.newHashSet(3);
    }

    /**
     * Checks if the vote was successful, otherwise throws an exception.
     *
     * @param player  the player who voted
     * @param success the success status of the vote
     * @throws RoleplayApplicationServiceException if the player has already voted
     */
    private static void isVoteSuccessfulElseThrow(Player player, boolean success) {
        if (!success) {
            val staffIgn = player.getIgn();
            log.warn("Player [{}] already added their vote to the application", staffIgn);
            throw RoleplayApplicationServiceException.playerAlreadyVoted(staffIgn);
        }
    }

    /**
     * Checks if the player is a staff member, otherwise throws an exception.
     *
     * @param player the player to check
     * @throws RoleplayApplicationServiceException if the player is not a staff member
     */
    private static void isStaffElseThrow(Player player) {
        if (Boolean.FALSE.equals(player.getRoles().contains(Role.ROLE_STAFF))) {
            log.warn("Player [{}] cannot vote because they are not staff", player.getIgn());
            throw RoleplayApplicationServiceException.playerIsNotStaff(player.getIgn());
        }
    }

    /**
     * Builds the application message embed.
     *
     * @return the application message embed
     */
    protected abstract EmbedBuilder buildApplicationMessage();

    /**
     * Sends the application message to the specified channel.
     *
     * @param channel the channel to send the message to
     * @return the sent message
     */
    public Message sendApplicationMessage(TextChannel channel) {
        val embed = buildApplicationMessage();
        val message = channel.sendMessage(embed).join();
        this.discordApplicationMessageLink = message.getLink();
        this.discordApplicationMessageId = message.getId();
        return message;
    }

    /**
     * Updates the application message in the specified channel.
     *
     * @param channel the channel containing the message to update
     */
    public void updateApplicationMessage(TextChannel channel) {
        val message = channel.getMessageById(this.discordApplicationMessageId).join();
        message.edit(buildApplicationMessage());
    }

    /**
     * Builds the accepted message embed.
     *
     * @return the accepted message embed
     */
    protected abstract EmbedBuilder buildAcceptedMessage();

    /**
     * Sends the accepted message to the specified channel.
     *
     * @param channel the channel to send the message to
     * @return the sent message
     */
    public Message sendAcceptedMessage(TextChannel channel) {
        val embed = buildAcceptedMessage();
        val message = channel.sendMessage(embed).join();
        this.discordAcceptedMessageLink = message.getLink();
        this.discordAcceptedMessageId = message.getId();
        return message;
    }

    /**
     * Returns an unmodifiable set of players who accepted the application.
     *
     * @return the set of players who accepted the application
     */
    public Set<Player> getAcceptedBy() {
        return Collections.unmodifiableSet(acceptedBy);
    }

    /**
     * Returns an unmodifiable set of players who declined the application.
     *
     * @return the set of players who declined the application
     */
    public Set<Player> getDeclinedBy() {
        return Collections.unmodifiableSet(declinedBy);
    }

    /**
     * Adds a player to the set of acceptors.
     *
     * @param player the player to add
     * @throws RoleplayApplicationServiceException if the player is not a staff member or has already voted
     */
    public void addAcceptor(Player player) {
        Objects.requireNonNull(player, "Player must not be null to vote");
        isStaffElseThrow(player);
        declinedBy.remove(player);
        val success = acceptedBy.add(player);
        isVoteSuccessfulElseThrow(player, success);
        voteCount = (short) acceptedBy.size();
        lastVoteAt = OffsetDateTime.now();
    }

    /**
     * Adds a player to the set of decliners.
     *
     * @param player the player to add
     * @throws RoleplayApplicationServiceException if the player is not a staff member or has already voted
     */
    public void addDecline(Player player) {
        Objects.requireNonNull(player, "Player must not be null to vote");
        isStaffElseThrow(player);
        acceptedBy.remove(player);
        val success = declinedBy.add(player);
        isVoteSuccessfulElseThrow(player, success);
        if (voteCount != acceptedBy.size()) {
            voteCount = (short) acceptedBy.size();
            lastVoteAt = OffsetDateTime.now();
        }
    }

    /**
     * Removes a player's vote.
     *
     * @param player the player whose vote to remove
     * @throws ApplicationException if the player has not voted
     */
    public void removeVote(Player player) {
        Objects.requireNonNull(player);
        if (acceptedBy.contains(player)) {
            acceptedBy.remove(player);
        } else if (declinedBy.contains(player)) {
            declinedBy.remove(player);
        } else {
            throw ApplicationException.noVoteNeededToBeRemoved(player.getIgn());
        }
    }

    /**
     * Checks if the application is acceptable.
     *
     * @return true if the application is acceptable, false otherwise
     */
    public boolean acceptable() {
        return declinedBy.isEmpty() && voteCount >= getRequiredVoteCount();
    }

    /**
     * Accepts the application and updates its state.
     *
     * @return the result of the application
     */
    public T accept() {
        resolvedAt = OffsetDateTime.now();
        state = ApplicationState.ACCEPTED;
        return finishApplication();
    }

    /**
     * Finishes the application process.
     *
     * @return the result of the application
     */
    protected abstract T finishApplication();

    /**
     * Returns the required vote count for the application to be accepted.
     *
     * @return the required vote count
     */
    protected abstract Short getRequiredVoteCount();
}