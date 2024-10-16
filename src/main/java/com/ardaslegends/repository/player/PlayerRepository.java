package com.ardaslegends.repository.player;

import com.ardaslegends.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link Player} entities.
 */
@Repository
public interface PlayerRepository extends JpaRepository<Player, String>, PlayerRepositoryCustom {

    Optional<Player> findPlayerByIgn(String ign);

    Optional<Player> findByDiscordID(String discordId);
}
