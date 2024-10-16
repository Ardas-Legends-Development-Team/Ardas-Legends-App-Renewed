package com.ardaslegends.service.dto.applications;

import com.ardaslegends.domain.Coordinate;
import com.ardaslegends.domain.claimbuilds.ClaimBuildType;
import com.ardaslegends.domain.claimbuilds.SpecialBuilding;
import com.ardaslegends.service.dto.player.DiscordIdDto;
import com.ardaslegends.service.dto.productionSite.ProductionSiteDto;

public record CreateClaimbuildApplicationDto(DiscordIdDto applicant, String claimbuildName, String regionId,
                                             ClaimBuildType type, String factionNameOwnedBy, Coordinate coordinate,
                                             ProductionSiteDto[] productionSites, SpecialBuilding[] specialBuildings,
                                             String traders,
                                             String siege, String houses, String[] builtBy) {
}
