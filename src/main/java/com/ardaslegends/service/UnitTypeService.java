package com.ardaslegends.service;

import com.ardaslegends.domain.UnitType;
import com.ardaslegends.repository.unittype.UnitTypeRepository;
import com.ardaslegends.service.exceptions.logic.units.UnitServiceException;
import com.ardaslegends.service.utils.ServiceUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Service class for managing unit types.
 * <p>
 * This service provides methods to interact with the {@link UnitTypeRepository}
 * to perform CRUD operations on {@link UnitType} entities.
 * </p>
 */
@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class UnitTypeService extends AbstractService<UnitType, UnitTypeRepository> {

    private final UnitTypeRepository unitTypeRepository;

    /**
     * Retrieves a unit type by its name.
     *
     * @param name the name of the unit type.
     * @return the unit type with the specified name.
     * @throws UnitServiceException     if no unit type with the specified name is found.
     * @throws NullPointerException     if the name is null.
     * @throws IllegalArgumentException if the name is blank.
     */
    public UnitType getUnitTypeByName(String name) {
        log.debug("Getting UnitType with name [{}]", name);

        Objects.requireNonNull(name);
        ServiceUtils.checkBlankString(name, "name");

        log.debug("Fetching unit with name [{}]", name);
        Optional<UnitType> fetchedUnitType = secureFind(name, unitTypeRepository::findById);

        if (fetchedUnitType.isEmpty()) {
            log.warn("No unitType found with name [{}]", name);
            throw UnitServiceException.unitNotFound(name);
        }

        log.info("Successfully returning Unit with name [{}]", fetchedUnitType.get().getUnitName());
        return fetchedUnitType.get();
    }

    /**
     * Retrieves a list of unit types by their faction names.
     *
     * @param factions a list of faction names.
     * @return a list of unit types associated with the specified faction names.
     * @throws NullPointerException if the factions list is null.
     */
    public List<UnitType> getByFactionNames(List<String> factions) {
        log.debug("Getting unitTypes by faction names [{}]", StringUtils.join(factions, ", "));
        Objects.requireNonNull(factions, "getByFactionNames factions were null!");

        val unitTypes = unitTypeRepository.queryByFactionNames(factions);

        log.info("Found unitTypes, [{}]", StringUtils.join(unitTypes, ", "));
        return unitTypes;
    }

    /**
     * Retrieves all unit types.
     *
     * @return a list of all unit types.
     */
    public List<UnitType> getAll() {
        log.debug("Getting all unitTypes");

        val all = unitTypeRepository.findAll();
        log.debug("Returning all [{}] unitTypes", all.size());

        return all;
    }
}