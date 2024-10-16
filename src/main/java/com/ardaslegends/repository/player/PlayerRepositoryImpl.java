package com.ardaslegends.repository.player;

import com.ardaslegends.domain.Player;
import com.ardaslegends.domain.QPlayer;
import com.ardaslegends.domain.QRPChar;
import com.ardaslegends.repository.exceptions.PlayerRepositoryException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.lang.NonNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Custom repository implementation for managing {@link Player} entities.
 * <p>
 * This implementation provides custom query methods for {@link Player} entities.
 * </p>
 */
@Slf4j
public class PlayerRepositoryImpl extends QuerydslRepositorySupport implements PlayerRepositoryCustom {

    /**
     * Constructs a new {@link PlayerRepositoryImpl}.
     */
    public PlayerRepositoryImpl() {
        super(Player.class);
    }

    /**
     * Fetches a player object that corresponds with the given discordId.
     *
     * @param discordId the Discord ID which the queried player should have.
     * @return a non-null player object.
     * @throws NullPointerException      if the discordId is null.
     * @throws PlayerRepositoryException if no player was found with the given discordId.
     */
    @Override
    public @NonNull Player queryByDiscordId(String discordId) {
        Objects.requireNonNull(discordId, "DiscordId must not be null!");

        val qplayer = QPlayer.player;

        val fetchedPlayer = from(qplayer)
                .where(qplayer.discordID.eq(discordId))
                .fetchFirst();

        if (fetchedPlayer == null) {
            throw PlayerRepositoryException.entityNotFound("discordId", discordId);
        }
        return fetchedPlayer;
    }

    /**
     * Fetches a player object that corresponds with the given in-game name (IGN).
     *
     * @param ign the in-game name which the queried player should have.
     * @return a non-null player object.
     * @throws NullPointerException      if the ign is null.
     * @throws PlayerRepositoryException if no player was found with the given ign.
     */
    @Override
    public @NonNull Player queryByIgn(String ign) {
        Objects.requireNonNull(ign, "Ign must not be null!");

        val qplayer = QPlayer.player;

        val fetchedPlayer = from(qplayer)
                .where(qplayer.ign.eq(ign))
                .fetchFirst();

        if (fetchedPlayer == null) {
            throw PlayerRepositoryException.entityNotFound("ign", ign);
        }
        return fetchedPlayer;
    }

    /**
     * Fetches a set of players that correspond with the given array of Discord IDs.
     *
     * @param discordIds an array of Discord IDs, null values will be filtered out.
     * @return a set of players, size does not have to match discordIds size.
     * @throws NullPointerException if the discordIds array is null.
     */
    @Override
    public @NonNull Set<Player> queryAllByDiscordIds(@NonNull String[] discordIds) {
        Objects.requireNonNull(discordIds, "DiscordIds must not be null");
        val qplayer = QPlayer.player;

        val filteredNullsSet = Arrays.stream(discordIds)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        val fetchedPlayers = from(qplayer)
                .where(qplayer.discordID.in(filteredNullsSet))
                .fetch();

        return new HashSet<>(fetchedPlayers);
    }

    /**
     * Fetches a set of players that correspond with the given array of in-game names (IGNs).
     *
     * @param igns an array of in-game names, null values will be filtered out.
     * @return a set of players, size does not have to match igns size.
     * @throws NullPointerException if the igns array is null.
     */
    @Override
    public @NonNull Set<Player> queryAllByIgns(@NonNull String[] igns) {
        Objects.requireNonNull(igns, "igns must not be null");
        val qplayer = QPlayer.player;

        val filteredNullsSet = Arrays.stream(igns)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        val fetchedPlayers = from(qplayer)
                .where(qplayer.ign.in(filteredNullsSet))
                .fetch();

        return new HashSet<>(fetchedPlayers);
    }

    /**
     * Fetches an optional player object that corresponds with the given role-playing character (RPChar) name.
     *
     * @param name the name of the role-playing character.
     * @return an {@link Optional} containing the found player, or empty if not found.
     * @throws NullPointerException if the name is null.
     */
    @Override
    public Optional<Player> queryPlayerByRpChar(String name) {
        Objects.requireNonNull(name, "Name must not be null");

        val qPlayer = QPlayer.player;
        val qRpChar = QRPChar.rPChar;

        val joinedRpChars = new QRPChar("rpCharacters");

        val result = from(qPlayer)
                .innerJoin(qPlayer.rpChars, joinedRpChars)
                .where(joinedRpChars.name.equalsIgnoreCase(name))
                .fetchFirst();

        return Optional.ofNullable(result);
    }

    /**
     * Fetches a list of players that have healing role-playing characters (RPChars).
     *
     * @return a list of players with healing RPChars.
     */
    @Override
    public List<Player> queryPlayersWithHealingRpchars() {
        val qPlayer = QPlayer.player;

        val joinedRpChars = new QRPChar("rpCharacters");

        return from(qPlayer)
                .innerJoin(qPlayer.rpChars, joinedRpChars)
                .where(joinedRpChars.isHealing.isTrue())
                .fetch();
    }
}