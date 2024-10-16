package com.ardaslegends.repository.unittype;

import com.ardaslegends.domain.QFaction;
import com.ardaslegends.domain.QUnitType;
import com.ardaslegends.domain.UnitType;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

/**
 * Custom repository implementation for managing {@link UnitType} entities.
 * <p>
 * This implementation provides custom query methods for {@link UnitType} entities.
 * </p>
 */
@Slf4j
public class UnitTypeRepositoryImpl extends QuerydslRepositorySupport implements UnitTypeRepositoryCustom {

    /**
     * Constructs a new {@link UnitTypeRepositoryImpl}.
     */
    public UnitTypeRepositoryImpl() {
        super(UnitType.class);
    }

    /**
     * Queries {@link UnitType} entities by faction names.
     *
     * @param factionNames the list of faction names to query unit types for.
     * @return a list of {@link UnitType} entities usable by the specified factions.
     * @throws NullPointerException if the factionNames parameter is null.
     */
    @Override
    public List<UnitType> queryByFactionNames(List<String> factionNames) {
        log.debug("Querying UnitTypes by faction names: [{}]", StringUtils.join(factionNames, ", "));

        QUnitType qUnitType = QUnitType.unitType;
        QFaction qFaction = new QFaction("factions");

        val result = from(qUnitType)
                .innerJoin(qUnitType.usableBy, qFaction)
                .where(qFaction.name.in(factionNames))
                .stream().toList();

        log.debug("Queried [{}] unitTypes: [{}]", result.size(), StringUtils.join(result, ", "));
        return result;
    }
}