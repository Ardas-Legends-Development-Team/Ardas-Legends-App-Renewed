package com.ardaslegends.repository.rpchar;

import com.ardaslegends.domain.RPChar;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

/**
 * Custom repository interface for managing {@link RPChar} entities.
 */
public interface RpcharRepositoryCustom {
    Slice<RPChar> queryAll(Pageable pageable);

    List<RPChar> findRpCharsByNames(String[] names);
}
