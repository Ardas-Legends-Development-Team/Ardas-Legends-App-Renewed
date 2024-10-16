package com.ardaslegends.service;

import com.ardaslegends.domain.ClaimBuild;
import com.ardaslegends.domain.Player;
import com.ardaslegends.domain.RPChar;
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

@Slf4j
@RequiredArgsConstructor

@Service
public class RpCharService extends AbstractService<RPChar, RpcharRepository> {

    private final RpcharRepository rpcharRepository;
    private final PlayerService playerService;
    private final ClaimbuildRepository claimBuildRepository;

    public Slice<RPChar> getAll(Pageable pageable) {
        log.info("Getting slice of rpchars with data [{}]", pageable);
        return rpcharRepository.queryAll(pageable);
    }

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

    public List<RPChar> saveRpChars(List<RPChar> chars) {
        log.debug("Saving chars [{}]", chars);
        return secureSaveAll(chars, rpcharRepository);
    }

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

        log.info("Station Character Service Method for Character [{}] completed successfully");
        return character;
    }

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

        log.debug("Unstationed character [{}], persisting");
        secureSave(character, rpcharRepository);

        log.info("Unstation Character Service Method for Character [{}] completed successfully");
        return character;
    }
}
