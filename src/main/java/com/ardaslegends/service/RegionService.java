package com.ardaslegends.service;

import com.ardaslegends.domain.Region;
import com.ardaslegends.repository.region.RegionRepository;
import com.ardaslegends.service.exceptions.logic.region.RegionServiceException;
import com.ardaslegends.service.utils.ServiceUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Service class for managing regions.
 * <p>
 * This service provides methods to interact with the {@link RegionRepository}
 * to perform CRUD operations on {@link Region} entities.
 * </p>
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class RegionService extends AbstractService<Region, RegionRepository> {

    private final RegionRepository regionRepository;

    /**
     * Resets the ownership status of all regions that have had their ownership changed.
     * <p>
     * This method sets the {@code hasOwnershipChanged} attribute of all regions to {@code false}
     * and persists the changes.
     * </p>
     *
     * @return a list of regions with updated ownership status.
     */
    @Transactional(readOnly = false)
    public List<Region> resetHasOwnership() {
        log.debug("Resetting all regions with hasOwnership set to true");

        log.trace("Fetching all regions with ownership true");
        var regions = regionRepository.findAllByHasOwnershipChangedTrue();

        log.trace("Changing every region to true with stream [size:{}]", regions.size());
        regions.stream().forEach(region -> {
            log.trace("Region [{}: ownership: {}] gets set to false", region.getId(), region.isHasOwnershipChanged());
            region.setHasOwnershipChanged(false);
        });

        log.debug("Persisting changed regions");
        regions = secureSaveAll(regions, regionRepository);

        log.info("Finished setting all to false!");
        return regions;
    }

    /**
     * Retrieves a region by its ID.
     *
     * @param regionId the ID of the region to retrieve.
     * @return the region with the specified ID.
     * @throws RegionServiceException   if no region with the specified ID is found.
     * @throws NullPointerException     if the region ID is null.
     * @throws IllegalArgumentException if the region ID is blank.
     */
    public Region getRegion(String regionId) {
        log.debug("Getting single region with id {}", regionId);

        log.debug("Checking if regionId is null or empty");
        Objects.requireNonNull(regionId, "Region ID must not be null");
        ServiceUtils.checkBlankString(regionId, "regionId");

        log.debug("Fetching region {} from repository", regionId);
        Optional<Region> foundRegion = regionRepository.findById(regionId);

        if (foundRegion.isEmpty()) {
            log.warn("Found no region with id [{}]", regionId);
            throw RegionServiceException.noRegionFound(regionId);
        }

        log.info("Fetched region with id [{}]!", regionId);
        return foundRegion.get();
    }

    /**
     * Retrieves all regions.
     *
     * @return a list of all regions.
     */
    @Transactional(readOnly = true)
    public List<Region> getAll() {
        return regionRepository.queryAll();
    }
}