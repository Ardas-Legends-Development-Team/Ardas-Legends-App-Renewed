package com.ardaslegends.repository.region;

import com.ardaslegends.domain.QRegion;
import com.ardaslegends.domain.Region;
import com.ardaslegends.repository.exceptions.RegionRepositoryException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Custom repository implementation for managing {@link Region} entities.
 * <p>
 * This implementation provides custom query methods for {@link Region} entities.
 * </p>
 */
@Slf4j
public class RegionRepositoryImpl extends QuerydslRepositorySupport implements RegionRepositoryCustom {

    /**
     * Constructs a new {@link RegionRepositoryImpl}.
     */
    public RegionRepositoryImpl() {
        super(Region.class);
    }

    /**
     * Queries all {@link Region} entities.
     *
     * @return a list of all {@link Region} entities.
     */
    @Override
    public List<Region> queryAll() {
        log.debug("Querying all Regions in the database");
        QRegion qRegion = QRegion.region;

        return from(qRegion)
                .orderBy(qRegion.id.asc())
                .stream()
                .sorted((o1, o2) -> convertRegionIdToInt(o1.getId()) - convertRegionIdToInt(o2.getId()))
                .collect(Collectors.toList());
    }

    /**
     * Queries a {@link Region} by its ID.
     *
     * @param id the ID of the region.
     * @return the {@link Region} with the specified ID.
     * @throws RegionRepositoryException if no region with the specified ID is found.
     * @throws NullPointerException      if the ID is null.
     */
    @Override
    public Region queryById(String id) {
        val fetchedRegion = queryByIdOptional(id);

        if (fetchedRegion.isEmpty()) {
            throw RegionRepositoryException.entityNotFound("id", id);
        }

        return fetchedRegion.get();
    }

    /**
     * Queries an {@link Optional} containing a {@link Region} by its ID.
     *
     * @param id the ID of the region.
     * @return an {@link Optional} containing the found {@link Region}, or empty if not found.
     * @throws NullPointerException if the ID is null.
     */
    @Override
    public Optional<Region> queryByIdOptional(String id) {
        Objects.requireNonNull(id);

        QRegion qRegion = QRegion.region;

        val fetchedRegion = from(qRegion)
                .where(qRegion.id.equalsIgnoreCase(id))
                .fetchFirst();

        return Optional.ofNullable(fetchedRegion);
    }

    /**
     * Converts a region ID to an integer for sorting purposes.
     *
     * @param regionId the ID of the region.
     * @return the integer representation of the region ID.
     */
    private int convertRegionIdToInt(String regionId) {
        return regionId.contains(".") ? Integer.parseInt(regionId.split("\\.")[0]) + 10000 : Integer.parseInt(regionId);
    }
}