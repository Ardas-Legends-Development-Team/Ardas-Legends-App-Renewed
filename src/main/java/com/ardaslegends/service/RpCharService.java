package com.ardaslegends.service;

import com.ardaslegends.domain.Army;
import com.ardaslegends.domain.ClaimBuild;
import com.ardaslegends.domain.Player;
import com.ardaslegends.domain.RPChar;
import com.ardaslegends.repository.rpchar.RpcharRepository;
import com.ardaslegends.service.dto.army.StationArmyDto;
import com.ardaslegends.service.dto.army.UnstationDto;
import com.ardaslegends.service.exceptions.logic.army.ArmyServiceException;
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
    public RPChar station(StationArmyDto dto) {
        log.debug("Trying to station rpchar [{}] at [{}]", dto.armyName(), dto.claimbuildName());

        log.trace("Validating data");
        ServiceUtils.checkAllNulls(dto);
        ServiceUtils.checkAllBlanks(dto);

        log.trace("Fetching army instance");
        Army army = getArmyByName(dto.armyName());

        log.trace("Fetching player instance");
        Player player = playerService.getPlayerByDiscordId(dto.executorDiscordId());

        log.trace("Fetching claimbuild)");
        Optional<ClaimBuild> optionalClaimBuild = secureFind(dto.claimbuildName(), claimBuildRepository::findClaimBuildByName);

        if (optionalClaimBuild.isEmpty()) {
            log.warn("Claimbuild with name [{}] does not exist in database", dto.claimbuildName());
            throw ClaimBuildServiceException.noCbWithName(dto.claimbuildName());
        }

        ClaimBuild claimBuild = optionalClaimBuild.get();

        log.debug("Check if army is already stationed");
        if (army.getStationedAt() != null) {
            log.warn("Army [{}] is already stationed at Claimbuild [{}]", army.getName(), claimBuild.getName());
            throw ArmyServiceException.armyAlreadyStationed(army.getArmyType(), army.getName(), army.getStationedAt().getName());
        }

        // TODO: Check ally system
        log.debug("Checking if Claimbuild is in the same or an allied faction of the army");
        if (!claimBuild.getOwnedBy().equals(army.getFaction()) && !army.getFaction().getAllies().contains(claimBuild.getOwnedBy())) {
            log.warn("Claimbuild is not in the same or allied faction of the army");
            throw ArmyServiceException.claimbuildNotInTheSameOrAlliedFaction(army.getArmyType(), claimBuild.getName());
        }

        log.debug("Checking if executor is allowed to perform the movement");
        boolean isAllowed = ServiceUtils.boundLordLeaderPermission(player, army);

        log.debug("Is player [{}] allowed to perform station?: {}", player.getIgn(), isAllowed);
        if (!isAllowed) {
            log.warn("Player [{}] is not allowed to perform station", player.getIgn());
            throw ArmyServiceException.noPermissionToPerformThisAction();
        }

        army.setStationedAt(claimBuild);

        log.debug("Set stationed, performing persist");
        secureSave(army, armyRepository);

        log.info("Station Army Service Method for Army [{}] completed successfully");
        return army;
    }

    @Transactional(readOnly = false)
    public Army unstation(UnstationDto dto) {
        log.debug("Trying to unstation army with data: [{}]", dto);

        ServiceUtils.checkNulls(dto, List.of("executorDiscordId", "armyName"));
        ServiceUtils.checkBlanks(dto, List.of("executorDiscordId", "armyName"));

        log.trace("Fetching army instance");
        Army army = getArmyByName(dto.armyName());

        log.trace("Fetching player instance");
        Player player = playerService.getPlayerByDiscordId(dto.executorDiscordId());

        if (army.getStationedAt() == null) {
            log.warn("Army [{}] is not stationed at a cb, so cannot be unstationed!", army.toString());
            throw ArmyServiceException.armyNotStationed(army.getArmyType(), army.toString());
        }

        boolean isAllowed = ServiceUtils.boundLordLeaderPermission(player, army);

        if (!isAllowed) {
            log.warn("Player not does not have permission to perform unstation");
            throw ArmyServiceException.noPermissionToPerformThisAction();
        }

        log.debug("Player [{}] is allowed to perform unstation");

        army.setStationedAt(null);

        log.debug("Unstationed army [{}], persisting");
        secureSave(army, armyRepository);

        log.info("Unstation Army Service Method for Army [{}] completed successfully");
        return army;
    }
}
