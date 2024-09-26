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

@Getter
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class AbstractApplication<T> extends AbstractEntity {

    @ManyToOne
    @NotNull
    @JoinColumn(name = "player_id")
    protected Player applicant;
    @Getter
    @NotNull
    @Enumerated(EnumType.STRING)
    protected ApplicationState state;
    @NotNull
    @PastOrPresent
    private OffsetDateTime appliedAt;
    @Getter
    @NotNull
    private Short voteCount;
    @NotNull
    private OffsetDateTime lastVoteAt;
    @OneToMany
    private Set<Player> acceptedBy = new HashSet<>();
    @OneToMany
    private Set<Player> declinedBy = new HashSet<>();
    @PastOrPresent
    private OffsetDateTime resolvedAt;
    @Setter(value = AccessLevel.PROTECTED)
    private URL discordApplicationMessageLink;

    @Setter(value = AccessLevel.PROTECTED)
    private Long discordApplicationMessageId;

    @Setter(value = AccessLevel.PROTECTED)
    private URL discordAcceptedMessageLink;

    @Setter(value = AccessLevel.PROTECTED)
    private Long discordAcceptedMessageId;

    protected AbstractApplication(Player applicant) {
        this.applicant = applicant;
        voteCount = 0;
        appliedAt = OffsetDateTime.now();
        lastVoteAt = OffsetDateTime.now();
        state = ApplicationState.OPEN;

        acceptedBy = HashSet.newHashSet(3);
    }

    private static void isVoteSuccessfulElseThrow(Player player, boolean success) {
        if (!success) {
            val staffIgn = player.getIgn();
            log.warn("Player [{}] already added their vote to the application", staffIgn);
            throw RoleplayApplicationServiceException.playerAlreadyVoted(staffIgn);
        }
    }

    private static void isStaffElseThrow(Player player) {
        if (Boolean.FALSE.equals(player.getRoles().contains(Role.ROLE_STAFF))) {
            log.warn("Player [{}] cannot vote because they are not staff", player.getIgn());
            throw RoleplayApplicationServiceException.playerIsNotStaff(player.getIgn());
        }
    }

    protected abstract EmbedBuilder buildApplicationMessage();

    public Message sendApplicationMessage(TextChannel channel) {
        val embed = buildApplicationMessage();
        val message = channel.sendMessage(embed).join();
        this.discordApplicationMessageLink = message.getLink();
        this.discordApplicationMessageId = message.getId();
        return message;
    }

    public void updateApplicationMessage(TextChannel channel) {
        val message = channel.getMessageById(this.discordApplicationMessageId).join();
        message.edit(buildApplicationMessage());
    }

    protected abstract EmbedBuilder buildAcceptedMessage();

    public Message sendAcceptedMessage(TextChannel channel) {
        val embed = buildAcceptedMessage();
        val message = channel.sendMessage(embed).join();
        this.discordAcceptedMessageLink = message.getLink();
        this.discordAcceptedMessageId = message.getId();
        return message;
    }

    public Set<Player> getAcceptedBy() {
        return Collections.unmodifiableSet(acceptedBy);
    }

    public Set<Player> getDeclinedB() {
        return Collections.unmodifiableSet(declinedBy);
    }

    public void addAcceptor(Player player) {
        Objects.requireNonNull(player, "Player must not be null to vote");

        isStaffElseThrow(player);

        declinedBy.remove(player);
        val success = acceptedBy.add(player);

        isVoteSuccessfulElseThrow(player, success);
        voteCount = (short) acceptedBy.size();
        lastVoteAt = OffsetDateTime.now();
    }

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

    public boolean acceptable() {
        return declinedBy.isEmpty() && voteCount >= getRequiredVoteCount();
    }

    public T accept() {
        resolvedAt = OffsetDateTime.now();
        state = ApplicationState.ACCEPTED;
        return finishApplication();
    }

    protected abstract T finishApplication();

    protected abstract Short getRequiredVoteCount();
}
