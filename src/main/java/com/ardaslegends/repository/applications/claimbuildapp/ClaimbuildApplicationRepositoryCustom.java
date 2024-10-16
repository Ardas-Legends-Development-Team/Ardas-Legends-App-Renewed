package com.ardaslegends.repository.applications.claimbuildapp;

import com.ardaslegends.domain.applications.ApplicationState;
import com.ardaslegends.domain.applications.ClaimbuildApplication;

import java.util.Optional;
import java.util.Set;


/**
 * Custom repository interface for managing {@link ClaimbuildApplication} entities.
 * <p>
 * This interface provides custom query methods for {@link ClaimbuildApplication} entities.
 * </p>
 */
public interface ClaimbuildApplicationRepositoryCustom {
    ClaimbuildApplication queryById(long id);

    Set<ClaimbuildApplication> queryAllByState(ApplicationState state);

    ClaimbuildApplication queryByNameIgnoreCaseAndState(String claimbuildName, ApplicationState state);

    Optional<ClaimbuildApplication> queryByNameIgnoreCaseAndStateOptional(String claimbuildName, ApplicationState state);

    boolean existsByNameIgnoreCaseAndState(String claimbuildName, ApplicationState state);
}
