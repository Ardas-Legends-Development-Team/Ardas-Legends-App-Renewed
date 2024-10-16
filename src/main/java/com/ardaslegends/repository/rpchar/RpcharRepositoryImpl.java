package com.ardaslegends.repository.rpchar;

import com.ardaslegends.domain.QRPChar;
import com.ardaslegends.domain.RPChar;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Custom repository implementation for managing {@link RPChar} entities.
 * <p>
 * This implementation provides custom query methods for {@link RPChar} entities.
 * </p>
 */
@Slf4j
public class RpcharRepositoryImpl extends QuerydslRepositorySupport implements RpcharRepositoryCustom {

    /**
     * Constructs a new {@link RpcharRepositoryImpl}.
     */
    public RpcharRepositoryImpl() {
        super(RPChar.class);
    }

    /**
     * Queries all {@link RPChar} entities with pagination support.
     *
     * @param pageable the pagination information.
     * @return a {@link Slice} of {@link RPChar} entities.
     * @throws NullPointerException if the pageable parameter is null.
     */
    @Override
    public Slice<RPChar> queryAll(Pageable pageable) {
        Objects.requireNonNull(pageable, "Pageable must not be null!");
        QRPChar qRpchar = QRPChar.rPChar;

        val result = from(qRpchar)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchResults();

        val totalCount = result.getTotal();
        val rpchars = result.getResults();

        return new SliceImpl<>(rpchars, pageable, (pageable.getOffset() < totalCount));
    }

    /**
     * Finds {@link RPChar} entities by their names.
     *
     * @param names an array of names to search for.
     * @return a list of {@link RPChar} entities with the specified names.
     * @throws NullPointerException if the names array is null.
     */
    @Override
    public List<RPChar> findRpCharsByNames(String[] names) {
        log.debug("Querying rpchars by names: {}", Arrays.toString(names));
        Objects.requireNonNull(names, "Names must not be null");
        QRPChar qRpChar = QRPChar.rPChar;

        log.trace("Executing query");
        val fetchedRpChars = from(qRpChar)
                .where(qRpChar.name.in(names))
                .stream().toList();

        log.debug("Queried rpchars: [{}]", fetchedRpChars);
        return fetchedRpChars;
    }
}