package com.ardaslegends.service;

import com.ardaslegends.domain.Faction;
import com.ardaslegends.domain.Player;
import com.ardaslegends.repository.faction.FactionRepository;
import com.ardaslegends.repository.player.PlayerRepository;
import com.ardaslegends.service.dto.UpdateFactionLeaderDto;
import com.ardaslegends.service.dto.faction.UpdateStockpileDto;
import com.ardaslegends.service.exceptions.logic.faction.FactionServiceException;
import com.ardaslegends.service.exceptions.logic.player.PlayerServiceException;
import com.ardaslegends.service.utils.ServiceUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service for handling faction operations.
 * <p>
 * This service provides methods to create, update, and retrieve factions, as well as to manage their leaders and stockpiles.
 * </p>
 */
@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class FactionService extends AbstractService<Faction, FactionRepository> {

    private final FactionRepository factionRepository;
    private final PlayerRepository playerRepository;

    /**
     * Retrieves a paginated list of factions.
     *
     * @param pageable The pagination information.
     * @return A {@link Page} of {@link Faction} objects.
     */
    public Page<Faction> getFactionsPaginated(Pageable pageable) {
        return secureFind(pageable, factionRepository::findAll);
    }

    /**
     * Adds to the stockpile of a faction based on the provided data.
     *
     * @param dto The data for updating the stockpile.
     * @return The updated {@link Faction} object.
     */
    @Transactional(readOnly = false)
    public Faction addToStockpile(UpdateStockpileDto dto) {
        log.debug("Updating stockpile of faction with data [{}]", dto);

        ServiceUtils.checkAllNulls(dto);
        ServiceUtils.checkBlanks(dto, List.of("factionName"));

        log.trace("Fetching faction with name [{}]", dto.factionName());
        Faction faction = getFactionByName(dto.factionName());
        log.trace("Fetched Faction [{}]", faction.getName());

        log.debug("Adding amount");
        faction.addFoodToStockpile(dto.amount());

        log.debug("Persisting faction [{}] with stockpile [{}]", faction.getName(), faction.getFoodStockpile());
        secureSave(faction, factionRepository);

        log.info("Returning faction [{}] with stockpile [{}]", faction.getName(), faction.getFoodStockpile());
        return faction;
    }

    /**
     * Removes from the stockpile of a faction based on the provided data.
     *
     * @param dto The data for updating the stockpile.
     * @return The updated {@link Faction} object.
     */
    @Transactional(readOnly = false)
    public Faction removeFromStockpile(UpdateStockpileDto dto) {
        log.debug("Updating stockpile of faction with data [{}]", dto);

        ServiceUtils.checkAllNulls(dto);
        ServiceUtils.checkBlanks(dto, List.of("factionName"));

        log.trace("Fetching faction with name [{}]", dto.factionName());
        Faction faction = getFactionByName(dto.factionName());
        log.trace("Fetched Faction [{}]", faction.getName());

        log.debug("Removing amount");
        faction.subtractFoodFromStockpile(dto.amount());

        log.debug("Persisting faction [{}] with stockpile [{}]", faction.getName(), faction.getFoodStockpile());
        secureSave(faction, factionRepository);

        log.info("Returning faction [{}] with stockpile [{}]", faction.getName(), faction.getFoodStockpile());
        return faction;
    }

    /**
     * Sets the leader of a faction based on the provided data.
     *
     * @param dto The data for updating the faction leader.
     * @return The updated {@link Faction} object.
     * @throws PlayerServiceException  if the player is not found or does not have an RpChar.
     * @throws FactionServiceException if the player is not in the same faction.
     */
    @Transactional(readOnly = false)
    public Faction setFactionLeader(UpdateFactionLeaderDto dto) {
        log.debug("Updating leader of faction [{}], discordId [{}]", dto.factionName(), dto.targetDiscordId());

        ServiceUtils.checkAllNulls(dto);
        ServiceUtils.checkAllBlanks(dto);

        log.trace("Fetching faction, dto:[{}]", dto.factionName());
        Faction faction = getFactionByName(dto.factionName());
        log.trace("Fetched Faction [{}]", faction.getName());

        log.trace("Fetching player, dto [{}]", dto.targetDiscordId());
        Optional<Player> fetchedPlayer = playerRepository.findByDiscordID(dto.targetDiscordId());
        if (fetchedPlayer.isEmpty()) {
            log.warn("Could not find a player with discord id [{}]", dto.targetDiscordId());
            throw PlayerServiceException.noPlayerFound(dto.targetDiscordId());
        }
        Player player = fetchedPlayer.get();
        log.trace("Fetched Player, IGN:[{}], DiscordId: [{}]", player.getIgn(), player.getDiscordID());

        log.debug("Fetched relevant data!");

        log.debug("Checking if player is in the same faction");
        if (!faction.equals(player.getFaction())) {
            log.warn("Player [ign:{}] is not in the same faction - target [{}]", player.getIgn(), faction.getName());
            throw FactionServiceException.factionLeaderMustBeOfSameFaction();
        }

        log.debug("Checking if player has an RpChar");
        val character = player.getActiveCharacter().orElseThrow(() -> {
            log.warn("Player [ign:{}] does not have an RpChar and cannot be leader", player.getIgn());
            return PlayerServiceException.playerHasNoRpchar();
        });

        log.debug("Player [ign:{}] has an rpchar [name:{}]", player.getIgn(), character.getName());

        String oldLeaderIgn = faction.getLeader() == null ? "No Leader" : faction.getLeader().getIgn();
        log.debug("Faction [{}] current leader [ign:{}], setting it to new player [ign:{}]", faction.getName(), oldLeaderIgn, player.getIgn());
        faction.setLeader(player);

        log.debug("Faction [{}] leader is set to [ign:{}]", faction.getName(), faction.getLeader().getIgn());
        log.trace("Persisting faction object");
        faction = factionRepository.save(faction);

        log.info("Persisted faction [{}] object with new leader [ign:{}]", faction.getName(), faction.getLeader().getIgn());
        return faction;
    }

    /**
     * Removes the leader of a faction.
     *
     * @param factionName The name of the faction.
     * @return The previous leader {@link Player} object.
     * @throws FactionServiceException if the faction does not have a leader.
     */
    @Transactional(readOnly = false)
    public Player removeFactionLeader(String factionName) {
        log.debug("RmFactionLeader: Removing leader of faction [{}]", factionName);
        Objects.requireNonNull(factionName, "Faction name must not be null!");

        log.trace("RmFactionLeader: Fetching faction with name [{}]", factionName);
        Faction factionRm = getFactionByName(factionName);

        if (factionRm.getLeader() == null) {
            log.warn("RmFactionLeader: Faction [{}] does not have a leader!", factionName);
            throw FactionServiceException.noFactionLeader(factionName, "Therefore the command cannot be executed.");
        }

        Player previousLeader = factionRm.getLeader();
        log.trace("RmFactionLeader: Faction Leader to be removed is [{}]", previousLeader.getIgn());
        factionRm.setLeader(null);

        log.trace("RmFactionLeader: removed leader, saving faction");
        secureSave(factionRm, factionRepository);

        log.info("RmFactionLeader: Successfully removed {} from his leader position in {}", previousLeader.getIgn(), factionRm.getName());
        return previousLeader;
    }

    /**
     * Retrieves a faction by its name.
     *
     * @param name The name of the faction.
     * @return The {@link Faction} object.
     * @throws IllegalArgumentException if the faction name is null or blank.
     * @throws FactionServiceException  if no faction with the specified name is found.
     */
    public Faction getFactionByName(String name) {
        log.debug("Fetching Faction with name [{}]", name);
        Objects.requireNonNull(name, "Faction name must not be nulL!");

        if (name.isBlank()) {
            log.warn("Faction name is blank");
            throw new IllegalArgumentException("Faction name must not be blank!");
        }
        Optional<Faction> fetchedFaction = secureFind(name, factionRepository::findFactionByName);

        if (fetchedFaction.isEmpty()) {
            log.warn("No faction found with name {}", name);
            // TODO: This is stupid as fuck, find other solution
            List<Faction> allFactions = factionRepository.findAll();
            String allFactionString = allFactions.stream().map(Faction::getName).collect(Collectors.joining(", "));
            throw FactionServiceException.noFactionWithNameFoundAndAll(name, allFactionString);
        }
        log.debug("Successfully fetched faction [{}]", fetchedFaction);

        return fetchedFaction.get();
    }

    /**
     * Sets the role ID of a faction.
     *
     * @param factionName The name of the faction.
     * @param roleId      The role ID to set.
     * @return The updated {@link Faction} object.
     * @throws FactionServiceException if the role ID is already used by another faction or if the faction does not exist.
     */
    @Transactional(readOnly = false)
    public Faction setFactionRoleId(String factionName, Long roleId) {
        log.debug("SetFactionRole: Setting roleId of faction [{}] to [{}]", factionName, roleId);
        Objects.requireNonNull(roleId, "RoleId must not be null");
        Objects.requireNonNull(factionName, "FactionName must not be null");

        log.trace("Checkign if roleId [{}] is already used", roleId);
        var fetchedFaction = secureFind(roleId, factionRepository::findFactionByFactionRoleId);

        if (fetchedFaction.isPresent()) {
            log.warn("RoleId [{}] is already used by Faction [{}]", roleId, fetchedFaction.get());
            throw FactionServiceException.roleIdAlreadyUsed(roleId, fetchedFaction.get().getName());
        }

        log.trace("Fetching faction with name [{}]", factionName);
        fetchedFaction = secureFind(factionName, factionRepository::findFactionByName);

        if (fetchedFaction.isEmpty()) {
            log.warn("Faction with name [{}] does not exist", factionName);
            List<Faction> allFactions = factionRepository.findAll();
            String allFactionString = allFactions.stream().map(Faction::getName).collect(Collectors.joining(", "));
            throw FactionServiceException.noFactionWithNameFoundAndAll(factionName, allFactionString);
        }

        Faction faction = fetchedFaction.get();
        log.debug("Found faction [{}], changing roleId", faction.getName());

        faction.setFactionRoleId(roleId);

        log.debug("Persisting Faction [{}] with roleId [{}]", faction.getName(), faction.getFactionRoleId());
        faction = secureSave(faction, factionRepository);

        log.debug("New IDDDDD [{}]", faction.getFactionRoleId());
        return faction;
    }

    /**
     * Saves a faction.
     *
     * @param faction The faction to save.
     * @return The saved {@link Faction} object.
     */
    public Faction save(Faction faction) {
        log.debug("Saving Faction [{}]", faction);

        secureSave(faction, factionRepository);

        log.debug("Successfully saved faction [{}]", faction);
        return faction;
    }
}
