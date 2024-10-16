package com.ardaslegends.service.applications;

import com.ardaslegends.domain.Player;
import com.ardaslegends.domain.applications.ApplicationState;
import com.ardaslegends.domain.applications.RoleplayApplication;
import com.ardaslegends.presentation.discord.config.BotProperties;
import com.ardaslegends.repository.applications.RoleplayApplicationRepository;
import com.ardaslegends.repository.faction.FactionRepository;
import com.ardaslegends.repository.player.PlayerRepository;
import com.ardaslegends.service.AbstractService;
import com.ardaslegends.service.dto.applications.ApplicationVoteDto;
import com.ardaslegends.service.dto.applications.CreateRpApplicatonDto;
import com.ardaslegends.service.exceptions.logic.applications.RoleplayApplicationServiceException;
import com.ardaslegends.service.exceptions.logic.faction.FactionServiceException;
import com.ardaslegends.service.exceptions.logic.player.PlayerServiceException;
import com.ardaslegends.service.utils.ServiceUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Service class for managing roleplay applications.
 * This class provides methods to create, find, and manage roleplay applications.
 */
@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class RoleplayApplicationService extends AbstractService<RoleplayApplication, RoleplayApplicationRepository> {

    private final RoleplayApplicationRepository rpRepository;
    private final FactionRepository factionRepository;
    private final PlayerRepository playerRepository;
    private final BotProperties botProperties;
    private final UrlValidator urlValidator;
    private final Clock clock;

    /**
     * Fetches a slice of all roleplay applications.
     *
     * @param pageable the pagination information
     * @return a slice of all roleplay applications
     */
    public Slice<RoleplayApplication> findAll(Pageable pageable) {
        log.debug("Fetching slice of all rpApplications [{}]", pageable);
        Objects.requireNonNull(pageable);

        val applications = secureFind(pageable, rpRepository::findAll);
        log.debug("Fetched active rpApplications [{}]", applications);

        return applications;
    }

    /**
     * Fetches a slice of all active roleplay applications.
     *
     * @param pageable the pagination information
     * @return a slice of all active roleplay applications
     */
    public Slice<RoleplayApplication> findAllActive(Pageable pageable) {
        log.debug("Fetching slice of active roleplay applications [{}]", pageable);
        Objects.requireNonNull(pageable);

        val applications = secureFind(ApplicationState.OPEN, pageable, rpRepository::findByState);
        log.debug("Fetched active rpApplications [{}]", applications);

        return applications;
    }

    /**
     * Creates a new roleplay application.
     *
     * @param dto the data transfer object containing the application details
     * @return the created roleplay application
     * @throws RoleplayApplicationServiceException if any validation fails
     */
    @Transactional(readOnly = false)
    public RoleplayApplication createRpApplication(CreateRpApplicatonDto dto) {
        log.debug("Creating RpApplication with data [{}]", dto);
        Objects.requireNonNull(dto);

        ServiceUtils.checkAllNulls(dto);
        ServiceUtils.checkAllBlanks(dto);

        if (!urlValidator.isValid(dto.linkToLore())) {
            log.warn("Given url [{}] is not a valid URL", dto.linkToLore());
            throw RoleplayApplicationServiceException.urlIsNotValid(dto.linkToLore());
        }

        val optionalPlayer = secureFind(dto.discordId(), playerRepository::findByDiscordID);
        val optionalFaction = secureFind(dto.factionName(), factionRepository::findFactionByName);

        if (optionalFaction.isEmpty()) {
            log.warn("Faction with name [{}] was not found", dto.factionName());
            throw FactionServiceException.noFactionWithNameFoundAndAll(dto.factionName());
        }

        val faction = optionalFaction.get();

        if (optionalPlayer.isEmpty()) {
            log.warn("Player with discordId [{}] is required but was not found", dto.discordId());
            throw PlayerServiceException.noPlayerFound(dto.discordId());
        }

        val player = optionalPlayer.get();

        var application = new RoleplayApplication(player, faction, dto.characterName(), dto.characterTitle(), dto.characterReason(), dto.gear(), dto.pvpPreference(), dto.linkToLore());
        val applicationMessage = application.sendApplicationMessage(botProperties.getRpAppsChannel());

        try {
            application = secureSave(application, rpRepository);
        } catch (Exception e) {
            applicationMessage.delete("Failed to save application into Database therefore deleting application message in discord").join();
            throw e;
        }

        log.info("Successfully created rpApplication [{}]", application);
        return application;
    }

    /**
     * Adds an accept vote to a roleplay application.
     *
     * @param dto the data transfer object containing the vote details
     * @return the updated roleplay application
     */
    @Transactional(readOnly = false)
    public RoleplayApplication addAcceptVote(@NonNull ApplicationVoteDto dto) {
        log.debug("Adding accept Vote to roleplay application [{}]", dto);
        ServiceUtils.checkAllNulls(dto);

        var application = getRoleplayApplication(dto);
        val player = getPlayer(dto);

        application.addAcceptor(player);

        application = secureSave(application, rpRepository);
        log.info("Added accept vote to roleplay application [{}]", application);

        // Updates the embed so players can see the current votes
        application.updateApplicationMessage(botProperties.getRpAppsChannel());

        return application;
    }

    /**
     * Adds a decline vote to a roleplay application.
     *
     * @param dto the data transfer object containing the vote details
     * @return the updated roleplay application
     */
    @Transactional(readOnly = false)
    public RoleplayApplication addDeclineVote(@NonNull ApplicationVoteDto dto) {
        log.debug("Adding decline vote to roleplay application [{}]", dto);
        ServiceUtils.checkAllNulls(dto);

        var application = getRoleplayApplication(dto);
        val player = getPlayer(dto);

        application.addDecline(player);

        application = secureSave(application, rpRepository);
        log.info("Added decline vote to roleplay application [{}]", application);

        // Updates the embed so players can see the current votes
        application.updateApplicationMessage(botProperties.getRpAppsChannel());

        return application;
    }

    /**
     * Removes a vote from a roleplay application.
     *
     * @param dto the data transfer object containing the vote details
     * @return the updated roleplay application
     */
    @Transactional(readOnly = false)
    public RoleplayApplication removeVote(@NonNull ApplicationVoteDto dto) {
        log.debug("Removing vote from roleplay application [{}]", dto);
        ServiceUtils.checkAllNulls(dto);

        var application = getRoleplayApplication(dto);
        val player = getPlayer(dto);

        application.removeVote(player);

        application = secureSave(application, rpRepository);
        log.info("Removed vote from roleplay application [{}]", application);

        // Updates the embed so players can see the current votes
        application.updateApplicationMessage(botProperties.getRpAppsChannel());

        return application;
    }

    /**
     * Retrieves a player based on the provided vote data transfer object.
     *
     * @param dto the data transfer object containing the vote details
     * @return the player associated with the vote
     * @throws PlayerServiceException if the player is not found
     */
    private Player getPlayer(ApplicationVoteDto dto) {
        val optionalPlayer = secureFind(dto.discordId(), playerRepository::findByDiscordID);

        if (optionalPlayer.isEmpty()) {
            log.warn("No player found with discordId [{}]", dto.discordId());
            throw PlayerServiceException.noPlayerFound(dto.discordId());
        }
        return optionalPlayer.get();
    }

    /**
     * Retrieves a roleplay application based on the provided vote data transfer object.
     *
     * @param dto the data transfer object containing the vote details
     * @return the roleplay application associated with the vote
     * @throws RoleplayApplicationServiceException if the application is not found
     */
    private RoleplayApplication getRoleplayApplication(ApplicationVoteDto dto) {
        val optionalApplication = secureFind(dto.applicationId(), rpRepository::findById);

        if (optionalApplication.isEmpty()) {
            log.warn("No rp application found with id [{}]", dto.applicationId());
            throw RoleplayApplicationServiceException.noApplicationFoundWithId(dto.applicationId());
        }
        return optionalApplication.get();
    }

    /**
     * Handles open roleplay applications periodically.
     * This method is scheduled to run every 15 minutes.
     */
    @Async
    @Scheduled(cron = "0 */15 * ? * *")
    @Transactional(readOnly = false)
    public void handleOpenRoleplayApplications() {
        val startDateTime = OffsetDateTime.now(clock);
        long startNanos = System.nanoTime();
        log.debug("Starting scheduled handling of open roleplay applications - System time: [{}]", startDateTime);

        log.debug("Fetching all open roleplay-applications");
        int acceptedCount = 0;
        val openApplications = secureFind(ApplicationState.OPEN, rpRepository::findByState);
        for (RoleplayApplication application : openApplications) {
            if (application.acceptable()) {
                accept().accept(application);
                acceptedCount++; // Increment the counter
            }
        }

        long endNanos = System.nanoTime();
        log.info("Finished handling open roleplay-application [Time: {}, Amount accepted: {}]", TimeUnit.NANOSECONDS.toMillis(endNanos - startNanos), acceptedCount);
    }

    /**
     * Accepts a roleplay application.
     *
     * @return a consumer that accepts a roleplay application
     */
    public Consumer<RoleplayApplication> accept() {
        return application -> {
            val message = application.sendAcceptedMessage(botProperties.getRpAppsChannel());
            val character = application.accept();
            val player = application.getApplicant();
            player.addActiveRpChar(character);

            try {
                secureSave(application, rpRepository);
                log.info("Accepted rp application from [{}]", player.getIgn());
            } catch (Exception e) {
                message.delete("Failed to update rp application to accepted in database therefore deleting message");
                throw e;
            }
        };
    }
}