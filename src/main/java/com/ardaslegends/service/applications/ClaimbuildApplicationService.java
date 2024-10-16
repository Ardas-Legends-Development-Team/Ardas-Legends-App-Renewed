package com.ardaslegends.service.applications;

import com.ardaslegends.domain.applications.ApplicationState;
import com.ardaslegends.domain.applications.ClaimbuildApplication;
import com.ardaslegends.domain.applications.EmbeddedProductionSite;
import com.ardaslegends.presentation.discord.config.BotProperties;
import com.ardaslegends.repository.ProductionSiteRepository;
import com.ardaslegends.repository.applications.claimbuildapp.ClaimbuildApplicationRepository;
import com.ardaslegends.repository.claimbuild.ClaimbuildRepository;
import com.ardaslegends.repository.faction.FactionRepository;
import com.ardaslegends.repository.player.PlayerRepository;
import com.ardaslegends.repository.region.RegionRepository;
import com.ardaslegends.service.AbstractService;
import com.ardaslegends.service.dto.applications.ApplicationVoteDto;
import com.ardaslegends.service.dto.applications.CreateClaimbuildApplicationDto;
import com.ardaslegends.service.exceptions.logic.applications.ClaimbuildApplicationException;
import com.ardaslegends.service.utils.ServiceUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Service class for managing claim build applications.
 * This class provides methods to create, find, and manage claim build applications.
 */
@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class ClaimbuildApplicationService extends AbstractService<ClaimbuildApplication, ClaimbuildApplicationRepository> {

    private final ClaimbuildApplicationRepository cbAppRepository;
    private final ClaimbuildRepository claimBuildRepository;
    private final PlayerRepository playerRepository;
    private final FactionRepository factionRepository;
    private final RegionRepository regionRepository;
    private final ProductionSiteRepository productionSiteRepository;
    private final BotProperties botProperties;
    private final Clock clock;

    /**
     * Fetches a slice of all claim build applications.
     *
     * @param pageable the pagination information
     * @return a slice of all claim build applications
     */
    public Slice<ClaimbuildApplication> findAll(Pageable pageable) {
        log.debug("Fetching slice of all cbApplications[{}]", pageable);
        Objects.requireNonNull(pageable);

        val applications = secureFind(pageable, cbAppRepository::findAll);
        log.debug("Fetched active cbApplications [{}]", applications);

        return applications;
    }

    /**
     * Fetches a slice of all active claim build applications.
     *
     * @param pageable the pagination information
     * @return a slice of all active claim build applications
     */
    public Slice<ClaimbuildApplication> findAllActive(Pageable pageable) {
        log.debug("Fetching slice of active cbApplications [{}]", pageable);
        Objects.requireNonNull(pageable);

        val applications = secureFind(ApplicationState.OPEN, pageable, cbAppRepository::findByState);
        log.debug("Fetched active cbApplications [{}]", applications);

        return applications;
    }

    /**
     * Creates a new claim build application.
     *
     * @param dto the data transfer object containing the application details
     * @return the created claim build application
     * @throws ClaimbuildApplicationException if any validation fails
     */
    @Transactional(readOnly = false)
    public ClaimbuildApplication createClaimbuildApplication(CreateClaimbuildApplicationDto dto) {
        log.debug("Creating ClaimbuildApplication with data [{}]", dto);
        Objects.requireNonNull(dto);
        ServiceUtils.checkAllNulls(dto);

        log.debug("Check if claimbuild's name [{}] ends with 'starter hamlet'", dto.claimbuildName());
        if (dto.claimbuildName().toLowerCase().endsWith("starter hamlet")) {
            log.warn("Applied claimbuild name [{}] ends with 'starter hamlet'", dto.claimbuildName());
            throw ClaimbuildApplicationException.nameEndsWithStarterHamlet();
        }

        val applicantPlayer = playerRepository.queryByDiscordId(dto.applicant().discordId());

        // Check if CB with Name already exists, throw if so
        if (claimBuildRepository.existsByNameIgnoreCase(dto.claimbuildName())) {
            log.warn("Claimbuild with name [{}] already exists", dto.claimbuildName());
            throw ClaimbuildApplicationException.claimbuildWithNameAlreadyExists(dto.claimbuildName());
        }

        // Check if CBApp with Name already exists that is active, throw if so
        if (cbAppRepository.existsByNameIgnoreCaseAndState(dto.claimbuildName(), ApplicationState.OPEN)) {
            log.warn("Claimbuild Application with name [{}] already exists", dto.claimbuildName());
            throw ClaimbuildApplicationException.claimbuildApplicationWithNameAlreadyExists(dto.claimbuildName());
        }

        // Fetching all builders
        val foundPlayers = playerRepository.queryAllByIgns(dto.builtBy());
        // Iterating over initial discordId which should be present in foundPlayers and mapping which Ids have not been found.
        List<String> notFoundPlayers = Arrays.stream(dto.builtBy())
                .filter(ign -> foundPlayers.stream().noneMatch(player -> player.getIgn().equals(ign)))
                .toList();

        if (!notFoundPlayers.isEmpty()) {
            String playersNotFound = String.join(", ", notFoundPlayers);

            log.warn("ClaimbuildApplicationService: Failed to find following builders [{}]", playersNotFound);
            throw ClaimbuildApplicationException.buildersNotFound(playersNotFound);
        }

        val faction = factionRepository.queryByName(dto.factionNameOwnedBy());
        val region = regionRepository.queryById(dto.regionId());

        val productionSites = Arrays.stream(dto.productionSites())
                .filter(Objects::nonNull)
                .filter(siteDto -> Objects.nonNull(siteDto.type()) && Objects.nonNull(siteDto.resource()))
                .map(siteDto -> {
                    val productionSite = productionSiteRepository.queryByTypeAndResource(siteDto.type(), siteDto.resource());
                    return new EmbeddedProductionSite(productionSite, siteDto.count());
                })
                .collect(Collectors.toSet());

        // Building the application

        var application = new ClaimbuildApplication(
                applicantPlayer,
                dto.claimbuildName(),
                faction,
                region,
                dto.type(),
                dto.coordinate(),
                productionSites,
                Arrays.stream(dto.specialBuildings()).toList(),
                dto.traders(),
                dto.siege(),
                dto.houses(),
                foundPlayers);

        val applicationMessage = application.sendApplicationMessage(botProperties.getClaimbuildAppsChannel());

        try {
            application = secureSave(application, cbAppRepository);
        } catch (Exception e) {
            applicationMessage.delete("Failed to save application into Database therefore deleting application message in discord").join();
        }

        return application;
    }

    /**
     * Adds an accept vote to a claim build application.
     *
     * @param dto the data transfer object containing the vote details
     * @return the updated claim build application
     */
    @Transactional(readOnly = false)
    public ClaimbuildApplication addAcceptVote(ApplicationVoteDto dto) {
        log.debug("Adding Vote to  claimbuild application [{}]", dto);
        Objects.requireNonNull(dto);

        ServiceUtils.checkAllNulls(dto);

        var application = cbAppRepository.queryById(dto.applicationId());
        val player = playerRepository.queryByDiscordId(dto.discordId());

        application.addAcceptor(player);

        application = secureSave(application, cbAppRepository);
        log.info("Added vote to claimbuild application [{}]", application);

        // Updates the embed so players can see the current votes
        application.updateApplicationMessage(botProperties.getClaimbuildAppsChannel());

        return application;
    }

    /**
     * Adds a decline vote to a claim build application.
     *
     * @param dto the data transfer object containing the vote details
     * @return the updated claim build application
     */
    @Transactional(readOnly = false)
    public ClaimbuildApplication addDeclineVote(@NonNull ApplicationVoteDto dto) {
        log.debug("Adding decline vote to claimbuild application with data [{}]", dto);
        ServiceUtils.checkAllNulls(dto);

        var application = cbAppRepository.queryById(dto.applicationId());
        val player = playerRepository.queryByDiscordId(dto.discordId());

        application.addDecline(player);

        application = secureSave(application, cbAppRepository);
        log.info("Added decline vote to claimbuild application [{}]", application);

        // Updates the embed so players can see the current votes
        application.updateApplicationMessage(botProperties.getClaimbuildAppsChannel());

        return application;
    }

    /**
     * Removes a vote from a claim build application.
     *
     * @param dto the data transfer object containing the vote details
     * @return the updated claim build application
     */
    @Transactional(readOnly = false)
    public ClaimbuildApplication removeVote(ApplicationVoteDto dto) {
        log.debug("Removing vote from claimbuild application [{}]", dto);
        Objects.requireNonNull(dto);

        ServiceUtils.checkAllNulls(dto);

        var application = cbAppRepository.queryById(dto.applicationId());
        val player = playerRepository.queryByDiscordId(dto.discordId());

        application.removeVote(player);

        application = secureSave(application, cbAppRepository);
        log.info("Removed vote from claimbuild application [{}]", application);

        // Updates the embed so players can see the current votes
        application.updateApplicationMessage(botProperties.getClaimbuildAppsChannel());

        return application;
    }

    /**
     * Handles open claim build applications periodically.
     * This method is scheduled to run every 15 minutes.
     */
    @Async
    @Scheduled(cron = "0 */15 * ? * *")
    @Transactional(readOnly = false)
    public void handleOpenClaimbuildApplications() {
        val startDateTime = OffsetDateTime.now(clock);
        long startNanos = System.nanoTime();
        log.debug("Starting scheduled handling of open claimbuild applications - System time: [{}]", startDateTime);

        log.debug("Fetching all open roleplay-applications");
        int acceptedCount = 0; // Counter for accepted applications
        val openApplications = secureFind(ApplicationState.OPEN, cbAppRepository::queryAllByState);
        for (ClaimbuildApplication application : openApplications) {
            if (application.acceptable()) {
                accept(application);
                acceptedCount++; // Increment the counter
            }
        }

        long endNanos = System.nanoTime();
        log.info("Finished handling open claimbuild-application [Time: {}, Amount accepted: {}]", TimeUnit.NANOSECONDS.toMillis(endNanos - startNanos), acceptedCount);
    }

    /**
     * Accepts a claim build application.
     *
     * @param application the claim build application to accept
     */
    public void accept(ClaimbuildApplication application) {
        val message = application.sendAcceptedMessage(botProperties.getClaimbuildAppsChannel());
        val claimbuild = application.accept();
        val player = application.getApplicant();

        try {
            claimBuildRepository.save(claimbuild);
            secureSave(application, cbAppRepository);
            log.info("Accepted cb application from [{}]", player.getIgn());
        } catch (Exception e) {
            message.delete("Failed to update cb application to accepted in database therefore deleting message");
        }
    }
}