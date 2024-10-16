package com.ardaslegends.domain.applications;

import com.ardaslegends.domain.Coordinate;
import com.ardaslegends.domain.Faction;
import com.ardaslegends.domain.Player;
import com.ardaslegends.domain.Region;
import com.ardaslegends.domain.claimbuilds.ClaimBuild;
import com.ardaslegends.domain.claimbuilds.ClaimBuildType;
import com.ardaslegends.domain.claimbuilds.ProductionClaimbuild;
import com.ardaslegends.domain.claimbuilds.SpecialBuilding;
import com.ardaslegends.presentation.discord.utils.ALColor;
import com.ardaslegends.presentation.discord.utils.FactionBanners;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a claim build application.
 * This class extends {@link AbstractApplication} and provides specific fields and methods for claim build applications.
 */
@Slf4j
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "claimbuild_apps")
public class ClaimbuildApplication extends AbstractApplication<ClaimBuild> {

    /**
     * The name of the claim build.
     */
    @NotBlank
    private String claimbuildName;

    /**
     * The faction that owns the claim build.
     */
    @ManyToOne
    @NotNull
    @JoinColumn(name = "owned_by_id", foreignKey = @ForeignKey(name = "fk_claimbuild_apps_owned_by_id"))
    private Faction ownedBy;

    /**
     * The region where the claim build is located.
     */
    @ManyToOne
    @NotNull
    @JoinColumn(name = "region_id", foreignKey = @ForeignKey(name = "fk_claimbuild_apps_region_id"))
    private Region region;

    /**
     * The type of the claim build.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private ClaimBuildType claimBuildType;

    /**
     * The coordinates of the claim build.
     */
    @NotNull
    private Coordinate coordinate;

    /**
     * The production sites associated with the claim build.
     */
    @ElementCollection(targetClass = EmbeddedProductionSite.class)
    @CollectionTable(name = "claimbuild_application_production_sites",
            joinColumns = @JoinColumn(name = "claimbuild_id", foreignKey = @ForeignKey(name = "fk_claimbuild_application_production_sites_")))
    private Set<EmbeddedProductionSite> productionSites = new HashSet<>();

    /**
     * The special buildings associated with the claim build.
     */
    @ElementCollection(targetClass = SpecialBuilding.class)
    @Enumerated(EnumType.STRING)
    private List<SpecialBuilding> specialBuildings = new ArrayList<>();

    /**
     * The traders associated with the claim build.
     */
    private String traders;

    /**
     * The siege information of the claim build.
     */
    private String siege;

    /**
     * The number of houses in the claim build.
     */
    private String numberOfHouses;

    /**
     * The players who built the claim build.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Player> builtBy = new HashSet<>();

    /**
     * Constructs a new ClaimbuildApplication.
     *
     * @param applicant        the player who applied
     * @param claimbuildName   the name of the claim build
     * @param ownedBy          the faction that owns the claim build
     * @param region           the region where the claim build is located
     * @param claimBuildType   the type of the claim build
     * @param coordinate       the coordinates of the claim build
     * @param productionSites  the production sites associated with the claim build
     * @param specialBuildings the special buildings associated with the claim build
     * @param traders          the traders associated with the claim build
     * @param siege            the siege information of the claim build
     * @param numberOfHouses   the number of houses in the claim build
     * @param builtBy          the players who built the claim build
     */
    public ClaimbuildApplication(Player applicant, String claimbuildName, Faction ownedBy, Region region, ClaimBuildType claimBuildType, Coordinate coordinate, Set<EmbeddedProductionSite> productionSites, List<SpecialBuilding> specialBuildings, String traders, String siege, String numberOfHouses, Set<Player> builtBy) {
        super(applicant);
        this.claimbuildName = claimbuildName;
        this.ownedBy = ownedBy;
        this.region = region;
        this.claimBuildType = claimBuildType;
        this.coordinate = coordinate;
        this.productionSites = productionSites;
        this.specialBuildings = specialBuildings;
        this.traders = traders;
        this.siege = siege;
        this.numberOfHouses = numberOfHouses;
        this.builtBy = builtBy;
    }

    /**
     * Builds the application message embed.
     *
     * @return the application message embed
     */
    @Override
    public EmbedBuilder buildApplicationMessage() {
        String builtByString = builtBy.stream()
                .map(Player::getIgn)
                .collect(Collectors.joining(", "));

        return new EmbedBuilder()
                .setTitle("Claimbuild Application")
                .setColor(ALColor.YELLOW)
                .addInlineField("Name", claimbuildName)
                .addInlineField("Faction", ownedBy.getName())
                .addInlineField("Region", region.getId())
                .addInlineField("Type", claimBuildType.getName())
                .addField("Production Sites", createProductionSiteString())
                .addField("Special Buildings", createSpecialBuildingsString())
                .addInlineField("Traders", traders)
                .addInlineField("Siege", siege)
                .addInlineField("Houses", numberOfHouses)
                .addInlineField("Coordinates", coordinate.toString())
                .addField("Built by", builtByString)
                .setThumbnail(FactionBanners.getBannerUrl(ownedBy.getName()))
                .addField("Upvoted By", getAcceptedBy().stream().map(Player::getIgn).collect(Collectors.joining(", ")))
                .addInlineField("Downvoted By", getDeclinedBy().stream().map(Player::getIgn).collect(Collectors.joining(", ")))
                .setTimestampToNow();
    }

    /**
     * Builds the accepted message embed.
     *
     * @return the accepted message embed
     */
    @Override
    public EmbedBuilder buildAcceptedMessage() {
        String builtByString = builtBy.stream()
                .map(Player::getIgn)
                .collect(Collectors.joining(", "));

        return new EmbedBuilder()
                .setTitle("%s %s has been accepted!".formatted(claimBuildType.getName(), claimbuildName))
                .addInlineField("Name", claimbuildName)
                .addInlineField("Faction", ownedBy.getName())
                .addInlineField("Region", region.getId())
                .addInlineField("Type", claimBuildType.getName())
                .addField("Production Sites", createProductionSiteString())
                .addField("Special Buildings", createSpecialBuildingsString())
                .addInlineField("Traders", traders)
                .addInlineField("Siege", siege)
                .addInlineField("Houses", numberOfHouses)
                .addInlineField("Coordinates", coordinate.toString())
                .addField("Built by", builtByString)
                .setThumbnail(FactionBanners.getBannerUrl(ownedBy.getName()))
                .setColor(ALColor.GREEN)
                .setTimestampToNow();
    }

    /**
     * Finishes the application process and creates a {@link ClaimBuild}.
     *
     * @return the created {@link ClaimBuild}
     * @throws RuntimeException if the application state is not accepted
     */
    @Override
    public ClaimBuild finishApplication() {
        if (state != ApplicationState.ACCEPTED) {
            throw new RuntimeException("Claimbuild Application not yet accepted!");
        }
        val cb = new ClaimBuild(claimbuildName, region, claimBuildType, ownedBy, coordinate, specialBuildings, traders, siege, numberOfHouses, builtBy);
        cb.setProductionSites(mapProductionSites(cb));
        return cb;
    }

    /**
     * Returns the required vote count for the application to be accepted.
     *
     * @return the required vote count
     */
    @Override
    protected Short getRequiredVoteCount() {
        return 2;
    }

    /**
     * Maps the production sites to {@link ProductionClaimbuild} instances.
     *
     * @param claimBuild the claim build to map the production sites to
     * @return the list of mapped {@link ProductionClaimbuild} instances
     * @throws NullPointerException if the claim build is null
     */
    public List<ProductionClaimbuild> mapProductionSites(ClaimBuild claimBuild) {
        Objects.requireNonNull(claimBuild);
        return productionSites.stream()
                .map(embeddedProductionSite -> new ProductionClaimbuild(embeddedProductionSite.getProductionSite(), claimBuild, embeddedProductionSite.getCount()))
                .toList();
    }

    /**
     * Creates a string representation of the production sites.
     *
     * @return the string representation of the production sites
     */
    public String createProductionSiteString() {
        log.debug("ProductionSiteList Count: {}", productionSites.size());
        StringBuilder prodString = new StringBuilder();
        productionSites.forEach(productionSite -> {
            String resource = productionSite.getProductionSite().getProducedResource().getResourceName();
            String type = productionSite.getProductionSite().getType().getName();
            int count = productionSite.getCount().intValue();
            prodString.append(count).append(" ").append(resource).append(" ").append(type).append("\n");
        });

        String returnProdString = prodString.toString();
        log.debug("CreateProductionSiteString: {}", returnProdString);

        return returnProdString.isBlank() ? "None" : returnProdString;
    }

    /**
     * Creates a string representation of the special buildings.
     *
     * @return the string representation of the special buildings
     */
    private String createSpecialBuildingsString() {
        StringBuilder specialString = new StringBuilder();

        Map<SpecialBuilding, Long> countedSpecialBuildings = specialBuildings.stream()
                .collect(Collectors.groupingBy(specialBuilding -> specialBuilding, Collectors.counting()));

        countedSpecialBuildings.forEach((specialBuilding, aLong) -> specialString.append(aLong).append(" ").append(specialBuilding.getName()).append(", "));

        String returnSpecialString = specialString.toString();

        return returnSpecialString.isBlank() ? "None" : returnSpecialString;
    }

}