package com.ardaslegends.repository.applications.claimbuildapp;

import com.ardaslegends.domain.applications.ApplicationState;
import com.ardaslegends.domain.applications.ClaimbuildApplication;
import com.ardaslegends.domain.applications.QClaimbuildApplication;
import com.ardaslegends.repository.exceptions.ClaimbuildApplicationRepositoryException;
import lombok.val;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.lang.NonNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Custom repository implementation for managing {@link ClaimbuildApplication} entities.
 * <p>
 * This implementation provides custom query methods for {@link ClaimbuildApplication} entities.
 * </p>
 */
public class ClaimbuildApplicationRepositoryImpl extends QuerydslRepositorySupport implements ClaimbuildApplicationRepositoryCustom {
    public ClaimbuildApplicationRepositoryImpl() {
        super(ClaimbuildApplication.class);
    }

    /**
     * Queries a {@link ClaimbuildApplication} by its ID.
     *
     * @param id the ID of the claim build application.
     * @return the {@link ClaimbuildApplication} with the specified ID.
     * @throws ClaimbuildApplicationRepositoryException if no application with the specified ID is found.
     */
    @Override
    public ClaimbuildApplication queryById(long id) {
        QClaimbuildApplication qApp = QClaimbuildApplication.claimbuildApplication;

        val fetchedCbApp = from(qApp)
                .where(qApp.id.eq(id))
                .fetchFirst();

        if (fetchedCbApp == null) {
            throw ClaimbuildApplicationRepositoryException.entityNotFound("id", String.valueOf(id));
        }

        return fetchedCbApp;
    }

    /**
     * Queries all {@link ClaimbuildApplication} entities by their state.
     *
     * @param state the state of the applications.
     * @return a set of {@link ClaimbuildApplication} entities with the specified state.
     * @throws NullPointerException if the state is null.
     */
    @Override
    public Set<ClaimbuildApplication> queryAllByState(ApplicationState state) {
        Objects.requireNonNull(state, "State must not be null");
        QClaimbuildApplication qApp = QClaimbuildApplication.claimbuildApplication;

        val fetchedApplications = from(qApp)
                .where(qApp.state.eq(state))
                .fetch();

        return new HashSet<>(fetchedApplications);
    }

    /**
     * Queries a {@link ClaimbuildApplication} by its name (case-insensitive) and state.
     *
     * @param claimbuildName the name of the claim build.
     * @param state          the state of the application.
     * @return the {@link ClaimbuildApplication} with the specified name and state.
     * @throws ClaimbuildApplicationRepositoryException if no application with the specified name and state is found.
     * @throws NullPointerException                     if the claim build name or state is null.
     */
    @Override
    public @NonNull ClaimbuildApplication queryByNameIgnoreCaseAndState(@NonNull String claimbuildName, @NonNull ApplicationState state) {
        val claimbuildApp = queryByNameIgnoreCaseAndStateOptional(claimbuildName, state);

        if (claimbuildApp.isEmpty()) {
            throw ClaimbuildApplicationRepositoryException
                    .entityNotFound("(claimbuildName, state)", "(" + claimbuildName + ", " + state.displayName + ")");
        }

        return claimbuildApp.get();
    }

    /**
     * Queries an {@link Optional} containing a {@link ClaimbuildApplication} by its name (case-insensitive) and state.
     *
     * @param claimbuildName the name of the claim build.
     * @param state          the state of the application.
     * @return an {@link Optional} containing the found {@link ClaimbuildApplication}, or empty if not found.
     * @throws NullPointerException if the claim build name or state is null.
     */
    @Override
    public Optional<ClaimbuildApplication> queryByNameIgnoreCaseAndStateOptional(String claimbuildName, ApplicationState state) {
        Objects.requireNonNull(claimbuildName);
        Objects.requireNonNull(state);
        val qclaimbuildApp = QClaimbuildApplication.claimbuildApplication;

        val claimbuildApp = from(qclaimbuildApp)
                .where(qclaimbuildApp.claimbuildName.equalsIgnoreCase(claimbuildName).and(qclaimbuildApp.state.eq(state)))
                .fetchFirst();

        return Optional.ofNullable(claimbuildApp);
    }

    /**
     * Checks if a {@link ClaimbuildApplication} exists by its name (case-insensitive) and state.
     *
     * @param claimbuildName the name of the claim build.
     * @param state          the state of the application.
     * @return true if a {@link ClaimbuildApplication} with the specified name and state exists, false otherwise.
     * @throws NullPointerException if the claim build name or state is null.
     */
    @Override
    public boolean existsByNameIgnoreCaseAndState(String claimbuildName, ApplicationState state) {
        return queryByNameIgnoreCaseAndStateOptional(claimbuildName, state).isPresent();
    }
}