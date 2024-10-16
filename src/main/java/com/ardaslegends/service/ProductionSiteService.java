package com.ardaslegends.service;

import com.ardaslegends.domain.claimbuilds.ProductionSite;
import com.ardaslegends.repository.ProductionSiteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Service class for managing production sites.
 * <p>
 * This service provides methods to interact with the {@link ProductionSiteRepository}
 * to perform CRUD operations on {@link ProductionSite} entities.
 * </p>
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ProductionSiteService {

    private final ProductionSiteRepository productionSiteRepository;

    /**
     * Retrieves all production sites.
     *
     * @return a set of all production sites.
     */
    public Set<ProductionSite> getAll() {
        return productionSiteRepository.queryAll();
    }
}