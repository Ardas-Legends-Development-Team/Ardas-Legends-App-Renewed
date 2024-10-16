package com.ardaslegends.presentation.api.application;

import com.ardaslegends.domain.applications.RoleplayApplication;
import com.ardaslegends.presentation.AbstractRestController;
import com.ardaslegends.presentation.api.response.applications.RoleplayApplicationResponse;
import com.ardaslegends.service.applications.RoleplayApplicationService;
import com.ardaslegends.service.dto.applications.ApplicationVoteDto;
import com.ardaslegends.service.dto.applications.CreateRpApplicatonDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing Roleplay Applications.
 * <p>
 * This controller provides endpoints for creating, retrieving, and voting on Roleplay Applications.
 * </p>
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(RoleplayApplicationController.BASE_URL)
public class RoleplayApplicationController extends AbstractRestController {
    public static final String BASE_URL = "/api/applications/roleplay";
    private static final String FIND_ALL = "/all";
    private static final String FIND_ACTIVE = "/active";
    private static final String ADD_VOTE = "/vote/accept";
    private static final String REMOVE_VOTE = "/vote/remove";
    private static final String ADD_DECLINE_VOTE = "/vote/decline";

    private final RoleplayApplicationService rpService;

    /**
     * Creates a new Roleplay Application.
     *
     * @param applicationDto the data transfer object containing the details of the application to create.
     * @return the created {@link RoleplayApplicationResponse}.
     */
    @Operation(summary = "Create a Roleplay Application")
    @PostMapping
    public HttpEntity<RoleplayApplicationResponse> createRoleplayApplication(@RequestBody CreateRpApplicatonDto applicationDto) {
        log.debug("Incoming createRoleplayApplication Request: Data [{}]", applicationDto);

        val application = rpService.createRpApplication(applicationDto);

        return ResponseEntity.ok(new RoleplayApplicationResponse(application));
    }

    /**
     * Returns a slice of all Roleplay Applications.
     *
     * @param pageable the pagination information.
     * @return a slice of {@link RoleplayApplicationResponse} containing all applications.
     */
    @Operation(summary = "Returns a slice of ALL Roleplay Applications")
    @GetMapping(FIND_ALL)
    public HttpEntity<Slice<RoleplayApplicationResponse>> findAllSliced(Pageable pageable) {
        log.debug("Incoming findAllApplications Request: Data [{}]", pageable.toString());

        Slice<RoleplayApplication> originalSlice = rpService.findAll(pageable);
        Slice<RoleplayApplicationResponse> appsResponse = originalSlice.map(RoleplayApplicationResponse::new);

        return ResponseEntity.ok(appsResponse);
    }

    /**
     * Returns a slice of only active Roleplay Applications.
     *
     * @param pageable the pagination information.
     * @return a slice of {@link RoleplayApplicationResponse} containing only active applications.
     */
    @Operation(summary = "Returns a slice of only ACTIVE Roleplay Applications")
    @GetMapping(FIND_ACTIVE)
    public HttpEntity<Slice<RoleplayApplicationResponse>> findAllActiveAppsSliced(Pageable pageable) {
        log.debug("Incoming findAllActiveApplications Request: Data [{}]", pageable.toString());

        val roleplayAppSlice = rpService.findAllActive(pageable);
        val roleplayAppResponseSlice = roleplayAppSlice.map(RoleplayApplicationResponse::new);

        return ResponseEntity.ok(roleplayAppResponseSlice);
    }

    /**
     * Adds an upvote to an application. If the same staff member has downvoted this application, that downvote will be removed.
     *
     * @param voteDto the data transfer object containing the vote details.
     * @return the updated {@link RoleplayApplicationResponse}.
     */
    @Operation(summary = "Adds a upvote to an application, if the same staff member has downvoted this application then that downvote will be removed")
    @PatchMapping(ADD_VOTE)
    public HttpEntity<RoleplayApplicationResponse> addAcceptVote(ApplicationVoteDto voteDto) {
        log.debug("Incoming accept-vote to application Request: Data [{}]", voteDto);

        val application = rpService.addAcceptVote(voteDto);

        return ResponseEntity.ok(new RoleplayApplicationResponse(application));
    }

    /**
     * Adds a downvote to an application. If the same staff member has upvoted this application, that upvote will be removed.
     *
     * @param voteDto the data transfer object containing the vote details.
     * @return the updated {@link RoleplayApplicationResponse}.
     */
    @Operation(summary = "Adds a downvote to an application, if the same staff member has upvoted this application then that upvoted will be removed")
    @PatchMapping(ADD_DECLINE_VOTE)
    public HttpEntity<RoleplayApplicationResponse> addDeclineVote(ApplicationVoteDto voteDto) {
        log.debug("Incoming decline-vote to application Request: Data [{}]", voteDto);

        val application = rpService.addDeclineVote(voteDto);

        return ResponseEntity.ok(new RoleplayApplicationResponse(application));
    }

    /**
     * Removes a vote from an application.
     *
     * @param voteDto the data transfer object containing the vote details.
     * @return the updated {@link RoleplayApplicationResponse}.
     */
    @Operation(summary = "Removes a vote from an application")
    @PatchMapping(REMOVE_VOTE)
    public HttpEntity<RoleplayApplicationResponse> removeVoteFromApplication(ApplicationVoteDto voteDto) {
        log.debug("Incoming remove-vote from application Request: Data [{}]", voteDto);

        val application = rpService.removeVote(voteDto);

        return ResponseEntity.ok(new RoleplayApplicationResponse(application));
    }

}