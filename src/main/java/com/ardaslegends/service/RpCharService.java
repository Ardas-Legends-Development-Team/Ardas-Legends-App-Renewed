package com.ardaslegends.service;

import com.ardaslegends.domain.Player;
import com.ardaslegends.domain.RPChar;
import com.ardaslegends.domain.claimbuilds.ClaimBuild;
import com.ardaslegends.repository.claimbuild.ClaimbuildRepository;
import com.ardaslegends.repository.rpchar.RpcharRepository;
import com.ardaslegends.service.dto.player.rpchar.StationRpCharDto;
import com.ardaslegends.service.dto.player.rpchar.UnstationRpCharDto;
import com.ardaslegends.service.exceptions.logic.claimbuild.ClaimBuildServiceException;
import com.ardaslegends.service.exceptions.logic.rpchar.RpCharServiceException;
import com.ardaslegends.service.utils.ServiceUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Service class for managing roleplay characters (RPChars).
 * <p>
 * This service provides methods to interact with the {@link RpcharRepository}
 * to perform CRUD operations on {@link RPChar} entities.
 * </p>
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RpCharService extends AbstractService<RPChar, RpcharRepository> {

    private final RpcharRepository rpcharRepository;
    private final PlayerService playerService;
    private final ClaimbuildRepository claimBuildRepository;

    /**
     * Retrieves a slice of all roleplay characters (RPChars) with pagination.
     *
     * @param pageable the pagination information.
     * @return a slice of RPChars.
     */
    public Slice<RPChar> getAll(Pageable pageable) {
        log.info("Getting slice of rpchars with data [{}]", pageable);
        return rpcharRepository.queryAll(pageable);
    }

    /**
     * Retrieves a list of roleplay characters (RPChars) by their names.
     *
     * @param names an array of RPChar names.
     * @return a list of RPChars with the specified names.
     * @throws RpCharServiceException   if no RPChars with the specified names are found.
     * @throws NullPointerException     if the names array is null.
     * @throws IllegalArgumentException if any name in the array is blank.
     */
    public List<RPChar> getRpCharsByNames(String[] names) {
        log.debug("Getting RpChars with names [{}]", (Object) names);

        Objects.requireNonNull(names, "Names must not be null");
        Arrays.stream(names).forEach(str -> ServiceUtils.checkBlankString(str, "Name"));

        log.debug("Fetching RpChars with names [{}]", (Object) names);
        List<RPChar> fetchedChars = secureFind(names, rpcharRepository::findRpCharsByNames);

        if (fetchedChars.isEmpty()) {
            log.warn("No RpChars found with names [{}]", (Object) names);
            throw RpCharServiceException.noRpCharsFound(names);
        }

        log.debug("Successfully returning RPChars found with names [{}]", (Object) names);
        return fetchedChars;
    }

    /**
     * Retrieves a roleplay character (RPChar) by its name.
     *
     * @param name the name of the RPChar.
     * @return the RPChar with the specified name.
     * @throws RpCharServiceException if no RPChar with the specified name is found.
     */
    public RPChar getRpCharByName(@NonNull String name) {
        log.debug("Getting RpChar with name [{}]", name);

        log.debug("Fetching Rpchar with name [{}]", name);
        val character = rpcharRepository.findRpcharByName(name);

        if (character.isEmpty()) {
            log.warn("No RpChar found with name [{}]", name);
            throw RpCharServiceException.noRpCharFound(name);
        }

        log.debug("Successfully returning RpChar with name [{}]", name);
        return character.get();
    }

    /**
     * Saves a list of roleplay characters (RPChars).
     *
     * @param chars the list of RPChars to be saved.
     */
    public void saveRpChars(List<RPChar> chars) {
        log.debug("Saving chars [{}]", chars);
        secureSaveAll(chars, rpcharRepository);
    }

    /**
     * Stations a roleplay character (RPChar) at a specified claim build.
     *
     * @param dto the data transfer object containing the RPChar and claim build information.
     * @return the stationed RPChar.
     * @throws RpCharServiceException     if the RPChar is already stationed, not in the same region as the claim build, or the claim build is not in the same or allied faction.
     * @throws ClaimBuildServiceException if the claim build does not exist.
     */
    @Transactional(readOnly = false)
    public RPChar station(StationRpCharDto dto) {
        log.debug("Trying to station rpchar [{}] at [{}]", dto.characterName(), dto.claimbuildName());

        log.trace("Validating data");
        ServiceUtils.checkAllNulls(dto);
        ServiceUtils.checkAllBlanks(dto);

        log.trace("Fetching character instance");
        RPChar character = getRpCharByName(dto.characterName());

        log.trace("Fetching player instance");
        Player player = playerService.getPlayerByDiscordId(dto.executorDiscordId());

        log.trace("Fetching claimbuild)");
        Optional<ClaimBuild> optionalClaimBuild = secureFind(dto.claimbuildName(), claimBuildRepository::findClaimBuildByName);

        if (optionalClaimBuild.isEmpty()) {
            log.warn("Claimbuild with name [{}] does not exist in database", dto.claimbuildName());
            throw ClaimBuildServiceException.noCbWithName(dto.claimbuildName());
        }

        ClaimBuild claimBuild = optionalClaimBuild.get();

        log.debug("Check if character is already stationed");
        if (character.getStationedAt() != null) {
            log.warn("Character [{}] is already stationed at Claimbuild [{}]", character.getName(), claimBuild.getName());
            throw RpCharServiceException.characterAlreadyStationed(character.getName(), character.getStationedAt().getName());
        }

        log.debug("Check if character is in the same region as the claimbuild");
        if (!character.getCurrentRegion().equals(claimBuild.getRegion())) {
            log.warn("Character [{}] is not in the same region as the claimbuild [{}]", character.getName(), claimBuild.getName());
            throw RpCharServiceException.characterNotInSameRegion(character.getName(), claimBuild.getName());
        }

        // TODO: Check ally system
        log.debug("Checking if Claimbuild is in the same or an allied faction of the character");
        if (!claimBuild.getOwnedBy().equals(player.getFaction()) && !player.getFaction().getAllies().contains(claimBuild.getOwnedBy())) {
            log.warn("Claimbuild is not in the same or allied faction of the army");
            throw RpCharServiceException.claimbuildNotInTheSameOrAlliedFaction(character.getName(), claimBuild.getName());
        }

        character.setStationedAt(claimBuild);

        log.debug("Set stationed, performing persist");
        secureSave(character, rpcharRepository);

        log.info("Station Character Service Method for Character [{}] completed successfully", character);
        return character;
    }

    /**
     * Unstations a roleplay character (RPChar) from its current claim build.
     *
     * @param dto the data transfer object containing the RPChar information.
     * @return the unstationed RPChar.
     * @throws RpCharServiceException if the RPChar is not stationed at any claim build.
     */
    @Transactional(readOnly = false)
    public RPChar unstation(UnstationRpCharDto dto) {
        log.debug("Trying to unstation character with data: [{}]", dto);

        ServiceUtils.checkNulls(dto, List.of("executorDiscordId", "characterName"));
        ServiceUtils.checkBlanks(dto, List.of("executorDiscordId", "characterName"));

        log.trace("Fetching character instance");
        RPChar character = getRpCharByName(dto.characterName());

        if (character.getStationedAt() == null) {
            log.warn("Character [{}] is not stationed at any claimbuild, so cannot be unstationed!", character);
            throw RpCharServiceException.characterNotStationed(character.getName());
        }

        character.setStationedAt(null);

        log.debug("Unstationed character [{}], persisting", character);
        secureSave(character, rpcharRepository);

        log.info("Unstation Character Service Method for Character [{}] completed successfully", character);
        return character;
    }
}