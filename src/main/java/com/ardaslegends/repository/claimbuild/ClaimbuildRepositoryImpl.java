package com.ardaslegends.repository.claimbuild;

import com.ardaslegends.domain.ClaimBuild;
import com.ardaslegends.domain.Faction;
import com.ardaslegends.domain.QClaimBuild;
import com.ardaslegends.repository.exceptions.ClaimbuildRepositoryException;
import com.ardaslegends.repository.exceptions.RepositoryNullPointerException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Custom repository implementation for managing {@link ClaimBuild} entities.
 * <p>
 * This implementation provides custom query methods for {@link ClaimBuild} entities.
 * </p>
 */
@Slf4j
@Repository
public class ClaimbuildRepositoryImpl extends QuerydslRepositorySupport implements ClaimbuildRepositoryCustom {
    public ClaimbuildRepositoryImpl() {
        super(ClaimBuild.class);
    }

    /**
     * Queries a {@link ClaimBuild} by its name (case-insensitive).
     *
     * @param claimbuildName the name of the claim build.
     * @return the {@link ClaimBuild} with the specified name.
     * @throws ClaimbuildRepositoryException if no claim build with the specified name is found.
     * @throws NullPointerException          if the claim build name is null.
     */
    @Override
    public ClaimBuild queryByNameIgnoreCase(String claimbuildName) {
        val fetchedClaimbuild = queryByNameIgnoreCaseOptional(claimbuildName);

        if (fetchedClaimbuild.isEmpty()) {
            throw ClaimbuildRepositoryException.entityNotFound("claimbuildName", claimbuildName);
        }

        return fetchedClaimbuild.get();
    }

    /**
     * Queries an {@link Optional} containing a {@link ClaimBuild} by its name (case-insensitive).
     *
     * @param claimbuildName the name of the claim build.
     * @return an {@link Optional} containing the found {@link ClaimBuild}, or empty if not found.
     * @throws NullPointerException if the claim build name is null.
     */
    @Override
    public Optional<ClaimBuild> queryByNameIgnoreCaseOptional(String claimbuildName) {
        Objects.requireNonNull(claimbuildName, "Claimbuild Name must not be null!");
        QClaimBuild qClaimBuild = QClaimBuild.claimBuild;

        val fetchedClaimbuild = from(qClaimBuild)
                .where(qClaimBuild.name.equalsIgnoreCase(claimbuildName))
                .fetchFirst();

        return Optional.ofNullable(fetchedClaimbuild);
    }

    /**
     * Checks if a {@link ClaimBuild} exists by its name (case-insensitive).
     *
     * @param claimbuildName the name of the claim build.
     * @return true if a {@link ClaimBuild} with the specified name exists, false otherwise.
     * @throws NullPointerException if the claim build name is null.
     */
    @Override
    public boolean existsByNameIgnoreCase(String claimbuildName) {
        log.trace("Checking if a claimbuild with name '%s' already exists");
        return queryByNameIgnoreCaseOptional(claimbuildName).isPresent();
    }

    /**
     * Queries a list of {@link ClaimBuild} entities by their names.
     *
     * @param names an array of claim build names.
     * @return a list of {@link ClaimBuild} entities with the specified names.
     * @throws NullPointerException if the names array is null.
     */
    @Override
    public List<ClaimBuild> queryByNames(String[] names) {
        log.debug("Querying claimbuilds by names: {}", Arrays.toString(names));
        Objects.requireNonNull(names, "Names must not be null");
        QClaimBuild qClaimBuild = QClaimBuild.claimBuild;

        log.trace("Executing query");
        val fetchedClaimbuilds = from(qClaimBuild)
                .where(qClaimBuild.name.in(names))
                .stream().toList();

        log.debug("Queried claimbuilds: [{}]", fetchedClaimbuilds);

        return fetchedClaimbuilds;
    }

    /**
     * Queries a list of {@link ClaimBuild} entities by their faction.
     *
     * @param faction the faction of the claim builds.
     * @return a list of {@link ClaimBuild} entities associated with the specified faction.
     * @throws RepositoryNullPointerException if the faction is null.
     */
    @Override
    public List<ClaimBuild> queryByFaction(Faction faction) {
        log.debug("Querying claimbuilds of faction [{}]", faction);
        if (faction == null) {
            log.warn("Faction was null in findClaimBuildsByFaction!");
            throw RepositoryNullPointerException.queryMethodParameterWasNull("faction", "findClaimBuildsByFaction");
        }

        QClaimBuild qClaimBuild = QClaimBuild.claimBuild;

        val fetchedClaimbuilds = from(qClaimBuild)
                .where(qClaimBuild.ownedBy.name.eq(faction.getName()))
                .stream().toList();

        log.debug("Queried claimbuilds: [{}]", fetchedClaimbuilds);
        return fetchedClaimbuilds;
    }
}