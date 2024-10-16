package com.ardaslegends.presentation.api;

import com.ardaslegends.presentation.AbstractRestController;
import com.ardaslegends.presentation.api.response.productionsite.ProductionSiteResponse;
import com.ardaslegends.service.ProductionSiteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing ProductionSite entities.
 * <p>
 * This controller provides endpoints for retrieving ProductionSite entities.
 * </p>
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(ProductionSiteController.BASE_URL)
public class ProductionSiteController extends AbstractRestController {
    public static final String BASE_URL = "/api/productionsite";
    private static final String GET_ALL = "/all";

    private final ProductionSiteService productionSiteService;

    /**
     * Retrieves all ProductionSite entities.
     *
     * @return an array of {@link ProductionSiteResponse} containing all ProductionSite entities.
     */
    @GetMapping(GET_ALL)
    public HttpEntity<ProductionSiteResponse[]> getAll() {
        log.debug("Incoming getAll productionSites request");

        val productionSiteSet = productionSiteService.getAll().stream()
                .map(ProductionSiteResponse::new)
                .toArray(ProductionSiteResponse[]::new);

        return ResponseEntity.ok(productionSiteSet);
    }
}